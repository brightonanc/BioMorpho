package bioMorpho.data;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import bioMorpho.util.IdGenerator;

/**
 * @author Brighton Ancelin
 *
 */
public enum KeyBindingData {
	
	BIO_MORPHO_PLAYER(ConfigSettings.KEY_BIO_MORPHO_PLAYER, false, "bioMorphoPlayer", 0),
	;
	
	private final KeyBinding kb;
	private final boolean isRepeating;
	private final int id;
	
	private KeyBindingData(int keyCode, boolean isRepeating, String keyDescription, int id) {
		this.kb = new KeyBinding(keyDescription, keyCode);
		this.isRepeating = isRepeating;
		this.id = id;
	}
	
	public KeyBinding getKeyBinding() { return this.kb; }
	public boolean getIsRepeating() { return this.isRepeating; }
	public int getId() { return this.id; }
	
	public static KeyBinding[] getAllKeyBindings() {
		KeyBinding[] keyBindings = new KeyBinding[values().length];
		for(int i = 0; i < keyBindings.length; i++) {
			keyBindings[i] = values()[i].getKeyBinding();
		}
		return keyBindings;
	}
	
	public static boolean[] getAllIsRepeatings() {
		boolean[] isRepeatings = new boolean[values().length];
		for(int i = 0; i < isRepeatings.length; i++) {
			isRepeatings[i] = values()[i].getIsRepeating();
		}
		return isRepeatings;
	}
	
	public static KeyBindingData getEnumValueFromKeyBinding(KeyBinding kb) {
		for(KeyBindingData curKeyBinding : values()) {
			if(curKeyBinding.getKeyBinding() == kb) return curKeyBinding;
		}
		return null;
	}
	
	public static KeyBindingData getEnumValueFromId(int id) {
		for(KeyBindingData curKeyBinding : values()) {
			if(curKeyBinding.getId() == id) return curKeyBinding;
		}
		return null;
	}
	
}
