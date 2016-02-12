package bioMorpho.gui.lifeformBasic;

import net.minecraft.client.renderer.texture.TextureManager;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.gui.GuiMyButton;
import bioMorpho.gui.lifeformSelection.GuiLifeformSelection;
import bioMorpho.gui.modelAnimation.GuiModelAnimation;
import bioMorpho.gui.modelEditor.GuiModelEditor;
import bioMorpho.util.IdGenerator;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class LifeformGuiButtonSelection {
	
	protected IdGenerator ids = new IdGenerator();
	
	protected GuiBasicLifeform gui;
	
	protected GuiMyButton btnLifeformSelection;
	protected GuiMyButton btnModelEditor;
	protected GuiMyButton btnModelAnimation;
	
	public LifeformGuiButtonSelection(GuiBasicLifeform gui, int unscaledLeft, int unscaledTop) {
		this.gui = gui;
		this.btnLifeformSelection = new GuiMyButton(unscaledLeft+0, unscaledTop-24, 24, 24, ids.getNextId());
		this.btnModelEditor = new GuiMyButton(unscaledLeft+24, unscaledTop-24, 24, 24, ids.getNextId());
		this.btnModelAnimation = new GuiMyButton(unscaledLeft+48, unscaledTop-24, 24, 24, ids.getNextId());
	}
	
	public GuiBasicLifeform mousePressedButtons(int x, int y, int button) {
		int index = -1;
		if(this.gui instanceof GuiLifeformSelection) index = ((GuiLifeformSelection)this.gui).getCurrentLifeformIndex();
		else if(this.gui instanceof GuiBasicModel) index = ((GuiBasicModel)this.gui).lifeformIndex;
		if(index == -1) return null;
		GuiBasicLifeform mainGui = null;
		if(this.btnLifeformSelection.mousePressed(x, y, button)) {
			mainGui = new GuiLifeformSelection(this.gui.inventorySlots, this.gui.getTileEntity(), index);
			mainGui.setInitLifeforms(this.gui.getInitLifeforms());
			mainGui.setLifeforms(this.gui.getLifeforms());
			mainGui.setIndexPointers(this.gui.getIndexPointers());
		} else if(this.btnModelEditor.mousePressed(x, y, button)) {
			mainGui = new GuiModelEditor(this.gui.inventorySlots, this.gui.getTileEntity(), index);
			mainGui.setInitLifeforms(this.gui.getInitLifeforms());
			mainGui.setLifeforms(this.gui.getLifeforms());
			mainGui.setIndexPointers(this.gui.getIndexPointers());
		} else if(this.btnModelAnimation.mousePressed(x, y, button)) {
			mainGui = new GuiModelAnimation(this.gui.inventorySlots, this.gui.getTileEntity(), index);
			mainGui.setInitLifeforms(this.gui.getInitLifeforms());
			mainGui.setLifeforms(this.gui.getLifeforms());
			mainGui.setIndexPointers(this.gui.getIndexPointers());
		}
		return mainGui;
	}
	
	public void draw(GuiBasic gui, TextureManager renderEngine) {
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		RenderUtils.bindTexture(renderEngine, GuiTextures.GUI_LIFEFORM_SWAP_BUTTONS);
		gui.drawTexturedRect(this.btnLifeformSelection.getX(), this.btnLifeformSelection.getY(), 
				this.btnLifeformSelection.getWidth(), this.btnLifeformSelection.getHeight(), 0, 0, 256, 256);
		gui.drawTexturedRect(this.btnModelEditor.getX(), this.btnModelEditor.getY(), 
				this.btnModelEditor.getWidth(), this.btnModelEditor.getHeight(), 24, 0, 256, 256);
		gui.drawTexturedRect(this.btnModelAnimation.getX(), this.btnModelAnimation.getY(), 
				this.btnModelAnimation.getWidth(), this.btnModelAnimation.getHeight(), 48, 0, 256, 256);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
