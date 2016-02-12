package bioMorpho.lifeform.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.Tessellator;

/**
 * @author Brighton Ancelin
 *
 */
public class SegmentBox {

	public PositionTextureVertex[] vertexPositions;
	public TexturedQuad[] quads;
	
	public EntitySegment entitySegment;
	public int textureOffsetX;
	public int textureOffsetY;
	private float posX1;
	private float posY1;
	private float posZ1;
	private int width;
	private int height;
	private int depth;
	private float rotationPointX;
	private float rotationPointY;
	private float rotationPointZ;
	// Rotation Angles are stored as DEGREES
	private float rotationAngleX;
	private float rotationAngleY;
	private float rotationAngleZ;
	private float scaleAddend;
	private boolean mirror;
	
	public String name;
	
	private float posX2;
	private float posY2;
	private float posZ2;
	
	public SegmentBox(EntitySegment entitySegment, String name, int textureOffsetX, int textureOffsetY,
			float posX1, float posY1, float posZ1, int width, int height, int depth, float scaleAddend, boolean mirror) {
		this.entitySegment = entitySegment;
		this.name = name;
		
		this.textureOffsetX = textureOffsetX;
		this.textureOffsetY = textureOffsetY;
		this.posX1 = posX1;
		this.posY1 = posY1;
		this.posZ1 = posZ1;
		this.posX2 = posX1 + width;
		this.posY2 = posY1 + height;
		this.posZ2 = posZ1 + depth;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.scaleAddend = scaleAddend;
		this.mirror = mirror;
		
		this.vertexPositions = new PositionTextureVertex[8];
		this.quads = new TexturedQuad[6];
		
		this.updateAll(textureOffsetX, textureOffsetY, posX1, posY1, posZ1, width, height, depth, scaleAddend, mirror);
	}
		
	public void updateAll(int textureOffsetX, int textureOffsetY,
			float posX1, float posY1, float posZ1, int width, int height, int depth, float scaleAddend, boolean mirror) {
		this.updateQuads(textureOffsetX, textureOffsetY, posX1, posY1, posZ1, width, height, depth, scaleAddend, mirror);
	}
	
	public void updateQuads(int textureOffsetX, int textureOffsetY,
			float posX1, float posY1, float posZ1, int width, int height, int depth, float scaleAddend, boolean mirror) {
		float scaledX1 = posX1 - scaleAddend;
		float scaledY1 = posY1 - scaleAddend;
		float scaledZ1 = posZ1 - scaleAddend;
		float scaledX2 = (posX1 + width) + scaleAddend;
		float scaledY2 = (posY1 + height) + scaleAddend;
		float scaledZ2 = (posZ1 + depth) + scaleAddend;
		
		if (mirror) {
			// Swaps the values of scaledX1 and scaledX2 
			float newX1 = scaledX2;
			scaledX2 = scaledX1;
			scaledX1 = newX1;
		}
		
		PositionTextureVertex ptv1 = new PositionTextureVertex(scaledX1, scaledY1, scaledZ1, 0.0F, 0.0F);
		PositionTextureVertex ptv2 = new PositionTextureVertex(scaledX2, scaledY1, scaledZ1, 0.0F, 8.0F);
		PositionTextureVertex ptv3 = new PositionTextureVertex(scaledX2, scaledY2, scaledZ1, 8.0F, 8.0F);
		PositionTextureVertex ptv4 = new PositionTextureVertex(scaledX1, scaledY2, scaledZ1, 8.0F, 0.0F);
		PositionTextureVertex ptv5 = new PositionTextureVertex(scaledX1, scaledY1, scaledZ2, 0.0F, 0.0F);
		PositionTextureVertex ptv6 = new PositionTextureVertex(scaledX2, scaledY1, scaledZ2, 0.0F, 8.0F);
		PositionTextureVertex ptv7 = new PositionTextureVertex(scaledX2, scaledY2, scaledZ2, 8.0F, 8.0F);
		PositionTextureVertex ptv8 = new PositionTextureVertex(scaledX1, scaledY2, scaledZ2, 8.0F, 0.0F);
		this.vertexPositions[0] = ptv1;
		this.vertexPositions[1] = ptv2;
		this.vertexPositions[2] = ptv3;
		this.vertexPositions[3] = ptv4;
		this.vertexPositions[4] = ptv5;
		this.vertexPositions[5] = ptv6;
		this.vertexPositions[6] = ptv7;
		this.vertexPositions[7] = ptv8;
		
		// TODO might be an issue that I use the model to find texture height and width, but time will tell :)
		this.quads[0] = new TexturedQuad(new PositionTextureVertex[] {ptv6, ptv2, ptv3, ptv7}, textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + depth, textureOffsetY + depth + height, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		this.quads[1] = new TexturedQuad(new PositionTextureVertex[] {ptv1, ptv5, ptv8, ptv4}, textureOffsetX, textureOffsetY + depth, textureOffsetX + depth, textureOffsetY + depth + height, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		this.quads[2] = new TexturedQuad(new PositionTextureVertex[] {ptv6, ptv5, ptv1, ptv2}, textureOffsetX + depth, textureOffsetY, textureOffsetX + depth + width, textureOffsetY + depth, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		this.quads[3] = new TexturedQuad(new PositionTextureVertex[] {ptv3, ptv4, ptv8, ptv7}, textureOffsetX + depth + width, textureOffsetY + depth, textureOffsetX + depth + width + width, textureOffsetY, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		this.quads[4] = new TexturedQuad(new PositionTextureVertex[] {ptv2, ptv1, ptv4, ptv3}, textureOffsetX + depth, textureOffsetY + depth, textureOffsetX + depth + width, textureOffsetY + depth + height, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		this.quads[5] = new TexturedQuad(new PositionTextureVertex[] {ptv5, ptv6, ptv7, ptv8}, textureOffsetX + depth + width + depth, textureOffsetY + depth, textureOffsetX + depth + width + depth + width, textureOffsetY + depth + height, entitySegment.model.textureWidth, entitySegment.model.textureHeight);
		
		if (mirror) {
			for (int i = 0; i < this.quads.length; i++) {
				this.quads[i].flipFace();
			}
		}
	}
	
	public void boxAltered() {
		this.updateAll(this.textureOffsetX, this.textureOffsetY, this.posX1, this.posY1, this.posZ1, this.width, this.height, this.depth, this.scaleAddend, this.mirror);
		this.entitySegment.setNeedsRecompile(true);
	}
	
	public void render(Tessellator tess, float scale) {
		if(this.rotationPointX != 0 || this.rotationPointY != 0 || this.rotationPointZ != 0) {
			GL11.glTranslatef(this.rotationPointX*scale, this.rotationPointY*scale, this.rotationPointZ*scale);
		}
		
		if(this.rotationAngleX != 0) {
			GL11.glRotatef(this.rotationAngleX, 1F, 0F, 0F);
		}
		if(this.rotationAngleY != 0) {
			GL11.glRotatef(this.rotationAngleY, 0F, 1F, 0F);
		}
		if(this.rotationAngleZ != 0) {
			GL11.glRotatef(this.rotationAngleZ, 0F, 0F, 1F);
		}
		
		for(int i = 0; i < this.quads.length; i++) {
			this.quads[i].draw(tess, scale);
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPosX1(float x1) {
		this.posX1 = x1;
		this.boxAltered();
	}
	public void setPosY1(float y1) {
		this.posY1 = y1;
		this.boxAltered();
	}
	public void setPosZ1(float z1) {
		this.posZ1 = z1;
		this.boxAltered();
	}
	public void setWidth(int width) {
		this.width = width;
		this.boxAltered();
	}
	public void setHeight(int height) {
		this.height = height;
		this.boxAltered();
	}
	public void setDepth(int depth) {
		this.depth = depth;
		this.boxAltered();
	}
	public void setRotPointX(float pointX) {
		this.rotationPointX = pointX;
		this.boxAltered();
	}
	public void setRotPointY(float pointY) {
		this.rotationPointY = pointY;
		this.boxAltered();
	}
	public void setRotPointZ(float pointZ) {
		this.rotationPointZ = pointZ;
		this.boxAltered();
	}
	public void setRotAngleX(float angleX) {
		this.rotationAngleX = angleX;
		this.boxAltered();
	}
	public void setRotAngleY(float angleY) {
		this.rotationAngleY = angleY;
		this.boxAltered();
	}
	public void setRotAngleZ(float angleZ) {
		this.rotationAngleZ = angleZ;
		this.boxAltered();
	}
	// The below methods do not currently have a use, but will be left here just in case I need them in the future
	/*public void setPosX2(float x2) {
		this.posX2 = x2;
		this.boxAltered();
	}
	public void setPosY2(float y2) {
		this.posY2 = y2;
		this.boxAltered();
	}
	public void setPosZ2(float z2) {
		this.posZ2 = z2;
		this.boxAltered();
	}*/
	public float getPosX1() {
		return this.posX1;
	}
	public float getPosY1() {
		return this.posY1;
	}
	public float getPosZ1() {
		return this.posZ1;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getDepth() {
		return this.depth;
	}
	public float getRotPointX() {
		return this.rotationPointX;
	}
	public float getRotPointY() {
		return this.rotationPointY;
	}
	public float getRotPointZ() {
		return this.rotationPointZ;
	}
	public float getRotAngleX() {
		return this.rotationAngleX;
	}
	public float getRotAngleY() {
		return this.rotationAngleY;
	}
	public float getRotAngleZ() {
		return this.rotationAngleZ;
	}
	
}
