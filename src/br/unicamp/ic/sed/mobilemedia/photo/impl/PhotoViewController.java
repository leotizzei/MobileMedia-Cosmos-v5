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



/**
 * Added in MobileMedia-Cosmos-OO-v4
 */
//#ifdef includeCopyPhoto
class PhotoViewController extends AbstractController {

	private AddPhotoToAlbum addPhotoToAlbum;
	
	private void setAddPhotoToAlbum(AddPhotoToAlbum addPhotoToAlbum) {
		this.addPhotoToAlbum = addPhotoToAlbum;
	}

	String imageName = "";

	
	public PhotoViewController(MIDlet midlet,  String imageName) {
		super( midlet );
		this.imageName = imageName;

	}

	private AddPhotoToAlbum getAddPhotoToAlbum() {
		if( this.addPhotoToAlbum == null)
			this.addPhotoToAlbum = new AddPhotoToAlbum("Copy Photo to Album");
		return addPhotoToAlbum;
	}


	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public boolean handleCommand(Command c) throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException {
		String label = c.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);
		
		/** Case: Copy photo to a different album */
		if (label.equals("Copy")) {
			this.initCopyPhotoToAlbum( );
			return true;
		}

		/** Case: Save a copy in a new album */
		else if (label.equals("Save Photo")) {
			this.savePhoto();
			return true;
		}

		return false;
	}

	// #ifdef includeCopyPhoto
	private void initCopyPhotoToAlbum() {
		
		String title = new String("Copy Photo to Album");
		String labelPhotoPath = new String("Copy to Album:");
		
		AddPhotoToAlbum addPhotoToAlbum = new AddPhotoToAlbum( title );
		addPhotoToAlbum.setPhotoName( imageName );
		addPhotoToAlbum.setLabelPhotoPath( labelPhotoPath );
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (((PhotoViewScreen)this.getCurrentScreen()).isFromSMS()){
			addPhotoToAlbum.setImage(((PhotoViewScreen)this.getCurrentScreen()).getImage());
			addPhotoToAlbum.setImageBytes(((PhotoViewScreen)this.getCurrentScreen()).getImageBytes());
		}
		//#endif
		
		this.setAddPhotoToAlbum( addPhotoToAlbum );
		
		//Get all required interfaces for this method
		MIDlet midlet = this.getMidlet();
		
		//addPhotoToAlbum.setCommandListener( this );
		Display.getDisplay( midlet ).setCurrent( addPhotoToAlbum );
				
		addPhotoToAlbum.setCommandListener(this);
	
	}
	// #endif
	
	
	
	private void savePhoto() throws ImageNotFoundException, NullAlbumDataReference, InvalidImageDataException, PersistenceMechanismException, UnavailablePhotoAlbumException{
		IManager manager = ComponentFactory.createInstance();
		IImageData imageData = null;
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");


		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		Image img = ((AddPhotoToAlbum)this.getCurrentScreen()).getImage();
		
		if (img == null)
		//#endif
		{
		imageData = filesystem.getImageInfo(imageName);
		}
		
		
		AddPhotoToAlbum addPhotoToAlbum = this.getAddPhotoToAlbum();
		
		
		String photoName = addPhotoToAlbum.getPhotoName(); 
		System.out.println("[PhotoViewController.savePhoto] photoName = "+photoName);
		
		String albumName = addPhotoToAlbum.getPath();
		System.out.println("[PhotoViewController.savePhoto] albumName = "+albumName);
		
		// #ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (img != null){
			Image image = Image.createImage( img.getWidth() , img.getHeight() );
			filesystem.addImageData(photoName, addPhotoToAlbum.getImageBytes(), albumName);
		}
		// #endif 
		
		// #if includeCopyPhoto && includeSmsFeature
		/* [NC] Added in scenario 06 */
		if (img == null)
		//#endif
			
		// #if includeCopyPhoto 
		/* [NC] Added in scenario 06 */
		filesystem.addImageData(photoName, imageData, albumName);
		// #endif
		
		
		
		
		((PhotoController)this.getNextController()).showImageList(ScreenSingleton.getInstance().getCurrentStoreName(), false, false);
		
		ScreenSingleton.getInstance().setCurrentScreenName(Constants.IMAGELIST_SCREEN);
	
	}
}
//#endif