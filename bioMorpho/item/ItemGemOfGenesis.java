package bioMorpho.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.item.BasicItems.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemGemOfGenesis extends ItemBasic {
	
	public ItemGemOfGenesis(int id, String unlocalizedName) {
		super(id, unlocalizedName);
		this.setMaxStackSize(1);
	}
	
	/**
	 * The boolean at the end specifies if advanced tooltips are enabled (AKA, the thing that shows ids and metadatas when the F3+H key combo is used)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedTooltipsEnabled) {
		list.add("Stored Models: "+LifeformHelper.getLifeformCount(itemStack));
	}
	
}
