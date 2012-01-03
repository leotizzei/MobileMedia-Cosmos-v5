//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl;


import br.unicamp.ic.sed.mobilemedia.mainuimidlet_sms.impl.IManager;

public class ComponentFactory {

	private static IManager manager = null;

	public static IManager createInstance(){
	
		if (manager==null)
			manager = new Manager();
		
		return manager;
	}
}
//#endif


