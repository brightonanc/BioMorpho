package bioMorpho.nbt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import bioMorpho.data.ModData;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.SaveHandler;

/**
 * @author Brighton Ancelin
 *
 */
public class NBTDataHandler {
	
	public static byte failedReads = 0;
	
	/*
	 * Currently always saves to server files
	 */
	public static void writeNBTDataToFileInWorldSave(NBTTagCompound nbtDataCompound, String fileName, World world) {
		if(world instanceof WorldServer) {
			// The only reason I can typecast world.getSaveHandler() to SaveHandler is because the worls IS AN INSTANCE OF WORLDSERVER!!!!!
			// DO NOT TYPECAST IT TO SAVEHANDLER UNLESS IT IS AN INSTANCE OF WORLDSERVER!!!
			SaveHandler saveHandler = (SaveHandler)world.getSaveHandler();
			File saveDir = saveHandler.getWorldDirectory();
			try {
				File baseDir = new File(saveDir, ModData.BIO_MORPHO_WORLD_DIR);
				if(!baseDir.exists()) baseDir.mkdir();
				// Create the new File to hold the parameter NBT data
				// NOTE: this act does not really "create" the file in the computer. That requires the .createNewFile() method to be invoked
				File file = new File(saveDir + File.separator + ModData.BIO_MORPHO_WORLD_DIR, fileName);
				if(file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				// The below method writes our NBT data to our file
				CompressedStreamTools.writeCompressed(nbtDataCompound, fileOutputStream);
				fileOutputStream.close();
			}
			catch(IOException e) {
				System.out.println("Error when writing to NBT - in NBTDataHandler:writeNBTDataToFileInCurrentWorldSave - btw, Hi future me!");
				e.printStackTrace();
			}
		}
		else {
			// This will only run if the world given was not a server world
			System.out.println("World is not a server - no file will be written to.");
			return;
		}
	}
	
	/*
	 * Currently always reads from server files
	 */
	public static NBTTagCompound readNBTDataFromFileInWorldSave(String fileName, World world) {
		// NOTE: if checking for instanceof becomes an issue, using the world.isRemote variable is an alternative
		if(world instanceof WorldServer) {
			// The only reason I can typecast world.getSaveHandler() to SaveHandler is because the worls IS AN INSTANCE OF WORLDSERVER!!!!!
			// DO NOT TYPECAST IT TO SAVEHANDLER UNLESS IT IS AN INSTANCE OF WORLDSERVER!!!
			SaveHandler saveHandler = (SaveHandler)world.getSaveHandler();
			File saveDir = saveHandler.getWorldDirectory();
			try {
				File file = new File(saveDir + File.separator + ModData.BIO_MORPHO_WORLD_DIR, fileName);
				if(!file.exists()) {
					System.out.println("No file with the fileName given - Calling writeNBTDataToFileInCurrentWorldSave to create one now :)");
					failedReads++;
					if(failedReads > 1) {
						failedReads = 0;
						System.out.println("Error in the NBT process: NBTDataHandler:readNBTDataFromFileInCurrentWorldSave");
						System.out.println("Failed to read NBTData more times than I should have - Aborting and returning null");
						System.out.println("You were probably creating a new world, weren't you? :)");
						return null;
					}
					writeNBTDataToFileInWorldSave(new NBTTagCompound(), fileName, world);
					return readNBTDataFromFileInWorldSave(fileName, world);
				}
				FileInputStream fileInputStream = new FileInputStream(file);
				NBTTagCompound nbtDataCompound = CompressedStreamTools.readCompressed(fileInputStream);
				fileInputStream.close();
				failedReads = 0;
				return nbtDataCompound;
			}
			catch(IOException e) {
				System.out.println("Error in NBTDataHandler:readNBTDataFromFileInCurrentWorldSave - btw Hello future me!");
				e.printStackTrace();
				return null;
			}
		}
		else {
			// This will only run if the world given was not a server world
			System.out.println("World is not a server - no file will be read.");
			return null;
		}
	}
}
