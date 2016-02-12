package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsRotation extends AnimSettings {
	
	private final int xId = this.registerSetting(new SettingFloat("X", -360F, 360F));
	private final int yId = this.registerSetting(new SettingFloat("Y", -360F, 360F));
	private final int zId = this.registerSetting(new SettingFloat("Z", -360F, 360F));
	
	public SettingsRotation() {
		super(EnumAnimSettings.ROTATION);
	}
	
	public SettingFloat getX() { return (SettingFloat)this.getSetting(this.xId); }
	public SettingFloat getY() { return (SettingFloat)this.getSetting(this.yId); }
	public SettingFloat getZ() { return (SettingFloat)this.getSetting(this.zId); }
	
}
