/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group
 *
 * date: February 2009
 * 
 */
package br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.prov.IManager;

class Manager  implements IManager{

	Hashtable providedInterfaces;
	Hashtable requiredInterfaces;

	

	Manager(){

		System.out.println(this.getClass().getName()+" constructor");
		providedInterfaces = new Hashtable();
		requiredInterfaces = new Hashtable();
		this.setProvidedInterface("IMobilePhone", new IMobilePhoneMgrFacade());

	}

	public Object getProvidedInterface(String name){
		return this.providedInterfaces.get(name);
	}

	public Object getRequiredInterface(String name){
		return this.requiredInterfaces.get(name);
	}

	public void setProvidedInterface(String name, Object facade){
		this.providedInterfaces.put(name, facade);
	}

	public void setRequiredInterface(String name,Object facade){
		if(name == null)
			System.err.println("name is null");
		if(facade == null)
			System.err.println("br.unicamp.ic.sed.mobilemedia.mobilephonemgr.impl.Manager facade is null");
		if(requiredInterfaces == null)
			System.err.println("requiredinterfaces is null");
		this.requiredInterfaces.put(name, facade);
		System.out.println("[mobilephonemgr.Manager] name = "+name+" facade="+facade);
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


	
	public String[] getProvidedInterfaces() {
		Enumeration keys = this.providedInterfaces.keys();
		return this.convertEnumerationToArray(keys); 
	}


	public String[] getRequiredInterfaces() {
		Enumeration keys = this.requiredInterfaces.keys();
		return this.convertEnumerationToArray(keys);
	}
	
	
}



