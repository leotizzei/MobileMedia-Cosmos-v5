package br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IExceptionHandler;

class IAdapterAlbumEH implements IExceptionHandler {

	public void handle(Exception exception) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler handler = (br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler)manager.getRequiredInterface("IExceptionHandler");
		handler.handle(exception);

	}

}
