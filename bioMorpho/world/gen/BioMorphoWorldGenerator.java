package bioMorpho.world.gen;

import java.util.Random;

import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.etc.Nucleobases;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * @author Brighton Ancelin
 *
 */
public class BioMorphoWorldGenerator implements IWorldGenerator {
	
	protected Random currentRand;
	protected int currentChunkX;
	protected int currentChunkZ;
	protected World currentWorld;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		this.currentRand = random;
		this.currentChunkX = chunkX;
		this.currentChunkZ = chunkZ;
		this.currentWorld = world;
		
		switch(world.provider.dimensionId) {
		case -1:
			break;
		case 1:
			break;
		case 0:
			
			this.addOreGeneration(BioMorphoBlocks.nucleobaseOre.blockID, Nucleobases.ADENINE.ordinal(), 1, 8, 3, 7, 0, 256);
			this.addOreGeneration(BioMorphoBlocks.nucleobaseOre.blockID, Nucleobases.GUANINE.ordinal(), 1, 8, 3, 7, 0, 256);
			this.addOreGeneration(BioMorphoBlocks.nucleobaseOre.blockID, Nucleobases.THYMINE.ordinal(), 1, 8, 3, 7, 0, 256);
			this.addOreGeneration(BioMorphoBlocks.nucleobaseOre.blockID, Nucleobases.CYTOSINE.ordinal(), 1, 8, 3, 7, 0, 256);
			
			this.addNucleobaseTreeGeneration(BioMorphoBlocks.nucleobaseLog.blockID, Nucleobases.ADENINE.ordinal(),
					BioMorphoBlocks.nucleobaseLeaves.blockID, Nucleobases.ADENINE.ordinal(), 0.073F);
			this.addNucleobaseTreeGeneration(BioMorphoBlocks.nucleobaseLog.blockID, Nucleobases.GUANINE.ordinal(),
					BioMorphoBlocks.nucleobaseLeaves.blockID, Nucleobases.GUANINE.ordinal(), 0.073F);
			this.addNucleobaseTreeGeneration(BioMorphoBlocks.nucleobaseLog.blockID, Nucleobases.THYMINE.ordinal(),
					BioMorphoBlocks.nucleobaseLeaves.blockID, Nucleobases.THYMINE.ordinal(), 0.073F);
			this.addNucleobaseTreeGeneration(BioMorphoBlocks.nucleobaseLog.blockID, Nucleobases.CYTOSINE.ordinal(),
					BioMorphoBlocks.nucleobaseLeaves.blockID, Nucleobases.CYTOSINE.ordinal(), 0.073F);
			
			break;
			
		default: break;
		}
	}
	
	public static int findLowestLevelForBlock(World world, int x, int z, int blockId, int blockMeta) {
		for(int y = 0; y < world.getActualHeight(); y++) {
			if(world.getBlockId(x, y, z) == blockId && world.getBlockMetadata(x, y, z) == blockMeta) {
				return y;
			}
		}
		return -1;
	}
	
	public void addOreGeneration(int id, int meta, int minOreVein, int maxOreVein, int minFreq, int maxFreq, int minY, int maxY) {
		this.addOreGeneration(id, meta, Block.stone.blockID, minOreVein, maxOreVein, minFreq, maxFreq, minY, maxY);
	}
	public void addOreGeneration(int id, int meta, int targetId, int minOreVein, int maxOreVein, int minFreq, int maxFreq, int minY, int maxY) {
		for(int i = 0; i < this.currentRand.nextInt(maxFreq-minFreq)+minFreq; i++) {
			int x = (this.currentChunkX * 16) + this.currentRand.nextInt(16);
			int z = (this.currentChunkZ * 16) + this.currentRand.nextInt(16);
			int y = this.currentRand.nextInt(maxY-minY) + minY;
			int oreVeinSize = this.currentRand.nextInt(maxOreVein-minOreVein) + minOreVein;
			
			(new WorldGenMinable(id, meta, oreVeinSize, targetId)).generate(this.currentWorld, this.currentRand, x, y, z);
		}
	}
	
	public void addNucleobaseTreeGeneration(int logId, int logMeta, int leavesId, int leavesMeta, float baseChance) {
		if(this.currentRand.nextFloat() < baseChance) {
			int x = (this.currentChunkX * 16) + this.currentRand.nextInt(16);
			int z = (this.currentChunkZ * 16) + this.currentRand.nextInt(16);
			int y = findLowestLevelForBlock(this.currentWorld, x, z, Block.grass.blockID, 0);
			if(y == -1) return;
			else y++;
			
			float rainfallChance = (float)Math.pow(1 - this.currentWorld.getBiomeGenForCoords(x, z).getFloatRainfall(), 3);
			if(this.currentRand.nextFloat() < rainfallChance) return;
			
			(new WorldGenNucleobaseTree(logId, logMeta, leavesId, leavesMeta)).generate(this.currentWorld, this.currentRand, x, y, z);
		}
	}
	
}
