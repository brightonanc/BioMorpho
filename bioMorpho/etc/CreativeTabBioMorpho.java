package bioMorpho.etc;

import bioMorpho.data.ItemData;
import bioMorpho.item.BioMorphoItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author Brighton Ancelin
 *
 */
public class CreativeTabBioMorpho extends CreativeTabs {

	public CreativeTabBioMorpho(String label) {
		super(label);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex() {
		return BioMorphoItems.gemOfGenesis.itemID;
	}
	
}
