package bioMorpho.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import bioMorpho.data.ModData;

/**
 * @author Brighton Ancelin
 *
 */
public class BasicBlocks {
	
	public static abstract class BlockBasic extends Block {
		public BlockBasic(int id, Material material, String unlocalizedName) {
			super(id, material);
			this.setUnlocalizedName(unlocalizedName);
			this.setCreativeTab(ModData.tabBioMorpho);
		}
	}
	
	public static abstract class BlockContainerBasic extends BlockContainer {
		public BlockContainerBasic(int id, Material material, String unlocalizedName) {
			super(id, material);
			this.setUnlocalizedName(unlocalizedName);
			this.setCreativeTab(ModData.tabBioMorpho);
		}
	}
	
	public static abstract class BlockPlantBasic extends BlockFlower {
		public BlockPlantBasic(int id, Material material, String unlocalizedName) {
			super(id, material);
			this.setUnlocalizedName(unlocalizedName);
			this.setCreativeTab(ModData.tabBioMorpho);
		}
	}

}
