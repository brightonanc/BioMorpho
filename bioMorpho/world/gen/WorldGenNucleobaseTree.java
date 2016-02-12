package bioMorpho.world.gen;

import java.util.ArrayList;
import java.util.Random;

import bioMorpho.block.BioMorphoBlocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * @author Brighton Ancelin
 *
 */
public class WorldGenNucleobaseTree extends WorldGenerator {
	
	public final int logId;
	public final int logMeta;
	public final int leavesId;
	public final int leavesMeta;
	
	private ArrayList<Integer> queueX = new ArrayList<Integer>();
	private ArrayList<Integer> queueY = new ArrayList<Integer>();
	private ArrayList<Integer> queueZ = new ArrayList<Integer>();
	private ArrayList<Integer> queueId = new ArrayList<Integer>();
	private ArrayList<Integer> queueMeta = new ArrayList<Integer>();
	private int successfulBlockCount = 0;
	private int unsuccessfulBlockCount = 0;
	
	public WorldGenNucleobaseTree(int logId, int logMeta, int leavesId, int leavesMeta) {
		super(true);
		this.logId = logId;
		this.logMeta = logMeta;
		this.leavesId = leavesId;
		this.leavesMeta = leavesMeta;
	}
	
	@Override
	protected void setBlockAndMetadata(World world, int x, int y, int z, int id, int meta) {
		if(world.isAirBlock(x, y, z)) {
			this.queueX.add(x);
			this.queueY.add(y);
			this.queueZ.add(z);
			this.queueId.add(id);
			this.queueMeta.add(meta);
			this.successfulBlockCount++;
		}
		else this.unsuccessfulBlockCount++;
	}
	
	/**
	 * Should only be used when you are intentionally replaceing a block that you know is NOT air! AKA, replacing the sapling with a log.
	 * This works better than simply using the world object to set the block and metadata value at the xyz because it still utilizes the queue system.
	 * The usage of the queue system means that if the generate() method decides it is not going to grow the tree due to too many unsuccessful block
	 * placements, the sapling doesn't just disappear, which would be the case if the World.setBlockAndMetadata() method was used instead
	 */
	protected void setBlockAndMetadataUnchecked(World world, int x, int y, int z, int id, int meta) {
		this.queueX.add(x);
		this.queueY.add(y);
		this.queueZ.add(z);
		this.queueId.add(id);
		this.queueMeta.add(meta);
	}
	
	public void setBlocksFromQueues(World world) {
		for(int i = 0; i < this.queueX.size(); i++) {
			super.setBlockAndMetadata(world, queueX.get(i), queueY.get(i), queueZ.get(i), queueId.get(i), queueMeta.get(i));
		}
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		if(this.genTrunkAndLeaves(world, rand, x, y, z)) {
			if(this.successfulBlockCount > this.unsuccessfulBlockCount) {
				this.setBlocksFromQueues(world);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Grows an entire tree at the specified x y z in the given world
	 * @param world
	 * @param rand
	 * @param x
	 * @param y
	 * @param z
	 * @return Success of trunk growth
	 */
	public boolean genTrunkAndLeaves(World world, Random rand, int x, int y, int z) {
		int height = 7 + rand.nextInt(5);
		for(int i = 0; i < height; i++) {
			if(i == 0 && world.getBlockId(x, y+i, z) == BioMorphoBlocks.nucleobaseSapling.blockID) this.setBlockAndMetadataUnchecked(world, x, y+i, z, this.logId, this.logMeta);
			else this.setBlockAndMetadata(world, x, y+i, z, this.logId, this.logMeta);
			if(this.unsuccessfulBlockCount > 0) return false;
		}
		this.setBlockAndMetadata(world, x, y+height, z, this.leavesId, this.leavesMeta|4);
		if(7 <= height && height <= 9) {
			this.genLeafLayer(world, rand, x, y+height-1, z, 1);
			this.genLeafLayer(world, rand, x, y+height-2, z, 2);
			this.genLeafLayer(world, rand, x, y+height-3, z, 2);
		}
		if(10 <= height && height <= 11) {
			this.genLeafLayer(world, rand, x, y+height-1, z, 1);
			this.genLeafLayer(world, rand, x, y+height-2, z, 2);
			this.genLeafLayer(world, rand, x, y+height-3, z, 3);
			this.genLeafLayer(world, rand, x, y+height-4, z, 3);
		}
		return true;
	}
	
	public void genLeafLayer(World world, Random rand, int baseX, int y, int baseZ, int radius) {
		ArrayList<Integer> xLimits = new ArrayList<Integer>();
		ArrayList<Integer> zLimits = new ArrayList<Integer>();
		switch(radius) {
		case 1:
			break;
		case 2:
			xLimits.add(2);
			zLimits.add(2);
			break;
		case 3:
			xLimits.add(2);
			zLimits.add(3);
			xLimits.add(3);
			zLimits.add(2);
			xLimits.add(3);
			zLimits.add(3);
			break;
		}
		
		for(int xShift = -radius; xShift <= radius; xShift++) {
			for(int zShift = -radius; zShift <= radius; zShift++) {
				if(xShift != 0 || zShift != 0) {
					boolean isAllowed = true;
					for(int i = 0; i < xLimits.size(); i++) {
						if((xShift == xLimits.get(i) && zShift == zLimits.get(i)) || (xShift == -xLimits.get(i) && zShift == zLimits.get(i)) ||
								(xShift == xLimits.get(i) && zShift == -zLimits.get(i)) || (xShift == -xLimits.get(i) && zShift == -zLimits.get(i))) {
							isAllowed = false;
							break;
						}
					}
					if(isAllowed) this.setBlockAndMetadata(world, baseX+xShift, y, baseZ+zShift, this.leavesId, this.leavesMeta|4);
				}
			}
		}
	}
		
}
