package bioMorpho.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import bioMorpho.block.BlockNucleobaseCrystal;
import bioMorpho.data.NBTData;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BasicItems.ItemBlockWithTEOrientation;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemNucleobaseCrystal extends ItemBlockWithTEOrientation {

	public ItemNucleobaseCrystal(int id) {
		super(id);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List dataList, boolean par4) {
		if(itemStack.hasTagCompound()) dataList.add(itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_CRYSTAL_CHARGE)+" Moles");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		String endName = "";
		if(itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM) == Nucleobases.ADENINE.ordinal()) endName = "adenine";
		else if(itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM) == Nucleobases.GUANINE.ordinal()) endName = "guanine";
		else if(itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM) == Nucleobases.THYMINE.ordinal()) endName = "thymine";
		else if(itemStack.getTagCompound().getInteger(NBTData.NUCLEOBASE_ENUM) == Nucleobases.CYTOSINE.ordinal()) endName = "cytosine";
		String uberness = itemStack.getTagCompound().getBoolean(NBTData.NUCLEOBASE_CRYSTAL_UBERNESS) ? "uber" : "default";
		return this.getUnlocalizedName() + "." + endName + "_" + uberness;
	}

}
