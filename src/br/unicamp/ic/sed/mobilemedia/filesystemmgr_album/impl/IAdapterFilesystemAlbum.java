package br.unicamp.ic.sed.mobilemedia.filesystemmgr_album.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;


public class IAdapterFilesystemAlbum implements IFilesystem {


	public void createNewPhotoAlbum(String albumName) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException, br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try{
		filesystem.createNewPhotoAlbum(albumName);
		}catch(InvalidPhotoAlbumNameException e){
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException( e.getMessage() );
		}catch(PersistenceMechanismException e){
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

	public void deletePhotoAlbum(String albumName) throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException
	 {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.deletePhotoAlbum(albumName);
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

	public String[] getAlbumNames() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		return filesystem.getAlbumNames();
	}

	public void resetImageData() throws br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException  {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.resetImageData();
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}

}
