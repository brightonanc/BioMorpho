package bioMorpho.gui.lifeformSelection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.lifeformBasic.ModelRenderingPort;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class CustomModelRenderingPort extends ModelRenderingPort {
	
	protected boolean isInteractive = true;
	protected int guiHeight;
	
	public CustomModelRenderingPort(int unscaledLeft, int unscaledTop, ModelCustom model, TextureManager renderEngine) {
		super(unscaledLeft, unscaledTop, model, renderEngine);
		this.guiHeight = 320; // 320 is the pixel size of the default GuiBasicLifeform/GuiBasicModel gui(s)
	}
	
	/**
	 * Only used for initialization of custom sized rendering ports, like in TabLifeform
	 */
	public CustomModelRenderingPort initCustomAttributes(int shiftX, int shiftY, int width, int height) {
		this.viewportShiftX = shiftX;
		this.viewportShiftY = this.guiHeight - (shiftY + height);
		this.viewportBaseWidthValue = width;
		this.viewportBaseHeightValue = height;
		return this;
	}
	/**
	 * Only used for initialization of custom sized rendering ports, like in TabLifeform
	 */
	public CustomModelRenderingPort initCustomAttributes(int shiftX, int shiftY, int width, int height, double viewingScale) {
		this.initCustomAttributes(shiftX, shiftY, width, height);
		this.viewingScale = viewingScale;
		return this;
	}
	/**
	 * Only used for initialization of custom sized rendering ports, like in TabLifeform
	 */
	public CustomModelRenderingPort initCustomAttributes(int shiftX, int shiftY, int width, int height, double viewingScale, int guiHeight) {
		this.initCustomAttributes(shiftX, shiftY, width, height, viewingScale);
		this.guiHeight = guiHeight;
		return this;
	}
	
	public void setInteractivity(boolean isInteractive) {
		this.isInteractive = isInteractive;
	}
	
	public void setModel(ModelCustom model) {
		this.model = model;
	}
	
	public void setRotation(float rotX, float rotY) {
		this.rotationX = rotX;
		this.rotationY = rotY;
	}
	
	public void addRotation(float rotX, float rotY) {
		this.rotationX += rotX;
		this.rotationY += rotY;
	}
	
	@Override
	public void handleMouseInput(int width, int height, Minecraft mc) {
		if(!this.isInteractive) return;
		int x = Mouse.getX() * width / mc.displayWidth;
		int y = height - Mouse.getY() * height / mc.displayHeight - 1;
		
		// If the mouse is over the viewport
		if(x > this.unscaledLeft+this.viewportShiftX &&
				y > this.unscaledTop+(this.guiHeight-this.viewportShiftY) &&
				x <= this.unscaledLeft+this.viewportShiftX+this.viewportBaseWidthValue &&
				y <= this.unscaledTop+(this.guiHeight-this.viewportShiftY)+this.viewportBaseHeightValue) {
			
			int wheel = Mouse.getDWheel();
			this.viewingScale *= Math.pow(1.001, wheel);
			
			if(Mouse.isButtonDown(1)) {
				if(this.mouseIsDown) {
					this.rotationX += +(this.prevMouseY - y);
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
	
	public void drawWithoutRotPoints(GuiBasic gui, Minecraft mc) {
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
		GL11.glScaled(1D, -1D, 1D);
		GL11.glRotatef(this.rotationX, 1F, 0F, 0F);
		GL11.glRotatef(this.rotationY, 0F, 1F, 0F);
		GL11.glScaled(this.viewingScale, this.viewingScale, this.viewingScale);
		RenderUtils.bindTexture(this.renderEngine, TextureData.PATTERN_DEFAULT);
		this.renderModel();
		GL11.glScaled(1/this.viewingScale, 1/this.viewingScale, 1/this.viewingScale);
		GL11.glRotatef(this.rotationY, 0F, -1F, 0F);
		GL11.glRotatef(this.rotationX, -1F, 0F, 0F);
		GL11.glScaled(1D, -1D, 1D);
		this.resetViewport(mc);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawBackground(GuiBasic gui) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		int x = this.unscaledLeft + this.viewportShiftX;
		int y = this.unscaledTop + (this.guiHeight - (this.viewportShiftY + this.viewportBaseHeightValue));
		RenderUtils.bindTexture(this.renderEngine, GuiTextures.MODEL_RENDERING_PORT_BKGRND);
		gui.drawTexturedRect(x, y, this.viewportBaseWidthValue, this.viewportBaseHeightValue, 0, 0, 512, 512);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void setupViewport(Minecraft mc) {
		GL11.glPushMatrix();
		
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int x = this.unscaledLeft + this.viewportShiftX;
		int y = (sr.getScaledHeight() - (this.unscaledTop + this.guiHeight)) + this.viewportShiftY;
		int width = this.viewportBaseWidthValue;
		int height = this.viewportBaseHeightValue;
		x *= sr.getScaleFactor();
		y *= sr.getScaleFactor();
		width *= sr.getScaleFactor();
		height *= sr.getScaleFactor();
		GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
		GL11.glViewport(x, y, width, height);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, this.zNear, this.zFar);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(0D, 0D, -1*(((this.zFar-this.zNear)/2)+this.zNear));
		GL11.glTranslated(width/2, height/2, 0);
	}
	
	@Override
	protected void resetViewport(Minecraft mc) {
		//GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
		GL11.glPopAttrib();
		
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, sr.getScaledWidth(), sr.getScaledHeight(), 0, 1000D, 3000D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(0D, 0D, -2000D);
		
		GL11.glPopMatrix();
	}
	
}
