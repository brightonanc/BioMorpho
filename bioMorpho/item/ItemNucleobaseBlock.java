package bioMorpho.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import bioMorpho.etc.Nucleobases;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemNucleobaseBlock extends ItemBlockWithMetadata {

	public ItemNucleobaseBlock(int id, Block block) {
		super(id, block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		String endName = "";
		if(itemStack.getItemDamage() == Nucleobases.ADENINE.ordinal()) endName = "adenine";
		else if(itemStack.getItemDamage() == Nucleobases.GUANINE.ordinal()) endName = "guanine";
		else if(itemStack.getItemDamage() == Nucleobases.THYMINE.ordinal()) endName = "thymine";
		else if(itemStack.getItemDamage() == Nucleobases.CYTOSINE.ordinal()) endName = "cytosine";
		return this.getUnlocalizedName() + "." + endName;
	}

}
