
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;

public class AddPhotoToAlbum extends Form {
	
	TextField labeltxt = new TextField("Photo label", "", 15, TextField.ANY);
	TextField photopathtxt = new TextField("Path", "", 20, TextField.ANY);
	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	Image image = null;
	//#endif	
	
	Command ok;
	Command cancel;
	private byte[] imageBytes;

	public AddPhotoToAlbum(String title) {
		super(title);
		this.append(labeltxt);
		this.append(photopathtxt);
		ok = new Command("Save Photo", Command.SCREEN, 0);
		cancel = new Command("Cancel", Command.EXIT, 1);
		this.addCommand(ok);
		this.addCommand(cancel);
		System.out.println("AddPhotoToAlbum created");
	}
	
	public String getPhotoName(){
		System.out.println("[AddPhotoToAlbum.getPhotoName] "+labeltxt.getString());
		return labeltxt.getString();
	}
	
	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	public void setPhotoName(String photoName) {
		labeltxt.setString( photoName );
	}
	
	public String getPath() {
		System.out.println("[AddPhotoToAlbum.getPath] "+labeltxt.getString());
		return photopathtxt.getString();
	}

	/**
	 * [EF] Added in scenario 05 in order to reuse this screen in the Copy Photo functionality
	 * @param photoName
	 */
	public void setLabelPhotoPath(String label) {
		photopathtxt.setLabel(label);
	}
	
	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	//#endif

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	
	public byte[] getImageBytes(){
		return this.imageBytes;
	}
}
