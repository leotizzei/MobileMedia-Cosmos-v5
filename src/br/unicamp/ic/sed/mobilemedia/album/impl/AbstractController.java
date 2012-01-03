/*
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 * MobileMedia-Cosmos-OO-v4
 * 
 */

/*begin - added in MobileMedia-Cosmos-OO-v4*/
package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IPhoto;

abstract class AbstractController implements CommandListener, ControllerInterface {

	//Define the basic screens
	private AlbumListScreen albumListScreen;
	
	private MIDlet midlet;

	//Define a successor to implement the Chain of Responsibility design pattern
	private ControllerInterface nextController;

	/**
	 * @param midlet
	 * @param nextController
	 * @param albumData
	 * @param albumListScreen
	 * @param currentScreenName
	 */
	public AbstractController(MIDlet midlet, AlbumListScreen screen) {
		this.midlet = midlet;
		this.albumListScreen = screen;
		// [EF] Senario 04: A singleton ScreenSingleton was created in order to all other access it. 
		// [EF] I think some data need to be unique (e.g. currentScreenName) to make them consistent for all controllers.
	}
	
	/* 
	 * Handle events. For now, this just passes control off to a 'wrapper'
	 * so we can ensure, in order to use it in the aspect advice
	 * (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		
			this.postCommand(c);
		
	}

	/**
	 * [EF] Scenario 04: Just forward method.
	 * @return the currentStoreName
	 *//*
	public String getCurrentStoreName() {
		return ScreenSingleton.getInstance().getCurrentStoreName();
	}
*/
	/**
	 * @return the albumListScreen
	 */
	protected AlbumListScreen getAlbumListScreen() {
		return this.albumListScreen;
	}


    /**
     * [EF] RENAMED in Scenario 04: remove "Name". Purpose: avoid method name conflict
	 * Get the current screen name that is displayed
	 */
	protected Displayable getCurrentScreen() {
        return Display.getDisplay(midlet).getCurrent();
    }
	
    protected MIDlet getMidlet() {
		return midlet;
	} 

    /**
	 * @return the nextController
	 */
    protected ControllerInterface getNextController() {
		return nextController;
	} 
    
    /* (non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#postCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
    public boolean postCommand(Command command) {
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
			        IPhoto photo = (IPhoto) manager.getRequiredInterface("IPhoto");
			        return photo.postCommand(command);
			    }
			}
			
		} catch (PersistenceMechanismException e) {
			Handler handler = new Handler();
			handler.handle( new PersistenceMechanismException( "Impossible to load album: " ));
		} catch (InvalidPhotoAlbumNameException e) {
			Handler handler = new Handler();
			handler.handle(e);
		}
        return false;

	} 

	
	
	protected void setAlbumListAsCurrentScreen(Alert a) {
    	setCurrentScreen(a, albumListScreen);
    }

	/**
	 * Set the current screen for display, after alert
	 */
	protected void setCurrentScreen(Alert a, Displayable d) {
    	System.out.println("midlet = "+midlet);
        Display.getDisplay(midlet).setCurrent(a, d);
    }

	/**
	 * Set the current screen for display
	 */
	protected void setCurrentScreen(Displayable d) {
        System.out.println("[AbstractController.setCurrentScreen(..)] midlet = "+midlet);
    	Display.getDisplay(midlet).setCurrent(d);
    }

	protected void setMidlet(MIDlet midlet) {
		this.midlet = midlet;
	}

	/**
	 * @param nextController the nextController to set
	 */
	protected void setNextController(ControllerInterface nextController) {
		System.out.println("[AbstractController.setNextController] nextController="+nextController.getClass().getName());
		this.nextController = nextController;
	}
}
/*end - added in MobileMedia-Cosmos-OO-v4*/