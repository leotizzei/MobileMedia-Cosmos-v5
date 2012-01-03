package br.unicamp.ic.sed.mobilemedia.main.impl;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMobileResources;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;


//Following are pre-processor statements to include the required
//classes for device specific features. They must be commented out
//if they aren't used, otherwise it will throw exceptions trying to
//load classes that aren't available for a given platform.


/* 
 * 
 *
 * This is the main Midlet class for the core J2ME application
 * It contains all the basic functionality that should be executable
 * in any standard J2ME device that supports MIDP 1.0 or higher. 
 * Any additional J2ME features for this application that are dependent
 * upon a particular device (ie. optional or proprietary library) are
 * de-coupled from the core application so they can be conditionally included
 * depending on the target platform 
 * 
 * This Application provides a basic Photo Album interface that allows a user to view
 * images on their mobile device. 
 * */
public class MainUIMidlet extends MIDlet implements IMobileResources {

	//components
	br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager album;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IManager filesystem;
	br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager photo;
	br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IManager mobilePhone;
	br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager exceptionHandler;

	//connectors
	br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl.IManager album_mobilePhone;
//	br.unicamp.ic.sed.mobilemedia.filesystemmgr_mobilephonemgr.impl.IManager filesystem_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.photo_mobilephonemgr.impl.IManager photo_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.IManager mainMidlet_album;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.IManager mainMidlet_photo;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_mobilephonemgr.impl.IManager mainMidlet_mobilePhone;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.IManager filesystemmgr_album;
	br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl.IManager filesystemmgr_photo;
	br.unicamp.ic.sed.mobilemedia.photo_album.impl.IManager photo_album;
	br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.IManager mainMidlet_exceptionHandler;
	br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl.IManager album_exceptionhandler;
	br.unicamp.ic.sed.mobilemedia.photo_exceptionhandler.impl.IManager photo_exceptionhandler; 

	public MainUIMidlet() {
		//do nothing
	}

	/**
	 * Start the MIDlet by creating new model and controller classes, and
	 * initialize them as necessary
	 */
	public void startApp() throws MIDletStateChangeException {

		System.out.println("Starting MobileMediaOO - v1");

		
		
		// create all imanagers
		filesystem = br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl.ComponentFactory.createInstance();

		album_mobilePhone = br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl.ComponentFactory.createInstance();

		mobilePhone = br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.ComponentFactory.createInstance();

		album = br.unicamp.ic.sed.mobilemedia.album.impl.ComponentFactory.createInstance();

		exceptionHandler = br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl.ComponentFactory.createInstance();
		
//		filesystem_mobilePhone = br.unicamp.ic.sed.mobilemedia.filesystemmgr_mobilephonemgr.impl.ComponentFactory.createInstance();
		
		mainMidlet_album = br.unicamp.ic.sed.mobilemedia.mainuimidlet_album.impl.ComponentFactory.createInstance();
		
		photo = br.unicamp.ic.sed.mobilemedia.photo.impl.ComponentFactory.createInstance();
		
		photo_mobilePhone = br.unicamp.ic.sed.mobilemedia.photo_mobilephonemgr.impl.ComponentFactory.createInstance();
		
		mainMidlet_photo = br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl.ComponentFactory.createInstance();
		
		mainMidlet_mobilePhone = br.unicamp.ic.sed.mobilemedia.mainuimidlet_mobilephonemgr.impl.ComponentFactory.createInstance();
		
		filesystemmgr_album = br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl.ComponentFactory.createInstance();
		
		filesystemmgr_photo = br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl.ComponentFactory.createInstance();
		
		photo_album = br.unicamp.ic.sed.mobilemedia.photo_album.impl.ComponentFactory.createInstance();
		
		mainMidlet_exceptionHandler = br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.ComponentFactory.createInstance();
		
		album_exceptionhandler = br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl.ComponentFactory.createInstance();
		
		photo_exceptionhandler = br.unicamp.ic.sed.mobilemedia.photo_exceptionhandler.impl.ComponentFactory.createInstance();
		
		/*********************************************************************************************/
		//setting required interfaces
		
		//component mobilePhoneMgr
//		mobilePhone.setRequiredInterface("IFilesystem", filesystem_mobilePhone.getProvidedInterface("IFilesystem"));
		
		mobilePhone.setRequiredInterface("IPhoto", photo_mobilePhone.getProvidedInterface("IPhoto"));
		
		mobilePhone.setRequiredInterface("IMobileResources", mainMidlet_mobilePhone.getProvidedInterface("IMobileResources"));
		
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum ialbum = (IAlbum) album_mobilePhone.getProvidedInterface("IAlbum");
		
		mobilePhone.setRequiredInterface("IAlbum", ialbum );
		
		
		//component album
//		album.setRequiredInterface("IMobilePhone", album_mobilePhone.getProvidedInterface("IMobilePhone"));

		album.setRequiredInterface("IMobileResources", mainMidlet_album.getProvidedInterface("IMobileResources") );

		album.setRequiredInterface("IPhoto", photo_album.getProvidedInterface("IPhoto"));

		album.setRequiredInterface("IFilesystem",filesystemmgr_album.getProvidedInterface("IFilesystem"));

		album.setRequiredInterface("IExceptionHandler", album_exceptionhandler.getProvidedInterface("IExceptionHandler"));
		System.out.println("step 2");
		
		// component photo
		photo.setRequiredInterface("IMobilePhone", photo_mobilePhone.getProvidedInterface("IMobilePhone"));

		photo.setRequiredInterface("IMobileResources", mainMidlet_photo.getProvidedInterface("IMobileResources") );

		photo.setRequiredInterface("IFilesystem", filesystemmgr_photo.getProvidedInterface("IFilesystem"));

		photo.setRequiredInterface("IExceptionHandler", photo_exceptionhandler.getProvidedInterface("IExceptionHandler"));
		System.out.println("step 3");
		
		// connectors
		mainMidlet_photo.setRequiredInterface("IMobileResources", this );

		mainMidlet_mobilePhone.setRequiredInterface("IMobileResources", this);

		filesystemmgr_album.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));

		filesystemmgr_photo.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));

		photo_album.setRequiredInterface("IPhoto", photo.getProvidedInterface("IPhoto"));

//		filesystem_mobilePhone.setRequiredInterface("IFilesystem", filesystem.getProvidedInterface("IFilesystem"));
		System.out.println("step 4");
		album_mobilePhone.setRequiredInterface("IAlbum", album.getProvidedInterface("IAlbum"));
		
		album_mobilePhone.setRequiredInterface("IMobilePhone", mobilePhone.getProvidedInterface("IMobilePhone"));
		
		photo_mobilePhone.setRequiredInterface("IPhoto", photo.getProvidedInterface("IPhoto"));
		
		photo_mobilePhone.setRequiredInterface("IMobilePhone", mobilePhone.getProvidedInterface("IMobilePhone"));

		mainMidlet_album.setRequiredInterface("IMobileResources", this );

		mainMidlet_exceptionHandler.setRequiredInterface("IMobileResources",  this );
		
		exceptionHandler.setRequiredInterface("IMobileResources", mainMidlet_exceptionHandler.getProvidedInterface("IMobileResources"));
		
		album_exceptionhandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler") );
		
		photo_exceptionhandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler") );
		System.out.println("step 5");
		
		
		//#ifdef includeSmsFeature
		/* [NC] Added in scenario 06 */
		
		int cont = 0;
		System.out.println("Setting up SMS feature");
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.IManager sms = br.unicamp.ic.sed.mobilemedia.sms.impl.ComponentFactory.createInstance();
		
		System.out.println(cont++);
		//Sms-MobileResources
		br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl.IManager mobile_sms = br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl.ComponentFactory.createInstance();
		mobile_sms.setRequiredInterface("IMobileResources", this);
		
		sms.setRequiredInterface("IMobileResources", mobile_sms.getProvidedInterface("IMobileResources"));
	
		System.out.println(cont++);
		//Photo-Sms
		br.unicamp.ic.sed.mobilemedia.sms_photo.IManager photo_sms = br.unicamp.ic.sed.mobilemedia.sms_photo.ComponentFactory.createInstance();
		
		photo_sms.setRequiredInterface("IPhoto", photo.getProvidedInterface("IPhoto"));
		photo_sms.setRequiredInterface("ISms", sms.getProvidedInterface("ISms"));
		photo.setRequiredInterface("ISms", photo_sms.getProvidedInterface("ISms"));
		sms.setRequiredInterface("IPhoto", photo_sms.getProvidedInterface("IPhoto"));
		
		System.out.println(cont++);
		//sms-filesystem
		br.unicamp.ic.sed.mobilemedia.sms_filesystem.IManager sms_filesystem = br.unicamp.ic.sed.mobilemedia.sms_filesystem.ComponentFactory.createInstance(); 
		
		System.out.println(cont++);
		sms_filesystem.setRequiredInterface("IFilesystem",filesystem.getProvidedInterface("IFilesystem"));
		sms.setRequiredInterface("IFilesystem", sms_filesystem.getProvidedInterface("IFilesystem" ));
		
		System.out.println(cont++);
		//sms-album
		br.unicamp.ic.sed.mobilemedia.sms_album.IManager sms_album = br.unicamp.ic.sed.mobilemedia.sms_album.ComponentFactory.createInstance();
		
		System.out.println(cont++);
		sms_album.setRequiredInterface("IAlbum", album.getProvidedInterface("IAlbum"));
		sms.setRequiredInterface("IAlbum", sms_album.getProvidedInterface("IAlbum"));
		
		//sms-exceptionhandler
		br.unicamp.ic.sed.mobilemedia.sms_exceptionhandler.IManager sms_exceptionHandler = br.unicamp.ic.sed.mobilemedia.sms_exceptionhandler.ComponentFactory.createInstance();
		sms_exceptionHandler.setRequiredInterface("IExceptionHandler", exceptionHandler.getProvidedInterface("IExceptionHandler"));
		sms.setRequiredInterface("IExceptionHandler", sms_exceptionHandler.getProvidedInterface("IExceptionHandler"));
		
		System.out.println(cont++);
		System.out.println("SMS feature configured");
		
		//#endif
		
		
		IMobilePhone mobPhone = (IMobilePhone) mobilePhone.getProvidedInterface("IMobilePhone");

		System.out.println("mobPhone.startUp()");

		mobPhone.startUp();
	}

	/**
	 * Pause the MIDlet
	 * This method does nothing at the moment.
	 */
	public void pauseApp() {
		//do nothing
	}

	/**
	 * Destroy the MIDlet
	 */
	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}

	public MIDlet getMainMIDlet() {
		return this;
	}

	
}