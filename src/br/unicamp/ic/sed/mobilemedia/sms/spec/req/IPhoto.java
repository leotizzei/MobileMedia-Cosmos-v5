//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms.spec.req;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;

public interface IPhoto{
	
	
	
	public boolean postCommand(Command c);
	
	public void initPhotoViewScreen(Image image, byte[] img);
	
	/**[MD][Cosmos][SMS]*/
	public String getSelectedImageName();
}

//#endif