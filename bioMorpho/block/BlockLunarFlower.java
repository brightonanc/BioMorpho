package bioMorpho.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import bioMorpho.block.BasicBlocks.BlockContainerBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.data.TextureData.ItemTextures;
import bioMorpho.tileEntity.TELunarFlower;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockLunarFlower extends BlockContainerBasic {
	
	public BlockLunarFlower(int id, String unlocalizedName) {
		super(id, Material.plants, unlocalizedName);
		this.setBlockBounds(0.375F, 0F, 0.375F, 0.625F, 0.03125F, 0.625F);
		this.setHardness(0F);
		this.setStepSound(soundGrassFootstep);
		this.setResistance(7F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TELunarFlower) return ((TELunarFlower)tileEntity).onRightClick();
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlockMaterial(x, y-1, z).isSolid();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId) {
		if(!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
		super.onNeighborBlockChange(world, x, y, z, neighborId);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TELunarFlower) {
			TELunarFlower te = (TELunarFlower)tileEntity;
			ItemStack lunarOrb = te.getLunarOrbCopy();
			if(lunarOrb != null) {
				world.spawnEntityInWorld(new EntityItem(world, x+0.5D, y+0.3D, z+0.5D, lunarOrb));
			}
		}
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {return new TELunarFlower();}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {return null;}
	
	public boolean renderAsNormalBlock() {return false;}
	public boolean isOpaqueCube() {return false;}
	public int getRenderType() {return -1;}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() {return ItemTextures.LUNAR_FLOWER;}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {this.blockIcon = iconRegister.registerIcon(BlockTextures.ALPHA);}
	
}
