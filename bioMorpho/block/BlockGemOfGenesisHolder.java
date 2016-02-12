package bioMorpho.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bioMorpho.block.BasicBlocks.BlockContainerBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.item.ItemGemOfGenesisHolder;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockGemOfGenesisHolder extends BlockContainerBasic implements IBlockWithItemBlock, IBlockWithTEOrientation {
	
	public BlockGemOfGenesisHolder(int id, String unlocalizedName) {
		super(id, Material.rock, unlocalizedName);
		this.setHardness(3F);
		this.setStepSound(soundStoneFootstep);
		this.setResistance(2000F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {return new TEGemOfGenesisHolder();}
	
	@Override
	public void blockPlacedAfterTESetup(World world, int x, int y, int z, int side) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TEGemOfGenesisHolder) {
			((TEGemOfGenesisHolder)tileEntity).setOrientation(ForgeDirection.getOrientation(side));
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity tileEntity = blockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TEGemOfGenesisHolder) {
			switch(((TEGemOfGenesisHolder)tileEntity).getOrientation()) {
			case UP: this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F); break;
			case DOWN: this.setBlockBounds(0F, 0.5F, 0F, 1F, 1F, 1F); break;
			case WEST: this.setBlockBounds(0.5F, 0F, 0F, 1F, 1F, 1F); break;
			case EAST: this.setBlockBounds(0F, 0F, 0F, 0.5F, 1F, 1F); break;
			case NORTH: this.setBlockBounds(0F, 0F, 0.5F, 1F, 1F, 1F); break;
			case SOUTH: this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 0.5F); break;
			default: this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F); break;
			}
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity tileEntity_1 = world.getBlockTileEntity(x, y, z);
		if(tileEntity_1 != null && tileEntity_1 instanceof TEGemOfGenesisHolder) {
			TEGemOfGenesisHolder tileEntity = (TEGemOfGenesisHolder)tileEntity_1;
			if(tileEntity.getGem() != null) {
				EntityItem entityItem = new EntityItem(world, x+0.5D, y+0.5D, z+0.5D, tileEntity.getGem());
				world.spawnEntityInWorld(entityItem);
			}
		}
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, 
			int side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		TEGemOfGenesisHolder tileEntity = (TEGemOfGenesisHolder)world.getBlockTileEntity(x, y, z);
		if(tileEntity.onRightClick(player)) return true;
		return false;
	}
	
	@Override
	public Class getItemBlockClass() {return ItemGemOfGenesisHolder.class;}
	
	public boolean renderAsNormalBlock() {return false;}
	public boolean isOpaqueCube() {return false;}
	public int getRenderType() {return -1;}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {this.blockIcon = iconRegister.registerIcon(BlockTextures.FX_GEM_OF_GENESIS_HOLDER);}
	
}
