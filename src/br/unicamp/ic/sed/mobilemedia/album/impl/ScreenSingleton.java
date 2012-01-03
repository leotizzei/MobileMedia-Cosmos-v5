
package br.unicamp.ic.sed.mobilemedia.album.impl;



class ScreenSingleton {
	
	private static ScreenSingleton instance;

	//Keep track of the navigation so we can go 'back' easily
	private String currentScreenName;
	
	//Keep track of the current photo album being viewed
	//Ie. name of the currently active J2ME record store
	private String currentStoreName = "My Photo Album";
	
	
	private ScreenSingleton() {
	}

	/**
	 * @return the instance
	 */
	public static ScreenSingleton getInstance() {
		if (instance == null) instance = new ScreenSingleton();
		return instance;
	}

	/**
	 * @param currentScreenName the currentScreenName to set
	 */
	public void setCurrentScreenName(String currentScreenName) {
		this.currentScreenName = currentScreenName;
	}

	/**
	 * @return the currentScreenName
	 */
	public String getCurrentScreenName() {
		return currentScreenName;
	}

	/**
	 * @param currentStoreName the currentStoreName to set
	 */
	public void setCurrentStoreName(String currentStoreName) {
		this.currentStoreName = currentStoreName;
	}

	/**
	 * @return the currentStoreName
	 */
	public String getCurrentStoreName() {
		return currentStoreName;
	}
}
