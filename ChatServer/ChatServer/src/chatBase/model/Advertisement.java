package chatBase.model;

import java.util.List;

public class Advertisement implements java.io.Serializable{
	private static final long serialVersionUID = -1608837701366114900L;
	private List<String> fontFamilies; // zugelassene Schriftarten
	private int fontSize;	// Schriftgröße
	/**
	 * @return the fontSize
	 */
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
}