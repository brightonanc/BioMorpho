package bioMorpho.lifeform;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.attribute.AtbSpeed;
import bioMorpho.lifeform.attribute.Attribute;
import bioMorpho.lifeform.model.ModelCustom;

/**
 * @author Brighton Ancelin
 *
 */
public class Lifeform {
	
	public String name = "";
	
	private ModelCustom model = new ModelCustom(false);
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	
	public ModelCustom getModel() {return this.model;}
	public ArrayList<Attribute> getAttributes() {return this.attributes;}
	
	public Lifeform copy() {
		return loadFromNBT(this.writeToNBT(new NBTTagCompound()));
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setCompoundTag(NBTData.LIFEFORM_MODEL, this.model.writeModelCustomDataToNBT(new NBTTagCompound()));
		nbt.setString("name", this.name);
		//TODO: attributes/abilities, etc.
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.model = ModelCustom.loadFromNBT(nbt.getCompoundTag(NBTData.LIFEFORM_MODEL));
		this.name = nbt.getString("name");
	}
	
	public static Lifeform loadFromNBT(NBTTagCompound nbt) {
		Lifeform lifeform = new Lifeform();
		lifeform.readFromNBT(nbt);
		return lifeform;
	}
	
	@Deprecated
	public void setModel(ModelCustom model) {
		this.model = model;
	}
	
}
