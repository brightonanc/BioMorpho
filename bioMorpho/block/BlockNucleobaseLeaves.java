package bioMorpho.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import bioMorpho.block.BasicBlocks.BlockBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.item.ItemNucleobaseLeaves;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseLeaves extends BlockBasic implements IBlockWithItemBlock, IShearable {
	
	/**
	 * Metadata info:
	 * 1, 2 - Nucleobase Meta
	 * 4 - Able to decay
	 * 8 - Currently decaying
	 */
	
	protected Icon adenineFast;
	protected Icon guanineFast;
	protected Icon thymineFast;
	protected Icon cytosineFast;
	protected Icon adenineFancy;
	protected Icon guanineFancy;
	protected Icon thymineFancy;
	protected Icon cytosineFancy;
	
	public BlockNucleobaseLeaves(int id, String unlocalizedName) {
		super(id, Material.leaves, unlocalizedName);
		this.setLightOpacity(1);
		this.setTickRandomly(true);
		setBurnProperties(this.blockID, 31, 73);
		this.setHardness(0.2F);
		this.setStepSound(soundGrassFootstep);
	}
	
	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseLeaves.class;
	}
	
	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z) { return true; }
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(this.blockID, world.rand.nextInt(fortune+1)+1, this.damageDropped(world.getBlockMetadata(x, y, z))));
		return items;
	}
		
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
		Random rand = world.rand;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		int gooDropCount = 0;
		while(true) {
			if(rand.nextInt(8) == 3) gooDropCount++;
			else break;
			if(gooDropCount >= 7) break;
		}
		items.add(new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, gooDropCount, this.damageDropped(meta)));
		if(rand.nextInt(21) == 7) items.add(new ItemStack(BioMorphoBlocks.nucleobaseSapling, 1, this.damageDropped(meta)));
		return items;
	}
	
	@Override
	public int damageDropped(int meta) { return meta & (2|1); }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		if(!world.isRemote) {
			// If the block is in decaying mode
			if((world.getBlockMetadata(x, y, z) & 8) != 0) {
				if(!areLeavesTouchingCorrespondingLog(world, x, y, z, 7)) {
					world.destroyBlock(x, y, z, true);
				}
			}
		}
	}
	
	public static boolean areLeavesTouchingCorrespondingLog(World world, int x, int y, int z, int searchRadius) {
		int leavesMeta = world.getBlockMetadata(x, y, z);
		for(int xShift = -searchRadius; xShift < searchRadius; xShift++) {
			for(int yShift = -searchRadius; yShift < searchRadius; yShift++) {
				for(int zShift = -searchRadius; zShift < searchRadius; zShift++) {
					int blockId = world.getBlockId(x+xShift, y+yShift, z+zShift);
					int blockMeta = world.getBlockMetadata(x+xShift, y+yShift, z+zShift);
					if(blockId == BioMorphoBlocks.nucleobaseLog.blockID && ((blockMeta & (2|1)) == (leavesMeta & (2|1)))) {
						if(findLeavesPath(world, x, y, z, x+xShift, y+yShift, z+zShift)) return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean findLeavesPath(World world, int leavesX, int leavesY, int leavesZ, int logX, int logY, int logZ) {
		int leavesId = world.getBlockId(leavesX, leavesY, leavesZ);
		int leavesMeta = world.getBlockMetadata(leavesX, leavesY, leavesZ);
		int logId = world.getBlockId(logX, logY, logZ);
		int logMeta = world.getBlockMetadata(logX, logY, logZ);
		boolean xNeg = leavesX - logX < 0;
		boolean yNeg = leavesY - logY < 0;
		boolean zNeg = leavesZ - logZ < 0;
		boolean xPos = leavesX - logX > 0;
		boolean yPos = leavesY - logY > 0;
		boolean zPos = leavesZ - logZ > 0;
		if(xNeg) {
			if(world.getBlockId(leavesX+1, leavesY, leavesZ) == leavesId && ((world.getBlockMetadata(leavesX+1, leavesY, leavesZ) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX+1, leavesY, leavesZ, logX, logY, logZ)) return true;
			} else if(leavesX+1 == logX && leavesY == logY && leavesZ == logZ) return true;
		}
		if(xPos) {
			if(world.getBlockId(leavesX-1, leavesY, leavesZ) == leavesId && ((world.getBlockMetadata(leavesX-1, leavesY, leavesZ) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX-1, leavesY, leavesZ, logX, logY, logZ)) return true;
			} else if(leavesX-1 == logX && leavesY == logY && leavesZ == logZ) return true;
		}
		if(yNeg) {
			if(world.getBlockId(leavesX, leavesY+1, leavesZ) == leavesId && ((world.getBlockMetadata(leavesX, leavesY+1, leavesZ) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX, leavesY+1, leavesZ, logX, logY, logZ)) return true;
			} else if(leavesX == logX && leavesY+1 == logY && leavesZ == logZ) return true;
		}
		if(yPos) {
			if(world.getBlockId(leavesX, leavesY-1, leavesZ) == leavesId && ((world.getBlockMetadata(leavesX, leavesY-1, leavesZ) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX, leavesY-1, leavesZ, logX, logY, logZ)) return true;
			} else if(leavesX == logX && leavesY-1 == logY && leavesZ == logZ) return true;
		}
		if(zNeg) {
			if(world.getBlockId(leavesX, leavesY, leavesZ+1) == leavesId && ((world.getBlockMetadata(leavesX, leavesY, leavesZ+1) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX, leavesY, leavesZ+1, logX, logY, logZ)) return true;
			} else if(leavesX == logX && leavesY == logY && leavesZ+1 == logZ) return true;
		}
		if(zPos) {
			if(world.getBlockId(leavesX, leavesY, leavesZ-1) == leavesId && ((world.getBlockMetadata(leavesX, leavesY, leavesZ-1) & (2|1)) == (leavesMeta & (2|1)))) {
				if(findLeavesPath(world, leavesX, leavesY, leavesZ-1, logX, logY, logZ)) return true;
			} else if(leavesX == logX && leavesY == logY && leavesZ-1 == logZ) return true;
		}
		if(xNeg || xPos || yNeg || yPos || zNeg || zPos) return false;
		else return true;
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
		this.adenineFast = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_A_FAST);
		this.guanineFast = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_G_FAST);
		this.thymineFast = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_T_FAST);
		this.cytosineFast = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_C_FAST);
		this.adenineFancy = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_A_FANCY);
		this.guanineFancy = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_G_FANCY);
		this.thymineFancy = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_T_FANCY);
		this.cytosineFancy = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LEAVES_C_FANCY);
	}
	
	@Override
	public boolean isOpaqueCube() { return !FMLClientHandler.instance().getClient().gameSettings.fancyGraphics; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int realMeta = meta & (2|1);
		if(realMeta >= Nucleobases.values().length) return null;
		boolean isFancy = FMLClientHandler.instance().getClient().gameSettings.fancyGraphics;
		switch(Nucleobases.values()[realMeta]) {
		case ADENINE: return isFancy ? this.adenineFancy : this.adenineFast;
		case GUANINE: return isFancy ? this.guanineFancy : this.guanineFast;
		case THYMINE: return isFancy ? this.thymineFancy : this.thymineFast;
		case CYTOSINE: return isFancy ? this.cytosineFancy : this.cytosineFast;
		}
		return null;
	}
	
}
