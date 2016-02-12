package bioMorpho.block;

import net.minecraft.world.World;

/**
 * @author Brighton Ancelin
 *
 */
public interface IBlockWithTEOrientation {
	
	/**
	 * See ItemBlockWithTEOrientation.placeBlockAt() for more information
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param side
	 */
	public abstract void blockPlacedAfterTESetup(World world, int x, int y, int z, int side);
	
}
