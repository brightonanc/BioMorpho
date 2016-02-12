package bioMorpho.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import bioMorpho.block.BasicBlocks.BlockBasic;
import bioMorpho.data.TextureData.BlockTextures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockEnergeticSand extends BlockBasic {
	
	public BlockEnergeticSand(int id, String unlocalizedName) {
		super(id, Material.sand, unlocalizedName);
		this.setLightValue(1F);
		this.setHardness(0.5F);
		this.setStepSound(soundSandFootstep);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(BlockTextures.ENERGETIC_SAND);
	}
	
}
