package bioMorpho.handler;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import bioMorpho.data.ConfigSettings;
import bioMorpho.data.InventoryData;
import bioMorpho.data.ModData;
import bioMorpho.manager.InventoryManager;
import bioMorpho.manager.LifeformIndexManager;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author Brighton Ancelin
 *
 */
public class IngameSelectionHandler implements ITickHandler {
	
	private static int inventoryItemIndex;
	public static boolean isTriggered = false;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(this.canContinue()) {
			if(!isTriggered) Mouse.getDWheel();
			inventoryItemIndex = FMLClientHandler.instance().getClient().thePlayer.inventory.currentItem;
			isTriggered = true;
		} else {
			isTriggered = false;
		}
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(isTriggered) {
			int wheel = -Mouse.getDWheel();
			if(wheel != 0) {
				int index = LifeformIndexManager.getClientInstance().getIndex();
				index += wheel < 0 ? -1 : 1;
				while(index < 0) index += 7;
				while(index >= 7) index -= 7;
				LifeformIndexManager.getClientInstance().setClientPlayerIndex(index);
				
				FMLClientHandler.instance().getClient().thePlayer.inventory.currentItem = inventoryItemIndex;
			}
		}
		
/*		if(FMLClientHandler.instance().getClient().thePlayer != null && InventoryManager.getClientInstance().getCustomInventoryForClientPlayer() != null) {
			ItemStack gemOfGenesis = InventoryManager.getClientInstance().getCustomInventoryForClientPlayer().getStackInSlot(InventoryData.BIO_MORPHO_PLAYER_GEM_INDEX);
			if(Keyboard.isKeyDown(ConfigSettings.KEY_INGAME_SELECTION) && gemOfGenesis != null && FMLClientHandler.instance().getClient().inGameHasFocus) {
				int wheel = -Mouse.getDWheel();
				if(wheel != 0) {
					int index = LifeformIndexManager.getClientInstance().getIndex();
					index += wheel < 0 ? -1 : 1;
					while(index < 0) index += 7;
					while(index >= 7) index -= 7;
					LifeformIndexManager.getClientInstance().setClientPlayerIndex(index);
					
					FMLClientHandler.instance().getClient().thePlayer.inventory.currentItem = inventoryItemIndex;
				}
			}
		}
*/	}
	
	@Override
	public EnumSet<TickType> ticks() {return EnumSet.of(TickType.CLIENT);}
	
	@Override
	public String getLabel() {return ModData.MOD_ID + ": " + this.getClass().getSimpleName();}
	
	private boolean canContinue() {
		return FMLClientHandler.instance().getClient().inGameHasFocus && InventoryManager.getClientInstance().getClientGemOfGenesis() != null && Keyboard.isKeyDown(ConfigSettings.KEY_INGAME_SELECTION);
	}
	
}
