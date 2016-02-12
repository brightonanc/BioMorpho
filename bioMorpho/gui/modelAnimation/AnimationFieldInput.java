package bioMorpho.gui.modelAnimation;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.lifeform.model.animation.settings.AnimSettings;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimationFieldInput implements IModelElement {
	
	private Gui gui;
	private FontRenderer fontRenderer;
	private AnimationSelectionSetup animSel;
	private int unscaledLeft;
	private int unscaledTop;
	private TrustMutex textFieldMutex;
	
	private AnimSettings settings = null;
	private AnimField[] inputFields = new AnimField[9];
		
	public AnimationFieldInput(Gui gui, AnimationSelectionSetup animSel, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex textFieldMutex) {
		this.gui = gui;
		this.fontRenderer = fontRenderer;
		this.animSel = animSel;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.textFieldMutex = textFieldMutex;
	}
	
	public void setSettingsAndFields(AnimSettings newSettings) {
		this.settings = newSettings;
		int textFieldHeight = 12;
		for(int i = 0; i < this.inputFields.length; i++) {
			if(i < this.settings.getSettings().size()) {
				this.inputFields[i] = new AnimField(this.settings.getSettings().get(i), this.textFieldMutex, this, this.fontRenderer, this.unscaledLeft+384, this.unscaledTop+204+(i*textFieldHeight), 44, textFieldHeight);
			} else {
				this.inputFields[i] = null;
			}
		}
	}
	
	public void tabToNextField(AnimField prevField) {
		boolean nextIsTheChosenOne = false;
		for(int i = 0; i < this.inputFields.length; i++) {
			if(nextIsTheChosenOne && this.inputFields[i] != null) {
				this.inputFields[i].setActiveTextField(this.inputFields[i].inputId);
				break;
			}
			if(prevField == this.inputFields[i]) nextIsTheChosenOne = true;
		}
	}
	
	@Override
	public void onGuiUpdate() {
		for(AnimField curField : this.inputFields) {
			if(curField != null) curField.onGuiUpdate();
		}
	}

	@Override
	public void mousePressedTextFields(int x, int y, int button) {
		for(AnimField curField : this.inputFields) {
			if(curField != null) {
				curField.mousePressed(x, y, button);
			}
		}
	}

	@Override
	public boolean mousePressed(int x, int y, int button) {
		return false;
	}

	@Override
	public boolean keyPressed(char keyChar, int keyCode) {
		for(AnimField curField : this.inputFields) {
			if(curField != null && curField.keyPressed(keyChar, keyCode)) return true;
		}
		return false;
	}
	
	public void draw(TextureManager renderEngine) {
		for(AnimField curField : this.inputFields) {
			if(curField != null) curField.draw(this.gui, renderEngine);
		}
	}

}
