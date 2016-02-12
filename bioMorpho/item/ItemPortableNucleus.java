package bioMorpho.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import bioMorpho.data.ItemData;
import bioMorpho.data.TextureData.ItemTextures;
import bioMorpho.item.BasicItems.ItemBasic;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.manager.ModelManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemPortableNucleus extends ItemBasic {
	
	public ItemPortableNucleus(int unshiftedId) {
		super(unshiftedId, null);//ItemData.PROTOTYPE_NUCLEUS.getUnlocalizedName());
		this.setMaxStackSize(1);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(ItemTextures.NUCLEUS);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List dataList, boolean par4) {
		if(stack.hasTagCompound()) dataList.add(stack.getTagCompound().getBoolean("Has Set Model") ? "Contains Model" : "No Model Stored");
		else dataList.add("No Model Stored");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int metaITHINK) {
		if(itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("Has Set Model")) return 7777777;
		else return super.getColorFromItemStack(itemStack, metaITHINK);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
			if(stack.getTagCompound().getBoolean("Has Set Model")) {
				ModelCustom storedModel = new ModelCustom(true);
				storedModel.readModelCustomDataFromNBT((NBTTagCompound)stack.getTagCompound().getTag("fdfsf"));
				ArrayList<ModelCustom> tempArray = new ArrayList<ModelCustom>();
				tempArray.add(0, storedModel);
				ModelManager.getServerInstance().putKeyAndValueIntoPlayerModelMap(player.username, tempArray);
			} else {
				NBTTagCompound nbtModelCustomCompound = new NBTTagCompound();
				ModelManager.getServerInstance().getCurrentModelForPlayer(player.username).writeModelCustomDataToNBT(nbtModelCustomCompound);
				stack.getTagCompound().setTag("fdfsf", nbtModelCustomCompound);
				stack.getTagCompound().setBoolean("Has Set Model", true);
			}
		}
		return stack;
	}

}
