package bioMorpho.gui.compAndBox;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.lifeformBasic.IModelElement;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class CompOnlySetup implements IModelElement {
	
	private IdGenerator ids;
	
	private Gui gui;
	private FontRenderer fontRenderer;
	protected ModelCustom model;
	private int unscaledTop;
	private int unscaledLeft;
	
	private CompOnlyTextFields textFields;
	
	private int compTabNum;
	private ArrayList<TabComp> compTabs;
	
	public CompOnlySetup(Gui gui, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, ModelCustom model, TrustMutex textFieldMutex) {
		this.ids = new IdGenerator();
		this.gui = gui;
		this.fontRenderer = fontRenderer;
		this.model = model;
		this.unscaledTop = unscaledTop;
		this.unscaledLeft = unscaledLeft;
		
		this.textFields = new CompOnlyTextFields(this, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
		
		this.compTabNum = 0;
		this.compTabs = new ArrayList<TabComp>();
		this.compTabs.add(this.compTabNum, new TabComp(this, unscaledLeft, unscaledTop, 23, model.components));
	}
	
	public void onGuiUpdate() {
		if(!this.textFields.getTextFieldById(this.textFields.compPageId).isFocused()) {
			this.textFields.getTextFieldById(this.textFields.compPageId).setText(this.getCurrentCompTab().getCurrentPageNum()+"");
		}
		boolean compExists;
		try {
			this.getCurrentCompTab().getCurrent();
			compExists = true;
		} catch(BioMorphoNoCurrentObjectException e) {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.compNameId) this.textFields.setActiveTextField(-1);
			this.textFields.getTextFieldById(this.textFields.compNameId).setText("");
			compExists = false;
		}
		if(!this.textFields.getTextFieldById(this.textFields.compNameId).isFocused() && compExists) {
			try {
				this.textFields.getTextFieldById(this.textFields.compNameId).setText(this.getCurrentCompTab().getCurrent().name);
			} catch(BioMorphoNoCurrentObjectException e) {
				this.textFields.getTextFieldById(this.textFields.compNameId).setText("");
			}
		}
		/*
		if(!this.textFields.getTextFieldById(this.textFields.compNameId).isFocused()) {
			try {
				this.textFields.getTextFieldById(this.textFields.compNameId).setText(this.getCurrentCompTab().getCurrent().name);
			} catch(BioMorphoNoCurrentObjectException e) {
				this.textFields.getTextFieldById(this.textFields.compNameId).setText("");
			}
		}
		*/
		try { 
			if(this.textFields.getActiveTextFieldValue() == this.textFields.compNameId) {
				this.getCurrentCompTab().getCurrent().name = this.textFields.getActiveTextField().getText();
			}
			//this.getCurrentCompTab().getCurrent().name = this.textFields.getActiveTextField().getText();
		} catch(BioMorphoNoCurrentObjectException e) {/*This is run if no component is selected, which means there's nothing to name!*/}
	}
	
	public ModelCustom getModel() {
		return this.model;
	}
	
	public boolean isCurrentCompTabAtZero() {
		return this.compTabNum == 0;
	}
	
	public TabComp getCurrentCompTab() {
		try {
			this.compTabs.get(this.compTabNum);
		}
		catch(IndexOutOfBoundsException e) {
			// Must check for if subcomps exist
			try {
				this.compTabs.add(this.compTabNum, new TabComp(this, this.unscaledLeft, this.unscaledTop, 23, this.compTabs.get(this.compTabNum-1).getCurrent().segment.getSubComps()));
			} catch(BioMorphoNoCurrentObjectException ex) {}
		}
		return this.compTabs.get(this.compTabNum);
	}
	
	public void compTabRight() {
		this.compTabNum++;
		// The getCurrentCompTab method creates the new comp Tab
	}
	public void compTabLeft() {
		// To insure we don't tab left when we can't
		if(this.compTabNum > 0) {
			// Remove the current tab from the ArrayList so we can create a new one at the same index the next time we tabRight
			// Further explanation: look at the code for the compTabRight() and getCurrentCompTab() methods above
			this.compTabs.remove(this.compTabNum);
			this.compTabNum--;
		}
	}
	
	public void mousePressedTextFields(int x, int y, int button) {
		this.textFields.mousePressed(x, y, button);
	}
	public boolean mousePressed(int x, int y, int button) {
		if(this.getCurrentCompTab().mousePressed(x, y, button)) return true;
		return false;
	}
	
	public boolean keyPressed(char keyChar, int keyCode) {
		if(this.textFields.keyPressed(keyChar, keyCode)) return true;
		return false;
	}
	
	public void draw(GuiBasic gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_ONLY_SETUP_BKGRND);
		int scale = 2; // The scale is 2 because our texture file is 512x512 instead of the default 256x256
		GL11.glScalef(scale, scale, 1F);
		gui.drawTexturedModalRect(this.unscaledLeft/scale, this.unscaledTop/scale, 0, 0, 128/scale, 320/scale);
		GL11.glPopMatrix();
		this.textFields.draw();
		GL11.glColor3f(1F, 1F, 1F); // The text fields alter the color scheme when they draw a vertical line to represent the limit of their String/text
		this.getCurrentCompTab().draw(gui, renderEngine, fontRenderer);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createCompRotXShift() {
		double x = 0;
		for(TabComp curComp : this.compTabs) {
			try {
				x += curComp.getCurrent().segment.mainRotationPointX;
			} catch(BioMorphoNoCurrentObjectException e) { break; }
		}
		return x;
	}
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createCompRotYShift() {
		double y = 0;
		for(TabComp curComp : this.compTabs) {
			try {
				y += curComp.getCurrent().segment.mainRotationPointY;
			} catch(BioMorphoNoCurrentObjectException e) { break; }
		}
		return y;
	}
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createCompRotZShift() {
		double z = 0;
		for(TabComp curComp : this.compTabs) {
			try {
				z += curComp.getCurrent().segment.mainRotationPointZ;
			} catch(BioMorphoNoCurrentObjectException e) { break; }
		}
		return z;
	}
	
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createCompRotationX() {
		double rotX = 0;
		for(int i = 0; i < (this.compTabs.size()-1); i++) {
			TabComp curCompTab = this.compTabs.get(i);
			try {rotX += curCompTab.getCurrent().segment.mainRotationAngleX;}
			catch(BioMorphoNoCurrentObjectException e) {break;}
		}
		return rotX;
	}
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createCompRotationY() {
		double rotY = 0;
		for(int i = 0; i < (this.compTabs.size()-1); i++) {
			TabComp curCompTab = this.compTabs.get(i);
			try {rotY += curCompTab.getCurrent().segment.mainRotationAngleY;}
			catch(BioMorphoNoCurrentObjectException e) {break;}
		}
		return rotY;
	}
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createCompRotationZ() {
		double rotZ = 0;
		for(int i = 0; i < (this.compTabs.size()-1); i++) {
			TabComp curCompTab = this.compTabs.get(i);
			try {rotZ += curCompTab.getCurrent().segment.mainRotationAngleZ;}
			catch(BioMorphoNoCurrentObjectException e) {break;}
		}
		return rotZ;
	}
	
}
