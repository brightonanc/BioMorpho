package bioMorpho.block;

import static bioMorpho.data.BlockData.EnumBlockData.ENERGETIC_SAND;
import static bioMorpho.data.BlockData.EnumBlockData.GEM_OF_GENESIS_HOLDER;
import static bioMorpho.data.BlockData.EnumBlockData.LUNAR_FLOWER;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_BLOCK;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_CRYSTAL;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_LEAVES;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_LOG;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_ORE;
import static bioMorpho.data.BlockData.EnumBlockData.NUCLEOBASE_SAPLING;

import java.lang.reflect.Constructor;

import net.minecraft.block.Block;
import bioMorpho.data.BlockData.EnumBlockData;
import bioMorpho.data.BlockData.IBlockData;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class BioMorphoBlocks {
	
	public static Block nucleobaseOre;
	public static Block nucleobaseLog;
	public static Block nucleobaseCrystal;
	public static Block nucleobaseBlock;
	public static Block nucleobaseSapling;
	public static Block nucleobaseLeaves;
	public static Block energeticSand;
	public static Block gemOfGenesisHolder;
	public static Block lunarFlower;
	
	public static void init() {
		nucleobaseOre = load(BlockNucleobaseOre.class, NUCLEOBASE_ORE);
		nucleobaseLog = load(BlockNucleobaseLog.class, NUCLEOBASE_LOG);
		nucleobaseCrystal = load(BlockNucleobaseCrystal.class, NUCLEOBASE_CRYSTAL);
		nucleobaseBlock = load(BlockNucleobaseBlock.class, NUCLEOBASE_BLOCK);
		nucleobaseSapling = load(BlockNucleobaseSapling.class, NUCLEOBASE_SAPLING);
		nucleobaseLeaves = load(BlockNucleobaseLeaves.class, NUCLEOBASE_LEAVES);
		energeticSand = load(BlockEnergeticSand.class, ENERGETIC_SAND);
		gemOfGenesisHolder = load(BlockGemOfGenesisHolder.class, GEM_OF_GENESIS_HOLDER);
		lunarFlower = load(BlockLunarFlower.class, LUNAR_FLOWER);
	}
	
	public static Block load(Class<? extends Block> clazz, IBlockData data) {
		Block block = null;
		
		try {block = clazz.getConstructor(int.class, String.class).newInstance(data.getId(), data.getUnlocalizedName());}
		catch(Exception e) {e.printStackTrace();}
				
		if(block != null) {
			if(block instanceof IBlockWithItemBlock) {
				GameRegistry.registerBlock(block, ((IBlockWithItemBlock)block).getItemBlockClass(), data.getRegisterName());
			} else {
				GameRegistry.registerBlock(block, data.getRegisterName());
			}
		}
		
		return block;
	}
	
}
