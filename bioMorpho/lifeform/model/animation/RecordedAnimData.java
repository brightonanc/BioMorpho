package bioMorpho.lifeform.model.animation;

/**
 * @author Brighton Ancelin
 *
 */
public class RecordedAnimData {
	
	private boolean isActive = false;
	
	// WARNING TO FUTURE ME: all variables in this class SHOULD be based on the ENTITY NOT A COMPONENT!!!
	// If the variables are based on a component, then inherited animations are screwed up
	// For more information, go to the EntityComponent.render() method and read it and its animation code
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
