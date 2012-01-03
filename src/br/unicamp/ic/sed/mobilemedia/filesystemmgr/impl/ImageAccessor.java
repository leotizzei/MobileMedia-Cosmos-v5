/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.ImageData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImagePathNotValidException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;

/**
 * @author trevor
 * 
 * This is the main data access class. It handles all the connectivity with the
 * RMS record stores to fetch and save data associated with MobileMedia TODO:
 * Refactor into stable interface for future updates. We may want to access data
 * from RMS, or eventually direct from the 'file system' on devices that support
 * the FileConnection optional API.
 * 
 */


public class ImageAccessor {

	// Note: Our midlet only ever has access to Record Stores it created
	// For now, use naming convention to create record stores used by
	// MobileMedia

	public static final String ALBUM_LABEL = "mpa-"; // "mpa- all album names
	// stores are prefixed with
	// this label
	public static final String DEFAULT_ALBUM_NAME = "My Photo Album"; // default
	// album
	// name
	public static final String IMAGE_LABEL = "ImageList"; // RecordStore name
	// prefixed

	// are prefixed with
	// this label
	public static final String INFO_LABEL = "mpi-"; // "mpi- all album info

	private AlbumData albumData;

	private String[] albumNames; // User defined names of photo albums

	private RecordStore imageInfoRS = null;
	// Record Stores
	private RecordStore imageRS = null;

	/*
	 * Constructor
	 */
	public ImageAccessor(AlbumData mod) {
		System.out.println("[ImageAccessor] constructor"+this.toString());
		this.albumData = mod;
	}

	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	/**[Original][cosmos sms]Included in cosmos v5*/
	/*private byte[] getByteFromImage(Image img){
		int w = img.getWidth();
		int h = img.getHeight();
		int data_int[] = new int[ w * h ];
		img.getRGB( data_int, 0, w, 0, 0, w, h );
		byte[] data_byte = new byte[ w * h * 3 ];
		for ( int i = 0; i < w * h; ++i )
		{
		int color = data_int[ i ];
		int offset = i * 3;
		data_byte[ offset ] = ( byte ) ( ( color & 0xff0000 ) >> 16 );
		data_byte[ offset +
		1 ] = ( byte ) ( ( color & 0xff00 ) >> 8 );
		data_byte[ offset + 2 ] = ( byte ) ( ( color & 0xff ) );
		}
		return data_byte;
	}*/
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	public void addMediaArrayOfBytes(String photoname, 
			byte[] data1, String albumname) throws RecordStoreException, InvalidImageDataException {	
		imageRS = RecordStore.openRecordStore(ALBUM_LABEL + albumname, false);
		imageInfoRS = RecordStore.openRecordStore(INFO_LABEL + albumname, false);
		
		int rid; // new record ID for Image (bytes)
		int rid2; // new record ID for ImageData (metadata)
		rid = imageRS.addRecord(data1, 0, data1.length);
		ImageData ii = new ImageData(rid,ImageAccessor.ALBUM_LABEL + albumname, photoname);
		rid2 = imageInfoRS.getNextRecordID();
		ii.setRecordId(rid2);
		
		ImageUtil converter = new ImageUtil();
		data1 = converter.getBytesFromImageInfo(ii);
		imageInfoRS.addRecord(data1, 0, data1.length);
				
		imageRS.closeRecordStore();
		imageInfoRS.closeRecordStore();
	}
	//#endif
	
	// #ifdef includeCopyPhoto
	/**
	 * Added in MobileMedia-Cosmos-OO-v4
	 * @param photoname
	 * @param imageData
	 * @param albumname
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	protected void addImageData(String photoname, IImageData imageData, String albumname) throws InvalidImageDataException, PersistenceMechanismException {
		try {
			
			
			imageRS = RecordStore.openRecordStore(ALBUM_LABEL + albumname, true);
			imageInfoRS = RecordStore.openRecordStore(INFO_LABEL + albumname, true);
			int rid2; // new record ID for ImageData (metadata)
			ImageUtil converter = new ImageUtil();
			rid2 = imageInfoRS.getNextRecordID();
			
			imageData.setRecordId(rid2);
			imageData.setImageLabel( photoname );
			imageData.setParentAlbumName( ALBUM_LABEL + albumname );
			
			System.out.println("[ImageAccessor.addImageData] imageLabel="+imageData.getImageLabel()+" parentAlbumName="+imageData.getParentAlbumName());
			byte[] data1 = converter.getBytesFromImageInfo(imageData);
			imageInfoRS.addRecord(data1, 0, data1.length);
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}finally{
			try {
				imageRS.closeRecordStore();
				imageInfoRS.closeRecordStore();
			} catch (RecordStoreNotOpenException e) {
				e.printStackTrace();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}
	}
	// #endif

	

	protected void addImageData(String photoname, String path, String albumname)
	throws InvalidImageDataException, PersistenceMechanismException {

		//		System.out.println("<* ImageAccessor.addImageData *> photoname = "+photoname);
		try {
			imageRS = RecordStore
			.openRecordStore(ALBUM_LABEL + albumname, true);
			imageInfoRS = RecordStore.openRecordStore(INFO_LABEL + albumname,
					true);

			int rid; // new record ID for Image (bytes)
			int rid2; // new record ID for ImageData (metadata)

			ImageUtil converter = new ImageUtil();

			// NOTE: For some Siemen's phone, all images have to be less than
			// 16K
			// May have to check for this, or try to convert to a lesser format
			// for display on Siemen's phones (Could put this in an Aspect)

			// Add Tucan
			byte[] data1 = converter.readImageAsByteArray(path);
			rid = imageRS.addRecord(data1, 0, data1.length);
			ImageData ii = new ImageData(rid, ImageAccessor.ALBUM_LABEL
					+ albumname, photoname);
			rid2 = imageInfoRS.getNextRecordID();
			ii.setRecordId(rid2);
			data1 = converter.getBytesFromImageInfo(ii);
			imageInfoRS.addRecord(data1, 0, data1.length);

			imageRS.closeRecordStore();

			imageInfoRS.closeRecordStore();
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}
	}

	/**
	 * Define a new photo album for mobile photo users. This creates a new
	 * record store to store photos for the album.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidPhotoAlbumNameException 
	 */
	protected void createNewPhotoAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {

		RecordStore newAlbumRS = null;
		RecordStore newAlbumInfoRS = null;
		if( albumName == null){
			System.err.println("Error in class "+this.getClass().getName()+"! AlbumName is null");
			return;
		}
		if (albumName.equals("")){
			throw new InvalidPhotoAlbumNameException( "Invalid album name!" );

		}
		
		//check if there is an album with the same name
		String[] albumNames  = this.getAlbumNames();
		for (int i = 0; i < albumNames.length; i++) {
			if (albumNames[i].equals(albumName))
				throw new InvalidPhotoAlbumNameException( "Invalid album name!" );
		}

		try {
			newAlbumRS = RecordStore.openRecordStore(ALBUM_LABEL + albumName,true);
			newAlbumInfoRS = RecordStore.openRecordStore(INFO_LABEL + albumName, true);
			newAlbumRS.closeRecordStore();
			newAlbumInfoRS.closeRecordStore();
		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}

	}

	protected void deletePhotoAlbum(String albumName) throws PersistenceMechanismException {

		//		System.out.println("<* deletePhotoAlbum.ImageAccessor *> albumName = "+albumName);
		//		System.out.println("<* deletePhotoAlbum.ImageAccessor *> ALBUM_LABEL + albumName = "+ALBUM_LABEL + albumName);
		try {
			RecordStore.deleteRecordStore(ALBUM_LABEL + albumName);
			RecordStore.deleteRecordStore(INFO_LABEL + albumName);
		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}

	}

	/**
	 * Delete a single (specified) image from the (specified) record store. This
	 * will permanently delete the image data and metadata from the device.
	 * @throws PersistenceMechanismException 
	 * @throws NullAlbumDataReference 
	 * @throws ImageNotFoundException 
	 */
	protected boolean deleteSingleImageFromRMS(String storeName, String imageName) throws PersistenceMechanismException, ImageNotFoundException, NullAlbumDataReference {

		boolean success = false;

		// Open the record stores containing the byte data and the meta data
		// (info)
		try {

			System.out.println("Delete: " + storeName+" : "+ imageName);
			
			// Verify storeName is name without pre-fix
			imageRS = RecordStore.openRecordStore(ALBUM_LABEL + storeName, true);
			imageInfoRS = RecordStore.openRecordStore(INFO_LABEL + storeName,true);

			IImageData imageData = getImageInfo(imageName);
			int rid = imageData.getForeignRecordId();

			imageRS.deleteRecord(rid);
			imageInfoRS.deleteRecord(rid);

			imageRS.closeRecordStore();
			imageInfoRS.closeRecordStore();

		} catch (RecordStoreException rse) {
			rse.printStackTrace();
			throw new PersistenceMechanismException(rse);
		}

		// TODO: It's not clear from the API whether the record store needs to
		// be closed or not...

		return success;
	}

	private AlbumData getAlbumData() {
		return albumData;
	}

	/**
	 * Get the list of photo album names currently loaded.
	 * 
	 * @return Returns the albumNames.
	 */
	protected String[] getAlbumNames() {
		
		return albumNames;
		
	}

	private void setAlbumNames(String[] albumNames) {
		this.albumNames = albumNames;
	}

	/**
	 * Retrieve the metadata associated with a specified image (by name)
	 * @throws ImageNotFoundException 
	 * @throws NullAlbumDataReference 
	 */
	protected IImageData getImageInfo(String imageName) throws ImageNotFoundException, NullAlbumDataReference {
		AlbumData albumData = this.getAlbumData();
		if (albumData == null)
			throw new NullAlbumDataReference("Null reference to the Album data");

		
		IImageData ii = (IImageData) albumData.getImageInfoTable().get(imageName);
		
		if (ii == null){
			System.out.println("----Image is null!----");
			throw new ImageNotFoundException(imageName +" was NULL in ImageAccessor Hashtable.");
		}else{
			System.out.println("----Image is not null!----");
		}

		return ii;

	}

	/**
	 * Load all existing photo albums that are defined in the record store.
	 * 
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	protected void loadAlbums() throws InvalidImageDataException,
	PersistenceMechanismException {
			
		
		
		// Try to find any existing Albums (record stores)

		String[] currentStores = RecordStore.listRecordStores();

		if (currentStores != null) {
			System.out.println("ImageAccessor::loadAlbums: Found: "
					+ currentStores.length + " existing record stores");
			//AlbumData albumData = this.getAlbumData();
		
			albumData.existingRecords = true;
			String[] temp = new String[currentStores.length];
			int count = 0;

			// Only use record stores that follow the naming convention defined
			for (int i = 0; i < currentStores.length; i++) {
				String curr = currentStores[i];
		
				// If this record store is a photo album...
				if (curr.startsWith(ALBUM_LABEL)) {

					// Strip out the mpa- identifier
					curr = curr.substring(4);
					// Add the album name to the array
					temp[i] = curr;
					count++;
		
				}
			}

			// Re-copy the contents into a smaller array now that we know the
			// size
			String[] albumNames = new String[count];
			int count2 = 0;
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] != null) {
					albumNames[count2] = temp[i];
					count2++;
				}
				
			}
			this.setAlbumNames( albumNames );
			
		} else {
			resetImageRecordStore();
			loadAlbums();
		}
	
	}

	/**
	 * Get the data for an Image as a byte array. This is useful for sending
	 * images via SMS or HTTP
	 * @throws PersistenceMechanismException 
	 */
	protected byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {

		byte[] imageData = null;

		try {

			RecordStore albumStore = RecordStore.openRecordStore(recordName,
					false);
			imageData = albumStore.getRecord(recordId);
			albumStore.closeRecordStore();

		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}

		return imageData;
	}

	/**
	 * This will populate the imageInfo hashtable with the ImageInfo object,
	 * referenced by label name and populate the imageTable hashtable with Image
	 * objects referenced by the RMS record Id
	 * 
	 * @throws PersistenceMechanismException
	 */
	protected ImageData[] loadImageDataFromRMS(String recordName)
	throws PersistenceMechanismException, InvalidImageDataException {

		Vector imagesVector = new Vector();

		try {

			// [EF] not used			String storeName = ImageAccessor.ALBUM_LABEL + recordName;
			String infoStoreName = ImageAccessor.INFO_LABEL + recordName;

			RecordStore infoStore = RecordStore.openRecordStore(infoStoreName,
					false);
			RecordEnumeration isEnum = infoStore.enumerateRecords(null, null,
					false);

			while (isEnum.hasNextElement()) {
				// Get next record
				int currentId = isEnum.nextRecordId();
				byte[] data = infoStore.getRecord(currentId);

				// Convert the data from a byte array into our ImageData
				// (metadata) object
				ImageUtil converter = new ImageUtil();
				ImageData iiObject = converter.getImageInfoFromBytes(data);

				//				System.out.println("<* ImageAccessor.loadImageDataFromRMS *> iiObject = "+iiObject.getImageLabel());

				// Add the info to the metadata hashtable
				String label = iiObject.getImageLabel();
				imagesVector.addElement(iiObject);
				AlbumData albumData = this.getAlbumData();
				albumData.getImageInfoTable().put(label, iiObject);

			}

			infoStore.closeRecordStore();

		}catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}

		// Re-copy the contents into a smaller array
		ImageData[] labelArray = new ImageData[imagesVector.size()];
		imagesVector.copyInto(labelArray);
		
		return labelArray;
	}

	/**
	 * Fetch a single image from the Record Store This should be used for
	 * loading images on-demand (only when they are viewed or sent via SMS etc.)
	 * to reduce startup time by loading them all at once.
	 * @throws PersistenceMechanismException 
	 */
	protected Image loadSingleImageFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {

		Image img = null;
		byte[] imageData = loadImageBytesFromRMS(recordName, imageName,
				recordId);
		
		img = Image.createImage(imageData, 0, imageData.length);
		return img;
	}

	/**
	 * Reset the album data for MobileMedia. This will delete all existing photo
	 * data from the record store and re-create the default album and photos.
	 * 
	 * @throws InvalidImageFormatException
	 * @throws ImagePathNotValidException
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	protected void resetImageRecordStore() throws InvalidImageDataException,
	PersistenceMechanismException {

		String storeName = null;
		String infoStoreName = null;
		
		String[] albumNames = this.getAlbumNames();
		// remove any existing album stores...
		if (albumNames != null) {
			for (int i = 0; i < albumNames.length; i++) {
				try {
					// Delete all existing stores containing Image objects as
					// well as the associated ImageInfo objects
					// Add the prefixes labels to the info store

					storeName = ALBUM_LABEL + albumNames[i];
					infoStoreName = INFO_LABEL + albumNames[i];

					RecordStore.deleteRecordStore(storeName);
					RecordStore.deleteRecordStore(infoStoreName);

				} catch (RecordStoreException e) {
					System.out.println("No record store named " + storeName
							+ " to delete.");
					System.out.println("...or...No record store named "
							+ infoStoreName + " to delete.");
					System.out.println("Ignoring Exception: " + e);
					// ignore any errors...
				}
			}
		} else {
			// Do nothing for now
			System.out.println("ImageAccessor::resetImageRecordStore: albumNames array was null. Nothing to delete.");
		}

		// Now, create a new default album for testing
		addImageData("Tucan Sam", "/images/Tucan.png",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add Penguin
		addImageData("Linux Penguin", "/images/Penguin.png",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add Duke
		addImageData("Duke (Sun)", "/images/Duke1.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		addImageData("UBC Logo", "/images/ubcLogo.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add Gail
		addImageData("Gail", "/images/Gail1.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add JG
		addImageData("J. Gosling", "/images/Gosling1.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add GK
		addImageData("Gregor", "/images/Gregor1.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);
		// Add KDV
		addImageData("Kris", "/images/Kdvolder1.PNG",
				ImageAccessor.DEFAULT_ALBUM_NAME);

	}

	private void setAlbumData(AlbumData albumData) {
		this.albumData = albumData;
	}

	/**
	 * Update the hashtable with new ImageInfo data
	 */
	private void setImageInfo(String imageName, IImageData newData) {
		AlbumData albumData = this.getAlbumData();
		albumData.getImageInfoTable().put(newData.getImageLabel(), newData);

	}

	/**
	 * Update the Image metadata associated with this named photo
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 */
	protected boolean updateImageInfo(IImageData oldData, IImageData newData) throws InvalidImageDataException, PersistenceMechanismException {

		boolean success = false;
		RecordStore infoStore = null;
		try {

			// Parse the Data store name to get the Info store name
			String infoStoreName = oldData.getParentAlbumName();
			infoStoreName = ImageAccessor.INFO_LABEL
			+ infoStoreName.substring(ImageAccessor.ALBUM_LABEL
					.length());
			infoStore = RecordStore.openRecordStore(infoStoreName, false);

			ImageUtil converter = new ImageUtil();
			byte[] imageDataBytes = converter.getBytesFromImageInfo(newData);

			infoStore.setRecord(oldData.getRecordId(), imageDataBytes, 0,
					imageDataBytes.length);

		} catch (RecordStoreException rse) {
			throw new PersistenceMechanismException(rse);
		}

		// Update the Hashtable 'cache'
		this.setImageInfo(oldData.getImageLabel(), newData);

		try {
			infoStore.closeRecordStore();
		} catch (RecordStoreNotOpenException e) {
			//No problem if the RecordStore is not Open
		} catch (RecordStoreException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}

		return success;
	}
}