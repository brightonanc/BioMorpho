package bioMorpho.gui.lifeformSelection;

import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformDataPanel implements IModelElement {
	
	private GuiLifeformSelection gui;
	private int unscaledLeft;
	private int unscaledTop;
	
	private LDPTextFields textFields;
	private LDPDrawnText drawnText;
	
	public LifeformDataPanel(GuiLifeformSelection gui, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex textFieldMutex) {
		this.gui = gui;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.textFields = new LDPTextFields(gui, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
		this.drawnText = new LDPDrawnText(unscaledLeft, unscaledTop, fontRenderer);
	}
	
	public boolean mousePressed(int x, int y, int button) {return false;}
	public boolean keyPressed(char keyChar, int keyCode) {return this.textFields.keyPressed(keyChar, keyCode);}
	public void mousePressedTextFields(int x, int y, int button) {this.textFields.mousePressed(x, y, button);}
	
	public void onGuiUpdate() {
		boolean lifeformExists;
		try {
			this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex());
			lifeformExists = true;
		} catch(IndexOutOfBoundsException e) {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.nameId) this.textFields.setActiveTextField(-1);
			this.textFields.getTextFieldById(this.textFields.nameId).setText("");
			if(this.textFields.getActiveTextFieldValue() == this.textFields.modelOffsetYId) this.textFields.setActiveTextField(-1);
			this.textFields.getTextFieldById(this.textFields.modelOffsetYId).setText("");
			lifeformExists = false;
		}
		if(!this.textFields.getTextFieldById(this.textFields.nameId).isFocused() && lifeformExists) {
			this.textFields.getTextFieldById(this.textFields.nameId).setText(this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex()).name);
		}
		if(!this.textFields.getTextFieldById(this.textFields.modelOffsetYId).isFocused() && lifeformExists) {
			this.textFields.getTextFieldById(this.textFields.modelOffsetYId).setText(StringUtils.numToStringSpecial(this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex()).getModel().offsetY));
		}
		try {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.nameId) {
				this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex()).name = this.textFields.getActiveTextField().getText();
			}
			if(this.textFields.getActiveTextFieldValue() == this.textFields.modelOffsetYId) {
				this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex()).getModel().offsetY = StringUtils.stringToFloat(this.textFields.getActiveTextField().getText());
			}
		} catch(BioMorphoNoCurrentObjectException e) {/*This is run if no component is selected, which means there's nothing to name!*/}
	}
	
	public void draw(GuiBasic gui) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(GuiTextures.LIFEFORM_DATA_PANEL);
		gui.drawTexturedRect(this.unscaledLeft+320, this.unscaledTop+8, 161, 233, 0, 0, 512, 512);
		this.drawnText.draw();
		this.textFields.draw();
		GL11.glPopMatrix();
	}
	
}
