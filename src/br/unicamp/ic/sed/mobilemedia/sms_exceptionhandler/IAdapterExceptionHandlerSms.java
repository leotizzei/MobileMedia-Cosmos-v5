//#ifdef includeSmsFeature
package br.unicamp.ic.sed.mobilemedia.sms_exceptionhandler;

import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IExceptionHandler;

public class IAdapterExceptionHandlerSms implements IExceptionHandler {

	public void handle(Exception exception) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler handler = (br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IExceptionHandler) manager.getRequiredInterface("IExceptionHandler");
		handler.handle( exception );
	}

}
//#endif