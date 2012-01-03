   
package br.unicamp.ic.sed.mobilemedia.photo_mobilephonemgr.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobilePhone;

class IMobilePhoneAdapter implements IMobilePhone{
	
	public boolean postCommand ( Command comand ){
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone mobilePhone;
		
		IManager manager = ComponentFactory.createInstance();
		mobilePhone = (br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone)manager.getRequiredInterface("IMobilePhone");
		return mobilePhone.postCommand(comand);
	}
	
	//[MD][cosmos add in v5]
	public void goToPreviousScreen() {
		br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone mobilePhone;
	
		IManager manager = ComponentFactory.createInstance();
		mobilePhone = (br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IMobilePhone)manager.getRequiredInterface("IMobilePhone");
		
		mobilePhone.gotoPreviousScreen();
	} 

}