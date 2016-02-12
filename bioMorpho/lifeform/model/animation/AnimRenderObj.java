package bioMorpho.lifeform.model.animation;

import bioMorpho.lifeform.model.animation.settings.AnimSettings;


/**
 * @author Brighton Ancelin
 *
 */
public class AnimRenderObj {
	
	private BioMorphoPlayerEvents event;
	private ModelAnimations animation;
	private UniqueAnimData uniqueData = new UniqueAnimData();
	
	public void setEvent(BioMorphoPlayerEvents event) { this.event = event; }
	public void setAnimationOnly(ModelAnimations animation) { this.animation = animation; }
	public void setUniqueData(UniqueAnimData uniqueData) { this.uniqueData = uniqueData; }
	public void setSettings(AnimSettings animSettings) { this.uniqueData.setSettings(animSettings); }
	public void setAnimationWithNewSettings(ModelAnimations animation) {
		this.animation = animation;
		this.uniqueData.setSettings(AnimSettings.createNewAnimationSettings(animation));
	}
	
	public BioMorphoPlayerEvents getEvent() { return this.event; }
	public ModelAnimations getAnimation() { return this.animation; }
	public UniqueAnimData getUniqueData() { return this.uniqueData; }
	public AnimSettings getSettings() { return this.uniqueData.getSettings(); }
	
}
