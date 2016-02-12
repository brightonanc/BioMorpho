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
import net.minecraftforge.common.EnumPlantType;
import bioMorpho.block.BasicBlocks.BlockPlantBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.ItemNucleobaseSapling;
import bioMorpho.world.gen.WorldGenNucleobaseTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseSapling extends BlockPlantBasic implements IBlockWithItemBlock {
	
	protected Icon adenineIcon;
	protected Icon guanineIcon;
	protected Icon thymineIcon;
	protected Icon cytosineIcon;
	
	public BlockNucleobaseSapling(int id, String unlocalizedName) {
		super(id, Material.grass, unlocalizedName);
		this.setTickRandomly(true);
		this.setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.75F, 0.75F);
		this.setHardness(0F);
		this.setStepSound(soundGrassFootstep);
	}
	
	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseSapling.class;
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, Nucleobases.ADENINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.GUANINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.THYMINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.CYTOSINE.ordinal()));
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(!world.isRemote) {
			super.updateTick(world, x, y, z, rand);
			if(rand.nextInt(7) != 2) return;
			int randomCap = world.getBlockId(x, y-1, z) == BioMorphoBlocks.energeticSand.blockID ? 1 : 8;
			if(rand.nextInt(randomCap) == 0) {
				if((new WorldGenNucleobaseTree(BioMorphoBlocks.nucleobaseLog.blockID, world.getBlockMetadata(x, y, z), BioMorphoBlocks.nucleobaseLeaves.blockID, world.getBlockMetadata(x, y, z))).generate(world, rand, x, y, z)){
					if(world.getBlockId(x, y-1, z) == BioMorphoBlocks.energeticSand.blockID) world.setBlock(x, y-1, z, Block.sand.blockID);
				}
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		if(world.getBlockId(x, y-1, z) == Block.dirt.blockID) return true;
		if(world.getBlockId(x, y-1, z) == Block.grass.blockID) return true;
		if(world.getBlockId(x, y-1, z) == BioMorphoBlocks.energeticSand.blockID) return true;
		return false;
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int id) {
		if(id == Block.dirt.blockID) return true;
		if(id == Block.grass.blockID) return true;
		if(id == BioMorphoBlocks.energeticSand.blockID) return true;
		return false;
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
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.adenineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_SAPLING_A);
		this.guanineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_SAPLING_G);
		this.thymineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_SAPLING_T);
		this.cytosineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_SAPLING_C);
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) { return EnumPlantType.Plains; }
	
}
