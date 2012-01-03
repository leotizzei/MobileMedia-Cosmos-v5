
package br.unicamp.ic.sed.mobilemedia.photo_mobilephonemgr.impl;

import javax.microedition.lcdui.Command;


import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IPhoto;


class IPhotoAdapter implements IPhoto{

	public boolean postCommand(Command c) {
		IManager mgr = ComponentFactory.createInstance();
		IPhoto photo = (IPhoto) mgr.getRequiredInterface("IPhoto");
		return photo.postCommand(c);
	}
}