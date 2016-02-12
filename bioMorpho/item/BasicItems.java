package bioMorpho.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bioMorpho.block.BlockNucleobaseCrystal;
import bioMorpho.block.IBlockWithTEOrientation;
import bioMorpho.data.ModData;

/**
 * @author Brighton Ancelin
 *
 */
public class BasicItems {
	
	public static class ItemBasic extends Item {
		public ItemBasic(int id, String unlocalizedName) {
			super(id);
			this.setUnlocalizedName(unlocalizedName);
			this.setCreativeTab(ModData.tabBioMorpho);
		}
	}
	public static class ItemBlockWithTEOrientation extends ItemBlock {
		public ItemBlockWithTEOrientation(int id) {
			super(id);
		}
		@Override
		public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, 
				float hitX, float hitY, float hitZ, int meta) {
			boolean ret = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta);
			Block block = Block.blocksList[this.getBlockID()];
			if(block != null && block instanceof IBlockWithTEOrientation && world.getBlockId(x, y, z) == this.getBlockID() && ret) {
				((IBlockWithTEOrientation)block).blockPlacedAfterTESetup(world, x, y, z, side);
			}
			return ret;
		}
	}
}
