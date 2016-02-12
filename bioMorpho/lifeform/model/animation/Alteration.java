package bioMorpho.lifeform.model.animation;

import org.lwjgl.opengl.GL11;

/**
 * @author Brighton Ancelin
 *
 */
public interface Alteration {
	public abstract void execute();
	
	public static class Translation implements Alteration {
		public final double x, y, z;
		public Translation(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public void execute() {
			if(this.x != 0 || this.y != 0 || this.z != 0) {
				GL11.glTranslated(this.x, this.y, this.z);
			}
		}
	}
	public static class RotationX implements Alteration {
		public final float x;
		public RotationX(float x) {
			this.x = x;
		}
		public void execute() {
			if(this.x != 0) GL11.glRotatef(this.x, 1F, 0F, 0F);
		}
	}
	public static class RotationY implements Alteration {
		public final float y;
		public RotationY(float y) {
			this.y = y;
		}
		public void execute() {
			if(this.y != 0) GL11.glRotatef(this.y, 0F, 1F, 0F);
		}
	}
	public static class RotationZ implements Alteration {
		public final float z;
		public RotationZ(float z) {
			this.z = z;
		}
		public void execute() {
			if(this.z != 0) GL11.glRotatef(this.z, 0F, 0F, 1F);
		}
	}
}
