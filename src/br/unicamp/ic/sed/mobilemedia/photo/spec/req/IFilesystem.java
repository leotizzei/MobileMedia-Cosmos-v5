package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype ImageData by the interface IImageData
 * that is implemented by the ImageData datatype.
 * 
 *
 */
public interface IFilesystem{

	// #ifdef includeCopyPhoto
	/**
	 * Added in MobileMedia-Cosmos-OO-v4 
	 * 
	 */
	public void addImageData(String photoName, IImageData imageData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	// #endif 
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif
	
	public void addNewPhotoToAlbum ( String imageName, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException; 
		
	public void deleteImage ( String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException; 
	
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException; 
 
	public IImageData getImageInfo( String imageName ) throws ImageNotFoundException, NullAlbumDataReference;
	
	public IImageData[] getImages( String albumName )throws UnavailablePhotoAlbumException;
	
	public void updateImageInfo( IImageData velhoID , IImageData novoID ) throws InvalidImageDataException, PersistenceMechanismException;
}