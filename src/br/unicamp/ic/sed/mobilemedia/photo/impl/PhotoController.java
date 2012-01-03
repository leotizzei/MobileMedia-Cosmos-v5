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
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobilePhone;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.ISms;




class PhotoController extends PhotoListController {

	private IImageData image;

	private MIDlet midlet;

	private NewLabelScreen screen;
	
	private String currentlySelectedImageName = "bug";

	public PhotoController (MIDlet midlet) {
		super( midlet );
		this.midlet = midlet;
	}

	private void editLabel() throws ImageNotFoundException, NullAlbumDataReference{
		//TODO print error message on the screen
		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		String selectedImageName = getSelectedImageName();
		image = filesystem.getImageInfo(selectedImageName);

		NewLabelScreen newLabelScreen = new NewLabelScreen("Edit Label Photo", NewLabelScreen.LABEL_PHOTO);
		newLabelScreen.setCommandListener(this);
		this.setScreen( newLabelScreen );
		setCurrentScreen( newLabelScreen );
		newLabelScreen = null;
		
		ScreenSingleton.getInstance().setCurrentScreenName( Constants.NEWLABEL_SCREEN );

	}



	/**
	 * @return the image
	 */
	private IImageData getImage() {
		return image;
	}

	private NewLabelScreen getScreen() {
		return screen;
	}

	/**
	 * Get the last selected image from the Photo List screen.
	 * TODO: This really only gets the selected List item. 
	 * So it is only an image name if you are on the PhotoList screen...
	 * Need to fix thisnextController
	 */
	private String getSelectedImageName() {
		List selected = (List) Display.getDisplay(midlet).getCurrent();
		if (selected == null)
			System.out.println("Current List from display is NULL!");
		String name = selected.getString(selected.getSelectedIndex());
		this.currentlySelectedImageName = name;
		System.out.println("====== EU: " + this + "ImageName: " + this.currentlySelectedImageName +" =====");
		return name;
	}

	/**
	 * TODO [EF] update this method or merge with method of super class.
	 * Go to the previous screen
	 * @throws UnavailablePhotoAlbumException 
	 */
	private boolean goToPreviousScreen() throws UnavailablePhotoAlbumException {
		System.out.println("<* PhotoController.goToPreviousScreen() *>");
		String currentScreenName = ScreenSingleton.getInstance().getCurrentScreenName();

		if (currentScreenName.equals(Constants.ALBUMLIST_SCREEN)) {
			System.out.println("Can't go back here...Should never reach this spot");
		} else if (currentScreenName.equals(Constants.IMAGE_SCREEN)) {		    
			//Go to the image list here, not the main screen...

			this.showImageList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		else if (currentScreenName.equals(Constants.ADDPHOTOTOALBUM_SCREEN)) {

			this.showImageList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}else if(currentScreenName.equals(Constants.IMAGELIST_SCREEN)){
			IManager manager = ComponentFactory.createInstance();
			IMobilePhone phone = (IMobilePhone) manager.getRequiredInterface("IMobilePhone");
			phone.goToPreviousScreen();
			return true;
		}else if(currentScreenName.equals(Constants.NEWLABEL_SCREEN )){
			this.showImageList(this.getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;
		}
		System.out.println("[PhotoController could not handle either BACK or CANCEL: goToPreviousScreen() returns false]");
		return false;
	}

	/**
	 * modified in MobileMedia-Cosmos-OO-v4
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException 
	 * @throws InvalidImageDataException 
	 * @throws br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException 
	 * @throws UnavailablePhotoAlbumException 
	 */
	public boolean handleCommand(Command command) throws ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException, UnavailablePhotoAlbumException  {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		IManager manager = (IManager) ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		//IPhoto photo = (IPhoto) manager.getRequiredInterface("IPhoto");

		if (label.equals("View")) {
			String selectedImageName = getSelectedImageName();
			showImage(selectedImageName);

			// #ifdef includeSorting
			// [EF] Added in the scenario 02

			IImageData image;

			
			image = filesystem.getImageInfo(selectedImageName);
			
			image.increaseNumberOfViews();
			this.updateImage(image);

			// #endif
			//System.out.println("<* BaseController.handleCommand() *> Image = " + selectedImageName + "; # views = " + image.getNumberOfViews());


			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGE_SCREEN);

			return true;

			/** Case: Add photo * */
		} else if (label.equals("Add")) {
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ADDPHOTOTOALBUM_SCREEN);
			String storeName = this.getCurrentStoreName();
			this.initAddPhotoToAlbum( storeName );
			return true;

			/** Case: Save Add photo * */
		} else if (label.equals("Save Photo")) {
			//TODO print message error on the screen
			
				AddPhotoToAlbum addPhotoToAlbum = (AddPhotoToAlbum) this.getCurrentScreen();
				String addedPhotoName = addPhotoToAlbum.getPhotoName();
				String addedPhotoPath = addPhotoToAlbum.getPath();
				filesystem.addNewPhotoToAlbum( addedPhotoName , addedPhotoPath , getCurrentStoreName() );
			
			/*getAlbumData().addNewPhotoToAlbum(((AddPhotoToAlbum) getCurrentScreen()).getPhotoName(), 
					((AddPhotoToAlbum) getCurrentScreen()).getPath(), getCurrentStoreName());*/
			return goToPreviousScreen();
			/** Case: Delete selected Photo from recordstore * */
		} else if (label.equals("Delete")) {
			String selectedImageName = getSelectedImageName();
			
			filesystem.deleteImage(selectedImageName, getCurrentStoreName());
			
			showImageList(getCurrentStoreName(), false, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
			return true;

			/** Case: Edit photo label
			 *  [EF] Added in the scenario 02 */
		} else if (label.equals("Edit Label")) {
			this.editLabel();
			return true;

			// #ifdef includeSorting
			/**
			 * Case: Sort photos by number of views 
			 * [EF] Added in the scenario 02 */
		} else if (label.equals("Sort by Views")) {
			showImageList(getCurrentStoreName(), true, false);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

			return true;
			// #endif

			// #ifdef includeFavourites
			/**
			 * Case: Set photo as favorite
			 * [EF] Added in the scenario 03 */
		} else if (label.equals("Set Favorite")) {
			String selectedImageName = getSelectedImageName();
			
				//ImageData image = getAlbumData().getImageInfo(selectedImageName);
				IImageData image = filesystem.getImageInfo(selectedImageName);
				image.toggleFavorite();
				updateImage(image);
				System.out.println("<* BaseController.handleCommand() *> Image = "+ selectedImageName + "; Favorite = " + image.isFavorite());
			
			return true;

			/**
			 * Case: View favorite photos 
			 * [EF] Added in the scenario 03 */
		} else if (label.equals("View Favorites")) {
			showImageList(getCurrentStoreName(), false, true);
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);

			return true;
			// #endif

			/** Case: Save new Photo Label */
		} else if (label.equals("Save new label")) {
			NewLabelScreen newLabelScreen = this.getScreen();
			String labelName = newLabelScreen.getLabelName();
			System.out.println("<* PhotoController.handleCommand() *> Save Photo Label = "+ labelName);
			this.getImage().setImageLabel( labelName );
			this.updateImage(image);
			return this.goToPreviousScreen();

			/** Case: Go to the Previous or Fallback screen * */
		} else if (label.equals("Back")) {
			System.out.println("[PhotoController.handleCommand()] Back");
			return this.goToPreviousScreen();

			/** Case: Cancel the current screen and go back one* */
		} else if (label.equals("Cancel")) {
			return this.goToPreviousScreen();

		}
		
		return false;
	} 

	/*protected void initEditLabelScreen( ) {

		//Get all required interfaces for this method

		NewLabelScreen newLabelScreen = new NewLabelScreen( "Edit label photo" , NewLabelScreen.LABEL_PHOTO );

		//newLabelScreen.setCommandListener( this );
		Display.getDisplay( midlet ).setCurrent( newLabelScreen );
	}*/

	private void initAddPhotoToAlbum(String albumName) {

		//Get all required interfaces for this method
		/*IManager manager = (IManager)ComponentFactory.createInstance();
		IMobileResources iMobileResources = (IMobileResources)manager.getRequiredInterface("IMobileResources");*/
		MIDlet midlet = this.getMidlet();

		if( albumName == null){
			System.err.println("Image name is null");
			return;
		}
		else{
			AddPhotoToAlbum addPhotoToAlbum = new AddPhotoToAlbum( albumName );
			addPhotoToAlbum.setCommandListener(this);
			Display.getDisplay( midlet ).setCurrent( addPhotoToAlbum );

		}
	}

	/**
	 * @param image the image to set
	 */
	/*public void setImage(IImageData image) {
		this.image = image;
	}*/



	private void setScreen(NewLabelScreen screen) {
		this.screen = screen;
	} 


	/**
	 * Show the current image that is selected
	 * Modified in MobileMedia-Cosmos-OO-v4
	 * @throws PersistenceMechanismException 
	 * @throws ImageNotFoundException 
	 */
	private void showImage(String name) throws ImageNotFoundException, PersistenceMechanismException {
		// [EF] Instead of replicating this code, I change to use the method "getSelectedImageName()". 		
		Image storedImage = null;
		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		storedImage = filesystem.getImageFromRecordStore(getCurrentStoreName(), name);


		//We can pass in the image directly here, or just the name/model pair and have it loaded
		PhotoViewScreen canv = new PhotoViewScreen(storedImage);
		canv.setCommandListener( this );
		AbstractController nextcontroller = this; 
		// #if includeCopyPhoto || includeSmsFeature
		PhotoViewController controller = new PhotoViewController(midlet, name);
		controller.setNextController(nextcontroller);
		canv.setCommandListener(controller);
		nextcontroller = controller;
		// #endif
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		ISms sms = (ISms) manager.getRequiredInterface("ISms");
		this.setNextController( sms );
		//#endif
		
		setCurrentScreen(canv);
	}

	private void updateImage(IImageData image) throws InvalidImageDataException, PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		filesystem.updateImageInfo(image, image);

	}

	public String getCurrentlySelectedImageName() {
		return currentlySelectedImageName;
	}

}
