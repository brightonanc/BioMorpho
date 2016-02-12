package bioMorpho.lifeform.model;

import java.util.ArrayList;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import bioMorpho.data.ModelData;
import bioMorpho.lifeform.model.animation.InheritedAnimRenderObj;
import bioMorpho.lifeform.model.animation.PlayerAnimationData;
import bioMorpho.lifeform.model.animation.PreRenderInfo;

/**
 * @author Brighton Ancelin
 *
 */
public class EntitySegment {
	
	public ModelCustom model;
	public int textureOffsetX;
	public int textureOffsetY;
	
	public ArrayList<SegmentBox> cubeList;
	public ArrayList<EntityComponent> subComps;
	
	public boolean needsRecompile;
	public int displayList;
	public boolean showSegment;
	
	public float mainRotationPointX;
	public float mainRotationPointY;
	public float mainRotationPointZ;
	// Rotation Angles stored in DEGREES
	public float mainRotationAngleX;
	public float mainRotationAngleY;
	public float mainRotationAngleZ;
	
	public EntitySegment(ModelCustom model, int textureOffsetX, int textureOffsetY) {
		this.model = model;
		this.textureOffsetX = textureOffsetX;
		this.textureOffsetY = textureOffsetY;
		
		this.needsRecompile = true;
		this.displayList = -1;
		this.showSegment = true;
		
		this.cubeList = new ArrayList<SegmentBox>();
	}
	
	public void setRotationPoint(float rPointX, float rPointY, float rPointZ) {
		this.mainRotationPointX = rPointX;
		this.mainRotationPointY = rPointY;
		this.mainRotationPointZ = rPointZ;
	}
	public void setRotationPointX(float rPointX) {
		this.mainRotationPointX = rPointX;
	}
	public void setRotationPointY(float rPointY) {
		this.mainRotationPointY = rPointY;
	}
	public void setRotationPointZ(float rPointZ) {
		this.mainRotationPointZ = rPointZ;
	}
	
	public void setRotationAngle(float rAngleX, float rAngleY, float rAngleZ) {
		this.mainRotationAngleX = rAngleX;
		this.mainRotationAngleY = rAngleY;
		this.mainRotationAngleZ = rAngleZ;
	}
	public void setRotationAngleX(float rAngleX) {
		this.mainRotationAngleX = rAngleX;
	}
	public void setRotationAngleY(float rAngleY) {
		this.mainRotationAngleY = rAngleY;
	}
	public void setRotationAngleZ(float rAngleZ) {
		this.mainRotationAngleZ = rAngleZ;
	}
	
	public SegmentBox getBoxAtIndex(int index) {
		return this.cubeList.get(index);
	}
	
	public void clearBoxes() {
		this.cubeList.clear();
		this.setNeedsRecompile(true);
	}
	
	public void removeBoxAtIndex(int index) {
		this.cubeList.remove(index);
		this.setNeedsRecompile(true);
	}
	
	public void addBoxAtIndex(int index, SegmentBox box) {
		this.cubeList.add(index, box);
		this.setNeedsRecompile(true);
	}
	
	public void addNewDefaultBox() {
		this.addBox(ModelData.BOX_DEFAULT_NAME, 0F, 0F, 0F, 1, 1, 1, 0F);
	}
	
	public void clearSubComps() {
		if(this.getHasSubComps()) {
			this.subComps.clear();
		}
		// Else no need to clear a null object:)
	}
	
	public void setSubCompsToNull() {
		this.subComps = null;
	}
	
	public ArrayList<EntityComponent> getSubComps() {
		return this.subComps;
	}
	
	/*
	 * USE THIS ONE!!!!!!!
	 */
	public void addBox(String name, float posX, float posY, float posZ, int width, int height, int depth, 
			float rotPointX, float rotPointY, float rotPointZ, float rotAngleX, float rotAngleY, float rotAngleZ, float scaleAddend, boolean mirror) {
		SegmentBox newBox = new SegmentBox(this, name, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, width, height, depth, scaleAddend, mirror);
		newBox.setRotPointX(rotPointX);
		newBox.setRotPointY(rotPointY);
		newBox.setRotPointZ(rotPointZ);
		newBox.setRotAngleX(rotAngleX);
		newBox.setRotAngleY(rotAngleY);
		newBox.setRotAngleZ(rotAngleZ);
		this.cubeList.add(newBox);
		// A box is added, therefore the displayList must be recompiled to incorporate the new box
		this.setNeedsRecompile(true);
	}
	
	public void addBox(String name, float posX, float posY, float posZ, int width, int height, int depth, float scaleAddend, boolean mirror) {
		this.addBox(name, posX, posY, posZ, width, height, depth, 0, 0, 0, 0, 0, 0, scaleAddend, mirror);
	}
	
	public void addBox(String name, float posX, float posY, float posZ, int width, int height, int depth, float scaleAddend) {
		this.addBox(name, posX, posY, posZ, width, height, depth, 0, 0, 0, 0, 0, 0, scaleAddend, false);
	}
	
	public void addNewDefaultSubComp() {
		this.addSubComp(new EntityComponent(this.model));
	}
	
	public void addSubComp(EntityComponent comp) {
		if(this.subComps == null) {
			this.subComps = new ArrayList<EntityComponent>();
		}
		this.subComps.add(comp);
	}
	
	public boolean getHasSubComps() {
		return this.subComps != null;
	}
	
	public void render(float scale, PlayerAnimationData animData, PreRenderInfo info, InheritedAnimRenderObj[] inheritedAnims) {
		if(this.showSegment) {
			
			GL11.glPushMatrix();
			
			if(this.needsRecompile) {
				this.compileDisplayList(scale);
			}
			
			if(this.mainRotationPointX != 0 || this.mainRotationPointY != 0 || this.mainRotationPointZ != 0) {
				GL11.glTranslatef(this.mainRotationPointX*scale, this.mainRotationPointY*scale, this.mainRotationPointZ*scale);
			}
			
			if(this.mainRotationAngleX != 0) {
				GL11.glRotatef(this.mainRotationAngleX, 1F, 0F, 0F);
			}
			if(this.mainRotationAngleY != 0) {
				GL11.glRotatef(this.mainRotationAngleY, 0F, 1F, 0F);
			}
			if(this.mainRotationAngleZ != 0) {
				GL11.glRotatef(this.mainRotationAngleZ, 0F, 0F, 1F);
			}
			
			info.genAlterations();
			GL11.glCallList(this.displayList);
			if(this.getHasSubComps()) {
				for(int i = 0; i < this.subComps.size(); i++) {
					this.subComps.get(i).render(scale, animData, inheritedAnims);
				}
			}
			info.reverseAlterations();
			
			GL11.glPopMatrix();
			
		}
	}
	
	public void setNeedsRecompile(boolean needsRecompile) {
		this.needsRecompile = needsRecompile;
	}
	
	public void compileDisplayList(float scale) {
		this.displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		Tessellator tess = Tessellator.instance;
		
		for(int i = 0; i < this.cubeList.size(); i++) {
			GL11.glPushMatrix();
			this.cubeList.get(i).render(tess, scale);
			GL11.glPopMatrix();
		}
		
		GL11.glEndList();
		
		this.needsRecompile = false;
	}
	
}
