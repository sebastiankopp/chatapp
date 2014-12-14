package chatBase.model;

import java.util.List;
/**
 * Repräsentiert eine Werbemessage mit Inhalt (Text), Schriftarten (wie CSS, falls eine nicht unterstützt wird,
 * dann halt eine andere) und Schriftgröße
 * 
 * @author Sebastian Kopp
 *
 */
public class Advertisement implements java.io.Serializable{
	private static final long serialVersionUID = -1608837701366114900L;
	private List<String> fontFamilies; // zugelassene Schriftarten
	private int fontSize;	// Schriftgröße
	private String adText;
	/**
	 * @return the fontSize
	 */
	public Advertisement(){}
	public Advertisement(String text){
		fontFamilies = null;
		adText = text;
		fontSize = 12;
	}
	public Advertisement(String text, List<String> ffam, int fsize){
		fontFamilies = ffam;
		fontSize = fsize;
		adText = text;
	}
	public int getFontSize() {
		return fontSize;
	}
	/**
	 * @param fontSize the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	/**
	 * @return the fontFamilies
	 */
	public List<String> getFontFamilies() {
		return fontFamilies;
	}
	/**
	 * @param fontFamilies the fontFamilies to set
	 */
	public void setFontFamilies(List<String> fontFamilies) {
		this.fontFamilies = fontFamilies;
	}
	/**
	 * @return the adText
	 */
	public String getAdText() {
		return adText;
	}
	/**
	 * @param adText the adText to set
	 */
	public void setAdText(String adText) {
		this.adText = adText;
	}
}