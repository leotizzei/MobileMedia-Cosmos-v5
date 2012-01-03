/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.album.impl;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import br.unicamp.ic.sed.mobilemedia.album.spec.dt.Constants;
import br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.album.spec.prov.IManager;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMobileResources;





class AlbumController extends AbstractController {

	private MIDlet midlet;
	//private AlbumListScreen albumListScreen;


	public AlbumController(MIDlet midlet, AlbumListScreen albumListScreen) {
		super(midlet, albumListScreen);
		this.midlet = midlet;
		//this.albumListScreen = albumListScreen;

	}

	/*(non-Javadoc)
	 * @see ubc.midp.mobilephoto.core.ui.controller.ControllerInterface#handleCommand(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)*/

	public boolean handleCommand(Command command) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {
		String label = command.getLabel();
		System.out.println( "<*"+this.getClass().getName()+".handleCommand() *> " + label);

		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");
		System.out.println("[AlbumController] filesystem = "+filesystem);
		if (label.equals("Reset")) {
			System.out.println("<* BaseController.handleCommand() *> Reset Photo Album");			
			resetImageData();
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.ALBUMLIST_SCREEN);
			this.goToPreviousScreen();
			return true;
			/** Case: Create PhotoAlbum **/
		}else if (label.equals("New Photo Album")) {
			ScreenSingleton.getInstance().setCurrentScreenName(Constants.NEWALBUM_SCREEN);
			NewLabelScreen newLabelScreen =  new NewLabelScreen("Add new Photo Album", NewLabelScreen.NEW_ALBUM);
			newLabelScreen.setCommandListener(this);
			this.setCurrentScreen(newLabelScreen);
			newLabelScreen = null;
			return true;
			/** Case: Delete Album Photo**/
		}else if (label.equals("Delete Album")) {
			this.deletePhotoAlbum();
			return true;	
			/**
			 *  TODO [EF] I think this confirmation questions are complicating the implementation
			 *  [EF] How do you know that "Yes - Delete" is to delete Photo Album instead of Photo?
			 *  Case: Yes delete Photo Album  **/
		}else if (label.equals("Yes - Delete")) {

			//delete photo album
			//begin - modified in MobileMedia-Cosmos-OO-v4

			String albumName = ScreenSingleton.getInstance().getCurrentStoreName();
			filesystem.deletePhotoAlbum(albumName);
			//end - modified in MobileMedia-Cosmos-OO-v4

			goToPreviousScreen();
			return true;	
			/** 
			 * [EF] Same question. How do you know that "No - Delete" is to delete Photo Album instead of Photo?
			 * Case: No delete Photo Album **/
		}else if (label.equals("No - Delete")) {
			goToPreviousScreen();
			return true;	
			/** 
			 * [EF] Again, see [EF] comments above.
			 * Case: Save new Photo Album  **/
		} else if (label.equals("Save new label")) {

			Displayable displayable = getCurrentScreen();
			System.out.println("[AlbumController] displayable = "+displayable);
			if ( displayable instanceof NewLabelScreen) {
				//begin - modified in MobileMedia-Cosmos-OO-v4
				NewLabelScreen currentScreen = (NewLabelScreen)getCurrentScreen();
				if (currentScreen.getFormType() == NewLabelScreen.NEW_ALBUM){
					String albumName = currentScreen.getLabelName();
					System.out.println("[AlbumController] filesystem = "+filesystem);
					filesystem.createNewPhotoAlbum(albumName);
				}
				else{
					if (currentScreen.getFormType() == NewLabelScreen.LABEL_PHOTO) {
						//do nothing?
					}
				}
				//end - modified in MobileMedia-Cosmos-OO-v4
			}

			goToPreviousScreen();
			return true;
		}

		return false;
	}

	/**
	 * This option is mainly for testing purposes. If the record store
	 * on the device or emulator gets into an unstable state, or has too 
	 * many images, you can reset it, which clears the record stores and
	 * re-creates them with the default images bundled with the application 
	 * @throws PersistenceMechanismException 
	 */
	private void resetImageData() throws PersistenceMechanismException {
		//begin - modified in MobileMedia-Cosmos-OO-v4
		IManager manager = ComponentFactory.createInstance();
		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		filesystem.resetImageData();

		//end - modified in MobileMedia-Cosmos-OO-v4

		//Clear the names from the album list
		for (int i = 0; i < getAlbumListScreen().size(); i++) {
			getAlbumListScreen().delete(i);
		}

		//Get the default ones from the album
		//begin - modified in MobileMedia-Cosmos-OO-v4
		String[] albumNames = filesystem.getAlbumNames();
		//end - modified in MobileMedia-Cosmos-OO-v4

		for (int i = 0; i < albumNames.length; i++) {
			if (albumNames[i] != null) {
				//Add album name to menu list
				getAlbumListScreen().append(albumNames[i], null);
			}
		}
		setCurrentScreen(getAlbumListScreen());
	}

	private void goToPreviousScreen() {
		System.out.println("<* AlbumController.goToPreviousScreen() *>");

		//begin - modified in MobileMedia-Cosmos-OO-v4
		IManager manager = ComponentFactory.createInstance();

		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		String[] albumNames = filesystem.getAlbumNames();

		AlbumListScreen albumListScreen = this.getAlbumListScreen();

		albumListScreen.repaintListAlbum( albumNames );
		//end - modified in MobileMedia-Cosmos-OO-v4

		this.setCurrentScreen( albumListScreen );

		ScreenSingleton.getInstance().setCurrentScreenName(Constants.ALBUMLIST_SCREEN);

		albumListScreen.setCommandListener(this);
	}

	/*protected void initAlbum(){
		//Get all MobilePhoto defined albums from the record store
		IManager manager = ComponentFactory.createInstance();

		IFilesystem filesystem = (IFilesystem) manager.getRequiredInterface("IFilesystem");

		String[] albumNames = filesystem.getAlbumNames();

		IAlbum album = (IAlbum) manager.getRequiredInterface("IAlbum");

		AlbumListScreen albumListScreen = super.getAlbumListScreen();

		album.initAlbumListScreen( albumNames );

		albumListScreen.setCommandListener( this );
	}*/

	/**
	 * Set the current screen for display
	 */
	public void setCurrentScreen(Displayable d) {
		IManager manager = ComponentFactory.createInstance();

		IMobileResources iMobileResources = (IMobileResources)manager.getRequiredInterface("IMobileResources");

		MIDlet midlet = iMobileResources.getMainMIDlet();

		Display.getDisplay(midlet).setCurrent(d);

	} 

	/**
	 * Set the current screen for display
	 */
	protected void setCurrentScreen(Alert a , Displayable d) {
		IManager manager = ComponentFactory.createInstance();

		IMobileResources iMobileResources = (IMobileResources)manager.getRequiredInterface("IMobileResources");

		MIDlet midlet = iMobileResources.getMainMIDlet();

		Display.getDisplay(midlet).setCurrent(a , d);
	} 

	private void deletePhotoAlbum(){
		System.out.println("Delete Photo Album here");
		List down = (List) Display.getDisplay(midlet).getCurrent();
		ScreenSingleton.getInstance().setCurrentScreenName(Constants.CONFIRMDELETEALBUM_SCREEN);
		ScreenSingleton.getInstance().setCurrentStoreName(down.getString(down.getSelectedIndex()));
		String message = "Would you like to remove the album "+ScreenSingleton.getInstance().getCurrentStoreName();
		Alert deleteConfAlert = new Alert("Delete Photo Album", message,null,AlertType.CONFIRMATION);
		deleteConfAlert.setTimeout(Alert.FOREVER);
		deleteConfAlert.addCommand(new Command("Yes - Delete", Command.OK, 2));
		deleteConfAlert.addCommand(new Command("No - Delete", Command.CANCEL, 2));
		setAlbumListAsCurrentScreen(deleteConfAlert);
		deleteConfAlert.setCommandListener(this);
	}

}
