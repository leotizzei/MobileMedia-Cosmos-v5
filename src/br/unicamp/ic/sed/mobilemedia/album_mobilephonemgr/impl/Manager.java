package br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

class Manager implements IManager{

	Hashtable requiredInterfaces = new Hashtable();
	Hashtable providedInterfaces = new Hashtable();

	public Manager() {
		System.out.println(this.getClass().getName()+"::Manager begin");
		providedInterfaces.put("IAlbum", new IAlbumAdapter(this));
		System.out.println(this.getClass().getName()+"::Manager end");
	}

	public String[] getProvidedInterfaces() {
		Enumeration keys = this.providedInterfaces.keys();
		return this.convertEnumerationToArray(keys); 
	}


	public String[] getRequiredInterfaces() {
		Enumeration keys = this.requiredInterfaces.keys();
		return this.convertEnumerationToArray(keys);
	}


	private String[] convertEnumerationToArray(Enumeration stringEnum){
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


	public Object getProvidedInterface(String name){
		
		return this.providedInterfaces.get(name);
	}

	public void setRequiredInterface(String name, Object adapter){
		this.requiredInterfaces.put(name,adapter);
	}

	public Object getRequiredInterface(String name){
		return this.requiredInterfaces.get(name);
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



