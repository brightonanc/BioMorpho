package bioMorpho.gui.lifeformBasic;

/**
 * @author Brighton Ancelin
 *
 */
public interface IModelElement {
	
	public void onGuiUpdate();
	/*
	 * This method MUST be run regardless of mousePressed(x, y, button) return values to ensure text fields work properly
	 */
	public void mousePressedTextFields(int x, int y, int button);
	public boolean mousePressed(int x, int y, int button);
	public boolean keyPressed(char keyChar, int keyCode);
}
