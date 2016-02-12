package bioMorpho.gui.modelEditor;

import net.minecraft.client.renderer.texture.TextureManager;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.gui.lifeformBasic.IModelElement;

/**
 * @author Brighton Ancelin
 *
 */
public class ModelEditorCenterPane implements IModelElement {
	
	private CompAndBoxSetup cAndB;
	private int unscaledLeft;
	private int unscaledTop;
	
	private MECPModelControl modelControl;
	
	public ModelEditorCenterPane(CompAndBoxSetup cAndB, int unscaledLeft, int unscaledTop) {
		this.cAndB = cAndB;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		
		this.modelControl = new MECPModelControl(cAndB, unscaledLeft, unscaledTop);
	}
	
	public boolean mousePressed(int x, int y, int button) {
		if(this.modelControl.mousePressed(x, y, button)) return true;
		return false;
	}
	
	public void draw(GuiBasic gui, TextureManager renderEngine) {
		this.modelControl.draw(gui, renderEngine);
	}

	public void onGuiUpdate() {}
	public void mousePressedTextFields(int x, int y, int button) {}
	public boolean keyPressed(char keyChar, int keyCode) { return false; }
	
}
