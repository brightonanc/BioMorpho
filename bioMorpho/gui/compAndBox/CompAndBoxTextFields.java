package bioMorpho.gui.compAndBox;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.gui.GuiMyTextFields;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class CompAndBoxTextFields extends GuiMyTextFields {

	private CompAndBoxSetup compAndBox;
	
	public final int compNameId;
	public final int boxNameId;
	public final int compPageId;
	public final int boxPageId;
	
	public CompAndBoxTextFields(CompAndBoxSetup compAndBox, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex trustMutex) {
		super(trustMutex);
		this.compAndBox = compAndBox;
		
		GuiTextField compName = new GuiTextField(fontRenderer, unscaledLeft+5+2, unscaledTop+5+1, 118, 10);
		compName.setTextColor(-1);
		compName.setDisabledTextColour(-1);
		compName.setEnableBackgroundDrawing(false);
		compName.setMaxStringLength(18);
		this.compNameId = this.registerNewTextField(compName);
		GuiTextField boxName = new GuiTextField(fontRenderer, unscaledLeft+5+2, unscaledTop+177+1, 118, 10);
		boxName.setTextColor(-1);
		boxName.setDisabledTextColour(-1);
		boxName.setEnableBackgroundDrawing(false);
		boxName.setMaxStringLength(18);
		this.boxNameId = this.registerNewTextField(boxName);
		GuiTextField compPage = new GuiTextField(fontRenderer, unscaledLeft+107+2, unscaledTop+23+1, 16, 10);
		compPage.setTextColor(-1);
		compPage.setDisabledTextColour(-1);
		compPage.setEnableBackgroundDrawing(false);
		compPage.setMaxStringLength(2);
		this.compPageId = this.registerNewTextField(compPage);
		GuiTextField boxPage = new GuiTextField(fontRenderer, unscaledLeft+107+2, unscaledTop+195+1, 16, 10);
		boxPage.setTextColor(-1);
		boxPage.setDisabledTextColour(-1);
		boxPage.setEnableBackgroundDrawing(false);
		boxPage.setMaxStringLength(2);
		this.boxPageId = this.registerNewTextField(boxPage);
	}
	
	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) {
			return;
		}
		if(id == this.compNameId) {
			try {
				this.compAndBox.getCurrentCompTab().getCurrent().name = this.getActiveTextField().getText();
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
		if(id == this.boxNameId) {
			try {
				this.compAndBox.getBoxTab().getCurrent().name = this.getActiveTextField().getText();
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
		if(id == this.compPageId) {
			try {
				this.compAndBox.getCurrentCompTab().setPageNum(StringUtils.stringToInt(this.getActiveTextField().getText()));
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
		if(id == this.boxPageId) {
			try {
				this.compAndBox.getBoxTab().setPageNum(StringUtils.stringToInt(this.getActiveTextField().getText()));
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
	}

	@Override
	public boolean keyPressed(char keyChar, int keyCode) {
		int id = this.getActiveTextFieldValue();
		if(id == -1) {
			return false;
		}
		GuiTextField field;
		try {
			field = this.getActiveTextField();
		} catch(BioMorphoNoCurrentObjectException e) {
			//This should NEVER be run because the above if-statement just checked to insure that the tried statement would NOT throw and error/exception
			field = new GuiTextField(null, -1, -1, -1, -1);
		}
		if(id == this.compNameId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			return field.textboxKeyTyped(keyChar, keyCode);
		}
		if(id == this.boxNameId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			return field.textboxKeyTyped(keyChar, keyCode);
		}
		if(id == this.compPageId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, false)) return true;
			if(this.keyPressedSpecialFunc(field, keyChar, keyCode)) return true;
			return false;
		}
		if(id == this.boxPageId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, false)) return true;
			if(this.keyPressedSpecialFunc(field, keyChar, keyCode)) return true;
			return false;
		}
		return false;
	}
	
	@Override
	public boolean mousePressed(int x, int y, int button) {
		boolean ret = super.mousePressed(x, y, button);
		// If the pressed button is a right-click
		if(button == 1) {
			if(this.getActiveTextFieldValue() == this.compNameId) {
				try {
					this.getActiveTextField().setText("");
				} catch(BioMorphoNoCurrentObjectException e) {/*The above if ensures the active text field is not -1 and therefore this error will not occur*/}
			}
			if(this.getActiveTextFieldValue() == this.boxNameId) {
				try {
					this.getActiveTextField().setText("");
				} catch(BioMorphoNoCurrentObjectException e) {/*The above if ensures the active text field is not -1 and therefore this error will not occur*/}
			}
		}
		return ret;
	}
	
}
