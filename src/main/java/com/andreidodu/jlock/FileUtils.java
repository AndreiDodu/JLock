package com.andreidodu.jlock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class FileUtils {

	static void touch(File file) throws IOException {
		if (!file.exists()) {
			new FileOutputStream(file).close();
		}
	}

}
