package bioMorpho.handler;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import bioMorpho.data.ModData;
import bioMorpho.data.NBTData;
import bioMorpho.entity.EntityBlueMeteor;
import bioMorpho.world.BioMorphoWorldData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author Brighton Ancelin
 *
 */
public class MeteorSpawningHandler implements ITickHandler {
	
	public static final Random rand = new Random();
	public static final int minWaitTime = 24000 * 3; // A day is 24000 long
	public static final int maxWaitTime = 24000 * 7;
	public static final float blueMeteorSpawnChance = 1F/1;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		World world = (World)tickData[0];
		BioMorphoWorldData storedData = (BioMorphoWorldData)world.perWorldStorage.loadData(BioMorphoWorldData.class, NBTData.METEOR_SPAWNING_DATA);
		if(world.provider.dimensionId != 0) return;
		if(world.isRemote) return;
		if(storedData == null) {
			world.perWorldStorage.setData(NBTData.METEOR_SPAWNING_DATA, new BioMorphoWorldData());
			storedData = (BioMorphoWorldData)world.perWorldStorage.loadData(BioMorphoWorldData.class, NBTData.METEOR_SPAWNING_DATA);
		}
		if(!storedData.nbt.hasKey(NBTData.METEOR_SPAWNING_DELAY)) {
			long curTotalWorldTime = world.getWorldInfo().getWorldTotalTime();
			storedData.nbt.setLong(NBTData.METEOR_SPAWNING_DELAY, getNewTimeDelay(curTotalWorldTime));
		}
		long requiredWorldTotalTime = storedData.nbt.getLong(NBTData.METEOR_SPAWNING_DELAY);
		if(world.getWorldInfo().getWorldTotalTime() > requiredWorldTotalTime) {
			if(rand.nextFloat() < blueMeteorSpawnChance) {
				spawnBlueMeteorInWorld(world);
				storedData.nbt.setLong(NBTData.METEOR_SPAWNING_DELAY, getNewTimeDelay(world.getWorldInfo().getWorldTotalTime()));
			}
		}
	}
	
	public static long getNewTimeDelay(long curTotalWorldTime) {
		long delay = rand.nextInt(maxWaitTime - minWaitTime) + minWaitTime;
		return curTotalWorldTime + delay;
	}
	
	public static void spawnBlueMeteorInWorld(World world) {
		EntityBlueMeteor blueMeteor = new EntityBlueMeteor(world);
		List<EntityPlayer> players = (List<EntityPlayer>)world.playerEntities;
		if(players.size() == 0) return;
		EntityPlayer selectedPlayer = players.get(rand.nextInt(players.size()));
		double xShift = rand.nextInt((48*2) + 1) - 48;
		double zShift = rand.nextInt((48*2) + 1) - 48;
		double x = selectedPlayer.posX + xShift + (xShift < 0 ? -21 : 21);
		double z = selectedPlayer.posZ + zShift + (zShift < 0 ? -21 : 21);
		blueMeteor.setPosition(x, world.getActualHeight(), z);
		double motionX = (rand.nextInt(4) + 1) * (xShift < 0 ? -1 : 1);
		double motionZ = (rand.nextInt(4) + 1) * (zShift < 0 ? -1 : 1);
		double motionY = -rand.nextInt(4) - 1;
		blueMeteor.setVelocity(motionX, motionY, motionZ);
		world.spawnEntityInWorld(blueMeteor);
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public EnumSet<TickType> ticks() { return EnumSet.of(TickType.WORLD); }

	@Override
	public String getLabel() { return ModData.MOD_ID + ":" + this.getClass().getSimpleName(); }

}
