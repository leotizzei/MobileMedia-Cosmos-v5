   
package br.unicamp.ic.sed.mobilemedia.album_mobilephonemgr.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.mobilephonemgr.spec.req.IAlbum;


class IAlbumAdapter implements IAlbum{
	
	private IManager manager;
	
	public IAlbumAdapter(IManager mgr) {
		System.out.println(this.getClass().getName()+" constructor");
		this.manager = mgr;
	}
		
	public void initAlbumListScreen (  ){
		
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum iAlbum;
		iAlbum = (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum) manager.getRequiredInterface("IAlbum");
		iAlbum.initAlbumListScreen();
	}
}