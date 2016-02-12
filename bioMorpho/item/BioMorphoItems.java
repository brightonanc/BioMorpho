package bioMorpho.item;

import static bioMorpho.data.ItemData.EnumItemData.BLUE_METEOR_SHARD;
import static bioMorpho.data.ItemData.EnumItemData.ENERGETIC_GLASS_ORB;
import static bioMorpho.data.ItemData.EnumItemData.GEM_OF_GENESIS;
import static bioMorpho.data.ItemData.EnumItemData.LUNAR_ORB;
import static bioMorpho.data.ItemData.EnumItemData.NUCLEOBASE_GOO;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import bioMorpho.block.IBlockWithItemBlock;
import bioMorpho.data.ItemData;
import bioMorpho.data.ItemData.EnumItemData;
import bioMorpho.data.ItemData.IItemData;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class BioMorphoItems {
	
	public static Item prototypeNucleus;
	public static Item nucleobaseGoo;
	public static Item gemOfGenesis;
	public static Item blueMeteorShard;
	public static Item lunarOrb;
	public static Item energeticGlassOrb;
	
	public static void init() {
//		prototypeNucleus = new ItemPortableNucleus(ItemData.PROTOTYPE_NUCLEUS.getId());
		nucleobaseGoo = load(ItemNucleobaseGoo.class, NUCLEOBASE_GOO);
		gemOfGenesis = load(ItemGemOfGenesis.class, GEM_OF_GENESIS);
		blueMeteorShard = load(ItemBlueMeteorShard.class, BLUE_METEOR_SHARD);
		lunarOrb = load(ItemLunarOrb.class, LUNAR_ORB);
		energeticGlassOrb = load(ItemGlassOrb.class, ENERGETIC_GLASS_ORB);
	}
	
	public static Item load(Class<? extends Item> clazz, IItemData data) {
		Item item = null;
		
		try {item = clazz.getConstructor(int.class, String.class).newInstance(data.getId(), data.getUnlocalizedName());}
		catch(Exception e) {e.printStackTrace();}
		
		if(item != null) {
			GameRegistry.registerItem(item, data.getRegisterName());
		}
		
		return item;
	}
	
}
