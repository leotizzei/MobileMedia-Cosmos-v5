package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov;

import javax.microedition.lcdui.Image;



import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype ImageData by the interface IImageData
 * that is implemented by the ImageData datatype.
 * 
 *
 */
public interface IFilesystem{

	// #ifdef includeCopyPhoto
	public void addImageData(String photoName, IImageData imageData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	// #endif 
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif 
	
	public void addNewPhotoToAlbum ( String imageName, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException; 
	
	public void createNewPhotoAlbum ( String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException; 
	
	public void deleteImage ( String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException; 
	
	public void deletePhotoAlbum ( String albumName ) throws PersistenceMechanismException; 
	
	public String[] getAlbumNames (  ); 
	
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException; 
 
	public IImageData getImageInfo( String imageName ) throws ImageNotFoundException, NullAlbumDataReference;
	
	public IImageData[] getImages( String albumName )throws UnavailablePhotoAlbumException;
	
	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	public byte[] loadImageBytesFromRMS(String recordName, String imageName, int recordId) throws PersistenceMechanismException; 
	
	public void resetImageData (  ) throws PersistenceMechanismException;
	
	public void updateImageInfo( IImageData velhoID , IImageData novoID ) throws InvalidImageDataException, PersistenceMechanismException;
}