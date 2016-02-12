package bioMorpho.handler;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import bioMorpho.data.BlockData.EnumBlockData;
import bioMorpho.data.BlockData.IBlockData;
import bioMorpho.data.ItemData.EnumItemData;
import bioMorpho.data.ItemData.IItemData;
import bioMorpho.data.ConfigSettings;
import bioMorpho.data.ModData;
import cpw.mods.fml.common.FMLLog;

/**
 * @author Brighton Ancelin
 *
 */
public class ConfigHandler {
	
	public static void init(File file) {
		Configuration config = new Configuration(file, true);
		
		try {
			config.load();
			config.addCustomCategoryComment(Configuration.CATEGORY_BLOCK, "Nucleobase refers to the 4-part system of Adenine, Guanine, Thymine, and Cytosine");
			config.addCustomCategoryComment(Configuration.CATEGORY_ITEM, "Nucleobase refers to the 4-part system of Adenine, Guanine, Thymine, and Cytosine");
			initBlocks(config);
			initItems(config);
			ConfigSettings.init(config);
		} catch(Exception e) {
			FMLLog.log(Level.SEVERE, e, ModData.MOD_ID + " has had an error when loading the configuration file");
		} finally {
			config.save();
		}
		
	}
	
	public static void initBlocks(Configuration config) {
		for(IBlockData block : EnumBlockData.values()) {
			block.setId(config.getBlock(block.getCfgKey(), block.getDefaultId(), block.getCfgComment()).getInt());
		}
	}
	
	public static void initItems(Configuration config) {
		for(IItemData item : EnumItemData.values()) {
			item.setId(config.getItem(item.getCfgKey(), item.getDefaultId(), item.getCfgComment()).getInt());
		}
	}
	
}
