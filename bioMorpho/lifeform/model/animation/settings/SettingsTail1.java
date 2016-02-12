package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsTail1 extends AnimSettings {
	
	private int speedId = this.registerSetting(new SettingFloat("Speed", 0F, Integer.MAX_VALUE));
	private int wavelengthId = this.registerSetting(new SettingFloat("Wavelength", 0F, Integer.MAX_VALUE));
	private int amplitudeId = this.registerSetting(new SettingFloat("Amplitude"));
	
	public SettingsTail1() {
		super(EnumAnimSettings.TAIL_1);
	}
	
	public SettingFloat getSpeed() { return (SettingFloat)this.getSetting(this.speedId); }
	public SettingFloat getWavelength() { return (SettingFloat)this.getSetting(this.wavelengthId); }
	public SettingFloat getAmplitude() { return (SettingFloat)this.getSetting(this.amplitudeId); }
	
}
