/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group (http://www.sed.ic.unicamp.br)
 *
 * date: February 2009
 * 
 */   
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;

class IFilesystemFacade implements IFilesystem{

	private AlbumData albumData;
	private ImageAccessor imageAccessor;
	
	public IFilesystemFacade(){
		if( albumData == null)
			albumData = new AlbumData();
		
		if( imageAccessor == null){
			this.imageAccessor = new ImageAccessor( this.albumData );
		}
	}
	
	// #ifdef includeCopyPhoto
	public void addImageData(String photoName, IImageData imageData,
			String albumName) throws InvalidImageDataException, PersistenceMechanismException {
			this.albumData.addImageData(photoName, imageData, albumName);
	} 
	// #endif 
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException {
		this.albumData.addImageData(photoname,img,albumname);
	}
//	 #endif
	
	public void addNewPhotoToAlbum ( String imageName, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException{
		albumData.addNewPhotoToAlbum(imageName, imagePath, albumName);
	} 
	
	public void createNewPhotoAlbum ( String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException{
		albumData.createNewPhotoAlbum(albumName);		
	} 
	
	public void deleteImage ( String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException{
		albumData.deleteImage(imageName, albumName);
	} 
	
	public void deletePhotoAlbum ( String albumName ) throws PersistenceMechanismException{
		albumData.deletePhotoAlbum(albumName);	
	} 
	
	public String[] getAlbumNames (  ){
		return albumData.getAlbumNames();
	} 
	
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException{
		return albumData.getImageFromRecordStore(albumName, imageName);
	} 
	
	public IImageData getImageInfo(String imageName) throws ImageNotFoundException, NullAlbumDataReference {
		return albumData.getImageInfo(imageName);
	}

	
	public IImageData[] getImages ( String albumName ) throws UnavailablePhotoAlbumException{
		return albumData.getImages(albumName);
	}

	
	public void resetImageData (  ) throws PersistenceMechanismException{
		albumData.resetImageData();	
	}

	public void updateImageInfo(IImageData velhoID, IImageData novoID) throws InvalidImageDataException, PersistenceMechanismException {
		albumData.updateImageInfo(velhoID, novoID);
		
	}
	

	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	public byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {
		
		return this.imageAccessor.loadImageBytesFromRMS(recordName, imageName, recordId);
	}
}