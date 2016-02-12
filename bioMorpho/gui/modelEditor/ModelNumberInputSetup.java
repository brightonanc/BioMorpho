package bioMorpho.gui.modelEditor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.SegmentBox;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.StringUtils;
import bioMorpho.util.TrustMutex;


/**
 * @author Brighton Ancelin
 *
 */
public class ModelNumberInputSetup implements IModelElement {
	
	private IdGenerator ids;
	
	private Gui gui;
	private CompAndBoxSetup cAndB;
	private FontRenderer fontRenderer;
	private int unscaledTop;
	private int unscaledLeft;
	
	private MNITextFields textFields;
	private MNIDrawnText drawnText;
	
	public ModelNumberInputSetup(Gui gui, CompAndBoxSetup compAndBox, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, TrustMutex textFieldMutex) {
		this.ids = new IdGenerator();
		this.gui = gui;
		this.cAndB = compAndBox;
		this.fontRenderer = fontRenderer;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		
		this.textFields = new MNITextFields(this.cAndB, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
		this.drawnText = new MNIDrawnText(unscaledLeft, unscaledTop, fontRenderer);
	}
	
	public void onGuiUpdate() {
		try {
			EntityComponent curComp = this.cAndB.getCurrentCompTab().getCurrent();
			if(!this.textFields.getTextFieldById(this.textFields.compRPXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRPXId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationPointX));
			}
			if(!this.textFields.getTextFieldById(this.textFields.compRPYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRPYId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationPointY));
			}
			if(!this.textFields.getTextFieldById(this.textFields.compRPZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRPZId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationPointZ));
			}
			if(!this.textFields.getTextFieldById(this.textFields.compRAXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRAXId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationAngleX));
			}
			if(!this.textFields.getTextFieldById(this.textFields.compRAYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRAYId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationAngleY));
			}
			if(!this.textFields.getTextFieldById(this.textFields.compRAZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.compRAZId).setText(StringUtils.numToStringSpecial(curComp.segment.mainRotationAngleZ));
			}
		} catch(BioMorphoNoCurrentObjectException e) {
			// Since there is no COMPONENT(which contains boxes as well) selected, all the textFields should be blank (including the box specifiers)
			// By constantly setting the activeTextField to -1, any time that any of the textField's are clicked on they will be set as the activeTextField, 
			// but then this method will run and they will be deselected (AKA .setFocused(false)) before another render call is made
			this.textFields.setActiveTextField(-1); 
			
			this.textFields.getTextFieldById(this.textFields.compRPXId).setText("");
			this.textFields.getTextFieldById(this.textFields.compRPYId).setText("");
			this.textFields.getTextFieldById(this.textFields.compRPZId).setText("");
			this.textFields.getTextFieldById(this.textFields.compRAXId).setText("");
			this.textFields.getTextFieldById(this.textFields.compRAYId).setText("");
			this.textFields.getTextFieldById(this.textFields.compRAZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRPXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRPYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRPZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeZId).setText("");
			return; // Already dealt with all text Fields, so there's no reason to continue!
		}
		
		try {
			SegmentBox curBox = this.cAndB.getBoxTab().getCurrent();
			if(!this.textFields.getTextFieldById(this.textFields.boxRPXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRPXId).setText(StringUtils.numToStringSpecial(curBox.getRotPointX()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxRPYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRPYId).setText(StringUtils.numToStringSpecial(curBox.getRotPointY()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxRPZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRPZId).setText(StringUtils.numToStringSpecial(curBox.getRotPointZ()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxRAXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRAXId).setText(StringUtils.numToStringSpecial(curBox.getRotAngleX()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxRAYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRAYId).setText(StringUtils.numToStringSpecial(curBox.getRotAngleY()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxRAZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxRAZId).setText(StringUtils.numToStringSpecial(curBox.getRotAngleZ()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxPosXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxPosXId).setText(StringUtils.numToStringSpecial(curBox.getPosX1()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxPosYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxPosYId).setText(StringUtils.numToStringSpecial(curBox.getPosY1()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxPosZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxPosZId).setText(StringUtils.numToStringSpecial(curBox.getPosZ1()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxSizeXId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxSizeXId).setText(StringUtils.numToStringSpecial(curBox.getWidth()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxSizeYId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxSizeYId).setText(StringUtils.numToStringSpecial(curBox.getHeight()));
			}
			if(!this.textFields.getTextFieldById(this.textFields.boxSizeZId).isFocused()) {
				this.textFields.getTextFieldById(this.textFields.boxSizeZId).setText(StringUtils.numToStringSpecial(curBox.getDepth()));
			}
		} catch(BioMorphoNoCurrentObjectException e) {
			// Since there's no box selected, all BOX textField's should be blank (NOT necessarily component textFields)
			int value = this.textFields.getActiveTextFieldValue();
			if(value == this.textFields.boxRPXId ||
					value == this.textFields.boxRPYId ||
					value == this.textFields.boxRPZId ||
					value == this.textFields.boxRAXId ||
					value == this.textFields.boxRAYId ||
					value == this.textFields.boxRAZId ||
					value == this.textFields.boxPosXId ||
					value == this.textFields.boxPosYId ||
					value == this.textFields.boxPosZId ||
					value == this.textFields.boxSizeXId ||
					value == this.textFields.boxSizeYId ||
					value == this.textFields.boxSizeZId) {
				this.textFields.setActiveTextField(-1);
			}
			
			this.textFields.getTextFieldById(this.textFields.boxRPXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRPYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRPZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxRAZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxPosZId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeXId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeYId).setText("");
			this.textFields.getTextFieldById(this.textFields.boxSizeZId).setText("");
		}
	}
	
	public void mousePressedTextFields(int x, int y, int button) {
		this.textFields.mousePressed(x, y, button);
	}
	public boolean mousePressed(int x, int y, int button) {
		return false;
	}
	
	public boolean keyPressed(char keyChar, int keyCode) {
		if(this.textFields.keyPressed(keyChar, keyCode)) return true;
		return false;
	}
	
	public void draw(Gui gui, TextureManager renderEngine) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		RenderUtils.bindTexture(renderEngine, GuiTextures.MODEL_NUMBER_INPUT_SETUP_BKGRND);
		int scale = 2; // The scale is 2 because our texture file is 512x512 instead of the default 256x256
		GL11.glScalef(scale, scale, 1F);
		gui.drawTexturedModalRect((this.unscaledLeft+384)/scale, this.unscaledTop/scale, 0, 0, 128/scale, 320/scale);
		GL11.glPopMatrix();
		this.textFields.draw();
		this.drawnText.draw();
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
