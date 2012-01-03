// #if includeSmsFeature
package br.unicamp.ic.sed.mobilemedia.sms.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.sms.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IPhoto;




class SmsReceiverController extends AbstractController {
	byte[] incomingImageData;

	public SmsReceiverController(MIDlet midlet) {
		super( midlet );
	}

	/**
	 * Handle SMS specific events.
	 * If we are given a standard command that is handled by the BaseController, pass 
	 * the handling off to our super class with the else clause
	 */

	public boolean handleCommand(Command c) {

		String label = c.getLabel();
		System.out.println("SmsReceiverController::handleCommand: " + label);
		IManager manager = ComponentFactory.createInstance();

		/** Case: ... **/
		if (label.equals("Accept Photo")) {

			Image image = Image.createImage(incomingImageData, 0, incomingImageData.length);
			Image copy = Image.createImage(image.getWidth(), image.getHeight());
			
			IPhoto photo = (IPhoto) manager.getRequiredInterface("IPhoto");
			photo.initPhotoViewScreen( image , incomingImageData );
			return true;

		} else if (label.equals("Reject Photo")) {

			//TODO: Go back to whatever screen they were previously on?
			System.out.println("Reject Photo command");

			this.initAlbumListScreen();
			return true;

			/* For All commands not handled here, send them to the super class */
		} else if (label.equals("Ok"))
		{
			this.initAlbumListScreen();
			return true;
		}

		return true;
	}

	protected void setIncommingData(byte[] incomingImageData){
		this.incomingImageData = incomingImageData;
	}
	
	private void initAlbumListScreen(){
		IManager manager = ComponentFactory.createInstance();
		IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");
		album.initAlbumListScreen();
	}
}

//#endif