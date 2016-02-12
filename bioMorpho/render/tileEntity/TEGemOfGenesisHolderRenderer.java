package bioMorpho.render.tileEntity;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.TextureData.ModelTextures;
import bioMorpho.model.Models;
import bioMorpho.tileEntity.TEBioMorpho;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class TEGemOfGenesisHolderRenderer extends TEBioMorphoRenderer {
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) {
		if(!(tileEntity instanceof TEGemOfGenesisHolder)) return;
		GL11.glPushMatrix();
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting) GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
		
		GL11.glTranslated(0.5D, 0.5D, 0.5D);
		switch(((TEBioMorpho)tileEntity).getOrientation()) {
		case UP: break;
		case DOWN: GL11.glRotatef(180, 1F, 0F, 0F); break;
		case WEST: GL11.glRotatef(90, 0F, 0F, 1F); break;
		case EAST: GL11.glRotatef(90, 0F, 0F, -1F); break;
		case NORTH: GL11.glRotatef(90, -1F, 0F, 0F); break;
		case SOUTH: GL11.glRotatef(90, 1F, 0F, 0F); break;
		default: break;
		}
//		GL11.glTranslated(-0.5D, -0.5D, -0.5D);
//These two calls in tandem cancel each other out		
//		GL11.glTranslated(0.5D, 0.5D, 0.5D);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		
		TEGemOfGenesisHolder castTE = (TEGemOfGenesisHolder)tileEntity;
		long worldTime = FMLClientHandler.instance().getClient().theWorld.getTotalWorldTime();
		
		if(castTE.getGem() == null) {
			this.func_110628_a(ModelTextures.GEM_OF_GENESIS_HOLDER_DORMANT);
		} else {
			int index = (int)((worldTime/2) % 4);
			this.func_110628_a(ModelTextures.GEM_OF_GENESIS_HOLDER_ACTIVE[index]);
		}
		Models.gemOfGenesisHolder.renderAll();
		if(castTE.getGem() != null) {
			GL11.glScaled(0.4D, 0.4D, 0.4D);
			this.func_110628_a(ModelTextures.GEM_OF_GENESIS);
			Models.gemOfGenesis.renderAll();
		}
		if(lighting) GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
}
