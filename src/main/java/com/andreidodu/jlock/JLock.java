package com.andreidodu.jlock;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class JLock {

	private static final String ALL_NOT_VALID_CHARACTERS = "[^a-zA-Z0-9\\.\\-]";

	private String applicationId;
	private FileLock fileLock;
	private FileChannel fileChannel;
	private RandomAccessFile randomAccessFile;
	private File file;
	private boolean firstCheckDone = false;

	public JLock(final String appId) {
		this.applicationId = appId.replaceAll(ALL_NOT_VALID_CHARACTERS, "_");
	}

	public boolean isLocked() {
		return this.isLocked(this.applicationId);
	}

	private boolean isLocked(String applicationId) {
		try {
			if (!firstCheckDone) {
				this.file = new File(applicationId + ".lock");
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
				throw new RuntimeException("It is locked!");
			}
			if (!this.firstCheckDone) {
				Runtime.getRuntime().addShutdownHook(new Thread(this::releaseLock));
			}
			this.firstCheckDone = true;
		} catch (RuntimeException | IOException e) {
			tryCloseFileChannel();
			tryCloseRandomAccessFile();
			return true;
		}
		return false;
	}

	public void releaseLock() {
		tryCloseFileLocker();
		tryCloseFileChannel();
		tryCloseRandomAccessFile();
		deleteLockFile();
	}

	private void deleteLockFile() {
		if (this.file != null) {
			this.file.delete();
		}
	}

	private void tryCloseFileLocker() {
		if (this.fileLock != null) {
			try {
				this.fileLock.release();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void tryCloseRandomAccessFile() {
		if (randomAccessFile != null) {
			try {
				randomAccessFile.close();
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}

	private void tryCloseFileChannel() {
		if (fileChannel != null) {
			try {
				fileChannel.close();
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}
