package com.andreidodu.jlock;

public class JLockUtils {

	static String normalize(String appId) {
		return appId.replaceAll(JLockConst.ALL_NOT_VALID_CHARACTERS, "_");
	}

}
