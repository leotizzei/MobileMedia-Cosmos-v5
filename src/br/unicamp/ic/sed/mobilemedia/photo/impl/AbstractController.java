/*
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 * MobileMedia-Cosmos-OO-v4
 * 
 */

/*begin - added in MobileMedia-Cosmos-OO-v4*/
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;


import br.unicamp.ic.sed.mobilemedia.main.spec.prov.ControllerInterface;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.UnavailablePhotoAlbumException;

import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.IMobilePhone;

//import br.unicamp.ic.sed.mobilemedia.album.impl.AlbumListScreen;

public abstract class AbstractController implements CommandListener, ControllerInterface {

	private MIDlet midlet;

	//Define a successor to implement the Chain of Responsibility design pattern
	private ControllerInterface nextController;

	/*private AlbumData albumData;*/

	//Define the basic screens


	/**
	 * @param midlet
	 * @param nextController
	 * @param albumData
	 * @param albumListScreen
	 * @param currentScreenName
	 */
	public AbstractController(MIDlet midlet) {
		this.midlet = midlet;

		// [EF] Senario 04: A singleton ScreenSingleton was created in order to all other access it. 
		// [EF] I think some data need to be unique (e.g. currentScreenName) to make them consistent for all controllers.
	}

	/* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#postCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public boolean postCommand(Command command)  {
		System.out.println("AbstractController::postCommand - Current controller is: " + this.getClass().getName());
		System.out.println("Command label="+command.getLabel());
		//If the current controller cannot handle the command, pass it to the next
		//controller in the chain.
		try {
			if (handleCommand(command) == false) {
				ControllerInterface next = this.getNextController();
				if (next != null) {
					System.out.println("Passing to next controller in chain: " + next.getClass().getName());
					return next.postCommand(command);
				} else {
					System.out.println("AbstractController::postCommand - Reached top of chain. No more handlers for command: " + command.getLabel());
					IManager manager = ComponentFactory.createInstance();
					IMobilePhone mobilePhone = (IMobilePhone) manager.getRequiredInterface("IMobilePhone");
					return mobilePhone.postCommand(command);

				}
			}
		} catch (ImageNotFoundException e) {
			Handler handler = new Handler();
			handler.handle(e);
		} catch (NullAlbumDataReference e) {
			Handler handler = new Handler();
			handler.handle(e);
		} catch (UnavailablePhotoAlbumException e) {
			Handler handler = new Handler();
			handler.handle(e);
		} catch (InvalidImageDataException e) {
			Handler handler = new Handler();
			handler.handle(e);
		} catch (PersistenceMechanismException e) {
			Handler handler = new Handler();
			handler.handle(e);
		}
		return false;

	}

	

	protected abstract boolean handleCommand(Command command)throws ImageNotFoundException, NullAlbumDataReference, PersistenceMechanismException, InvalidImageDataException, UnavailablePhotoAlbumException;

	/* 
	 * Handle events. For now, this just passes control off to a 'wrapper'
	 * so we can ensure, in order to use it in the aspect advice
	 * (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		postCommand(c);
	}


	protected void setAlbumListAsCurrentScreen(Alert a) {
		setCurrentScreen(a);
	}

	/**
	 * Set the current screen for display, after alert
	 */
	protected void setCurrentScreen(Alert a, Displayable d) {
		System.out.println("midlet = "+midlet);
		Display.getDisplay(midlet).setCurrent(a, d);
	} 

	/**
	 * [EF] RENAMED in Scenario 04: remove "Name". Purpose: avoid method name conflict
	 * Get the current screen name that is displayed
	 */
	protected Displayable getCurrentScreen() {
		return Display.getDisplay(midlet).getCurrent();
	} 

	/**
	 * Set the current screen for display
	 */
	protected void setCurrentScreen(Displayable d) {
		System.out.println("[AbstractController.setCurrentScreen(..)] midlet = "+midlet);
		Display.getDisplay(midlet).setCurrent(d);
	} 

	/**
	 * @return the albumData
	 *//*
	public AlbumData getAlbumData() {
		return albumData;
	}

	  *//**
	  * @param albumData the albumData to set
	  *//*
	public void setAlbumData(AlbumData albumData) {
		this.albumData = albumData;
	}*/

	/**
	 * @return the nextController
	 */
	public ControllerInterface getNextController() {
		return nextController;
	}

	/**
	 * @param nextController the nextController to set
	 */
	public void setNextController(ControllerInterface nextController) {
		if( nextController != null)
			System.out.println("[AbstractController.setNextController] nextController="+nextController.getClass().getName());
		else
			System.out.println("nextController is null!!!");
		this.nextController = nextController;
	}

	/**
	 * [EF] Scenario 04: Just forward method.
	 * @return the currentStoreName
	 */
	protected String getCurrentStoreName() {
		return ScreenSingleton.getInstance().getCurrentStoreName();
	}

	/**
	 * @return the albumListScreen
	 */
	/*protected AlbumListScreen getAlbumListScreen() {
		return this.albumListScreen;
	}*/

	protected MIDlet getMidlet() {
		return midlet;
	}

	protected void setMidlet(MIDlet midlet) {
		this.midlet = midlet;
	}
}
/*end - added in MobileMedia-Cosmos-OO-v4*/