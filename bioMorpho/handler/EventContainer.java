package bioMorpho.handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import bioMorpho.data.ModData;
import bioMorpho.manager.InventoryManager;
import bioMorpho.manager.LifeformIndexManager;
import bioMorpho.manager.LifeformStorageManager;
import bioMorpho.manager.ModelManager;
import bioMorpho.nbt.NBTDataHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class EventContainer {
	
	@ForgeSubscribe
	public void onWorldLoad(WorldEvent.Load event) {
		World world = event.world;
		if(world instanceof WorldServer) {
//			NBTTagCompound nbtModelManagerCompound = NBTDataHandler.readNBTDataFromFileInWorldSave(ModData.MODEL_MANAGER_NBT_FILE_NAME, world);
//			if(nbtModelManagerCompound != null) {
//				ModelManager.getServerInstance().readAllModelManagerDataFromNBT(nbtModelManagerCompound);
//			}
			
			NBTTagCompound nbtInventoryManagerCompound = NBTDataHandler.readNBTDataFromFileInWorldSave(ModData.INVENTORY_MANAGER_NBT_FILE_NAME, world);
			if(nbtInventoryManagerCompound != null) {
				InventoryManager.getServerInstance().readAllDataFromNBT(nbtInventoryManagerCompound);
			}
			
			NBTTagCompound nbtLifeformIndexManagerCompound = NBTDataHandler.readNBTDataFromFileInWorldSave(ModData.LIFEFORM_INDEX_MANAGER_NBT_FILE_NAME, world);
			if(nbtLifeformIndexManagerCompound != null) {
				LifeformIndexManager.getServerInstance().readDataFromNBT(nbtLifeformIndexManagerCompound);
			}
			
			NBTTagCompound nbtLFSMCompound = NBTDataHandler.readNBTDataFromFileInWorldSave(ModData.LIFEFORM_STORAGE_MANAGER_NBT_FILE_NAME, world);
			if(nbtLFSMCompound != null) {
				LifeformStorageManager.getServerInstance().readAllDataFromNBT(nbtLFSMCompound);
			}
		}
	}
	
	@ForgeSubscribe
	public void onWorldSave(WorldEvent.Save event) {
		World world = event.world;
		if(world instanceof WorldServer) {
//			NBTTagCompound nbtModelManagerCompound = ModelManager.getServerInstance().writeAllModelManagerDataToNBT(new NBTTagCompound());
//			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtModelManagerCompound, ModData.MODEL_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtInventoryManagerCompound = InventoryManager.getServerInstance().writeAllDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtInventoryManagerCompound, ModData.INVENTORY_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtLifeformIndexManagerCompound = LifeformIndexManager.getServerInstance().writeDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtLifeformIndexManagerCompound, ModData.LIFEFORM_INDEX_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtLFSMCompound = LifeformStorageManager.getServerInstance().writeAllDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtLFSMCompound, ModData.LIFEFORM_STORAGE_MANAGER_NBT_FILE_NAME, world);
		}
	}
	
	@ForgeSubscribe
	public void onWorldUnload(WorldEvent.Unload event) {
		World world = event.world;
		if(world instanceof WorldServer) {
//			NBTTagCompound nbtModelManagerCompound = ModelManager.getServerInstance().writeAllModelManagerDataToNBT(new NBTTagCompound());
//			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtModelManagerCompound, ModData.MODEL_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtInventoryManagerCompound = InventoryManager.getServerInstance().writeAllDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtInventoryManagerCompound, ModData.INVENTORY_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtLifeformIndexManagerCompound = LifeformIndexManager.getServerInstance().writeDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtLifeformIndexManagerCompound, ModData.LIFEFORM_INDEX_MANAGER_NBT_FILE_NAME, world);
			
			NBTTagCompound nbtLFSMCompound = LifeformStorageManager.getServerInstance().writeAllDataToNBT(new NBTTagCompound());
			NBTDataHandler.writeNBTDataToFileInWorldSave(nbtLFSMCompound, ModData.LIFEFORM_STORAGE_MANAGER_NBT_FILE_NAME, world);
		}
	}
}
