package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsLeg1 extends AnimSettings {
	
	private final int speedId = this.registerSetting(new SettingFloat("Speed", 0F, Integer.MAX_VALUE));
	private final int swingId = this.registerSetting(new SettingFloat("Swing", 0F, Integer.MAX_VALUE));
	
	public SettingsLeg1() {
		super(EnumAnimSettings.LEG_1);
	}
	
	public SettingFloat getSpeed() { return (SettingFloat)this.getSetting(this.speedId); }
	public SettingFloat getSwing() { return (SettingFloat)this.getSetting(this.swingId); }
	
}
