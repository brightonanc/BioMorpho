package bioMorpho.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bioMorpho.data.TextureData.ItemTextures;
import bioMorpho.item.BasicItems.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemLunarOrb extends ItemBasic {
	
	public ItemLunarOrb(int id, String unlocalizedName) {
		super(id, unlocalizedName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {this.itemIcon = iconRegister.registerIcon(ItemTextures.LUNAR_ORB);}
	
	
	
	
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			int val = itemRand.nextInt(64);
			if(0 <= val && val < 32) {
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Item.ingotIron, 7, 0)));
			} else if(32 <= val && val < 48) {
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Item.diamond, 4, 0)));
			} else if(48 <= val && val < 61) {
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Block.commandBlock, 2, 0)));
			} else if(61 <= val && val < 62) {
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Item.skull, 1, 1)));
			} else if(62 <= val && val < 63) {
				world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Item.netherStar, 1, 0)));
			}
			itemStack.stackSize--;
		}
		return itemStack;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
