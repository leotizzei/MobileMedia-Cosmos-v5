/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;




class PhotoListController extends AbstractController {

	private MIDlet midlet;

	private PhotoController photoController;

	private PhotoListScreen photoListScreen;

	public PhotoListController(MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;

	}

	// #ifdef includeSorting
	/**
	 * Sorts an int array using basic bubble sort
	 * 
	 * @param numbers the int array to sort
	 */
	private void bubbleSort(IImageData[] images) {
		System.out.print("Sorting by BubbleSort...");		
		for (int end = images.length; end > 1; end --) {
			for (int current = 0; current < end - 1; current ++) {
				if (images[current].getNumberOfViews() > images[current+1].getNumberOfViews()) {
					exchange(images, current, current+1);
				}
			}
		}
		System.out.println("done.");
	}
	// #endif

	// #ifdef includeSorting
	/**
	 * @param images
	 * @param pos1
	 * @param pos2
	 */
	private void exchange(IImageData[] images, int pos1, int pos2) {
		IImageData tmp = images[pos1];
		images[pos1] = images[pos2];
		images[pos2] = tmp;
	}
	// #endif





	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(java.lang.String)
	 */
	public boolean handleCommand(Command command) throws UnavailablePhotoAlbumException, ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		/** Case: Select PhotoAlbum to view **/
		if (label.equals("Select")) {
			// Get the name of the PhotoAlbum selected, and load image list from
			// RecordStore
			List down = (List) Display.getDisplay(midlet).getCurrent();
			ScreenSingleton.getInstance().setCurrentStoreName(down.getString(down.getSelectedIndex()));
			this.showImageList(getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN);
			return true;
		}

		return false;
	}

	

	/**
	 * Show the list of images in the record store
	 * TODO: Refactor - Move this to ImageList class
	 * @throws UnavailablePhotoAlbumException 
	 */
	protected void showImageList(String recordName, boolean sort, boolean favorite) throws UnavailablePhotoAlbumException {

		ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		
		if (recordName == null)
			recordName = getCurrentStoreName();

		/*begin - modified in MobileMedia-Cosmos-OO-v4*/
		IManager manager = ComponentFactory.createInstance();

		PhotoController photoController = this.getPhotoController();

		photoController.setNextController(this);
		System.out.println("[PhotoListController.showImageList] photoController="+photoController);

		PhotoListScreen photoListScreen = this.getPhotoListScreen();
		System.out.println("[PhotoListController.showImageList] photoListScreen="+photoListScreen);

		photoListScreen.setCommandListener(photoController);
		/*end - modified in MobileMedia-Cosmos-OO-v4*/


		//Command selectCommand = new Command("Open", Command.ITEM, 1);
		photoListScreen.initMenu();


		IImageData[] images = null;

		/*begin - modified in MobileMedia-Cosmos-OO-v4*/
		Object object = manager.getRequiredInterface("IFilesystem");
		System.out.println("object = "+object);
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		System.out.println("[PhotoListController.showImageList] filesystem="+filesystem);

		
			images = filesystem.getImages(recordName);
		
		/*end - modified in MobileMedia-Cosmos-OO-v4*/

		if (images==null) return;

		// #ifdef includeSorting
		// [EF] Check if sort is true (Add in the Scenario 02)
		if (sort) {
			this.bubbleSort(images);
		}
		// #endif

		//loop through array and add labels to list
		for (int i = 0; i < images.length; i++) {
			System.out.println("[PhotoListController.showImageList(..)]  images.length ="+images.length);
			if (images[i] != null) {
				//Add album name to menu list

				String imageLabel = images[i].getImageLabel();
				System.out.println("[PhotoListController.showImageList(..)] imageLabel = "+imageLabel+" favourite="+favorite);
				// #ifdef includeFavourites
				// [EF] Check if favorite is true (Add in the Scenario 03)
				if (favorite) {
					if (images[i].isFavorite())
						photoListScreen.append(imageLabel, null);
				}
				else 
					// #endif
					photoListScreen.append(imageLabel, null);

			}
		}

		this.setCurrentScreen(photoListScreen);
		//currentMenu = "list";

	}

	private PhotoController getPhotoController() {
		if( this.photoController == null){
			MIDlet midlet = this.getMidlet();
			this.photoController = new PhotoController( midlet );
		}
		return photoController;
	}

	private PhotoListScreen getPhotoListScreen() {

		this.photoListScreen = new PhotoListScreen();

		return photoListScreen;
	}

	public String getCurrentlySelectedImageName(){
		return photoController.getCurrentlySelectedImageName();
	}


}
