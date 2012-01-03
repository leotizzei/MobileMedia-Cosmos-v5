package br.unicamp.ic.sed.mobilemedia.photo.impl;

import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IExceptionHandler;

class Handler {
	
	protected void handle(Exception e){
		IManager manager = ComponentFactory.createInstance();
		IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
		handler.handle(e);
	}

}
