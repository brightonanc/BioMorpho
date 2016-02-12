package bioMorpho.lifeform.model.animation;

/**
 * @author Brighton Ancelin
 *
 */
public class InheritedAnimRenderObj extends AnimRenderObj {
	
	private InheritedAnimData inheritedData = new InheritedAnimData();
	private int subCompIndex;
	
	public InheritedAnimRenderObj(int subCompIndex) {
		this.subCompIndex = subCompIndex;
	}
	
	public void incrementSubCompIndex() { this.subCompIndex++; }
	
	public InheritedAnimData getInheritedData() { return this.inheritedData; }
	public int getSubCompIndex() { return this.subCompIndex; }
	
	public static InheritedAnimRenderObj createFromAnimRenderObj(AnimRenderObj input) {
		InheritedAnimRenderObj ret = new InheritedAnimRenderObj(0);
		ret.setEvent(input.getEvent());
		ret.setAnimationOnly(input.getAnimation());
		ret.setUniqueData(input.getUniqueData());
		ret.setSettings(input.getSettings());
		return ret;
	}
	
}
