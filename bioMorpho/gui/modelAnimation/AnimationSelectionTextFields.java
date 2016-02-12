package bioMorpho.gui.modelAnimation;

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
public class AnimationSelectionTextFields extends GuiMyTextFields {
	
	private AnimationSelectionSetup animSel;
	
	public final int eventNameSearchId;
	public final int animationNameSearchId;
	public final int eventPageId;
	public final int animationPageId;
	
	public AnimationSelectionTextFields(AnimationSelectionSetup animSel, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex trustMutex) {
		super(trustMutex);
		this.animSel = animSel;
		
		GuiTextField eventNameSearch = new GuiTextField(fontRenderer, unscaledLeft+384+5+2, unscaledTop+39+1, 118, 10) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(18);
		}};
		this.eventNameSearchId = this.registerNewTextField(eventNameSearch);
		GuiTextField animationNameSearch = new GuiTextField(fontRenderer, unscaledLeft+384+5+2, unscaledTop+129+1, 118, 10) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(18);
		}};
		this.animationNameSearchId = this.registerNewTextField(animationNameSearch);
		GuiTextField eventPage = new GuiTextField(fontRenderer, unscaledLeft+384+107+2, unscaledTop+57+1, 16, 10) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(2);
		}};
		this.eventPageId = this.registerNewTextField(eventPage);
		GuiTextField animationPage = new GuiTextField(fontRenderer, unscaledLeft+384+107+2, unscaledTop+147+1, 16, 10) {{
			this.setTextColor(-1);
			this.setDisabledTextColour(-1);
			this.setEnableBackgroundDrawing(false);
			this.setMaxStringLength(2);
		}};
		this.animationPageId = this.registerNewTextField(animationPage);
	}

	@Override
	protected void textFieldDeselected(int id) {
		if(id == -1) {
			return;
		}
		if(id == this.eventNameSearchId) {
			return;
		}
		if(id == this.animationNameSearchId) {
			return;
		}
		if(id == this.eventPageId) {
			try { this.animSel.getEventTab().setPageNum(StringUtils.stringToInt(this.getActiveTextField().getText()));
			} catch(BioMorphoNoCurrentObjectException e) {/*This is run if the deselected field is -1 - Should NEVER be run*/}
			return;
		}
		if(id == this.animationPageId) {
			try { this.animSel.getAnimationTab().setPageNum(StringUtils.stringToInt(this.getActiveTextField().getText()));
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
		if(id == this.eventNameSearchId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			boolean ret = field.textboxKeyTyped(keyChar, keyCode);
			if(ret) this.animSel.getEventTab().setPageNum(1);
			return ret;
		}
		if(id == this.animationNameSearchId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			boolean ret = field.textboxKeyTyped(keyChar, keyCode);
			if(ret) this.animSel.getAnimationTab().setPageNum(1);
			return ret;
		}
		if(id == this.eventPageId) {
			if(this.isKeyEnter(keyChar, keyCode) || this.isKeyTab(keyChar, keyCode)) {
				this.setActiveTextField(-1);
				return true;
			}
			if(this.keyPressedOnlyInt3p(field, keyChar, keyCode, false)) return true;
			if(this.keyPressedSpecialFunc(field, keyChar, keyCode)) return true;
			return false;
		}
		if(id == this.animationPageId) {
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
			if(this.getActiveTextFieldValue() == this.eventNameSearchId) {
				try {
					this.getActiveTextField().setText("");
				} catch(BioMorphoNoCurrentObjectException e) {/*The above if ensures the active text field is not -1 and therefore this error will not occur*/}
			}
			if(this.getActiveTextFieldValue() == this.animationNameSearchId) {
				try {
					this.getActiveTextField().setText("");
				} catch(BioMorphoNoCurrentObjectException e) {/*The above if ensures the active text field is not -1 and therefore this error will not occur*/}
			}
		}
		return ret;
	}

}
