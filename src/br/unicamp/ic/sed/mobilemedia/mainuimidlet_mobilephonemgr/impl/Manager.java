package br.unicamp.ic.sed.mobilemedia.mainuimidlet_mobilephonemgr.impl;

import java.util.*;

class Manager implements IManager{

	private Hashtable requiredInterfaces = new Hashtable();
	private Hashtable providedInterfaces = new Hashtable();

	Manager(){
		providedInterfaces.put("IMobileResources", new IAdapterMobilePhoneMgrMainMidlet(this));
	}
	
	public String[] getProvidedInterfaces(){
		Vector provInterfaceList = new Vector();
		provInterfaceList.addElement("IMobileResources");


		return convertListToArray(provInterfaceList.elements());
	}

	public String[] getRequiredInterfaces(){

		return convertListToArray(requiredInterfaces.keys());
	}

	public Object getProvidedInterface(String name){
		
		return this.providedInterfaces.get(name);
	}

	public void setRequiredInterface(String name, Object adapter){
		requiredInterfaces.put(name,adapter);
	}

	public Object getRequiredInterface(String name){
		return requiredInterfaces.get(name);
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



