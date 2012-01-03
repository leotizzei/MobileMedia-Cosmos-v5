/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Hashtable;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStoreException;


import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.ImageData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;

/**
 * @author tyoung
 * 
 * This class represents the data model for Photo Albums. A Photo Album object
 * is essentially a list of photos or images, stored in a Hashtable. Due to
 * constraints of the J2ME RecordStore implementation, the class stores a table
 * of the images, indexed by an identifier, and a second table of image metadata
 * (ie. labels, album name etc.)
 * 
 * This uses the ImageAccessor class to retrieve the image data from the
 * recordstore (and eventually file system etc.)
 */
class AlbumData {

	public boolean existingRecords = false; //If no records exist, try to reset

	private ImageAccessor imageAccessor;

	//imageInfo holds image metadata like label, album name and 'foreign key' index to
	// corresponding RMS entry that stores the actual Image object
	private Hashtable imageInfoTable = new Hashtable();

	/**
	 *  Constructor. Creates a new instance of ImageAccessor
	 */
	
	public AlbumData() {
		imageAccessor = new ImageAccessor(this);
	}

	/**
	 * Added in MobileMedia-Cosmos-OO-v4
	 * @param photoname
	 * @param imgdata
	 * @param albumname
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */

//	 #ifdef includeCopyPhoto	
	protected void addImageData(String photoname, IImageData imageData, String albumname) throws InvalidImageDataException, PersistenceMechanismException{
		imageAccessor.addImageData(photoname, imageData, albumname);
	}

	//TODO:[cosmos] I think this 'ifdef' definition must not include this method!
	/**
	 * @param photoname
	 * @param img
	 * @param albumname
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	/**[Original][cosmos sms]Included in cosmos v5*/
	//#ifdef includeSmsFeature 
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException {
		//imageAccessor.addImageData(photoname,img,albumname);
		try {
			imageAccessor.addMediaArrayOfBytes(photoname, img, albumname);
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//#endif
//	 #endif

	protected void addNewPhotoToAlbum(String label, String path, String album) throws InvalidImageDataException, PersistenceMechanismException{
		imageAccessor.addImageData(label, path, album);
	}

	/**
	 *  Define a new user photo album. This results in the creation of a new
	 *  RMS Record store.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidPhotoAlbumNameException 
	 */
	protected void createNewPhotoAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {
		imageAccessor.createNewPhotoAlbum(albumName);
	}

	/**
	 *  Delete a photo from the photo album. This permanently deletes the image from the record store
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 */
	protected void deleteImage(String imageName, String storeName) throws PersistenceMechanismException, ImageNotFoundException {
		try {
			imageAccessor.deleteSingleImageFromRMS( storeName, imageName);
		}
		catch (NullAlbumDataReference e) {
			imageAccessor = new ImageAccessor(this);
			e.printStackTrace();
		} 
	}
	
	protected void deletePhotoAlbum(String albumName) throws PersistenceMechanismException{
		imageAccessor.deletePhotoAlbum(albumName);
	}

	/**
	 *  Load any photo albums that are currently defined in the record store
	 */
	
	protected String[] getAlbumNames() {
		//Shouldn't load all the albums each time
		//Add a check somewhere in ImageAccessor to see if they've been
		//loaded into memory already, and avoid the extra work...
		System.out.println("[AlbumData.getAlbumNames()]");
		try {
			imageAccessor.loadAlbums();
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
		}
		return imageAccessor.getAlbumNames();
	}
	private ImageAccessor getImageAccessor() {
		return imageAccessor;
	}

	/**
	 *  Get a particular image (by name) from a photo album. The album name corresponds
	 *  to a record store.
	 * @throws ImageNotFoundException 
	 * @throws PersistenceMechanismException 
	 */
	protected Image getImageFromRecordStore(String recordStore, String imageName) throws ImageNotFoundException, PersistenceMechanismException {

		IImageData imageInfo = null;
		try {
			imageInfo = imageAccessor.getImageInfo(imageName);
		} catch (NullAlbumDataReference e) {
			imageAccessor = new ImageAccessor(this);
		}
		//Find the record ID and store name of the image to retrieve
		int imageId = imageInfo.getForeignRecordId();
		String album = imageInfo.getParentAlbumName();
		//Now, load the image (on demand) from RMS and cache it in the hashtable
		Image imageRec = imageAccessor.loadSingleImageFromRMS(album, imageName, imageId); //rs.getRecord(recordId);
		return imageRec;

	}
	
	/**
	 * [CD] Add in order to have access to ImageData
	 * @return
	 */
	
	protected IImageData getImageInfo(String name)throws ImageNotFoundException, NullAlbumDataReference{
		return imageAccessor.getImageInfo(name);
	}

	/**
	 * Get the hashtable that stores the image metadata in memory.
	 * @return Returns the imageInfoTable.
	 */
	protected Hashtable getImageInfoTable() {
		return imageInfoTable;
	}

	/**
	 *  Get the names of all images for a given Photo Album that exist in the Record Store.
	 * @throws UnavailablePhotoAlbumException 
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 */
	protected ImageData[] getImages(String recordName) throws UnavailablePhotoAlbumException  {

		ImageData[] result;
		try {
			result = imageAccessor.loadImageDataFromRMS(recordName);
		} catch (PersistenceMechanismException e) {
			throw new UnavailablePhotoAlbumException( e.getMessage() );
			
		} catch (InvalidImageDataException e) {
			throw new UnavailablePhotoAlbumException( e.getMessage() );
		}

		return result;

	}

	/**
	 *  Reset the image data for the application. This is a wrapper to the ImageAccessor.resetImageRecordStore
	 *  method. It is mainly used for testing purposes, to reset device data to the default album and photos.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidImageDataException 
	 */
	protected void resetImageData() throws PersistenceMechanismException {
		try {
			imageAccessor.resetImageRecordStore();
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * [EF] Add in order to have access to ImageData
	 * @param imageAccessor
	 */
	private void setImageAccessor(ImageAccessor imageAccessor) {
		this.imageAccessor = imageAccessor;
	}
	
	/**
	 * Update the hashtable that stores the image metadata in memory
	 * @param imageInfoTable
	 *            The imageInfoTable to set.
	 */
	private void setImageInfoTable(Hashtable imageInfoTable) {
		this.imageInfoTable = imageInfoTable;
	}
	

	
	/**
	 * [CD] Add in order to have access to ImageData
	 */
		
	protected void updateImageInfo(IImageData oldData,IImageData newData) throws InvalidImageDataException, PersistenceMechanismException{
			imageAccessor.updateImageInfo(oldData, newData);
	}
	
}