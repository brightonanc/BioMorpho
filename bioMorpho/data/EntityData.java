package bioMorpho.data;

import net.minecraft.entity.Entity;
import bioMorpho.entity.EntityBlueMeteor;
import bioMorpho.entity.EntityEnergeticGlassOrb;

/**
 * @author Brighton Ancelin
 *
 */
public class EntityData {
	
	public enum EnumEntityData {
		
		METEOR_BLUE(0, EntityBlueMeteor.class, "meteorBlue", 48, 1, true),
		ENERGETIC_GLASS_ORB(1, EntityEnergeticGlassOrb.class, "energeticGlassOrb", 48, 1, true),
		;
		
		private final int id;
		private final Class<? extends Entity> clazz;
		private final String name;
		private final int trackingRange;
		private final int updateFrequency;
		private final boolean sendsVelocityUpdates;
		
		private EnumEntityData(int id, Class<? extends Entity> clazz, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
			this.id = id;
			this.clazz = clazz;
			this.name = name;
			this.trackingRange = trackingRange;
			this.updateFrequency = updateFrequency;
			this.sendsVelocityUpdates = sendsVelocityUpdates;
		}
		
		public int getId() {return this.id;}
		public Class<? extends Entity> getEntityClass() {return this.clazz;}
		public String getName() { return this.name; }
		public int getTrackingRange() { return this.trackingRange; }
		public int getUpdateFrequency() { return this.updateFrequency; }
		public boolean getSendsVelocityUpdates() { return this.sendsVelocityUpdates; }
		
	}
	
}
