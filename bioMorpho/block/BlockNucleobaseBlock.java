package bioMorpho.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bioMorpho.block.BasicBlocks.BlockBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.ItemNucleobaseBlock;
import bioMorpho.tileEntity.TENucleobaseCrystal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseBlock extends BlockBasic implements IBlockWithItemBlock {
	
	protected Icon adenineIcon;
	protected Icon guanineIcon;
	protected Icon thymineIcon;
	protected Icon cytosineIcon;
	
	public BlockNucleobaseBlock(int id, String unlocalizedName) {
		super(id, Material.rock, unlocalizedName);
		this.setTickRandomly(true);
		this.setHardness(3F);
		this.setStepSound(soundStoneFootstep);
	}

	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseBlock.class;
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, Nucleobases.ADENINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.GUANINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.THYMINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.CYTOSINE.ordinal()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.adenineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_BLOCK_A);
		this.guanineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_BLOCK_G);
		this.thymineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_BLOCK_T);
		this.cytosineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_BLOCK_C);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(meta >= Nucleobases.values().length) return null;
		switch(Nucleobases.values()[meta]) {
		case ADENINE: return this.adenineIcon;
		case GUANINE: return this.guanineIcon;
		case THYMINE: return this.thymineIcon;
		case CYTOSINE: return this.cytosineIcon;
		}
		return null;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		if(rand.nextInt(3) != 1) return;
		for(ForgeDirection direction : ForgeDirection.values()) {
			int crystalX = x+direction.offsetX;
			int crystalY = y+direction.offsetY;
			int crystalZ = z+direction.offsetZ;
			if(world.getBlockId(crystalX, crystalY, crystalZ) == Block.waterStill.blockID) {
				world.setBlock(crystalX, crystalY, crystalZ, BioMorphoBlocks.nucleobaseCrystal.blockID, 0, 1|2);
				TENucleobaseCrystal newTileEntity = new TENucleobaseCrystal();
				newTileEntity.setNucleobase(Nucleobases.values()[world.getBlockMetadata(x, y, z)]);
				newTileEntity.setOrientation(direction);
				newTileEntity.setCharge(0);
				world.setBlockTileEntity(crystalX, crystalY, crystalZ, newTileEntity);
				break;
			}
		}
	}

}
