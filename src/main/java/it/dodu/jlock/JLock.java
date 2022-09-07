package it.dodu.jlock;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JLock {

	private String applicationId;
	private FileLock fileLock;
	private FileChannel fileChannel;
	private RandomAccessFile randomAccessFile;
	private File file;
	private boolean firstCheckDone = false;
	private boolean automaticUnlock = false;

	public JLock(final String appId, boolean automaticUnlock) {
		if (appId == null || appId.isEmpty()) {
			throw new JLockException("Input string is null or empty");
		}
		this.automaticUnlock = automaticUnlock;
		this.applicationId = StringUtils.normalize(appId);
	}

	public boolean isLocked() {
		return this.isLocked(this.applicationId);
	}

	public void releaseLock() {
		tryCloseFileLocker();
		tryCloseFileChannel();
		tryCloseRandomAccessFile();
		deleteLockFile();
	}

	private boolean isLocked(String applicationId) {
		try {
			if (!firstCheckDone) {

				String userHome = System.getProperty("user.home");
				String pathString = userHome + "/.jlock";
				Path path = Paths.get(pathString);
				if (!Files.exists(path)) {
					Files.createDirectories(path);
				}

				this.file = new File(pathString + "/" + applicationId + ".lock");
				FileUtils.touch(file);
				this.randomAccessFile = new RandomAccessFile(file, "rw");
				this.fileChannel = randomAccessFile.getChannel();
			}
		} catch (Exception e) {
			tryCloseFileChannel();
			tryCloseRandomAccessFile();
		}
		return tryToLock();
	}

	private boolean tryToLock() {
		try {
			this.fileLock = this.fileChannel.tryLock();
			if (null == this.fileLock) {
				throw new JLockException("It is locked!");
			}
			if (this.automaticUnlock && !this.firstCheckDone) {
				Runtime.getRuntime().addShutdownHook(new Thread(this::releaseLock));
			}
			this.firstCheckDone = true;
		} catch (JLockException | IOException e) {
			tryCloseFileChannel();
			tryCloseRandomAccessFile();
			return true;
		}
		return false;
	}

	private void deleteLockFile() {
		if (this.file != null && this.file.delete()) {
			this.file = null;
		}
	}

	private void tryCloseFileLocker() {
		if (this.fileLock != null) {
			try {
				this.fileLock.release();
				this.fileLock = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void tryCloseRandomAccessFile() {
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
				this.randomAccessFile = null;
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}

	private void tryCloseFileChannel() {
		if (fileChannel != null) {
			try {
				fileChannel.close();
				this.fileChannel = null;
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}
