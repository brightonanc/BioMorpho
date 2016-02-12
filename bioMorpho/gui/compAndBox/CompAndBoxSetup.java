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
import bioMorpho.lifeform.model.EntitySegment;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.lifeform.model.SegmentBox;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;
import bioMorpho.util.TrustMutex;


/**
 * @author Brighton Ancelin
 *
 */
public class CompAndBoxSetup implements IModelElement {
	
	private IdGenerator ids;
	
	private Gui gui;
	private FontRenderer fontRenderer;
	protected ModelCustom model;
	private int unscaledTop;
	private int unscaledLeft;
	
	private CompAndBoxTextFields textFields;
	
	private int compTabNum;
	private ArrayList<TabComp> compTabs;
	private TabBox boxTab;
	
	public CompAndBoxSetup(Gui gui, int unscaledLeft, int unscaledTop, FontRenderer fontRenderer, ModelCustom model, TrustMutex textFieldMutex) {
		this.ids = new IdGenerator();
		this.gui = gui;
		this.fontRenderer = fontRenderer;
		this.model = model;
		this.unscaledTop = unscaledTop;
		this.unscaledLeft = unscaledLeft;
		
		this.textFields = new CompAndBoxTextFields(this, unscaledLeft, unscaledTop, fontRenderer, textFieldMutex);
		
		this.compTabNum = 0;
		this.compTabs = new ArrayList<TabComp>();
		this.compTabs.add(this.compTabNum, new TabComp(this, unscaledLeft, unscaledTop, 11, model.components));
		this.boxTab = new TabBox(unscaledLeft, unscaledTop, 9, new ArrayList<SegmentBox>());
	}
	
	public void onGuiUpdate() {
		try {
			if(this.getBoxTab().members != this.getCurrentCompTab().getCurrent().segment.cubeList) {
				this.getBoxTab().members = this.getCurrentCompTab().getCurrent().segment.cubeList;
				this.getBoxTab().currentObjIndex = -1;
			}
		} catch(BioMorphoNoCurrentObjectException e) {
			this.getBoxTab().members = new ArrayList<SegmentBox>();
			this.getBoxTab().currentObjIndex = -1;
		}
		
		if(!this.textFields.getTextFieldById(this.textFields.compPageId).isFocused()) {
			this.textFields.getTextFieldById(this.textFields.compPageId).setText(this.getCurrentCompTab().getCurrentPageNum()+"");
		}
		if(!this.textFields.getTextFieldById(this.textFields.boxPageId).isFocused()) {
			this.textFields.getTextFieldById(this.textFields.boxPageId).setText(this.getBoxTab().getCurrentPageNum()+"");
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
		boolean boxExists;
		try {
			this.getBoxTab().getCurrent();
			boxExists = true;
		} catch(BioMorphoNoCurrentObjectException e) {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.boxNameId) this.textFields.setActiveTextField(-1);
			this.textFields.getTextFieldById(this.textFields.boxNameId).setText("");
			boxExists = false;
		}
		if(!this.textFields.getTextFieldById(this.textFields.boxNameId).isFocused() && boxExists) {
			try {
				this.textFields.getTextFieldById(this.textFields.boxNameId).setText(this.getBoxTab().getCurrent().name);
			} catch(BioMorphoNoCurrentObjectException e) {
				this.textFields.getTextFieldById(this.textFields.boxNameId).setText("");
			}
		}
		
		try {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.compNameId) {
				this.getCurrentCompTab().getCurrent().name = this.textFields.getActiveTextField().getText();
			}
		} catch(BioMorphoNoCurrentObjectException e) {/*This is run if no component is selected, which means there's nothing to name!*/}
		try {
			if(this.textFields.getActiveTextFieldValue() == this.textFields.boxNameId) {
				this.getBoxTab().getCurrent().name = this.textFields.getActiveTextField().getText();
			}
		} catch(BioMorphoNoCurrentObjectException e) {/*This is run if no box is selected, which means there's nothing to name!*/}
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
				this.compTabs.add(this.compTabNum, new TabComp(this, this.unscaledLeft, this.unscaledTop, 11, this.compTabs.get(this.compTabNum-1).getCurrent().segment.getSubComps()));
			} catch(BioMorphoNoCurrentObjectException ex) {}
		}
		return this.compTabs.get(this.compTabNum);
	}
	public TabBox getBoxTab() {
		return this.boxTab;
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
		if(this.boxTab.mousePressed(x, y, button)) return true;
		return false;
	}
	
	public boolean keyPressed(char keyChar, int keyCode) {
		if(this.textFields.keyPressed(keyChar, keyCode)) return true;
		return false;
	}
	
	public void draw(GuiBasic gui, TextureManager renderEngine, FontRenderer fontRenderer) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPushMatrix();
		RenderUtils.bindTexture(renderEngine, GuiTextures.COMP_AND_BOX_SETUP_BKGRND);
		int scale = 2; // The scale is 2 because our texture file is 512x512 instead of the default 256x256
		GL11.glScalef(scale, scale, 1F);
		gui.drawTexturedModalRect(this.unscaledLeft/scale, this.unscaledTop/scale, 0, 0, 128/scale, 320/scale);
		GL11.glPopMatrix();
		this.textFields.draw();
		GL11.glColor3f(1F, 1F, 1F); // The text fields alter the color scheme when they draw a vertical line to represent the limit of their String/text
		this.getCurrentCompTab().draw(gui, renderEngine, fontRenderer);
		this.getBoxTab().draw(gui, renderEngine, fontRenderer);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
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
	
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createBoxRotXShift() {
		double x = 0;
		x += this.createCompRotXShift();
		try { x += this.getBoxTab().getCurrent().getRotPointX(); }
		catch(BioMorphoNoCurrentObjectException e) {}
		return x;
	}
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createBoxRotYShift() {
		double y = 0;
		y += this.createCompRotYShift();
		try { y += this.getBoxTab().getCurrent().getRotPointY(); }
		catch(BioMorphoNoCurrentObjectException e) {}
		return y;
	}
	/**
	 * DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS
	 */
	public double createBoxRotZShift() {
		double z = 0;
		z += this.createCompRotZShift();
		try { z += this.getBoxTab().getCurrent().getRotPointZ(); }
		catch(BioMorphoNoCurrentObjectException e) {}
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
	
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createBoxRotationX() {
		double rotX = 0;
		rotX += this.createCompRotationX();
		try {rotX += this.getCurrentCompTab().getCurrent().segment.mainRotationAngleX;}
		catch(BioMorphoNoCurrentObjectException e) {}
		return rotX;
	}
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createBoxRotationY() {
		double rotY = 0;
		rotY += this.createCompRotationY();
		try {rotY += this.getCurrentCompTab().getCurrent().segment.mainRotationAngleY;}
		catch(BioMorphoNoCurrentObjectException e) {}
		return rotY;
	}
	/** DO NOT USE UNLESS YOU ARE ModelRenderingPort FOR COMP ROTATION POINTS */
	public double createBoxRotationZ() {
		double rotZ = 0;
		rotZ += this.createCompRotationZ();
		try {rotZ += this.getCurrentCompTab().getCurrent().segment.mainRotationAngleZ;}
		catch(BioMorphoNoCurrentObjectException e) {}
		return rotZ;
	}
	
	public void setupViewportCompMatrixAlterations() {
		for(int i = 0; i < (this.compTabs.size()-1); i++) {
			TabComp curCompTab = this.compTabs.get(i);
			try {
				EntitySegment seg = curCompTab.getCurrent().segment;
				if(seg.mainRotationPointX != 0 || seg.mainRotationPointY != 0 || seg.mainRotationPointZ != 0) GL11.glTranslatef(seg.mainRotationPointX/16F, seg.mainRotationPointY/16F, seg.mainRotationPointZ/16F);
				if(seg.mainRotationAngleX != 0) GL11.glRotatef(seg.mainRotationAngleX, 1F, 0F, 0F);
				if(seg.mainRotationAngleY != 0) GL11.glRotatef(seg.mainRotationAngleY, 0F, 1F, 0F);
				if(seg.mainRotationAngleZ != 0) GL11.glRotatef(seg.mainRotationAngleZ, 0F, 0F, 1F);
			} catch(BioMorphoNoCurrentObjectException e) {}
		}
		try {
			EntitySegment seg = this.getCurrentCompTab().getCurrent().segment;
			if(seg.mainRotationPointX != 0 || seg.mainRotationPointY != 0 || seg.mainRotationPointZ != 0) GL11.glTranslatef(seg.mainRotationPointX/16F, seg.mainRotationPointY/16F, seg.mainRotationPointZ/16F);
		} catch(BioMorphoNoCurrentObjectException e) {}
	}
	public void reverseViewportCompRotationAlterations() {
		for(int i = ((this.compTabs.size()-1)-1); i >= 0; i--) {
			TabComp curCompTab = this.compTabs.get(i);
			try {
				EntitySegment seg = curCompTab.getCurrent().segment;
				if(seg.mainRotationAngleZ != 0) GL11.glRotatef(-seg.mainRotationAngleZ, 0F, 0F, 1F);
				if(seg.mainRotationAngleY != 0) GL11.glRotatef(-seg.mainRotationAngleY, 0F, 1F, 0F);
				if(seg.mainRotationAngleX != 0) GL11.glRotatef(-seg.mainRotationAngleX, 1F, 0F, 0F);
			} catch(BioMorphoNoCurrentObjectException e) {}
		}
	}
	
	public void setupViewportBoxMatrixAlterations() {
		this.setupViewportCompMatrixAlterations();
		try {
			EntitySegment seg = this.getCurrentCompTab().getCurrent().segment;
			if(seg.mainRotationAngleX != 0) GL11.glRotatef(seg.mainRotationAngleX, 1F, 0F, 0F);
			if(seg.mainRotationAngleY != 0) GL11.glRotatef(seg.mainRotationAngleY, 0F, 1F, 0F);
			if(seg.mainRotationAngleZ != 0) GL11.glRotatef(seg.mainRotationAngleZ, 0F, 0F, 1F);
		} catch(BioMorphoNoCurrentObjectException e) {}
		try {
			SegmentBox box = this.getBoxTab().getCurrent();
			if(box.getRotPointX() != 0 || box.getRotPointY() != 0 || box.getRotPointZ() != 0) GL11.glTranslatef(box.getRotPointX()/16F, box.getRotPointY()/16F, box.getRotPointZ()/16F);
		} catch(BioMorphoNoCurrentObjectException e) {}
	}
	public void reverseViewportBoxRotationAlterations() {
		try {
			EntitySegment seg = this.getCurrentCompTab().getCurrent().segment;
			if(seg.mainRotationAngleZ != 0) GL11.glRotatef(-seg.mainRotationAngleZ, 0F, 0F, 1F);
			if(seg.mainRotationAngleY != 0) GL11.glRotatef(-seg.mainRotationAngleY, 0F, 1F, 0F);
			if(seg.mainRotationAngleX != 0) GL11.glRotatef(-seg.mainRotationAngleX, 1F, 0F, 0F);
		} catch(BioMorphoNoCurrentObjectException e) {}
		this.reverseViewportCompRotationAlterations();
	}
	
}
