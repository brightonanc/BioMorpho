package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public class SettingsOscillation extends AnimSettings {
	
	private final int speedId = this.registerSetting(new SettingFloat("Speed", 0F, Integer.MAX_VALUE));
	private final int posXId = this.registerSetting(new SettingFloat("Positive X", 0F, Integer.MAX_VALUE));
	private final int posYId = this.registerSetting(new SettingFloat("Positive Y", 0F, Integer.MAX_VALUE));
	private final int posZId = this.registerSetting(new SettingFloat("Positive Z", 0F, Integer.MAX_VALUE));
	private final int negXId = this.registerSetting(new SettingFloat("Negative X", Integer.MIN_VALUE, 0F));
	private final int negYId = this.registerSetting(new SettingFloat("Negative Y", Integer.MIN_VALUE, 0F));
	private final int negZId = this.registerSetting(new SettingFloat("Negative Z", Integer.MIN_VALUE, 0F));
	
	public SettingsOscillation() {
		super(EnumAnimSettings.OSCILLATION);
	}
	
	public SettingFloat getSpeed() { return (SettingFloat)this.getSetting(this.speedId); }
	public SettingFloat getPosX() { return (SettingFloat)this.getSetting(this.posXId); }
	public SettingFloat getPosY() { return (SettingFloat)this.getSetting(this.posYId); }
	public SettingFloat getPosZ() { return (SettingFloat)this.getSetting(this.posZId); }
	public SettingFloat getNegX() { return (SettingFloat)this.getSetting(this.negXId); }
	public SettingFloat getNegY() { return (SettingFloat)this.getSetting(this.negYId); }
	public SettingFloat getNegZ() { return (SettingFloat)this.getSetting(this.negZId); }
	
}
