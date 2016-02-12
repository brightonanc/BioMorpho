package bioMorpho.data;

import bioMorpho.etc.CreativeTabBioMorpho;


/**
 * @author Brighton Ancelin
 *
 */
public class ModData {
	
	// Basic mod information
	public static final String MOD_ID = "BioMorpho";
	public static final String MOD_NAME = "BioMorpho by 168168";
	public static final String MOD_VERSION = "1.0";
	public static final String CLIENT_PROXY = "bioMorpho.ClientProxy";
	public static final String COMMON_PROXY = "bioMorpho.CommonProxy";
	public static final boolean CLIENT_REQUIRED = true;
	public static final boolean SERVER_REQUIRED = true;
	
	// Mod-generated file name(s)
//	public static final String MODEL_MANAGER_NBT_FILE_NAME = "bioMorpho_ModelManager.dat";
	
	// Creative Tab(s)
	public static CreativeTabBioMorpho tabBioMorpho = new CreativeTabBioMorpho(MOD_ID);
	
	// Custom exceptions
	public static class BioMorphoNoCurrentObjectException extends Exception {

	}
	
	public static final String BIO_MORPHO_WORLD_DIR = "BioMorpho";
	
	public static final String INVENTORY_MANAGER_NBT_FILE_NAME = "bioMorpho_InventoryManager.dat";
	public static final String LIFEFORM_INDEX_MANAGER_NBT_FILE_NAME = "bioMorpho_lifeformIndexManager.dat";
	public static final String LIFEFORM_STORAGE_MANAGER_NBT_FILE_NAME = "bioMorpho_lifeformStorageManager.dat";
	
}
