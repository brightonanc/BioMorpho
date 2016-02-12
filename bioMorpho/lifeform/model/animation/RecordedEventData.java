package bioMorpho.lifeform.model.animation;

/**
 * @author Brighton Ancelin
 *
 */
public class RecordedEventData {
	
	private boolean isActive = false;
	
	private int initTicksExisted;
	private float initLimbSwing;
	
	public void attemptActivation(PlayerAnimationData animData) {
		if(!this.isActive) {
			this.initTicksExisted = animData.getEntity().ticksExisted;
			this.initLimbSwing = animData.getEntity().limbSwing;
		}
		this.isActive = true;
	}
	
	public void attemptDeactivation(PlayerAnimationData animData) {
		if(this.isActive) {
			
		}
		this.isActive = false;
	}
	
	public boolean isActive() { return this.isActive; }
	public int getInitTicksExisted() { return this.initTicksExisted; }
	public float getInitLimbSwing() { return this.initLimbSwing; }
	
}
