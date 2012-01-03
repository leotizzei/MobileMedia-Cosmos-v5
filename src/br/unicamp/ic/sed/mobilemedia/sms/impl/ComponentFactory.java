//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms.impl;

import br.unicamp.ic.sed.mobilemedia.sms.spec.prov.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}
//#endif


