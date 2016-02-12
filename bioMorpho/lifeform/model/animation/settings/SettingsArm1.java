package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsArm1 extends AnimSettings {
	
	private final int speedId = this.registerSetting(new SettingFloat("Speed", 0F, Integer.MAX_VALUE));
	private final int swingId = this.registerSetting(new SettingFloat("Swing", 0F, Integer.MAX_VALUE));
	private final int rotationZId = this.registerSetting(new SettingFloat("Rotation Z", -360F, 360F));
	
	public SettingsArm1() {
		super(EnumAnimSettings.ARM_1);
	}
	
	public SettingFloat getSpeed() { return (SettingFloat)this.getSetting(this.speedId); }
	public SettingFloat getSwing() { return (SettingFloat)this.getSetting(this.swingId); }
	public SettingFloat getRotationZ() { return (SettingFloat)this.getSetting(this.rotationZId); }
	
}
