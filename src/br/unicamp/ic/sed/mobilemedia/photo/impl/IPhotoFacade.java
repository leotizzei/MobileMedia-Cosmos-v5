
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.photo.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobileResources;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.ISms;

class IPhotoFacade implements IPhoto{

	private MIDlet midlet;

	private PhotoController photoController = null;
	private PhotoListController photoListController = null;
	
	//#ifdef includeCopyPhoto
	private PhotoViewController photoViewController = null;
	//#endif
	
	private PhotoViewScreen photoViewScreen = null;
	
	public IPhotoFacade(){

		IManager manager = ComponentFactory.createInstance();
		IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
		this.midlet = mobileResources.getMainMIDlet();
		
		this.photoController = new PhotoController( this.midlet );
		this.photoListController = new PhotoListController( this.midlet );
		this.photoController.setNextController( this.photoListController );

	}

	public boolean postCommand(Command c) {
		return photoController.postCommand(c);
	}

	//#if includeSmsFeature && includeCopyPhoto
	public void initPhotoViewScreen(Image image , byte[] img ) {
		this.photoViewScreen = new PhotoViewScreen(image);
		this.photoViewScreen.setFromSMS( true );
		this.photoViewScreen.setImageBytes(img);
		this.photoViewController = new PhotoViewController( midlet , "noName" );
		this.photoViewController.setNextController( this.photoController );
		this.photoViewScreen.setCommandListener(photoViewController);
		
		IManager manager = ComponentFactory.createInstance();
		ISms sms = (ISms) manager.getRequiredInterface("ISms");
		this.photoListController.setNextController( sms );
		
		if( ScreenSingleton.getInstance().getCurrentScreenName() == null )
			ScreenSingleton.getInstance().setCurrentScreenName( Constants.IMAGELIST_SCREEN );
		
		Display.getDisplay( midlet ).setCurrent( this.photoViewScreen );
	}
	//#endif

	public String getSelectedImageName() {
		String imageName = photoListController.getCurrentlySelectedImageName();
		System.out.println("ImageName: " + imageName +" =====");
		return imageName;
	}

}