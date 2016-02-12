package bioMorpho.handler;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import bioMorpho.data.ConfigSettings;
import bioMorpho.data.InventoryData;
import bioMorpho.data.TextureData;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.manager.InventoryManager;
import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.util.RenderUtils;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class IngameOverlayHandler {
	
	@ForgeSubscribe
	public void preIngameGui(RenderGameOverlayEvent.Pre event) {
		if(event.type == ElementType.ALL) this.preAll(event);
	}
	
	public void preAll(RenderGameOverlayEvent.Pre event) {
		ItemStack gemOfGenesis = InventoryManager.getClientInstance().getClientGemOfGenesis();
		if(IngameSelectionHandler.isTriggered) {
			ScaledResolution sr = event.resolution;
			int width = 238;
			int height = 26;
			int x = (sr.getScaledWidth()/2) - (width/2);
			int y = 0;
			
			int selectedX = x + (LifeformIndexManager.getClientInstance().getIndex() * (width/7));
			int selectedY = y;
			int selectedWidth = (width/7);
			int selectedHeight = height;
			
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
			
			FMLClientHandler.instance().getClient().entityRenderer.setupOverlayRendering();
			
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderUtils.bindTexture(GuiTextures.INGAME_SELECTION_MISC);
			RenderUtils.drawTexturedRect(x, y, 0D, width, height, 0, 0, 256, 256);
			RenderUtils.drawTexturedRect(selectedX, selectedY, 0D, selectedWidth, selectedHeight, 0, 28, 256, 256);
			
			for(int i = 0; i < 7; i++) {				
				int zNear = 1000;
				int zFar = 3000;
				
				GL11.glPushMatrix();
				
				int vX = x + 1 + ((width/7) * i);
				int vY = (sr.getScaledHeight() - (y + height)) + 1;
				int vWidth = (width/7) - 2;
				int vHeight = height - 2;
				vX *= sr.getScaleFactor();
				vY *= sr.getScaleFactor();
				vWidth *= sr.getScaleFactor();
				vHeight *= sr.getScaleFactor();
				GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
				GL11.glViewport(vX, vY, vWidth, vHeight);
				
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, vWidth, vHeight, 0, zNear, zFar);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glTranslated(0D, 0D, -1*(((zFar-zNear)/2)+zNear));
				GL11.glTranslated(vWidth/2, vHeight/2, 0);
				
				if(LifeformHelper.getIndexPointers(gemOfGenesis)[i] != -1) {
					GL11.glRotated(30D, 1D, 0D, 0D);
					GL11.glRotated(FMLClientHandler.instance().getClient().thePlayer.ticksExisted*7, 0D, 1D, 0D);
					GL11.glScalef(1F, -1F, 1F);
					GL11.glScaled(74D, 74D, 74D);
					RenderUtils.bindTexture(TextureData.PATTERN_DEFAULT);
					LifeformHelper.getLifeforms(gemOfGenesis).get(LifeformHelper.getIndexPointers(gemOfGenesis)[i]).getModel().renderWithShift();
				}
				
				GL11.glPopAttrib();
				
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0, sr.getScaledWidth(), sr.getScaledHeight(), 0, 1000D, 3000D);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glTranslated(0D, 0D, -2000D);
				
				GL11.glPopMatrix();
			}
			
			int zNear = 1000;
			int zFar = 3000;
			
			GL11.glPushMatrix();
			
			int width1 = 160;
			int height1 = 120;
			int x1 = (sr.getScaledWidth()/2) - (width1/2);
			int y1 = (sr.getScaledHeight() - (height + height1));
			x1 *= sr.getScaleFactor();
			y1 *= sr.getScaleFactor();
			width1 *= sr.getScaleFactor();
			height1 *= sr.getScaleFactor();
			GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
			GL11.glViewport(x1, y1, width1, height1);
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, width1, height1, 0, zNear, zFar);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslated(0D, 0D, -1*(((zFar-zNear)/2)+zNear));
			GL11.glTranslated(width1/2, height1/2, 0);
			
			if(LifeformHelper.getIndexPointers(gemOfGenesis)[LifeformIndexManager.getClientInstance().getIndex()] != -1) {
				GL11.glRotated(30D, 1D, 0D, 0D);
				GL11.glRotated(FMLClientHandler.instance().getClient().thePlayer.ticksExisted*7, 0D, 1D, 0D);
				GL11.glScalef(1F, -1F, 1F);
				GL11.glScaled(74D, 74D, 74D);
				RenderUtils.bindTexture(TextureData.PATTERN_DEFAULT);
				LifeformHelper.getLifeforms(gemOfGenesis).get(LifeformHelper.getIndexPointers(gemOfGenesis)[LifeformIndexManager.getClientInstance().getIndex()]).getModel().renderWithShift();
			}
			
			GL11.glPopAttrib();
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, sr.getScaledWidth(), sr.getScaledHeight(), 0, 1000D, 3000D);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslated(0D, 0D, -2000D);
			
			GL11.glPopMatrix();
			
			GL11.glPopAttrib();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}
	
}
