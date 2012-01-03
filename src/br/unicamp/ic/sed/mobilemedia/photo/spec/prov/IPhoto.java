package br.unicamp.ic.sed.mobilemedia.photo.spec.prov;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;

public interface IPhoto{
	
	public boolean postCommand(Command c);
	
	/**[MD][Cosmos][SMS]*/
	//#ifdef includeSmsFeature 
	
	public String getSelectedImageName();
	public void initPhotoViewScreen(Image image, byte[] img);
	
	//#endif
}