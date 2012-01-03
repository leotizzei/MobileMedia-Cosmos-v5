/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.main.spec.prov;

import javax.microedition.lcdui.Command;


/**
 * @author tyoung
 *
 * This interface must be implemented by all sub-Controllers used by MobilePhoto.
 * It is for the Chain of Responsibility design pattern, and defined two methods.
 * The entry point to handle a command using the implementing controller is the 
 * postCommand() method. postCommand() calls handleCommand, which will return True 
 * if the current controller has handled the command. handleCommand will return false otherwise.
 * If handleCommand returns false, then postCommand will attempt to call the next controller
 * in the chain, if one exists.
 */
public interface ControllerInterface {

    public boolean postCommand(Command command);
	
    public ControllerInterface getNextController();
    
    public void setNextController(ControllerInterface controller );
       
}
