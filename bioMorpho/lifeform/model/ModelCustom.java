package bioMorpho.lifeform.model;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.animation.InheritedAnimRenderObj;
import bioMorpho.lifeform.model.animation.PlayerAnimationData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Brighton Ancelin
 *
 */
@SideOnly(Side.CLIENT)
public class ModelCustom {
	
	public boolean isDefaultBipedModel;
	public ArrayList<EntityComponent> components;
	public float offsetY;
	
	/** Only here for segment box stuffz */
	public int textureWidth = 64;
	/** Only here for segment box stuffz */
	public int textureHeight = 64;
	
	public ModelCustom(boolean isActuallyCustom) {
		this.isDefaultBipedModel = !isActuallyCustom;
		this.components = new ArrayList<EntityComponent>();
		this.offsetY = 0F;
	}
	
	public void addNewDefaultComponent() {
		this.components.add(new EntityComponent(this));
	}
	
	/**
	 * Used for rendering by the class ModelRenderingPort in Gui's
	 * @param scale
	 */
	public void renderWithoutShift() {
		GL11.glPushMatrix();
		for(int i = 0; i < this.components.size(); i++) {
			this.components.get(i).render(0.0625F, null, new InheritedAnimRenderObj[0]);
		}
		GL11.glPopMatrix();
	}
	
	public void renderWithShift() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, this.offsetY*0.0625F, 0F);
		for(int i = 0; i < this.components.size(); i++) {
			this.components.get(i).render(0.0625F, null, new InheritedAnimRenderObj[0]);
		}
		GL11.glPopMatrix();
	}
	
	/**
	 * 
	 * @param entity
	 * @param scale
	 */
	public void render(EntityPlayer entity, float partialTickTime) {
		if(this.isDefaultBipedModel) {
//			super.render(entity, appendageTime, appendageDistance, time, headRotationY, headRotationX, scale);
//			super.setRotationAngles(appendageTime, appendageDistance, time, headRotationY, headRotationX, scale, entity);
			return;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef(0F, this.offsetY*0.0625F, 0F);
		for(int i = 0; i < this.components.size(); i++) {
			PlayerAnimationData animData = new PlayerAnimationData(entity, this, partialTickTime);
			this.components.get(i).render(0.0625F, animData, new InheritedAnimRenderObj[0]);
		}
		GL11.glPopMatrix();
	}
	
	public NBTTagCompound writeModelCustomDataToNBT(NBTTagCompound nbtModelCustomCompound) {
		nbtModelCustomCompound.setTag(NBTData.NBT_COMPONENTS_LIST_KEY, this.writeComponentsDataToNBT(new NBTTagList()));
		nbtModelCustomCompound.setBoolean(NBTData.NBT_IS_DEF_BIPED_KEY, this.isDefaultBipedModel);
		nbtModelCustomCompound.setFloat("yOffset", this.offsetY);
		// TODO add other ModelCustom data to nbtModelCustomCompound DONT FORGET must add a recall to the read methods
		return nbtModelCustomCompound;
	}
	
	public NBTTagList writeComponentsDataToNBT(NBTTagList nbtComponentsList) {
		for(int i = 0; i < this.components.size(); i++) {
			nbtComponentsList.appendTag(this.components.get(i).writeEntityComponentDataToNBT(new NBTTagCompound()));
		}
		return nbtComponentsList;
	}
	
	public void readModelCustomDataFromNBT(NBTTagCompound nbtModelCustomCompound) {
		this.readComponentDataFromNBT(nbtModelCustomCompound.getTagList(NBTData.NBT_COMPONENTS_LIST_KEY));
		this.isDefaultBipedModel = nbtModelCustomCompound.getBoolean(NBTData.NBT_IS_DEF_BIPED_KEY);
		this.offsetY = nbtModelCustomCompound.getFloat("yOffset");
	}
	
	public void readComponentDataFromNBT(NBTTagList nbtComponentsList) {
		this.components.clear();
		for(int i = 0; i < nbtComponentsList.tagCount(); i++) {
			this.createNewComponentInModelWithNBTData((NBTTagCompound)nbtComponentsList.tagAt(i));
		}
	}
	
	public void createNewComponentInModelWithNBTData(NBTTagCompound nbtEntityComponentCompound) {
		EntityComponent newComp = new EntityComponent(this);
		newComp.readEntityComponentDataFromNBT(nbtEntityComponentCompound);
		this.components.add(newComp);
	}
	
	private ModelCustom() {this(true);}
	public static ModelCustom loadFromNBT(NBTTagCompound nbt) {
		ModelCustom model = new ModelCustom();
		model.readModelCustomDataFromNBT(nbt);
		return model;
	}

}
