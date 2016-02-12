package bioMorpho.handler;

import bioMorpho.block.BioMorphoBlocks;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BioMorphoItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Brighton Ancelin
 *
 */
public class BioMorphoRecipes {
	
	public static void init() {
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.ADENINE.ordinal()),
				new Object[] {"AAA", "AAA", "AAA", 'A', new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 1, Nucleobases.ADENINE.ordinal())});
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.GUANINE.ordinal()),
				new Object[] {"GGG", "GGG", "GGG", 'G', new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 1, Nucleobases.GUANINE.ordinal())});
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.THYMINE.ordinal()),
				new Object[] {"TTT", "TTT", "TTT", 'T', new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 1, Nucleobases.THYMINE.ordinal())});
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.CYTOSINE.ordinal()),
				new Object[] {"CCC", "CCC", "CCC", 'C', new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 1, Nucleobases.CYTOSINE.ordinal())});
		
		GameRegistry.addShapelessRecipe(new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 9, Nucleobases.ADENINE.ordinal()), 
				new Object[] {new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.ADENINE.ordinal())});
		GameRegistry.addShapelessRecipe(new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 9, Nucleobases.GUANINE.ordinal()), 
				new Object[] {new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.GUANINE.ordinal())});
		GameRegistry.addShapelessRecipe(new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 9, Nucleobases.THYMINE.ordinal()), 
				new Object[] {new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.THYMINE.ordinal())});
		GameRegistry.addShapelessRecipe(new ItemStack(BioMorphoItems.nucleobaseGoo.itemID, 9, Nucleobases.CYTOSINE.ordinal()), 
				new Object[] {new ItemStack(BioMorphoBlocks.nucleobaseBlock.blockID, 1, Nucleobases.CYTOSINE.ordinal())});
		
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.energeticSand.blockID, 1, 0),
				new Object[] {"GGG", "GSG", "GGG", 'G', new ItemStack(Item.glowstone), 'S', new ItemStack(Block.sand)});
		
		GameRegistry.addRecipe(new ItemStack(BioMorphoBlocks.gemOfGenesisHolder.blockID, 1, 0),
				new Object[] {"LLL", "LOL", "LLL", 'L', new ItemStack(Item.dyePowder, 1, 4), 'O', new ItemStack(BioMorphoItems.lunarOrb)});
		
		GameRegistry.addRecipe(new ItemStack(BioMorphoItems.gemOfGenesis, 1, 0),
				new Object[] {"BBB", "BOB", "BBB", 'B', new ItemStack(BioMorphoItems.blueMeteorShard), 'O', new ItemStack(BioMorphoItems.lunarOrb)});
		
		GameRegistry.addSmelting(BioMorphoBlocks.energeticSand.blockID, new ItemStack(BioMorphoItems.energeticGlassOrb, 4, 0), 0F);
	}
	
}
