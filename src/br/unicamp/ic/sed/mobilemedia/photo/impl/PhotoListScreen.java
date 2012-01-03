/*
 * Created on Sep 13, 2004
 *
 */
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;


/**
 * @author trevor
 *
 * This screen shows a listing of all photos for a selected photo album.
 * This is the screen that contains most of the feature menu items. 
 * From this screen, a user can choose to view photos, add or delete photos,
 * send photos to other users etc.
 * 
 */
public class PhotoListScreen extends List {
	
	//Add the core application commands always
	public static final Command viewCommand = new Command("View", Command.ITEM, 1);
	public static final Command addCommand = new Command("Add", Command.ITEM, 1);
	public static final Command deleteCommand = new Command("Delete", Command.ITEM, 1);
	public static final Command backCommand = new Command("Back", Command.BACK, 0);
    
	// [EF] Added in the scenario 02 
	public static final Command editLabelCommand = new Command("Edit Label", Command.ITEM, 1);

	// #ifdef includeSorting
	public static final Command sortCommand = new Command("Sort by Views", Command.ITEM, 1);
	// #endif

	// #ifdef includeFavourites
	// [EF] Added in the scenario 03 
	public static final Command favoriteCommand = new Command("Set Favorite", Command.ITEM, 1);
	public static final Command viewFavoritesCommand = new Command("View Favorites", Command.ITEM, 1);
	// #endif
	
	/**
     * Constructor
     */
	public PhotoListScreen() {
		super("Choose Items", Choice.IMPLICIT);
	}
	
	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 */
	public PhotoListScreen(String arg0, int arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public PhotoListScreen(String arg0, int arg1, String[] arg2, Image[] arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Initialize the menu items for this screen
	 */
	public void initMenu() {
		
		//Add the core application commands always
		this.addCommand(viewCommand);
		this.addCommand(addCommand);
		this.addCommand(deleteCommand);
		this.addCommand(backCommand);

		// [EF] Added in the scenario 02 
		this.addCommand(editLabelCommand);

		// #ifdef includeSorting
		this.addCommand(sortCommand);
		// #endif

		// #ifdef includeFavourites
		// [EF] Added in the scenario 03 
		this.addCommand(favoriteCommand);
		this.addCommand(viewFavoritesCommand);
		// #endif
		
		//Add the optional feature menu items only if they are specified in 
		//the xxxBuild.properties file using the 'preprocessor.symbols' value
		
	
	}
	
}
