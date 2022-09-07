package it.dodu.jlock;

class StringUtils {

	static String normalize(String appId) {
		return appId.replaceAll(Const.ALL_NOT_VALID_CHARACTERS, "_");
	}

}
