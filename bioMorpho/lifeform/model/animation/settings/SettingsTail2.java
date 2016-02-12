package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsTail2 extends AnimSettings {
	
	private int speedId = this.registerSetting(new SettingFloat("Speed", 0F, Integer.MAX_VALUE));
	private int swingId = this.registerSetting(new SettingFloat("Swing"));
	
	public SettingsTail2() { super(EnumAnimSettings.TAIL_2); }
	
	public SettingFloat getSpeed() { return (SettingFloat)this.getSetting(this.speedId); }
	public SettingFloat getSwing() { return (SettingFloat)this.getSetting(this.swingId); }
	
}
