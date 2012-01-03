//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms.spec.prov;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.main.spec.prov.ControllerInterface;


public interface ISms extends ControllerInterface{

	public boolean postCommand(Command command);
	
}
//#endif