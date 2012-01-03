//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_photo;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IPhoto;

public class IAdapterPhotoSms implements IPhoto {

	public boolean postCommand(Command c) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		return photo.postCommand(c);
	}

	public void initPhotoViewScreen(Image image , byte[] img) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		photo.initPhotoViewScreen(image, img);
	}

	public String getSelectedImageName() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		return photo.getSelectedImageName();
	}

}
//#endif