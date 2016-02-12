package bioMorpho.handler;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import bioMorpho.data.KeyBindingData;
import bioMorpho.data.ModData;
import bioMorpho.gui.etc.GuiBioMorphoPlayer;
import bioMorpho.network.PacketTypeHandler;
import bioMorpho.network.packet.PacketKeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * @author Brighton Ancelin
 *
 */
public class KeyBindingHandler extends KeyHandler {

	public KeyBindingHandler() {
		super(KeyBindingData.getAllKeyBindings(), KeyBindingData.getAllIsRepeatings());
	}

	@Override
	public String getLabel() {
		return ModData.MOD_ID + ":" + this.getClass().getSimpleName();
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeating) {
		switch(KeyBindingData.getEnumValueFromKeyBinding(kb)) {
		case BIO_MORPHO_PLAYER:
			if(FMLClientHandler.instance().getClient().inGameHasFocus) {
				PacketDispatcher.sendPacketToServer(PacketTypeHandler.convertToMinecraftPacketForm(new PacketKeyBinding(KeyBindingData.BIO_MORPHO_PLAYER.getId())));
			}
			break;
		default: break;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
