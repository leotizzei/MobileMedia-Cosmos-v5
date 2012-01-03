//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_filesystem;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.IImageData;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IFilesystem;

public class IAdapterFilesystemSms implements IFilesystem {

	public String[] getAlbumNames() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem
			= (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)
				manager.getRequiredInterface("IFilesystem");
		
		return filesystem.getAlbumNames();
	}

	public IImageData getImageInfo(String imageName)
			throws ImageNotFoundException, NullAlbumDataReference {
		
		try{
			IManager manager = ComponentFactory.createInstance();
			br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem
				= (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)
					manager.getRequiredInterface("IFilesystem");
			
			return filesystem.getImageInfo(imageName);
		
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException e) {
			throw new ImageNotFoundException( e.getMessage() );
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference e) {
			throw new NullAlbumDataReference( e );
		}
		
	}

	public byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {
		
		try{
			IManager manager = ComponentFactory.createInstance();
			br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem
				= (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)
					manager.getRequiredInterface("IFilesystem");
		
			return filesystem.loadImageBytesFromRMS(recordName, imageName, recordId);
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}
		
	}

}
//#endif