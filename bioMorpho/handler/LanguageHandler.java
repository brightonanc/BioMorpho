package bioMorpho.handler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.data.LanguageData;
import bioMorpho.data.ModData;
import bioMorpho.data.LanguageData.BlockNames;
import bioMorpho.data.LanguageData.ItemNames;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BioMorphoItems;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class LanguageHandler {
	
	public static void setNames() {
		LanguageRegistry.instance().addStringLocalization(ModData.tabBioMorpho.getTranslatedTabLabel(), LanguageData.TAB_BIO_MORPHO);
		
		LanguageRegistry.addName(BioMorphoBlocks.energeticSand, BlockNames.ENERGETIC_SAND);
		LanguageRegistry.addName(BioMorphoBlocks.gemOfGenesisHolder, BlockNames.GEM_OF_GENESIS_HOLDER);
		LanguageRegistry.addName(BioMorphoBlocks.lunarFlower, BlockNames.LUNAR_FLOWER);
		
		List subBlocks;
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseOre);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_ORE_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_ORE_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_ORE_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_ORE_CYTOSINE);
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseLog);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_LOG_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_LOG_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_LOG_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_LOG_CYTOSINE);
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseCrystal);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_CRYSTAL_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_CRYSTAL_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_CRYSTAL_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_CRYSTAL_CYTOSINE);
		LanguageRegistry.addName(subBlocks.get(4), BlockNames.NUCLEOBASE_CRYSTAL_ADENINE_UBER);
		LanguageRegistry.addName(subBlocks.get(5), BlockNames.NUCLEOBASE_CRYSTAL_GUANINE_UBER);
		LanguageRegistry.addName(subBlocks.get(6), BlockNames.NUCLEOBASE_CRYSTAL_THYMINE_UBER);
		LanguageRegistry.addName(subBlocks.get(7), BlockNames.NUCLEOBASE_CRYSTAL_CYTOSINE_UBER);
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseBlock);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_BLOCK_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_BLOCK_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_BLOCK_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_BLOCK_CYTOSINE);
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseSapling);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_SAPLING_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_SAPLING_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_SAPLING_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_SAPLING_CYTOSINE);
		subBlocks = getSubBlocks(BioMorphoBlocks.nucleobaseLeaves);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.ADENINE.ordinal()), BlockNames.NUCLEOBASE_LEAVES_ADENINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.GUANINE.ordinal()), BlockNames.NUCLEOBASE_LEAVES_GUANINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.THYMINE.ordinal()), BlockNames.NUCLEOBASE_LEAVES_THYMINE);
		LanguageRegistry.addName(subBlocks.get(Nucleobases.CYTOSINE.ordinal()), BlockNames.NUCLEOBASE_LEAVES_CYTOSINE);
		
//		LanguageRegistry.addName(BioMorphoItems.prototypeNucleus, ItemNames.PROTOTYPE_NUCLEUS);
		LanguageRegistry.addName(BioMorphoItems.gemOfGenesis, ItemNames.GEM_OF_GENESIS);
		LanguageRegistry.addName(BioMorphoItems.blueMeteorShard, ItemNames.BLUE_METEOR_SHARD);
		LanguageRegistry.addName(BioMorphoItems.lunarOrb, ItemNames.LUNAR_ORB);
		LanguageRegistry.addName(BioMorphoItems.energeticGlassOrb, ItemNames.ENERGETIC_GLASS_ORB);
		
		List subItems;
		subItems = getSubItems(BioMorphoItems.nucleobaseGoo);
		LanguageRegistry.addName(subItems.get(Nucleobases.ADENINE.ordinal()), ItemNames.NUCLEOBASE_GOO_ADENINE);
		LanguageRegistry.addName(subItems.get(Nucleobases.GUANINE.ordinal()), ItemNames.NUCLEOBASE_GOO_GUANINE);
		LanguageRegistry.addName(subItems.get(Nucleobases.THYMINE.ordinal()), ItemNames.NUCLEOBASE_GOO_THYMINE);
		LanguageRegistry.addName(subItems.get(Nucleobases.CYTOSINE.ordinal()), ItemNames.NUCLEOBASE_GOO_CYTOSINE);
	}
	
	public static List getSubBlocks(Block block) {
		List list = new ArrayList();
		block.getSubBlocks(block.blockID, block.getCreativeTabToDisplayOn(), list);
		return list;
	}
	
	public static List getSubItems(Item item) {
		List list = new ArrayList();
		item.getSubItems(item.itemID, item.getCreativeTab(), list);
		return list;
	}
}
