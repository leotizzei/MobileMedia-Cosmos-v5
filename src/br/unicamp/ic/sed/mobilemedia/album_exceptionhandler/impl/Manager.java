package br.unicamp.ic.sed.mobilemedia.album_exceptionhandler.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class Manager implements IManager{

	Hashtable reqInterfaceMap = new Hashtable();
	Hashtable provInterfaceMap = new Hashtable();
	
	
	public Manager() {
		provInterfaceMap.put("IExceptionHandler", new IAdapterAlbumEH());
	}
	
	public String[] getProvidedInterfaces(){
	   Vector provInterfaceList = new Vector();
	  provInterfaceList.addElement("IAdapterAlbumEH");
	     
	   
	   return convertListToArray(provInterfaceList.elements());
	}
	
	public String[] getRequiredInterfaces(){
	
		return convertListToArray(reqInterfaceMap.keys());
	}
	
	public Object getProvidedInterface(String name){
		return this.provInterfaceMap.get( name );
	  
	}
	
	public void setRequiredInterface(String name, Object adapter){
		reqInterfaceMap.put(name,adapter);
	}
	
	public Object getRequiredInterface(String name){
	   return reqInterfaceMap.get(name);
	}

	private String[] convertListToArray(Enumeration stringEnum){
		Vector stringVector = new Vector();
		for (Enumeration iter = stringEnum; iter.hasMoreElements();) {
			String element = (String) iter.nextElement();
			stringVector.addElement(element);
		}
		
		String[] stringArray = new String[stringVector.size()];
		for (int i=0; i < stringVector.size(); i++){
			stringArray[i] = (String) stringVector.elementAt(i);
		}
		return stringArray;
	}
}



