
//#ifdef includeSmsFeature 
package br.unicamp.ic.sed.mobilemedia.sms.spec.req;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype ImageData by the interface IImageData
 * that is implemented by the ImageData datatype.
 * 
 *
 */
public interface IFilesystem{
	  
	public IImageData getImageInfo( String imageName ) throws ImageNotFoundException, NullAlbumDataReference;

	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	public byte[] loadImageBytesFromRMS(String recordName, String imageName, int recordId) throws PersistenceMechanismException; 
	
	}

//#endif