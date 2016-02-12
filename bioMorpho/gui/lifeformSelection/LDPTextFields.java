package bioMorpho.gui.lifeformSelection;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.gui.GuiMyTextFields;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class LDPTextFields extends GuiMyTextFields {
	
	private GuiLifeformSelection gui;
	
	public final int nameId;
	public final int modelOffsetYId;
	
	public LDPTextFields(GuiLifeformSelection gui, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex trustMutex) {
		super(trustMutex);
		this.gui = gui;
		this.nameId = this.registerNewTextField(new GuiTextField(fontRenderer, unscaledLeft+326+2, unscaledTop+14+1, 150, 12) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(24);
		}});
		this.modelOffsetYId = this.registerNewTextField(new GuiTextField(fontRenderer, unscaledLeft+438+2, unscaledTop+34+1, 38, 12) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(6);
		}});
	}
	
	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) return;
		Lifeform lifeform;
		try {lifeform = this.gui.getLifeforms().get(this.gui.getCurrentLifeformIndex());}
		catch(IndexOutOfBoundsException e) {return;}
		if(id == this.nameId) {
			try {lifeform.name = this.getActiveTextField().getText();}
			catch(BioMorphoNoCurrentObjectException e) {}
		} else if(id == this.modelOffsetYId) {
			try {lifeform.getModel().offsetY = StringUtils.stringToFloat(this.getActiveTextField().getText());}
			catch(BioMorphoNoCurrentObjectException e) {}
		}
	}
	
	@Override
	public boolean keyPressed(char keyChar, int keyCode) {
		int id = this.getActiveTextFieldValue();
		if(id == -1) return false;
		GuiTextField field;
		try {field = this.getActiveTextField();}
		catch(BioMorphoNoCurrentObjectException e) {field = null;}
		if(id == this.nameId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			} else if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.modelOffsetYId);
				return true;
			}
			return field.textboxKeyTyped(keyChar, keyCode);
		} else if(id == this.modelOffsetYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			} else if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, false)) return true;
		}
		return false;
	}
	
	@Override
	public void setActiveTextField(int newActiveTextField) {
		super.setActiveTextField(newActiveTextField);
		try {this.getActiveTextField().setCursorPositionEnd();}
		catch(BioMorphoNoCurrentObjectException e) {}
	}
	
}
