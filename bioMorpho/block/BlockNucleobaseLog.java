package bioMorpho.block;

import java.util.List;

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
import bioMorpho.item.ItemNucleobaseLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseLog extends BlockBasic implements IBlockWithItemBlock {
	
	protected Icon adenineOuter;
	protected Icon adenineInner;
	protected Icon guanineOuter;
	protected Icon guanineInner;
	protected Icon thymineOuter;
	protected Icon thymineInner;
	protected Icon cytosineOuter;
	protected Icon cytosineInner;
	
	public BlockNucleobaseLog(int id, String unlocalizedName) {
		super(id, Material.wood, unlocalizedName);
		setBurnProperties(this.blockID, 7, 7);
		this.setHardness(2F);
		this.setStepSound(soundWoodFootstep);
	}
	
	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseLog.class;
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta & (2+1);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		int logType = meta & (2+1);
		int orientation;
		switch(ForgeDirection.getOrientation(side)) {
		case UP:
		case DOWN:
			orientation = (0+0);
			break;
		case WEST:
		case EAST:
			orientation = (0+4);
			break;
		case NORTH:
		case SOUTH:
			orientation = (8+0);
			break;
		default:
			orientation = -1;
			break;
		}
		if(orientation == -1) orientation = (0+0);
		return logType | orientation;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		Nucleobases logType = Nucleobases.values()[meta & (2+1)];
		ForgeDirection orientation;
		switch(meta & (8+4)) {
		case 0: orientation = ForgeDirection.UP; break;
		case 4: orientation = ForgeDirection.WEST; break;
		case 8: orientation = ForgeDirection.NORTH; break;
		default: orientation = ForgeDirection.UNKNOWN; break;
		}
		ForgeDirection sideDirection = ForgeDirection.getOrientation(side);
		switch(logType) {
		case ADENINE:
			if(orientation == sideDirection || orientation == sideDirection.getOpposite()) return this.adenineInner;
			else return this.adenineOuter;
		case GUANINE:
			if(orientation == sideDirection || orientation == sideDirection.getOpposite()) return this.guanineInner;
			else return this.guanineOuter;
		case THYMINE:
			if(orientation == sideDirection || orientation == sideDirection.getOpposite()) return this.thymineInner;
			else return this.thymineOuter;
		case CYTOSINE:
			if(orientation == sideDirection || orientation == sideDirection.getOpposite()) return this.cytosineInner;
			else return this.cytosineOuter;
		default: return null;
		}
	}
	
	@Override
	public int getRenderType() { return 31; }
	
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
		this.adenineOuter = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_A_OUT);
		this.adenineInner = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_A_IN);
		this.guanineOuter = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_G_OUT);
		this.guanineInner = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_G_IN);
		this.thymineOuter = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_T_OUT);
		this.thymineInner = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_T_IN);
		this.cytosineOuter = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_C_OUT);
		this.cytosineInner = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_LOG_C_IN);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		boolean isLastLog = true;
		int logCheckRadius = 4;
		for(int xShift = -logCheckRadius; xShift <= logCheckRadius; xShift++) {
			for(int zShift = -logCheckRadius; zShift <= logCheckRadius; zShift++) {
				for(int yShift = -logCheckRadius; yShift <= logCheckRadius; yShift++) {
					if(xShift == 0 && zShift == 0 && yShift == 0) continue;
					if(world.getBlockId(x+xShift, y+yShift, z+zShift) == BioMorphoBlocks.nucleobaseLog.blockID) {
						if((world.getBlockMetadata(x+xShift, y+yShift, z+zShift) & (2|1)) == (meta & (2|1))) {
							isLastLog = false;
							break;
						}
					}
				}
			}
		}
		int radius = 3;
		for(int xShift = -radius; xShift <= radius; xShift++) {
			for(int zShift = -radius; zShift <= radius; zShift++) {
				for(int yShift = -radius; yShift <= radius; yShift++) {
					int blockId = world.getBlockId(x+xShift, y+yShift, z+zShift);
					int blockMeta = world.getBlockMetadata(x+xShift, y+yShift, z+zShift);
					if(blockId == BioMorphoBlocks.nucleobaseLeaves.blockID && ((blockMeta & (2|1)) == (meta & (2|1)))) {
						// If the block is decayable
						if((blockMeta & 4) != 0) {
							if(isLastLog) {
								world.destroyBlock(x+xShift, y+yShift, z+zShift, true);
							} else {
								world.setBlockMetadataWithNotify(x+xShift, y+yShift, z+zShift, blockMeta|8, 4);
							}
						}
					}
				}
			}
		}
	}
	
}
