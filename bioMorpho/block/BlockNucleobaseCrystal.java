package bioMorpho.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import bioMorpho.block.BasicBlocks.BlockContainerBasic;
import bioMorpho.data.NBTData;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.ItemNucleobaseCrystal;
import bioMorpho.tileEntity.TENucleobaseCrystal;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseCrystal extends BlockContainerBasic implements IBlockWithItemBlock, IBlockWithNBTItem, IBlockWithTEOrientation {
	
	public BlockNucleobaseCrystal(int id, String unlocalizedName) {
		super(id, Material.glass, unlocalizedName);
		this.setHardness(0F);
		this.setStepSound(soundGlassFootstep);
		this.setResistance(2000F);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
		Nucleobases nucleobase = Nucleobases.ADENINE;
		int crystalCharge = 0;
		boolean uberness = false;
		if(itemStack.getTagCompound() != null) {
			nucleobase = Nucleobases.values()[itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM)];
			crystalCharge = itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE);
			uberness = itemStack.getTagCompound().getBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS);
		}
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TENucleobaseCrystal) {
			((TENucleobaseCrystal)tileEntity).setNucleobase(nucleobase);
			((TENucleobaseCrystal)tileEntity).setCharge(crystalCharge);
			((TENucleobaseCrystal)tileEntity).setUberness(uberness);
		}
	}
	
	public void blockPlacedAfterTESetup(World world, int x, int y, int z, int side) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(!(tileEntity != null && tileEntity instanceof TENucleobaseCrystal)) return;
		((TENucleobaseCrystal)tileEntity).setOrientation(ForgeDirection.getOrientation(side));
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity tileEntity = blockAccess.getBlockTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TENucleobaseCrystal) {
			switch(((TENucleobaseCrystal)tileEntity).getOrientation()) {
			case UP: this.setBlockBounds((3F/16), 0F, (3F/16), (13F/16), 1F, (13F/16)); break;
			case DOWN: this.setBlockBounds((3F/16), 0F, (3F/16), (13F/16), 1F, (13F/16)); break;
			case WEST: this.setBlockBounds(0F, (3F/16), (3F/16), 1F, (13F/16), (13F/16)); break;
			case EAST: this.setBlockBounds(0F, (3F/16), (3F/16), 1F, (13F/16), (13F/16)); break;
			case NORTH: this.setBlockBounds((3F/16), (3F/16), 0F, (13F/16), (13F/16), 1F); break;
			case SOUTH: this.setBlockBounds((3F/16), (3F/16), 0F, (13F/16), (13F/16), 1F); break;
			default: this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F); break;
			}
		}
	}
	
	@Override
	public ItemStack createItemStackWithWorld(World world, int x, int y, int z) {
		TENucleobaseCrystal tileEntity = (TENucleobaseCrystal)world.getBlockTileEntity(x, y, z);
		if(tileEntity != null) {
			ItemStack itemStack = new ItemStack(this, 1, 0);
			itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setInteger(NBTData.NUCLEOBASE_ENUM, tileEntity.getNucleobase().ordinal());
			itemStack.getTagCompound().setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, tileEntity.getCharge());
			itemStack.getTagCompound().setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, tileEntity.isUber());
			return itemStack;
		}
		return null;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		world.spawnEntityInWorld(new EntityItem(world, x+0.5D, y+0.5D, z+0.5D, this.createItemStackWithWorld(world, x, y, z)));
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		return drops;
	}
	
	@Override
	public int quantityDropped(Random random) { return 0; }
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		ItemStack itemStack = this.createItemStackWithWorld(world, x, y, z);
		if(itemStack != null) return itemStack;
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
		return ((TENucleobaseCrystal)world.getBlockTileEntity(x, y, z)).onRightClick(player);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		ItemStack adenine = new ItemStack(id, 1, 0);
		adenine.setTagCompound(new NBTTagCompound());
		adenine.getTagCompound().setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.ADENINE.ordinal());
		adenine.getTagCompound().setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
		adenine.getTagCompound().setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, false);
		ItemStack guanine = new ItemStack(id, 1, 0);
		guanine.setTagCompound(new NBTTagCompound());
		guanine.getTagCompound().setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.GUANINE.ordinal());
		guanine.getTagCompound().setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
		guanine.getTagCompound().setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, false);
		ItemStack thymine = new ItemStack(id, 1, 0);
		thymine.setTagCompound(new NBTTagCompound());
		thymine.getTagCompound().setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.THYMINE.ordinal());
		thymine.getTagCompound().setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
		thymine.getTagCompound().setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, false);
		ItemStack cytosine = new ItemStack(id, 1, 0);
		cytosine.setTagCompound(new NBTTagCompound());
		cytosine.getTagCompound().setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.CYTOSINE.ordinal());
		cytosine.getTagCompound().setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
		cytosine.getTagCompound().setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, false);
		
		ItemStack adenineUber = new ItemStack(id, 1, 0);
		adenineUber.setTagCompound(new NBTTagCompound() {{
			this.setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.ADENINE.ordinal());
			this.setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
			this.setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, true);
		}});
		ItemStack guanineUber = new ItemStack(id, 1, 0);
		guanineUber.setTagCompound(new NBTTagCompound() {{
			this.setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.GUANINE.ordinal());
			this.setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
			this.setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, true);
		}});
		ItemStack thymineUber = new ItemStack(id, 1, 0);
		thymineUber.setTagCompound(new NBTTagCompound() {{
			this.setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.THYMINE.ordinal());
			this.setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
			this.setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, true);
		}});
		ItemStack cytosineUber = new ItemStack(id, 1, 0);
		cytosineUber.setTagCompound(new NBTTagCompound() {{
			this.setInteger(NBTData.NUCLEOBASE_ENUM, Nucleobases.CYTOSINE.ordinal());
			this.setInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE, 0);
			this.setBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS, true);
		}});
		
		list.add(adenine);
		list.add(guanine);
		list.add(thymine);
		list.add(cytosine);
		list.add(adenineUber);
		list.add(guanineUber);
		list.add(thymineUber);
		list.add(cytosineUber);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TENucleobaseCrystal(Nucleobases.ADENINE, 0, false);
	}
	
	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseCrystal.class;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(BlockTextures.ALPHA);
	}

}
