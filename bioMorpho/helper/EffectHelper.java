package bioMorpho.helper;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;

import bioMorpho.entity.particle.EntityCustomSmokeFX;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.tileEntity.TENucleobaseCrystal;

/**
 * @author Brighton Ancelin
 *
 */
public class EffectHelper {
	
	private static Random rand = new Random();
	
	/**
	 * Used for gemOfGenesisHolder effects and adds fx to Minecraft.effectRenderer via the FMLClientHandler.instance().getClient().effectRenderer
	 * SHOULD ONLY BE USED CLIENT SIDE DUE TO ADDING IN EffectRenderer!!!
	 */
	public static void createNucleobaseFXToHolderWithRandShift(TEGemOfGenesisHolder gemOfGenesisHolder, TENucleobaseCrystal crystal) {
		double startX = crystal.xCoord + 0.5D;
		double startY = crystal.yCoord + 0.5D;
		double startZ = crystal.zCoord + 0.5D;
		switch(crystal.getOrientation()) {
		case EAST: startX += 0.5D + 0.125D; break;
		case WEST: startX -= 0.5D + 0.125D; break;
		case UP: startY += 0.5D + 0.125D; break;
		case DOWN: startY -= 0.5D + 0.125D; break;
		case SOUTH: startZ += 0.5D + 0.125D; break;
		case NORTH: startZ -= 0.5D + 0.125D; break;
		default: break;
		}
		double endX = gemOfGenesisHolder.xCoord + 0.5D;
		double endY = gemOfGenesisHolder.yCoord + 0.5D;
		double endZ = gemOfGenesisHolder.zCoord + 0.5D;
		
		// The slight randomization begins
		startX += (rand.nextDouble() - 0.5D)/16D;
		startY += (rand.nextDouble() - 0.5D)/16D;
		startZ += (rand.nextDouble() - 0.5D)/16D;
		endX += 0;
		endY += 0;
		endZ += 0;
		// The slight randomization ends
		
		double deltaX = endX - startX;
		double deltaY = endY - startY;
		double deltaZ = endZ - startZ;
		int particleMaxAge = 14;
		double motionX = deltaX/particleMaxAge;
		double motionY = deltaY/particleMaxAge;
		double motionZ = deltaZ/particleMaxAge;
		
		EntityCustomSmokeFX fx = new EntityCustomSmokeFX(crystal.worldObj, startX, startY, startZ, motionX, motionY, motionZ, false);
		fx.setMaxParticleAge(particleMaxAge);
		float[] rgb = crystal.getNucleobase().getDefaultRGBFloatArray();
		fx.setRBGColorF(rgb[0], rgb[1], rgb[2]);
		fx.noClip = true;
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
	
}
