package bioMorpho.gui.etc;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.InventoryData;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.inventory.ContainerPlayerBioMorpho;
import bioMorpho.manager.InventoryManager;
import bioMorpho.util.RenderUtils;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiBioMorphoPlayer extends GuiContainer {
	
	public GuiBioMorphoPlayer(ContainerPlayerBioMorpho specialContainer) {
		super(specialContainer);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		if(!((ContainerPlayerBioMorpho)this.inventorySlots).gemSlot.getHasStack()) RenderUtils.bindTexture(this.mc.renderEngine, GuiTextures.INVENTORY_BIO_MORPHO_PLAYER_BASE);
		else RenderUtils.bindTexture(this.mc.renderEngine, GuiTextures.INVENTORY_BIO_MORPHO_PLAYER_DESIGNED);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
