package br.unicamp.ic.sed.mobilemedia.album.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IExceptionHandler;

class Handler {
	
	protected void handle(Exception e){
		IManager manager = ComponentFactory.createInstance();
		IExceptionHandler handler = (IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
		
		handler.handle(e);
	}

}
