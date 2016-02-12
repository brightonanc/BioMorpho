package bioMorpho.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiTextField;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.TrustMutex;
import bioMorpho.util.TrustMutex.ITrustMutexMember;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiMyTextFields implements ITrustMutexMember {
	
	private IdGenerator ids;
	protected TrustMutex trustMutex;
	
	private int activeTextField;
	
	private ArrayList<GuiTextField> textFields;
	
	public GuiMyTextFields(TrustMutex trustMutex) {
		this.ids = new IdGenerator();
		this.trustMutex = trustMutex;
		this.trustMutex.registerNewMember(this);
		this.activeTextField = -1;
		this.textFields = new ArrayList<GuiTextField>();
	}
	
	/*
	 * DO NOT USE UNLESS REALLY NEEDED!!! ONLY IMPLEMENTED FOR CompAndBoxSetup!!!
	 */
	public GuiTextField getTextFieldById(int id) {
		return this.textFields.get(id);
	}
	
	@Override
	public void onMutexGrabbedBySomeoneElse() {
		this.setActiveTextField(-1);
	}
	
	public int registerNewTextField(GuiTextField newTextField) {
		int id = this.ids.getNextId();
		this.textFields.add(id, newTextField);
		return id;
	}
	
	public boolean mousePressed(int x, int y, int button) {
		for(int i = 0; i < this.textFields.size(); i++) {
			GuiTextField currentField = this.textFields.get(i);
			currentField.mouseClicked(x, y, button);
			if(currentField.isFocused()) {
				this.setActiveTextField(i);
				// You must grab the mutex to eliminate two separate GuiMyTextField objects from both using a text field at the same time
				this.trustMutex.grab(this);
				return true;
			}
		}
		this.setActiveTextField(-1);
		return false;
	}
	
	public void setActiveTextField(int newActiveTextField) {
		// The if statement below is to eliminate an event when you click on a single text box more than one time.
		// Without the statement below, this event would cause the text box to be deselected.
		if(this.activeTextField != newActiveTextField) {
			try {
				this.currentTextFieldDeselected();
				this.getActiveTextField().setFocused(false);
			}
			catch(BioMorphoNoCurrentObjectException e) {}
			this.activeTextField = newActiveTextField;
			try {
				this.getActiveTextField().setFocused(true);
			}
			catch(BioMorphoNoCurrentObjectException e) {}
		}
	}
	public int getActiveTextFieldValue() {
		return this.activeTextField;
	}
	
	private void currentTextFieldDeselected() {
		this.textFieldDeselected(this.activeTextField);
	}
	
	public void draw() {
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		for(int i = 0; i < this.textFields.size(); i++) {
			this.textFields.get(i).drawTextBox();
		}
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	/*
	 * This method is designed to be run right BEFORE the textField is deselected. After this method, the textField is deselected (AKA .setFocused(false))
	 */
	protected abstract void textFieldDeselected(int id);
	public abstract boolean keyPressed(char keyChar, int keyCode);
	
	protected boolean isKeyTab(char keyChar, int keyCode) {
		if(keyCode == 15) return true;
		return false;
	}
	protected boolean isKeyEnter(char keyChar, int keyCode) {
		if(keyCode == 28) return true;
		return false;
	}
	protected boolean isKeyBackspaceOrDelete(char keyChar, int keyCode) {
		if(keyCode == 14 || keyCode == 211) return true;
		return false;
	}
	protected boolean keyPressedSpecialFunc(GuiTextField field, char keyChar, int keyCode) {
		// If the typed is a backspace or delete key
		if(keyCode == 14 || keyCode == 211) {
			return field.textboxKeyTyped(keyChar, keyCode);
		}
		// If the typed is an arrow key
		else if(keyCode == 203 || keyCode == 205) {
			return field.textboxKeyTyped(keyChar, keyCode);
		}
		// If the typed is the home or end key
		else if(keyCode == 199 || keyCode == 207) {
			return field.textboxKeyTyped(keyChar, keyCode);
		}
		return false;
	}
	protected boolean keyPressedOnlyInt3p(GuiTextField field, char keyChar, int keyCode, boolean posAndNeg) {
		// Below are the procedures for numeric entry of integer values only
		int numericalLength = field.getText().length();
		try {
			if(field.getText().charAt(0) == 45) numericalLength--;
		} catch(StringIndexOutOfBoundsException e) {
			// There's no string index at 0, therefore the string contains no characters at all.
			// Without characters, the numericalLength does not need to be altered.
		}
		
		if(posAndNeg) {
			// If the typed is a minus symbol
			if(keyChar == 45) {
				if(field.getText().length() == 0) {
					return field.textboxKeyTyped(keyChar, keyCode);
				}
			}
		}
		// If the typed is a numeral
		if(keyChar >= 48 && keyChar <= 57) {
			if(numericalLength < 3) {
				return field.textboxKeyTyped(keyChar, keyCode);
			}
			else {
				return false;
			}
		}
		return false;
	}
	protected boolean keyPressedOnlyFloat3p_2p(GuiTextField field, char keyChar, int keyCode, boolean posAndNeg) {
		// Below are the procedures for numeric entry with two decimal points of precision
		CharSequence period = ".";
		CharSequence comma = ",";
		int numericalLength = field.getText().length();
		try {
			if(field.getText().charAt(0) == 45) numericalLength--;
		} catch(StringIndexOutOfBoundsException e) {
			// There's no string index at 0, therefore the string contains no characters at all.
			// Without characters, the numericalLength does not need to be altered.
		}
		
		if(posAndNeg) {
			if(keyChar == 45) {
				if(field.getText().length() == 0) {
					return field.textboxKeyTyped(keyChar, keyCode);
				}
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
					return field.textboxKeyTyped(keyChar, keyCode);
				}
				else {
					return false;
				}
			}
			else {
				if(numericalLength < 3) {
					return field.textboxKeyTyped(keyChar, keyCode);
				}
				else {
					return false;
				}
			}
		}
		// If the typed is a period or comma (just in case foreign people prefer the comma as a decimal point)
		else if(keyChar == 46 || keyChar == 44) {
			if(!(field.getText().contains(period) || field.getText().contains(comma))) {
				return field.textboxKeyTyped(keyChar, keyCode);
			}
			else {
				return false;
			}
		}
		return false;
	}
	
	public GuiTextField getActiveTextField() throws BioMorphoNoCurrentObjectException {
		if(this.activeTextField != -1 && this.activeTextField < this.textFields.size()) {
			return this.textFields.get(this.activeTextField);
		}
		throw new BioMorphoNoCurrentObjectException();
	}
	
}
