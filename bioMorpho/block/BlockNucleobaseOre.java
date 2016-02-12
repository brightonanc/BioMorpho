package bioMorpho.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bioMorpho.block.BasicBlocks.BlockBasic;
import bioMorpho.data.TextureData.BlockTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BioMorphoItems;
import bioMorpho.item.ItemNucleobaseOre;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class BlockNucleobaseOre extends BlockBasic implements IBlockWithItemBlock {
	
	protected Icon adenineIcon;
	protected Icon guanineIcon;
	protected Icon thymineIcon;
	protected Icon cytosineIcon;
	
	public BlockNucleobaseOre(int id, String unlocalizedName) {
		super(id, Material.rock, unlocalizedName);
		this.setHardness(3F);
		this.setStepSound(soundStoneFootstep);
	}
	
	@Override
	public Class getItemBlockClass() {
		return ItemNucleobaseOre.class;
	}
	
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public int idDropped(int meta, Random rand, int fortune) {
		return BioMorphoItems.nucleobaseGoo.itemID;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		return rand.nextInt(fortune + 1) + 1;
	}
	
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, Nucleobases.ADENINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.GUANINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.THYMINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.CYTOSINE.ordinal()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.adenineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_ORE_A);
		this.guanineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_ORE_G);
		this.thymineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_ORE_T);
		this.cytosineIcon = iconRegister.registerIcon(BlockTextures.NUCLEOBASE_ORE_C);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if(meta >= Nucleobases.values().length) return null;
		switch(Nucleobases.values()[meta]) {
		case ADENINE: return this.adenineIcon;
		case GUANINE: return this.guanineIcon;
		case THYMINE: return this.thymineIcon;
		case CYTOSINE: return this.cytosineIcon;
		}
		return null;
	}

}
