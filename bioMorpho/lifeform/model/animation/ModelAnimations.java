package bioMorpho.lifeform.model.animation;

import java.util.Comparator;

import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.animation.settings.AnimSettings;
import bioMorpho.lifeform.model.animation.settings.SettingsArm1;
import bioMorpho.lifeform.model.animation.settings.SettingsLeg1;
import bioMorpho.lifeform.model.animation.settings.SettingsOscillation;
import bioMorpho.lifeform.model.animation.settings.SettingsRotation;
import bioMorpho.lifeform.model.animation.settings.SettingsTail1;
import bioMorpho.lifeform.model.animation.settings.SettingsTail2;


/**
 * @author Brighton Ancelin
 *
 */
public enum ModelAnimations {
	
	NONE("None", false),
	HEAD("Head", false),
	ROTATION("Rotation", false),
	OSCILLATION("Oscillation", false),
	LEG_1_LEFT("Leg 1 Left", true),
	LEG_1_RIGHT("Leg 1 Right", true),
	ARM_1_LEFT("Arm 1 Left", true),
	ARM_1_RIGHT("Arm 1 Right", true),
	TAIL_1("Tail 1 BUGGED", true),
	TAIL_2("Tail 2", true),
	;
	
	private final String guiName;
	private final boolean isInherited;
	
	private ModelAnimations(String guiName, boolean subCompInheritance) {
		this.guiName = guiName;
		this.isInherited = subCompInheritance;
	}
	
	public static Comparator getGuiComparator() {
		class GuiComparator implements Comparator {
			public int compare(Object anim1, Object anim2) {
				return ((ModelAnimations)anim1).getGuiName().compareTo(((ModelAnimations)anim2).getGuiName());
			}
		}
		return new GuiComparator();
	}
	public String getGuiName() { return this.guiName; }
	public boolean isInherited() { return this.isInherited; }
	
	public PreRenderInfo addAnimationData(PlayerAnimationData animData, UniqueAnimData uniqueData, EntityComponent comp, PreRenderInfo info) {
		return this.addAnimationData(animData, uniqueData, comp, info, 0, new InheritedAnimData());
	}
	
	/**
	 * Adds data about preRender animation GL11 calls to the input PreRenderInfo object
	 * @param animData Animation Data on render settings
	 * @param animSettings User defined animation settings (for the most part)
	 * @param info The PreRenderInfo object into which all alterations should be added
	 * @param subCompIndex The subCompIndex that this component requesting animation data is in (0 for main, 1 for first tier sub Comp, etc)
	 * @return The same PreRenderInfo object input
	 */
	public PreRenderInfo addAnimationData(PlayerAnimationData animData, UniqueAnimData uniqueData, EntityComponent comp, PreRenderInfo info,
			int subCompIndex, InheritedAnimData inheritedData) {
		AnimSettings animSettings = uniqueData.getSettings();
		switch(this) {
		case NONE:
			break;
		case HEAD:
			AnimationMethods.head(animData, info);
			break;
		case ROTATION:
			if(animSettings instanceof SettingsRotation) {
				AnimationMethods.rotation((SettingsRotation)animSettings, animData, info);
			}
			break;
		case OSCILLATION:
			if(animSettings instanceof SettingsOscillation) {
				AnimationMethods.oscillation((SettingsOscillation)animSettings, animData, info);
			}
			break;
		case LEG_1_LEFT:
			if(animSettings instanceof SettingsLeg1) {
				AnimationMethods.leg1(animData, (SettingsLeg1)animSettings, uniqueData.getAnimRecordData(), info, subCompIndex, true);
			}
			break;
		case LEG_1_RIGHT:
			if(animSettings instanceof SettingsLeg1) {
				AnimationMethods.leg1(animData, (SettingsLeg1)animSettings, uniqueData.getAnimRecordData(), info, subCompIndex, false);
			}
			break;
		case ARM_1_LEFT:
			if(animSettings instanceof SettingsArm1) {
				AnimationMethods.arm1(animData, (SettingsArm1)animSettings, uniqueData.getAnimRecordData(), info, subCompIndex, true);
			}
			break;
		case ARM_1_RIGHT:
			if(animSettings instanceof SettingsArm1) {
				AnimationMethods.arm1(animData, (SettingsArm1)animSettings, uniqueData.getAnimRecordData(), info, subCompIndex, false);
			}
			break;
		case TAIL_1:
			if(animSettings instanceof SettingsTail1) {
				AnimationMethods.tail1(animData, (SettingsTail1)animSettings, uniqueData.getAnimRecordData(), comp, info, subCompIndex, inheritedData);
			}
			break;
		case TAIL_2:
			if(animSettings instanceof SettingsTail2) {
				AnimationMethods.tail2(animData, (SettingsTail2)animSettings, uniqueData.getAnimRecordData(), info);
			}
			break;
		default: break;
		}
		return info;
	}
	
}
