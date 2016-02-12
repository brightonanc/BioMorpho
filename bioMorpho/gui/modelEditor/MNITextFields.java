package bioMorpho.gui.modelEditor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.gui.GuiMyTextFields;
import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.SegmentBox;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class MNITextFields extends GuiMyTextFields {

	private CompAndBoxSetup cAndB;
	
	public final int compRPXId;
	public final int compRPYId;
	public final int compRPZId;
	public final int compRAXId;
	public final int compRAYId;
	public final int compRAZId;
	
	public final int boxRPXId;
	public final int boxRPYId;
	public final int boxRPZId;
	public final int boxRAXId;
	public final int boxRAYId;
	public final int boxRAZId;
	public final int boxPosXId;
	public final int boxPosYId;
	public final int boxPosZId;
	public final int boxSizeXId;
	public final int boxSizeYId;
	public final int boxSizeZId;
	
	public MNITextFields(CompAndBoxSetup cAndB, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex trustMutex) {
		super(trustMutex);
		this.cAndB = cAndB;
		
		GuiTextField compRPX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+21+1, 38, 10);
		compRPX.setTextColor(-1);
		compRPX.setDisabledTextColour(-1);
		compRPX.setEnableBackgroundDrawing(false);
		compRPX.setMaxStringLength(7);
		this.compRPXId = this.registerNewTextField(compRPX);
		GuiTextField compRPY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+35+1, 38, 10);
		compRPY.setTextColor(-1);
		compRPY.setDisabledTextColour(-1);
		compRPY.setEnableBackgroundDrawing(false);
		compRPY.setMaxStringLength(7);
		this.compRPYId = this.registerNewTextField(compRPY);
		GuiTextField compRPZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+49+1, 38, 10);
		compRPZ.setTextColor(-1);
		compRPZ.setDisabledTextColour(-1);
		compRPZ.setEnableBackgroundDrawing(false);
		compRPZ.setMaxStringLength(7);
		this.compRPZId = this.registerNewTextField(compRPZ);
		GuiTextField compRAX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+69+1, 38, 10);
		compRAX.setTextColor(-1);
		compRAX.setDisabledTextColour(-1);
		compRAX.setEnableBackgroundDrawing(false);
		compRAX.setMaxStringLength(7);
		this.compRAXId = this.registerNewTextField(compRAX);
		GuiTextField compRAY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+83+1, 38, 10);
		compRAY.setTextColor(-1);
		compRAY.setDisabledTextColour(-1);
		compRAY.setEnableBackgroundDrawing(false);
		compRAY.setMaxStringLength(7);
		this.compRAYId = this.registerNewTextField(compRAY);
		GuiTextField compRAZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+97+1, 38, 10);
		compRAZ.setTextColor(-1);
		compRAZ.setDisabledTextColour(-1);
		compRAZ.setEnableBackgroundDrawing(false);
		compRAZ.setMaxStringLength(7);
		this.compRAZId = this.registerNewTextField(compRAZ);
		GuiTextField boxRPX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+133+1, 38, 10);
		boxRPX.setTextColor(-1);
		boxRPX.setDisabledTextColour(-1);
		boxRPX.setEnableBackgroundDrawing(false);
		boxRPX.setMaxStringLength(7);
		this.boxRPXId = this.registerNewTextField(boxRPX);
		GuiTextField boxRPY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+147+1, 38, 10);
		boxRPY.setTextColor(-1);
		boxRPY.setDisabledTextColour(-1);
		boxRPY.setEnableBackgroundDrawing(false);
		boxRPY.setMaxStringLength(7);
		this.boxRPYId = this.registerNewTextField(boxRPY);
		GuiTextField boxRPZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+161+1, 38, 10);
		boxRPZ.setTextColor(-1);
		boxRPZ.setDisabledTextColour(-1);
		boxRPZ.setEnableBackgroundDrawing(false);
		boxRPZ.setMaxStringLength(7);
		this.boxRPZId = this.registerNewTextField(boxRPZ);
		GuiTextField boxRAX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+181+1, 38, 10);
		boxRAX.setTextColor(-1);
		boxRAX.setDisabledTextColour(-1);
		boxRAX.setEnableBackgroundDrawing(false);
		boxRAX.setMaxStringLength(7);
		this.boxRAXId = this.registerNewTextField(boxRAX);
		GuiTextField boxRAY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+195+1, 38, 10);
		boxRAY.setTextColor(-1);
		boxRAY.setDisabledTextColour(-1);
		boxRAY.setEnableBackgroundDrawing(false);
		boxRAY.setMaxStringLength(7);
		this.boxRAYId = this.registerNewTextField(boxRAY);
		GuiTextField boxRAZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+209+1, 38, 10);
		boxRAZ.setTextColor(-1);
		boxRAZ.setDisabledTextColour(-1);
		boxRAZ.setEnableBackgroundDrawing(false);
		boxRAZ.setMaxStringLength(7);
		this.boxRAZId = this.registerNewTextField(boxRAZ);
		GuiTextField boxPosX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+229+1, 38, 10);
		boxPosX.setTextColor(-1);
		boxPosX.setDisabledTextColour(-1);
		boxPosX.setEnableBackgroundDrawing(false);
		boxPosX.setMaxStringLength(7);
		this.boxPosXId = this.registerNewTextField(boxPosX);
		GuiTextField boxPosY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+243+1, 38, 10);
		boxPosY.setTextColor(-1);
		boxPosY.setDisabledTextColour(-1);
		boxPosY.setEnableBackgroundDrawing(false);
		boxPosY.setMaxStringLength(7);
		this.boxPosYId = this.registerNewTextField(boxPosY);
		GuiTextField boxPosZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+257+1, 38, 10);
		boxPosZ.setTextColor(-1);
		boxPosZ.setDisabledTextColour(-1);
		boxPosZ.setEnableBackgroundDrawing(false);
		boxPosZ.setMaxStringLength(7);
		this.boxPosZId = this.registerNewTextField(boxPosZ);
		GuiTextField boxSizeX = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+277+1, 38, 10);
		boxSizeX.setTextColor(-1);
		boxSizeX.setDisabledTextColour(-1);
		boxSizeX.setEnableBackgroundDrawing(false);
		boxSizeX.setMaxStringLength(7);
		this.boxSizeXId = this.registerNewTextField(boxSizeX);
		GuiTextField boxSizeY = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+291+1, 38, 10);
		boxSizeY.setTextColor(-1);
		boxSizeY.setDisabledTextColour(-1);
		boxSizeY.setEnableBackgroundDrawing(false);
		boxSizeY.setMaxStringLength(7);
		this.boxSizeYId = this.registerNewTextField(boxSizeY);
		GuiTextField boxSizeZ = new GuiTextField(fontRenderer, unscaledLeft+401+2, unscaledTop+305+1, 38, 10);
		boxSizeZ.setTextColor(-1);
		boxSizeZ.setDisabledTextColour(-1);
		boxSizeZ.setEnableBackgroundDrawing(false);
		boxSizeZ.setMaxStringLength(7);
		this.boxSizeZId = this.registerNewTextField(boxSizeZ);
	}
	
	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) {
			return;
		}
		String text;
		try {
			text = this.getActiveTextField().getText();
		} catch(BioMorphoNoCurrentObjectException e) {
			//This should NEVER be run because the above if-statement just checked to insure that the tried statement would NOT throw and error/exception
			return;
		}
		/*COMP ALTERING BELOW*/
		EntityComponent curComp;
		try {
			curComp = this.cAndB.getCurrentCompTab().getCurrent();
		} catch(BioMorphoNoCurrentObjectException e) {
			//If no component is selected, nothing can be done anyways so return
			return;
		}
		
		if(id == this.compRPXId) {
			curComp.segment.setRotationPointX(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.compRPYId) {
			curComp.segment.setRotationPointY(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.compRPZId) {
			curComp.segment.setRotationPointZ(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.compRAXId) {
			curComp.segment.setRotationAngleX(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.compRAYId) {
			curComp.segment.setRotationAngleY(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.compRAZId) {
			curComp.segment.setRotationAngleZ(StringUtils.stringToFloat(text));
			return;
		}
		/*BOX ALTERING BELOW*/
		SegmentBox curBox;
		try {
			curBox = this.cAndB.getBoxTab().getCurrent();
		} catch(BioMorphoNoCurrentObjectException e) {
			//If no box is selected, AND we are modifying a box property(the only thing that can be done beyond this point), than nothing can be done!!!
			return;
		}
		if(id == this.boxRPXId) {
			curBox.setRotPointX(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxRPYId) {
			curBox.setRotPointY(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxRPZId) {
			curBox.setRotPointZ(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxRAXId) {
			curBox.setRotAngleX(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxRAYId) {
			curBox.setRotAngleY(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxRAZId) {
			curBox.setRotAngleZ(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxPosXId) {
			curBox.setPosX1(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxPosYId) {
			curBox.setPosY1(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxPosZId) {
			curBox.setPosZ1(StringUtils.stringToFloat(text));
			return;
		}
		if(id == this.boxSizeXId) {
			curBox.setWidth(StringUtils.stringToInt(text));
			return;
		}
		if(id == this.boxSizeYId) {
			curBox.setHeight(StringUtils.stringToInt(text));
			return;
		}
		if(id == this.boxSizeZId) {
			curBox.setDepth(StringUtils.stringToInt(text));
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
		if(id == this.compRPXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.compRPYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.compRPYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.compRPZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.compRPZId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.compRAXId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.compRAXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.compRAYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.compRAYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.compRAZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.compRAZId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRPXId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRPXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRPYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRPYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRPZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRPZId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRAXId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRAXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRAYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRAYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxRAZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxRAZId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxPosXId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxPosXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxPosYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxPosYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxPosZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxPosZId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxSizeXId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxSizeXId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxSizeYId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxSizeYId) {
			if(this.isKeyEnter(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(this.boxSizeZId);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		if(id == this.boxSizeZId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return field.textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, true)) return true;
			return false;
		}
		return false;
	}
	
	@Override
	public void setActiveTextField(int newActiveTextField) {
		super.setActiveTextField(newActiveTextField);
		// Set the cursorPosition to the end of the string to keep the logic working for entering numerical values
		try {
			this.getActiveTextField().setCursorPositionEnd();
		} catch(BioMorphoNoCurrentObjectException e) {/*No active text field so we don't have to worry*/}
	}
	
}
