package bioMorpho.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import bioMorpho.data.TextureData.ItemTextures;
import bioMorpho.etc.Nucleobases;
import bioMorpho.item.BasicItems.ItemBasic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
public class ItemNucleobaseGoo extends ItemBasic {
	
	protected Icon adenine;
	protected Icon guanine;
	protected Icon thymine;
	protected Icon cytosine;
	
	public ItemNucleobaseGoo(int id, String unlocalizedName) {
		super(id, unlocalizedName);
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		String endName = "";
		if(itemStack.getItemDamage() == Nucleobases.ADENINE.ordinal()) endName = "adenine";
		else if(itemStack.getItemDamage() == Nucleobases.GUANINE.ordinal()) endName = "guanine";
		else if(itemStack.getItemDamage() == Nucleobases.THYMINE.ordinal()) endName = "thymine";
		else if(itemStack.getItemDamage() == Nucleobases.CYTOSINE.ordinal()) endName = "cytosine";
		return this.getUnlocalizedName() + "." + endName;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.adenine = iconRegister.registerIcon(ItemTextures.NUCLEOBASE_GOO_A);
		this.guanine = iconRegister.registerIcon(ItemTextures.NUCLEOBASE_GOO_G);
		this.thymine = iconRegister.registerIcon(ItemTextures.NUCLEOBASE_GOO_T);
		this.cytosine = iconRegister.registerIcon(ItemTextures.NUCLEOBASE_GOO_C);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(id, 1, Nucleobases.ADENINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.GUANINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.THYMINE.ordinal()));
		list.add(new ItemStack(id, 1, Nucleobases.CYTOSINE.ordinal()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		switch(Nucleobases.values()[meta]) {
		case ADENINE: return this.adenine;
		case GUANINE: return this.guanine;
		case THYMINE: return this.thymine;
		case CYTOSINE: return this.cytosine;
		default: return null;
		}
	}
	
}
