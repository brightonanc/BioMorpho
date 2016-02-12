package bioMorpho.lifeform.model;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import bioMorpho.data.ModelData;
import bioMorpho.data.NBTData;
import bioMorpho.lifeform.model.animation.AnimRenderObj;
import bioMorpho.lifeform.model.animation.BioMorphoPlayerEvents;
import bioMorpho.lifeform.model.animation.InheritedAnimRenderObj;
import bioMorpho.lifeform.model.animation.ModelAnimations;
import bioMorpho.lifeform.model.animation.PlayerAnimationData;
import bioMorpho.lifeform.model.animation.PreRenderInfo;
import bioMorpho.lifeform.model.animation.UniqueAnimData;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityComponent {
	
	public ModelCustom model;
	public EntitySegment segment;
	public int textureOffsetX = 0;
	public int textureOffsetY = 0;
	public int animationsCount = 10;
	public AnimRenderObj[] animRenderObjs;
	public PreRenderInfo preRenderInfo;
	public String name;
	//TODO abilities
	
	public EntityComponent(ModelCustom model) {
		this.model = model;
		this.segment = new EntitySegment(model, this.textureOffsetX, this.textureOffsetY);
		this.animRenderObjs = new AnimRenderObj[this.animationsCount];
		for(int i = 0; i < this.animRenderObjs.length; i++) {
			this.animRenderObjs[i] = new AnimRenderObj();
			this.animRenderObjs[i].setEvent(BioMorphoPlayerEvents.ALWAYS);
			this.animRenderObjs[i].setAnimationWithNewSettings(ModelAnimations.NONE);
		}
		this.preRenderInfo = new PreRenderInfo();
		this.name = ModelData.COMP_DEFAULT_NAME;
	}
	
	public void render(float scale, PlayerAnimationData animData, InheritedAnimRenderObj[] inheritedAnims) {
		if(animData == null) {
			this.segment.render(scale, animData, this.preRenderInfo, new InheritedAnimRenderObj[0]);
			this.preRenderInfo.reset();
			return;
		}
		for(InheritedAnimRenderObj curAnimObj : inheritedAnims) {
			// if statement should NOT be necessary because only valid Event AnimRenderObjs are
			// added to the inheritedAnims, but just in case, the if statement is there
			if(curAnimObj.getEvent().isValid(animData.getEntity())) {
				curAnimObj.incrementSubCompIndex();
				curAnimObj.getAnimation().addAnimationData(animData, curAnimObj.getUniqueData(), this, this.preRenderInfo, curAnimObj.getSubCompIndex(), curAnimObj.getInheritedData());
			}
		}
		ArrayList<InheritedAnimRenderObj> nextInheritedAnims = new ArrayList<InheritedAnimRenderObj>();
		for(int i = 0; i < this.animRenderObjs.length; i++) {
			if(this.animRenderObjs[i].getEvent().isValid(animData.getEntity())) {
				// Store data for animations FIRST because the next method might need them
				this.animRenderObjs[i].getUniqueData().getEventRecordData().attemptActivation(animData);
				if(this.animRenderObjs[i].getAnimation().isInherited()) {
					InheritedAnimRenderObj castAnimRenderObj = InheritedAnimRenderObj.createFromAnimRenderObj(this.animRenderObjs[i]);
					castAnimRenderObj.getAnimation().addAnimationData(animData, castAnimRenderObj.getUniqueData(), this, this.preRenderInfo,
							castAnimRenderObj.getSubCompIndex(), castAnimRenderObj.getInheritedData());
					nextInheritedAnims.add(castAnimRenderObj);
				} else {
					this.animRenderObjs[i].getAnimation().addAnimationData(animData, this.animRenderObjs[i].getUniqueData(), this, this.preRenderInfo);
				}
			}
			else {
				this.animRenderObjs[i].getUniqueData().getEventRecordData().attemptDeactivation(animData);
			}
		}
		InheritedAnimRenderObj[] sentInheritedAnims = new InheritedAnimRenderObj[inheritedAnims.length + nextInheritedAnims.size()];
		for(int i = 0; i < sentInheritedAnims.length; i++) {
			if(i < inheritedAnims.length) sentInheritedAnims[i] = inheritedAnims[i];
			else if(i < (inheritedAnims.length + nextInheritedAnims.size())) sentInheritedAnims[i] = nextInheritedAnims.get(i);
		}
		this.segment.render(scale, animData, this.preRenderInfo, sentInheritedAnims);
		this.preRenderInfo.reset();
	}
	
	public NBTTagCompound writeEntityComponentDataToNBT(NBTTagCompound nbtEntityComponentCompound) {
		nbtEntityComponentCompound.setCompoundTag(NBTData.NBT_SEGMENT_DATA_KEY, this.writeSegmentDataToNBT(new NBTTagCompound()));
		
		int[] animationEventsArray = new int[this.animRenderObjs.length];
		int[] animationsArray = new int[this.animRenderObjs.length];
		NBTTagList uniqueAnimDataList = new NBTTagList();
		for(int i = 0; i < this.animRenderObjs.length; i++) {
			animationEventsArray[i] = this.animRenderObjs[i].getEvent().ordinal();
			animationsArray[i] = this.animRenderObjs[i].getAnimation().ordinal();
			uniqueAnimDataList.appendTag(this.animRenderObjs[i].getUniqueData().writeToNBT(new NBTTagCompound()));
		}
		nbtEntityComponentCompound.setIntArray(NBTData.NBT_COMPONENT_ANIMATION_EVENTS_KEY, animationEventsArray);
		nbtEntityComponentCompound.setIntArray(NBTData.NBT_COMPONENT_ANIMATIONS_KEY, animationsArray);
		nbtEntityComponentCompound.setTag(NBTData.NBT_COMPONENT_UNIQUE_ANIM_DATA_KEY, uniqueAnimDataList);
		
		nbtEntityComponentCompound.setString(NBTData.NBT_COMPONENT_NAME_KEY, this.name);
		// TODO add more information to nbtEntityComponentCompound DONT FORGET must add a recall of info in read
		return nbtEntityComponentCompound;
	}
	
	public NBTTagCompound writeSegmentDataToNBT(NBTTagCompound nbtSegmentCompound) {
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_POINT_X_KEY, this.segment.mainRotationPointX);
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_POINT_Y_KEY, this.segment.mainRotationPointY);
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_POINT_Z_KEY, this.segment.mainRotationPointZ);
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_ANGLE_X_KEY, this.segment.mainRotationAngleX);
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_ANGLE_Y_KEY, this.segment.mainRotationAngleY);
		nbtSegmentCompound.setFloat(NBTData.NBT_SEGMENT_R_ANGLE_Z_KEY, this.segment.mainRotationAngleZ);
		
		nbtSegmentCompound.setTag(NBTData.NBT_SUB_COMP_LIST_KEY, this.writeSubCompDataToNBT(new NBTTagList()));
		nbtSegmentCompound.setTag(NBTData.NBT_BOX_LIST_KEY, this.writeBoxDataToNBT(new NBTTagList()));
		// TODO add any other data from the segment into the Parameter Compound
		// DONT FORGET: must add recall of the info in read
		return nbtSegmentCompound;
	}
	
	public NBTTagList writeSubCompDataToNBT(NBTTagList nbtSubCompList) {
		if(this.segment.getHasSubComps()) {
			for(int i = 0; i < this.segment.subComps.size(); i++) {
				NBTTagCompound nbtSubCompCompound = this.segment.subComps.get(i).writeEntityComponentDataToNBT(new NBTTagCompound());
				nbtSubCompList.appendTag(nbtSubCompCompound);
			}
		}
		return nbtSubCompList;
	}
	
	public NBTTagList writeBoxDataToNBT(NBTTagList nbtBoxList) {
		for(int i = 0; i < this.segment.cubeList.size(); i++) {
			NBTTagCompound nbtBoxCompound = new NBTTagCompound(); // Create the Compound for the current box
			String name = this.segment.getBoxAtIndex(i).getName();
			float x1 = this.segment.getBoxAtIndex(i).getPosX1();
			float y1 = this.segment.getBoxAtIndex(i).getPosY1();
			float z1 = this.segment.getBoxAtIndex(i).getPosZ1();
			int width = this.segment.getBoxAtIndex(i).getWidth();
			int height = this.segment.getBoxAtIndex(i).getHeight();
			int depth = this.segment.getBoxAtIndex(i).getDepth();
			float rotationAngleX = this.segment.getBoxAtIndex(i).getRotAngleX();
			float rotationAngleY = this.segment.getBoxAtIndex(i).getRotAngleY();
			float rotationAngleZ = this.segment.getBoxAtIndex(i).getRotAngleZ();
			float rotationPointX = this.segment.getBoxAtIndex(i).getRotPointX();
			float rotationPointY = this.segment.getBoxAtIndex(i).getRotPointY();
			float rotationPointZ = this.segment.getBoxAtIndex(i).getRotPointZ();
			nbtBoxCompound.setString(NBTData.NBT_BOX_NAME_KEY, name);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_POS_X_KEY, x1);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_POS_Y_KEY, y1);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_POS_Z_KEY, z1);
			nbtBoxCompound.setInteger(NBTData.NBT_BOX_SIZE_X_KEY, width);
			nbtBoxCompound.setInteger(NBTData.NBT_BOX_SIZE_Y_KEY, height);
			nbtBoxCompound.setInteger(NBTData.NBT_BOX_SIZE_Z_KEY, depth);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_ANGLE_X_KEY, rotationAngleX);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_ANGLE_Y_KEY, rotationAngleY);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_ANGLE_Z_KEY, rotationAngleZ);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_POINT_X_KEY, rotationPointX);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_POINT_Y_KEY, rotationPointY);
			nbtBoxCompound.setFloat(NBTData.NBT_BOX_R_POINT_Z_KEY, rotationPointZ);
			nbtBoxList.appendTag(nbtBoxCompound); // Input the information of the current box into the box List
		}
		return nbtBoxList;
	}
	
	public void readEntityComponentDataFromNBT(NBTTagCompound nbtEntityComponentCompound) {
		this.readSegmentDataFromNBT(nbtEntityComponentCompound.getCompoundTag(NBTData.NBT_SEGMENT_DATA_KEY));
		
		int[] animationEventsArray = nbtEntityComponentCompound.getIntArray(NBTData.NBT_COMPONENT_ANIMATION_EVENTS_KEY);
		int[] animationsArray = nbtEntityComponentCompound.getIntArray(NBTData.NBT_COMPONENT_ANIMATIONS_KEY);
		NBTTagList uniqueAnimDataList = nbtEntityComponentCompound.getTagList(NBTData.NBT_COMPONENT_UNIQUE_ANIM_DATA_KEY);
		for(int i = 0; i < this.animRenderObjs.length; i++) {
			this.animRenderObjs[i].setEvent(BioMorphoPlayerEvents.values()[animationEventsArray[i]]);
			this.animRenderObjs[i].setAnimationOnly(ModelAnimations.values()[animationsArray[i]]);
			this.animRenderObjs[i].setUniqueData(UniqueAnimData.readAndCreateFromNBT((NBTTagCompound)uniqueAnimDataList.tagAt(i)));
		}

		this.name = nbtEntityComponentCompound.getString(NBTData.NBT_COMPONENT_NAME_KEY);
	}
	
	public void readSegmentDataFromNBT(NBTTagCompound nbtSegmentCompound) {
		this.segment.mainRotationPointX = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_POINT_X_KEY);
		this.segment.mainRotationPointY = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_POINT_Y_KEY);
		this.segment.mainRotationPointZ = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_POINT_Z_KEY);
		this.segment.mainRotationAngleX = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_ANGLE_X_KEY);
		this.segment.mainRotationAngleY = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_ANGLE_Y_KEY);
		this.segment.mainRotationAngleZ = nbtSegmentCompound.getFloat(NBTData.NBT_SEGMENT_R_ANGLE_Z_KEY);
		
		this.readSubCompDataFromNBT(nbtSegmentCompound.getTagList(NBTData.NBT_SUB_COMP_LIST_KEY));
		this.readBoxDataFromNBT(nbtSegmentCompound.getTagList(NBTData.NBT_BOX_LIST_KEY));
	}
	
	public void readSubCompDataFromNBT(NBTTagList nbtSubCompList) {
		this.segment.clearSubComps(); // Clear the subComps because we are about to re-enter them all
		for(int i = 0; i < nbtSubCompList.tagCount(); i++) {
			NBTTagCompound nbtSubCompCompound = (NBTTagCompound)nbtSubCompList.tagAt(i);
			EntityComponent currentSubComp = new EntityComponent(this.model);
			currentSubComp.readEntityComponentDataFromNBT(nbtSubCompCompound);
			this.segment.addSubComp(currentSubComp);
		}
	}
	
	public void readBoxDataFromNBT(NBTTagList nbtBoxList) {
		this.segment.clearBoxes(); // Clears the list because we are about to re-enter all the stored NBT data
		for(int i = 0; i < nbtBoxList.tagCount(); i++) {
			NBTTagCompound nbtBoxCompound = (NBTTagCompound)nbtBoxList.tagAt(i);
			String name = nbtBoxCompound.getString(NBTData.NBT_BOX_NAME_KEY);
			float posX = nbtBoxCompound.getFloat(NBTData.NBT_BOX_POS_X_KEY);
			float posY = nbtBoxCompound.getFloat(NBTData.NBT_BOX_POS_Y_KEY);
			float posZ = nbtBoxCompound.getFloat(NBTData.NBT_BOX_POS_Z_KEY);
			int sizeX = nbtBoxCompound.getInteger(NBTData.NBT_BOX_SIZE_X_KEY);
			int sizeY = nbtBoxCompound.getInteger(NBTData.NBT_BOX_SIZE_Y_KEY);
			int sizeZ = nbtBoxCompound.getInteger(NBTData.NBT_BOX_SIZE_Z_KEY);
			float rotateAngleX = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_ANGLE_X_KEY);
			float rotateAngleY = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_ANGLE_Y_KEY);
			float rotateAngleZ = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_ANGLE_Z_KEY);
			float rotationPointX = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_POINT_X_KEY);
			float rotationPointY = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_POINT_Y_KEY);
			float rotationPointZ = nbtBoxCompound.getFloat(NBTData.NBT_BOX_R_POINT_Z_KEY);
			this.segment.addBox(name, posX, posY, posZ, sizeX, sizeY, sizeZ, 
					rotationPointX, rotationPointY, rotationPointZ, rotateAngleX, rotateAngleY, rotateAngleZ, 0F, false); //TODO add a scale float and mirror into the NBT system
		}
	}
	
	public EntityComponent copy() {
		EntityComponent copy = new EntityComponent(this.model);
		copy.readEntityComponentDataFromNBT(this.writeEntityComponentDataToNBT(new NBTTagCompound()));
		return copy;
	}
	
}
