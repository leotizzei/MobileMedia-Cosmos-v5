package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

import javax.microedition.lcdui.Command;


public interface IMobilePhone{

	public boolean postCommand ( Command comand ); 
	
	//[MD][cosmos add in v5]
	public void goToPreviousScreen();
}