package bioMorpho.block;

import net.minecraft.item.ItemBlock;

/**
 * Interface for intertwining blocks with their corresponding ItemBlocks
 * 
 * @author Brighton Ancelin
 *
 */
public interface IBlockWithItemBlock {
	
	public Class getItemBlockClass();
	
}
