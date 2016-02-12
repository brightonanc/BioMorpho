package bioMorpho.lifeform.model.animation;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import bioMorpho.lifeform.model.animation.Alteration.RotationX;
import bioMorpho.lifeform.model.animation.Alteration.RotationY;
import bioMorpho.lifeform.model.animation.Alteration.RotationZ;
import bioMorpho.lifeform.model.animation.Alteration.Translation;


/**
 * @author Brighton Ancelin
 *
 */
public class PreRenderInfo {
	
	protected ArrayList<Alteration> alterations = new ArrayList<Alteration>();
	
	public void reset() {
		this.alterations.clear();
	}
	
	public void genAlterations() {
		GL11.glPushMatrix();
		for(Alteration alteration : this.alterations) {
			alteration.execute();
		}
	}
	public void reverseAlterations() {
		GL11.glPopMatrix();
	}
	
	public void addTrans(double x, double y, double z) {
		this.alterations.add(new Translation(x, y, z));
	}
	public void addRotX(float x) {
		if(x >= 360) x %= 360;
		this.alterations.add(new RotationX(x));
	}
	public void addRotY(float y) {
		if(y >= 360) y %= 360;
		this.alterations.add(new RotationY(y));
	}
	public void addRotZ(float z) {
		if(z >= 360) z %= 360;
		this.alterations.add(new RotationZ(z));
	}
	
}
