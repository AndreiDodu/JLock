package com.andreidodu.jlock;

public class Utils {

	static String normalize(String appId) {
		return appId.replaceAll(Const.ALL_NOT_VALID_CHARACTERS, "_");
	}

}
