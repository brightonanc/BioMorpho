package bioMorpho.util;

import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.EntitySegment;
import bioMorpho.lifeform.model.SegmentBox;

/**
 * @author Brighton Ancelin
 *
 */
public class RandomUtils {
	
	public static int getAbsLightValue(World world, int x, int y, int z) {
		int skylightSubtracted = world.calculateSkylightSubtracted(1F);
		int skylight = world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) - skylightSubtracted;
		int block = world.getSavedLightValue(EnumSkyBlock.Block, x, y, z);
		return MathUtils.max(skylight, block);
	}
	
	public static float getRotation(float prevAngle, float targetAngle, float partialTickTime) {
		return prevAngle + ((targetAngle - prevAngle) * partialTickTime);
	}
	
	/** Pretty much just copied over from RenderLivingEntity */
	public static float interpolateRotation(float prevAngle, float angle, float partialTickTime) {
		float difference = angle - prevAngle;
		while(difference < -180F) difference += 360F;
		while(difference >= 180F) difference -= 360F;
		return prevAngle + (difference * partialTickTime);
	}
	
	public static void reflectComp(EntityComponent comp) {
		EntitySegment seg = comp.segment;
		seg.setRotationPointX(-seg.mainRotationPointX);
		seg.setRotationAngleZ(-seg.mainRotationAngleZ);
		seg.setRotationAngleY(-seg.mainRotationAngleY);
		for(SegmentBox curBox : seg.cubeList) {
			curBox.setRotPointX(-curBox.getRotPointX());
			curBox.setRotAngleZ(-curBox.getRotAngleZ());
			curBox.setRotAngleY(-curBox.getRotAngleY());
			curBox.setPosX1(-curBox.getPosX1());
			curBox.setWidth(-curBox.getWidth());
		}
		if(seg.getHasSubComps()) for(EntityComponent curSubComp : seg.getSubComps()) reflectComp(curSubComp);
	}
	
}
