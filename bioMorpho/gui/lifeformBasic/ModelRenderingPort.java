package bioMorpho.gui.lifeformBasic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.gui.compAndBox.CompOnlySetup;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.lifeform.model.SegmentBox;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class ModelRenderingPort {
	
	protected int unscaledLeft;
	protected int unscaledTop;
	
	// x, y, width, and height are all in "Gui-pixels"
	/** X Shift from the Bottom Left corner of the Gui's screen */
	protected int viewportShiftX;
	/** Y Shift from the Bottom Left corner of the Gui's screen */
	protected int viewportShiftY;
	protected int viewportBaseWidthValue;
	protected int viewportBaseHeightValue;
	
	protected double zNear;
	protected double zFar;
	
	protected ModelCustom model;
	
	protected TextureManager renderEngine;
	
	// Below are all the values customizable in the gui
	protected double viewingScale;
	protected float rotationX;
	protected float rotationY;
	
	// Below are all the variables used in the handleMouseInput() method
	protected boolean mouseIsDown;
	protected int prevMouseX;
	protected int prevMouseY;
	
	public ModelRenderingPort(int unscaledLeft, int unscaledTop, ModelCustom model, TextureManager renderEngine) {
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.viewportShiftX = 128;
		this.viewportShiftY = 128;
		this.viewportBaseWidthValue = 256;
		this.viewportBaseHeightValue = 192;
		this.zNear = 1000D;
		this.zFar = 3000D;
		this.model = model;
		this.renderEngine = renderEngine;
		
		this.viewingScale = 7D;
		this.rotationX = 0F;
		this.rotationY = 0F;
	}
	
	public void handleMouseInput(int width, int height, Minecraft mc) {
		int x = Mouse.getX() * width / mc.displayWidth;
		int y = height - Mouse.getY() * height / mc.displayHeight - 1;
		
		// If the mouse is over the viewport
		if(x > this.unscaledLeft+this.viewportShiftX &&
				y > this.unscaledTop &&
				x <= this.unscaledLeft+this.viewportShiftX+this.viewportBaseWidthValue &&
				y <= this.unscaledTop+this.viewportBaseHeightValue) {
			
			int wheel = Mouse.getDWheel();
			this.viewingScale *= Math.pow(1.001, wheel);
			
			if(Mouse.isButtonDown(1)) {
				if(this.mouseIsDown) {
					this.rotationX += -(this.prevMouseY - y);
					this.rotationY += -(this.prevMouseX - x);
					this.prevMouseX = x;
					this.prevMouseY = y;
				} else {
					this.prevMouseX = x;
					this.prevMouseY = y;
					this.mouseIsDown = true;
				}
			}
			else this.mouseIsDown = false;
		}
	}
	
	@Deprecated
	public void draw(GuiBasic gui, Minecraft mc, EntityComponent comp, SegmentBox box) {
		/**
		 * Notes on how viewports and things associated with them work:
		 * 
		 * 	-Minecraft.displayWidth and Minecraft.displayHeight are integers representing the screen's dimensions IN PIXELS
		 * 	-In a GUI, a drawn pixel, or what I'm calling a Gui-pixel, may be the size of 4 pixels, 9 pixels, and other square values like that
		 * 	-To find the size of these "Gui-pixels", you can call ScaledResolution.getScaleFactor() method. The returned int is the number 
		 * 		of pixels on a side of a "Gui-pixel". Square the returned int to find out how many pixels are actually within a "Gui-pixel"
		 * 	-ScaledResolution.getScaledWidth() and ScaledResolution.getScaledHeight() are integers representing the screens dimensions IN "GUI-PIXELS"
		 * 	-Do NOT get the scaleFactor from Minecraft.gameSettings.guiScale!!! If the guiScale is too large for the screen, 
		 * 		the game intelligently turns the size of a "Gui-pixel" to a smaller quantity that can be supported/drawn effectively. 
		 * 		The ScaledResolution class accounts for this and gives you the correct values for a "Gui-pixel". For more information, 
		 * 		look into the ScaledResolution class.
		 */
		
		GL11.glPushMatrix();
		this.drawBackground(gui);
		this.setupViewport(mc);
		GL11.glRotatef(this.rotationX, 1F, 0F, 0F);
		GL11.glRotatef(this.rotationY, 0F, 1F, 0F);
		GL11.glScaled(this.viewingScale, this.viewingScale, this.viewingScale);
		RenderUtils.bindTexture(this.renderEngine, TextureData.PATTERN_DEFAULT);
		this.renderModel();
		if(comp != null) this.drawCompRotationPoint(comp);
		if(comp != null && box != null) this.drawBoxRotationPoint(comp, box);
		GL11.glScaled(1/this.viewingScale, 1/this.viewingScale, 1/this.viewingScale);
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		this.resetViewport(mc);
		GL11.glPopMatrix();
	}
	
	public void draw(GuiBasic gui, Minecraft mc, CompAndBoxSetup cAndB) {
		/**
		 * Notes on how viewports and things associated with them work:
		 * 
		 * 	-Minecraft.displayWidth and Minecraft.displayHeight are integers representing the screen's dimensions IN PIXELS
		 * 	-In a GUI, a drawn pixel, or what I'm calling a Gui-pixel, may be the size of 4 pixels, 9 pixels, and other square values like that
		 * 	-To find the size of these "Gui-pixels", you can call ScaledResolution.getScaleFactor() method. The returned int is the number 
		 * 		of pixels on a side of a "Gui-pixel". Square the returned int to find out how many pixels are actually within a "Gui-pixel"
		 * 	-ScaledResolution.getScaledWidth() and ScaledResolution.getScaledHeight() are integers representing the screens dimensions IN "GUI-PIXELS"
		 * 	-Do NOT get the scaleFactor from Minecraft.gameSettings.guiScale!!! If the guiScale is too large for the screen, 
		 * 		the game intelligently turns the size of a "Gui-pixel" to a smaller quantity that can be supported/drawn effectively. 
		 * 		The ScaledResolution class accounts for this and gives you the correct values for a "Gui-pixel". For more information, 
		 * 		look into the ScaledResolution class.
		 */
		
		EntityComponent comp;
		try { comp = cAndB.getCurrentCompTab().getCurrent(); }
		catch(BioMorphoNoCurrentObjectException e) { comp = null; }
		SegmentBox box;
		try { box = cAndB.getBoxTab().getCurrent(); }
		catch(BioMorphoNoCurrentObjectException e) { box = null; }
		
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		this.drawBackground(gui);
		this.setupViewport(mc);
		GL11.glScaled(1D, -1D, 1D);
		GL11.glRotatef(this.rotationX, 1F, 0F, 0F);
		GL11.glRotatef(this.rotationY, 0F, 1F, 0F);
		GL11.glScaled(this.viewingScale, this.viewingScale, this.viewingScale);
		RenderUtils.bindTexture(this.renderEngine, TextureData.PATTERN_DEFAULT);
		this.renderModel();
		if(comp != null) this.drawCompRotationPoint(cAndB);
		if(comp != null && box != null) this.drawBoxRotationPoint(cAndB);
		GL11.glScaled(1/this.viewingScale, 1/this.viewingScale, 1/this.viewingScale);
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		GL11.glScaled(1D, -1D, 1D);
		this.resetViewport(mc);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	public void draw(GuiBasic gui, Minecraft mc, CompOnlySetup compOnly) {
		/**
		 * Notes on how viewports and things associated with them work:
		 * 
		 * 	-Minecraft.displayWidth and Minecraft.displayHeight are integers representing the screen's dimensions IN PIXELS
		 * 	-In a GUI, a drawn pixel, or what I'm calling a Gui-pixel, may be the size of 4 pixels, 9 pixels, and other square values like that
		 * 	-To find the size of these "Gui-pixels", you can call ScaledResolution.getScaleFactor() method. The returned int is the number 
		 * 		of pixels on a side of a "Gui-pixel". Square the returned int to find out how many pixels are actually within a "Gui-pixel"
		 * 	-ScaledResolution.getScaledWidth() and ScaledResolution.getScaledHeight() are integers representing the screens dimensions IN "GUI-PIXELS"
		 * 	-Do NOT get the scaleFactor from Minecraft.gameSettings.guiScale!!! If the guiScale is too large for the screen, 
		 * 		the game intelligently turns the size of a "Gui-pixel" to a smaller quantity that can be supported/drawn effectively. 
		 * 		The ScaledResolution class accounts for this and gives you the correct values for a "Gui-pixel". For more information, 
		 * 		look into the ScaledResolution class.
		 */
		
		EntityComponent comp;
		try { comp = compOnly.getCurrentCompTab().getCurrent(); }
		catch(BioMorphoNoCurrentObjectException e) { comp = null; }
		
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		this.drawBackground(gui);
		this.setupViewport(mc);
		GL11.glScaled(1D, -1D, 1D);
		GL11.glRotatef(this.rotationX, 1F, 0F, 0F);
		GL11.glRotatef(this.rotationY, 0F, 1F, 0F);
		GL11.glScaled(this.viewingScale, this.viewingScale, this.viewingScale);
		RenderUtils.bindTexture(this.renderEngine, TextureData.PATTERN_DEFAULT);
		this.renderModel();
		if(comp != null) this.drawCompRotationPoint(compOnly);
		GL11.glScaled(1/this.viewingScale, 1/this.viewingScale, 1/this.viewingScale);
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		GL11.glScaled(1D, -1D, 1D);
		this.resetViewport(mc);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	/*
	 * Don't forget to call this.resetViewport(Minecraft mc) after you're done rendering out everything :)
	 */
	protected void setupViewport(Minecraft mc) {
		GL11.glPushMatrix();
		
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int x = this.unscaledLeft + this.viewportShiftX;
		int y = (sr.getScaledHeight() - (this.unscaledTop + this.viewportBaseHeightValue + this.viewportShiftY)) + this.viewportShiftY;
		int width = this.viewportBaseWidthValue;
		int height = this.viewportBaseHeightValue;
		x *= sr.getScaleFactor();
		y *= sr.getScaleFactor();
		width *= sr.getScaleFactor();
		height *= sr.getScaleFactor();
		GL11.glViewport(x, y, width, height);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, this.zNear, this.zFar);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(0D, 0D, -1*(((this.zFar-this.zNear)/2)+this.zNear));
		GL11.glTranslated(width/2, height/2, 0);
	}
	
	/*
	 * Don't call this method unless you've already called this.setupViewport(Minecraft mc) as it will call GL11.glPopMatrix() :)
	 */
	protected void resetViewport(Minecraft mc) {
		GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glPopMatrix();
	}
	
	protected void renderModel() {
		GL11.glPushMatrix();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		boolean depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		boolean cullFace = GL11.glGetBoolean(GL11.GL_CULL_FACE);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		if(!depthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
		if(cullFace) GL11.glDisable(GL11.GL_CULL_FACE);
		this.model.renderWithoutShift();
		if(cullFace) GL11.glEnable(GL11.GL_CULL_FACE);
		if(!depthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	protected void drawBackground(GuiBasic gui) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_BKGRND);
		gui.drawTexturedRect(this.unscaledLeft+128, this.unscaledTop, 256, 192, 0, 0, 512, 512);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Deprecated
	protected void drawCompRotationPoint(EntityComponent comp) {
		GL11.glPushMatrix();
		
		double scale = 0.333D;
		
		int circleDiameter = 64;
		double x = (double)comp.segment.mainRotationPointX;
		double y = (double)comp.segment.mainRotationPointY;
		double z = (double)comp.segment.mainRotationPointZ;
		
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_ROT_POINT);

		GL11.glTranslated(x, y, z);
		
		GL11.glScaled((1/this.viewingScale), (1/this.viewingScale), (1/this.viewingScale));
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		
		GL11.glScaled(scale, scale, scale);
		boolean depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		if(depthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderUtils.drawTexturedRect(-((double)circleDiameter/2), -((double)circleDiameter/2), 0, circleDiameter, circleDiameter, 0, 0, 64, 64);
		if(depthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	@Deprecated
	protected void drawBoxRotationPoint(EntityComponent comp, SegmentBox box) {
		GL11.glPushMatrix();
		
		double scale = 0.222D;
		
		int circleDiameter = 64;
		double x = (double)box.getRotPointX() + (double)comp.segment.mainRotationPointX;
		double y = (double)box.getRotPointY() + (double)comp.segment.mainRotationPointY;
		double z = (double)box.getRotPointZ() + (double)comp.segment.mainRotationPointZ;
		
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_ROT_POINT);

		GL11.glTranslated(x, y, z);
		
		GL11.glScaled((1/this.viewingScale), (1/this.viewingScale), (1/this.viewingScale));
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		
		GL11.glScaled(scale, scale, scale);
		boolean depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		if(depthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderUtils.drawTexturedRect(-((double)circleDiameter/2), -((double)circleDiameter/2), 0, circleDiameter, circleDiameter, 0, 0, 64, 64);
		if(depthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	
	protected void drawCompRotationPoint(Object cAndB_or_compOnly) {
		GL11.glPushMatrix();
		
		double scale = 0.333D;
		
		int circleDiameter = 64;
		//double x = (double)comp.segment.mainRotationPointX;
		//double y = (double)comp.segment.mainRotationPointY;
		//double z = (double)comp.segment.mainRotationPointZ;
		
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_ROT_POINT);
		
		if(cAndB_or_compOnly instanceof CompAndBoxSetup) {
			CompAndBoxSetup cAndB = (CompAndBoxSetup)cAndB_or_compOnly;
			cAndB.setupViewportCompMatrixAlterations();
			cAndB.reverseViewportCompRotationAlterations();
		} else if(cAndB_or_compOnly instanceof CompOnlySetup) {
			CompOnlySetup compOnly = (CompOnlySetup)cAndB_or_compOnly;
			
		}
		
		GL11.glScaled((1/this.viewingScale), (1/this.viewingScale), (1/this.viewingScale));
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		
		GL11.glScaled(scale, scale, scale);
		boolean depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		if(depthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glScaled(1D, -1D, 1D);
		RenderUtils.drawTexturedRect(-((double)circleDiameter/2), -((double)circleDiameter/2), 0, circleDiameter, circleDiameter, 0, 0, 64, 64);
		if(depthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	protected void drawBoxRotationPoint(Object cAndB_or_compOnly) {
		GL11.glPushMatrix();
		
		double scale = 0.222D;
		
		int circleDiameter = 64;
		//double x = (double)box.getRotPointX() + (double)comp.segment.mainRotationPointX;
		//double y = (double)box.getRotPointY() + (double)comp.segment.mainRotationPointY;
		//double z = (double)box.getRotPointZ() + (double)comp.segment.mainRotationPointZ;
		
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_ROT_POINT);
		
		if(cAndB_or_compOnly instanceof CompAndBoxSetup) {
			CompAndBoxSetup cAndB = (CompAndBoxSetup)cAndB_or_compOnly;
			cAndB.setupViewportBoxMatrixAlterations();
			cAndB.reverseViewportBoxRotationAlterations();
		} else if(cAndB_or_compOnly instanceof CompOnlySetup) {
			CompOnlySetup compOnly = (CompOnlySetup)cAndB_or_compOnly;
			
		}
		
		GL11.glScaled((1/this.viewingScale), (1/this.viewingScale), (1/this.viewingScale));
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		
		GL11.glScaled(scale, scale, scale);
		boolean depthTest = GL11.glGetBoolean(GL11.GL_DEPTH_TEST);
		if(depthTest) GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glScaled(1D, -1D, 1D);
		RenderUtils.drawTexturedRect(-((double)circleDiameter/2), -((double)circleDiameter/2), 0, circleDiameter, circleDiameter, 0, 0, 64, 64);
		if(depthTest) GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	
}
