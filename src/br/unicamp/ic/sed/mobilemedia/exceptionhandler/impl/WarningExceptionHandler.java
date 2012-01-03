package br.unicamp.ic.sed.mobilemedia.exceptionhandler.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.exceptionhandler.spec.req.IMobileResources;




class WarningExceptionHandler{

	protected void handle(Exception exception) {
		
		System.out.println( "[WarningExceptionHandler] Handling: " + exception.getMessage() );
		
		
		IManager manager = ComponentFactory.createInstance();
		IMobileResources mobile = (IMobileResources)manager.getRequiredInterface( "IMobileResources" );
		
		MIDlet midlet = mobile.getMainMIDlet();
		
		exception.printStackTrace();
		Alert alert = new Alert( "Error" , exception.getMessage() , null, AlertType.ERROR );
		alert.setTimeout( 5000 );
		
		Displayable currentDisplay = Display.getDisplay( midlet ).getCurrent();
		Display.getDisplay( midlet ).setCurrent(alert , currentDisplay );
	}
}
