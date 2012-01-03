//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_photo;

import javax.microedition.lcdui.Command;


import br.unicamp.ic.sed.mobilemedia.main.spec.prov.ControllerInterface;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.ISms;

public class IAdapterSmsPhoto implements ISms {

	public boolean postCommand(Command command) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		return sms.postCommand(command);
	}
	
	public ControllerInterface getNextController() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		return sms.getNextController();
	}

	public void setNextController(ControllerInterface controller) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		sms.setNextController(controller);
	}

}
//#endif