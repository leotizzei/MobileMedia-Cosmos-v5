package br.unicamp.ic.sed.mobilemedia.photo_album.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IPhoto;

public class IAdapterPhotoAlbum implements IPhoto {

	public boolean postCommand(Command c) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = (br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)manager.getRequiredInterface("IPhoto");
		return photo.postCommand(c);
	}

}
