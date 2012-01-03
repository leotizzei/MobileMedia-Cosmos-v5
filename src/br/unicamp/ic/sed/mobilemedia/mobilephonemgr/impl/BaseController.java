/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;
import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IMobileResources;


/**
 * @author tyoung
 *
 * This is the base controller class used in the MVC architecture.
 * It controls the flow of screens for the MobilePhoto application.
 * Commands handled by this class should only be for the core application
 * that runs on any MIDP platform. Each device or class of devices that supports
 * optional features will extend this class to handle feature specific commands.
 *  
 */
class BaseController extends AbstractController {

	// [EF] Attributes albumController and photoController were commented because 
	// I'm not sure which one is the best solution: 
	// [EF] (i) Declare controllers here and have only one instance or
	// [EF] (ii) create controllerns when needed (current solution)
	//	private AlbumController albumController;
	//	private PhotoController photoController;

	/**
	 * Pass a handle to the main Midlet for this controller
	 * @param midlet
	 */


	public BaseController(MIDlet midlet) {

		super( midlet );
		

	}

	/**
	 * Initialize the controller
	 */
	protected void init() {
		IManager manager = ComponentFactory.createInstance();

		IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");

		album.initAlbumListScreen();

	}

	/* 
	 * TODO [EF] Why this method receives Displayable and never uses?
	 */
	public boolean handleCommand(Command command) {


		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		//Can this controller handle the desired action?
		//If yes, handleCommand will return true, and we're done
		//If no, handleCommand will return false, and postCommand
		//will pass the request to the next controller in the chain if one exists.



		/** Case: Exit Application **/
		if (label.equals("Exit")) {
			IManager manager = ComponentFactory.createInstance();
			IMobileResources mobileResources = (IMobileResources) manager.getRequiredInterface("IMobileResources");
			System.out.println("mobileResources="+mobileResources);
			mobileResources.destroyApp(true);
			return true;

			/** Case: Go to the Previous or Fallback screen * */
		} else if (label.equals("Back")) {
			return goToPreviousScreen();

			/** Case: Cancel the current screen and go back one* */
		} else if (label.equals("Cancel")) {
			return goToPreviousScreen();

		}

		//If we couldn't handle the current command, return false
		return false;
	}

	/**
	 * BaseController handles actions in the IMAGELIST_SCREEN
	 * @return
	 */
	public boolean goToPreviousScreen() {
		System.out.println("<* BaseController.goToPreviousScreen() *>");
		
		IManager manager = ComponentFactory.createInstance();

		IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");
		
		album.initAlbumListScreen();
		
		return true;
		
	}
}
