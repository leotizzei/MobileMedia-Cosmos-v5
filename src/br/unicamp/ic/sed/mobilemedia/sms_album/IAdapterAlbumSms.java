//#ifdef includeSmsFeature
package br.unicamp.ic.sed.mobilemedia.sms_album;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IAlbum;

public class IAdapterAlbumSms implements IAlbum {

	
	public void initAlbumListScreen() {
		IManager manager = ComponentFactory.createInstance();
		
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum album;
		album = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum)
			manager.getRequiredInterface("IAlbum");
		
		album.initAlbumListScreen();
	}
}
//#endif