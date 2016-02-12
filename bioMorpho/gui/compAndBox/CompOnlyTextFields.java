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
public class CompOnlyTextFields extends GuiMyTextFields {

	private CompOnlySetup compOnly;
	
	public final int compNameId;
	public final int compPageId;
	
	public CompOnlyTextFields(CompOnlySetup compOnly, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex trustMutex) {
		super(trustMutex);
		this.compOnly = compOnly;
		
		GuiTextField compName = new GuiTextField(fontRenderer, unscaledLeft+5+2, unscaledTop+5+1, 118, 10);
		compName.setTextColor(-1);
		compName.setDisabledTextColour(-1);
		compName.setEnableBackgroundDrawing(false);
		compName.setMaxStringLength(18);
		this.compNameId = this.registerNewTextField(compName);
		GuiTextField compPage = new GuiTextField(fontRenderer, unscaledLeft+107+2, unscaledTop+23+1, 16, 10);
		compPage.setTextColor(-1);
		compPage.setDisabledTextColour(-1);
		compPage.setEnableBackgroundDrawing(false);
		compPage.setMaxStringLength(2);
		this.compPageId = this.registerNewTextField(compPage);
	}
	
	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) {
			return;
		}
		if(id == this.compNameId) {
			try {
				this.compOnly.getCurrentCompTab().getCurrent().name = this.getActiveTextField().getText();
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
		if(id == this.compPageId) {
			try {
				this.compOnly.getCurrentCompTab().setPageNum(StringUtils.stringToInt(this.getActiveTextField().getText()));
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
			boolean ret = field.textboxKeyTyped(keyChar, keyCode);
			try {
				this.compOnly.getCurrentCompTab().getCurrent().name = field.getText();
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if no component is selected, which means there's nothing to name!*/}
			return ret;
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
		}
		return ret;
	}
	
}
