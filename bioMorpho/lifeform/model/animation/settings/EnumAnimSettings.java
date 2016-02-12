package bioMorpho.lifeform.model.animation.settings;

/**
 * @author Brighton Ancelin
 *
 */
public enum EnumAnimSettings {
	
	UNKNOWN(SettingsBlank.class),
	ROTATION(SettingsRotation.class),
	OSCILLATION(SettingsOscillation.class),
	LEG_1(SettingsLeg1.class),
	ARM_1(SettingsArm1.class),
	TAIL_1(SettingsTail1.class),
	TAIL_2(SettingsTail2.class),
	;
	
	private final Class<? extends AnimSettings> clazz;
	
	private EnumAnimSettings(Class<? extends AnimSettings> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends AnimSettings> getEnumClass() { return this.clazz; }
	
	public AnimSettings createNewInstance() {
		try { return this.clazz.newInstance(); }
		catch(Exception e) { return null; }
	}
	
}
