package bioMorpho.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
public class ItemBlueMeteorShard extends ItemBasic {
	
	public ItemBlueMeteorShard(int id, String unlocalizedName) {
		super(id, unlocalizedName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ItemTextures.BLUE_METEOR_SHARD);
	}
	
	
	
	
	
	
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, 
			int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			int blockId = world.getBlockId(x, y, z);
			ItemStack newStack = new ItemStack(blockId, 1, 0);
			world.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, newStack));
			itemStack.stackSize--;
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}
