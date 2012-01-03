package br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl;

import br.unicamp.ic.sed.mobilemedia.mainuimidlet_exceptionhandler.impl.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}



