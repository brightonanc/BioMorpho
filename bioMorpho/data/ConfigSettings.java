package bioMorpho.data;

import static net.minecraftforge.common.Configuration.CATEGORY_GENERAL;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.common.Configuration;

/**
 * @author Brighton Ancelin
 *
 */
public class ConfigSettings {
	
	public static final String CATEGORY_KEY = "key";
	
	public static boolean METEOR_EXPLOSIONS;
	
	public static int KEY_BIO_MORPHO_PLAYER;
	public static int KEY_INGAME_SELECTION;
	
	public static void init(Configuration config) {
		config.addCustomCategoryComment(CATEGORY_KEY, "Refer to http://lwjgl.org/javadoc/constant-values.html#org.lwjgl.input.Keyboard.KEY_NUMPAD0 for key codes");
		
		boolean defBool;
		defBool = true;
		METEOR_EXPLOSIONS = config.get(CATEGORY_GENERAL, "meteorExplosions", defBool).getBoolean(defBool);
		
		KEY_BIO_MORPHO_PLAYER = config.get(CATEGORY_KEY, "bioMorphoInventory", Keyboard.KEY_R).getInt();
		KEY_INGAME_SELECTION = config.get(CATEGORY_KEY, "ingameSelection", Keyboard.KEY_LCONTROL).getInt();
	}
	
}
