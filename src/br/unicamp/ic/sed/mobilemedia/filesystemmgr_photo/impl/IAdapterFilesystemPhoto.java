package br.unicamp.ic.sed.mobilemedia.filesystemmgr_photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IFilesystem;


public class IAdapterFilesystemPhoto implements IFilesystem {

	// #ifdef includeCopyPhoto
	public void addImageData(String photoName, IImageData imageData, String albumName) 
	throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException, 
	br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException{
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.addImageData(photoName, imageData, albumName);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException( e.getMessage() );
			
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}
	//#endif
	

	//#ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname)
		throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException,
			br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException {

		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.addImageData(photoname, img, albumname);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException( e.getMessage() );
			
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}
	//#endif
	
	public void addNewPhotoToAlbum(String imageName, String imagePath,
			String albumName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.addNewPhotoToAlbum(imageName, imagePath, albumName);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );		}
	}

	public void deleteImage(String imageName, String albumName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.deleteImage(imageName, albumName);
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException( e.getMessage() );
		}
	}

	public Image getImageFromRecordStore(String albumName, String imageName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException  {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getImageFromRecordStore(albumName, imageName);
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

	public IImageData getImageInfo(String imageName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getImageInfo(imageName);
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException( e.getMessage() );
		} catch (NullAlbumDataReference e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference(e);
		}
	}

	public IImageData[] getImages(String albumName) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getImages(albumName);
		} catch (UnavailablePhotoAlbumException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException( e.getMessage() );
		}
	}

	public void updateImageInfo(IImageData velhoID, IImageData novoID) throws br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.updateImageInfo(velhoID, novoID);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

}
