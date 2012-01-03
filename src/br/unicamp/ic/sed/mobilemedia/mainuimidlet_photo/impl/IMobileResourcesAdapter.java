package br.unicamp.ic.sed.mobilemedia.mainuimidlet_photo.impl;

import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobileResources;

class IMobileResourcesAdapter implements IMobileResources{

	public MIDlet getMainMIDlet() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMobileResources iMobileResources = (br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMobileResources)manager.getRequiredInterface("IMobileResources");
		return iMobileResources.getMainMIDlet();
	}

	public void destroyApp(boolean unconditional) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMobileResources iMobileResources = (br.unicamp.ic.sed.mobilemedia.main.spec.prov.IMobileResources)manager.getRequiredInterface("IMobileResources");
		iMobileResources.destroyApp(unconditional);
		
	}	
}