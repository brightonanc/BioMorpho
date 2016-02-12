package bioMorpho.gui.modelAnimation;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiMyTextFields;
import bioMorpho.lifeform.model.animation.settings.AnimSetting;
import bioMorpho.lifeform.model.animation.settings.SettingFloat;
import bioMorpho.lifeform.model.animation.settings.SettingInt;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimField extends GuiMyTextFields {

	private AnimSetting setting;
	private AnimationFieldInput animFI;
	private FontRenderer fontRenderer;
	public final int inputId;
	
	private int x;
	private int y;
	
	public AnimField(AnimSetting setting, TrustMutex trustMutex, AnimationFieldInput animFI, FontRenderer fontRenderer, int x, int y, int width, int height) {
		super(trustMutex);
		this.setting = setting;
		this.animFI = animFI;
		this.fontRenderer = fontRenderer;
		GuiTextField input = new GuiTextField(fontRenderer, x+82+2, y+1+1, width, height) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(7);
		}};
		this.inputId = this.registerNewTextField(input);
		this.x = x;
		this.y = y;
	}
	
	public AnimSetting getSetting() { return this.setting; }
	public GuiTextField getInputTextField() { return this.getTextFieldById(this.inputId); }

	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) return;
		if(id == this.inputId) {
			if(this.setting instanceof SettingFloat) {
				((SettingFloat)this.setting).setValue(StringUtils.stringToFloat(this.getInputTextField().getText()));
			} else if(this.setting instanceof SettingInt) {
				((SettingInt)this.setting).setValue(StringUtils.stringToInt(this.getInputTextField().getText()));
			}
		}
	}

	@Override
	public boolean keyPressed(char keyChar, int keyCode) {
		int id = this.getActiveTextFieldValue();
		if(id == -1) {
			return false;
		}
		// If the activeTextFieldValue is not -1, then it is the input as it is the only input that exists
		if(this.isKeyEnter(keyChar, keyCode)) {
			this.setActiveTextField(-1);
			return true;
		}
		if(this.isKeyTab(keyChar, keyCode)) {
			this.setActiveTextField(-1);
			this.animFI.tabToNextField(this);
			return true;
		}
		if(this.setting instanceof SettingFloat) {
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return this.getInputTextField().textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyFloat3p_2p(this.getInputTextField(), keyChar, keyCode, ((SettingFloat)this.setting).getLowerBound(), ((SettingFloat)this.setting).getUpperBound())) {
				return true;
			}
		} else if(this.setting instanceof SettingInt) {
			if(this.isKeyBackspaceOrDelete(keyChar, keyCode)) return this.getInputTextField().textboxKeyTyped(keyChar, keyCode);
			if(this.keyPressedOnlyInt3p(this.getInputTextField(), keyChar, keyCode, ((SettingInt)this.setting).getLowerBound(), ((SettingInt)this.setting).getUpperBound())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param field
	 * @param keyChar
	 * @param keyCode
	 * @param lowerLimit (inclusive)
	 * @param upperLimit (inclusive)
	 * @return success of input
	 */
	protected boolean keyPressedOnlyFloat3p_2p(GuiTextField field, char keyChar, int keyCode, float lowerLimit, float upperLimit) {
		// Below are the procedures for numeric entry with two decimal points of precision
		boolean validInput = false;
		CharSequence period = ".";
		CharSequence comma = ",";
		int numericalLength = field.getText().length();
		try {
			if(field.getText().charAt(0) == 45) numericalLength--;
		} catch(StringIndexOutOfBoundsException e) {
			// There's no string index at 0, therefore the string contains no characters at all.
			// Without characters, the numericalLength does not need to be altered.
		}
		
		if(keyChar == 45) {
			if(field.getText().length() == 0) {
				validInput = true;
			}
		}
		
		// If the typed is a numeral
		if(keyChar >= 48 && keyChar <= 57) {
			if(field.getText().contains(period) || field.getText().contains(comma)) {
				int decimalDistance;
				if(field.getText().lastIndexOf('.') != -1) {
					decimalDistance = field.getText().length() - (field.getText().lastIndexOf('.') + 1);
				}
				else {
					decimalDistance = field.getText().length() - (field.getText().lastIndexOf(',') + 1);
				}
				
				if(decimalDistance < 2) {
					validInput = true;
				}
				else {
					validInput = false;
				}
			}
			else {
				if(numericalLength < 3) {
					validInput = true;
				}
				else {
					validInput = false;
				}
			}
		}
		// If the typed is a period or comma (just in case foreign people prefer the comma as a decimal point)
		else if(keyChar == 46 || keyChar == 44) {
			if(!(field.getText().contains(period) || field.getText().contains(comma))) {
				validInput = true;
			}
			else {
				validInput = false;
			}
		}
		
		if(validInput) {
			float postKeyPressedValue = StringUtils.stringToFloat(field.getText()+keyChar);
			if(lowerLimit <= postKeyPressedValue && postKeyPressedValue <= upperLimit) {
				return field.textboxKeyTyped(keyChar, keyCode);
			}
			else return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param field
	 * @param keyChar
	 * @param keyCode
	 * @param lowerLimit (inclusive)
	 * @param upperLimit (inclusive)
	 * @return success of input
	 */
	protected boolean keyPressedOnlyInt3p(GuiTextField field, char keyChar, int keyCode, int lowerLimit, int upperLimit) {
		// Below are the procedures for numeric entry of integer values only
		boolean validInput = false;
		int numericalLength = field.getText().length();
		try {
			if(field.getText().charAt(0) == 45) numericalLength--;
		} catch(StringIndexOutOfBoundsException e) {
			// There's no string index at 0, therefore the string contains no characters at all.
			// Without characters, the numericalLength does not need to be altered.
		}
		
			// If the typed is a minus symbol
		if(keyChar == 45) {
			if(field.getText().length() == 0) {
				validInput = true;
			}
		}
		// If the typed is a numeral
		if(keyChar >= 48 && keyChar <= 57) {
			if(numericalLength < 3) {
				validInput = true;
			}
			else {
				validInput = false;
			}
		}
		if(validInput) {
			float postKeyPressedValue = StringUtils.stringToInt(field.getText()+keyChar);
			if(lowerLimit <= postKeyPressedValue && postKeyPressedValue <= upperLimit) {
				return field.textboxKeyTyped(keyChar, keyCode);
			}
			else return false;
		}
		return false;
	}
	
	public void onGuiUpdate() {
		if(this.setting instanceof SettingFloat) {
			if(!this.getInputTextField().isFocused()) {
				this.getInputTextField().setText(StringUtils.numToStringSpecial(((SettingFloat)this.setting).getValue()));
			}
		} else if(this.setting instanceof SettingInt) {
			if(!this.getInputTextField().isFocused()) {
				this.getInputTextField().setText(StringUtils.numToStringSpecial(((SettingInt)this.setting).getValue()));
			}
		}
	}
	
	@Override
	public void setActiveTextField(int newActiveTextField) {
		super.setActiveTextField(newActiveTextField);
		// Set the cursorPosition to the end of the string to keep the logic working for entering numerical values
		try { this.getActiveTextField().setCursorPositionEnd(); }
		catch(BioMorphoNoCurrentObjectException e) {/*Input field is not selected so do nothing*/}
	}
	
	public void draw(Gui gui, TextureManager renderEngine) {
		GL11.glPushMatrix();
		RenderUtils.bindTexture(renderEngine, GuiTextures.ANIM_FIELD_INPUT_MISC);
		if(this.setting instanceof SettingFloat) {
			gui.drawTexturedModalRect(this.x+3, this.y, 0, 0, 122, 12);
		} else if(this.setting instanceof SettingInt) {
			gui.drawTexturedModalRect(this.x+3, this.y, 0, 0, 122, 12);
		}
		GL11.glPopMatrix();
		this.fontRenderer.drawString(this.setting.getGuiName(), this.x+3+1+2, this.y+1+1, -1, true);
		this.getInputTextField().drawTextBox();
	}

}
