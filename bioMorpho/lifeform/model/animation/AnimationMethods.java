package bioMorpho.lifeform.model.animation;

import bioMorpho.lifeform.model.EntityComponent;
import bioMorpho.lifeform.model.animation.settings.SettingsArm1;
import bioMorpho.lifeform.model.animation.settings.SettingsLeg1;
import bioMorpho.lifeform.model.animation.settings.SettingsOscillation;
import bioMorpho.lifeform.model.animation.settings.SettingsRotation;
import bioMorpho.lifeform.model.animation.settings.SettingsTail1;
import bioMorpho.lifeform.model.animation.settings.SettingsTail2;
import bioMorpho.util.MathUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class AnimationMethods {
	
	public static void head(PlayerAnimationData animData, PreRenderInfo info) {
		info.addRotY(animData.getHeadRotY());
		info.addRotX(animData.getHeadRotX());
	}
	
	public static void rotation(SettingsRotation settings, PlayerAnimationData animData, PreRenderInfo info) {
		info.addRotX(settings.getX().getValue() * animData.getEntity().ticksExisted);
		info.addRotY(settings.getY().getValue() * animData.getEntity().ticksExisted);
		info.addRotZ(settings.getZ().getValue() * animData.getEntity().ticksExisted);
	}
	
	public static void oscillation(SettingsOscillation settings, PlayerAnimationData animData, PreRenderInfo info) {
		double sine = MathUtils.sin(animData.getEntity().ticksExisted*settings.getSpeed().getValue()*0.01D);
		float midX = ((settings.getPosX().getValue()*0.0625F) + (settings.getNegX().getValue()*0.0625F)) / 2;
		float midY = ((settings.getPosY().getValue()*0.0625F) + (settings.getNegY().getValue()*0.0625F)) / 2;
		float midZ = ((settings.getPosZ().getValue()*0.0625F) + (settings.getNegZ().getValue()*0.0625F)) / 2;
		float ampX = Math.abs(((settings.getPosX().getValue()*0.0625F) - (settings.getNegX().getValue()*0.0625F)) / 2);
		float ampY = Math.abs(((settings.getPosY().getValue()*0.0625F) - (settings.getNegY().getValue()*0.0625F)) / 2);
		float ampZ = Math.abs(((settings.getPosZ().getValue()*0.0625F) - (settings.getNegZ().getValue()*0.0625F)) / 2);
		float transX = midX + ((float)sine * ampX);
		float transY = midY + ((float)sine * ampY);
		float transZ = midZ + ((float)sine * ampZ);
		info.addTrans(transX, transY, transZ);
	}
	
	public static void leg1(PlayerAnimationData animData, SettingsLeg1 settings, RecordedAnimData recordAnimData, 
			PreRenderInfo info, int subCompIndex, boolean isLeft) {
		double horizontalMotion = Math.sqrt(MathUtils.square(animData.getEntity().motionX) + MathUtils.square(animData.getEntity().motionZ));
		if(horizontalMotion != 0D) {
			recordAnimData.attemptActivation(animData);
		}
		else {
			recordAnimData.attemptDeactivation(animData);
		}
		float limbSwing = 0F;
		float limbYaw = 0F;
		if(recordAnimData.isActive()) {
			limbSwing = animData.getEntity().limbSwing - recordAnimData.getInitLimbSwing();
			limbYaw = animData.getEntity().limbYaw;
		}
		int subCompSpecifier = subCompIndex % 2;
		final double speedConst = 0.03D;
		final float swingConst = 1F;
		float rotX = 0F;
		if(subCompSpecifier == 0) {
			if(isLeft) {
				rotX = -1 * (float)MathUtils.sin(limbSwing * speedConst * settings.getSpeed().getValue());
			} else {
				rotX = -1 * (float)MathUtils.sin((limbSwing * speedConst * settings.getSpeed().getValue()) + Math.PI);
			}
		} else if(subCompSpecifier == 1) {
			if(isLeft) {
				rotX = (float)Math.abs(MathUtils.sin(limbSwing * speedConst * settings.getSpeed().getValue()));
			} else {
				rotX = (float)Math.abs(MathUtils.sin((limbSwing * speedConst * settings.getSpeed().getValue()) + Math.PI));
			}
		}
		info.addRotX(rotX * limbYaw * swingConst * settings.getSwing().getValue());
	}
	
	public static void arm1(PlayerAnimationData animData, SettingsArm1 settings, RecordedAnimData recordAnimData,
			PreRenderInfo info, int subCompIndex, boolean isLeft) {
		double horizontalMotion = Math.sqrt(MathUtils.square(animData.getEntity().motionX) + MathUtils.square(animData.getEntity().motionZ));
		if(horizontalMotion != 0D) {
			recordAnimData.attemptActivation(animData);
		}
		else {
			recordAnimData.attemptDeactivation(animData);
		}
		float limbSwing = 0F;
		float limbYaw = 0F;
		if(recordAnimData.isActive()) {
			limbSwing = animData.getEntity().limbSwing - recordAnimData.getInitLimbSwing();
			limbYaw = animData.getEntity().limbYaw;
		}
		final double speedConst = 0.03D;
		final float swingConst = 1F;
		float rotX = 0F;
		if(subCompIndex == 0) {
			if(isLeft) {
				rotX = -1 * (float)MathUtils.sin(limbSwing * speedConst * settings.getSpeed().getValue());
			} else {
				rotX = -1 * (float)MathUtils.sin((limbSwing * speedConst * settings.getSpeed().getValue()) + Math.PI);
			}
		} else {
			if(isLeft) {
				rotX = -1 * (float)Math.abs(MathUtils.sin(limbSwing * speedConst * settings.getSpeed().getValue()));
			} else {
				rotX = -1 * (float)Math.abs(MathUtils.sin((limbSwing * speedConst * settings.getSpeed().getValue()) + Math.PI));
			}
		}
		info.addRotX(rotX * limbYaw * swingConst * settings.getSwing().getValue());
	}
	
	public static void tail1(PlayerAnimationData animData, SettingsTail1 settings, RecordedAnimData recordAnimData, EntityComponent comp,
			PreRenderInfo info, int subCompIndex, InheritedAnimData inheritedData) {
		double horizontalMotion = Math.sqrt(MathUtils.square(animData.getEntity().motionX) + MathUtils.square(animData.getEntity().motionZ));
		if(horizontalMotion != 0D) {
			recordAnimData.attemptActivation(animData);
		}
		else {
			recordAnimData.attemptDeactivation(animData);
			return;
		}
		float limbSwing = 0F;
		float limbYaw = 0F;
		if(recordAnimData.isActive()) {
			limbSwing = animData.getEntity().limbSwing - recordAnimData.getInitLimbSwing();
			limbYaw = animData.getEntity().limbYaw;
		}
		
		float amplitude = settings.getAmplitude().getValue();
		float wavelength = settings.getWavelength().getValue();
		float speed = settings.getSpeed().getValue();
		
		double compLength;
		if(comp.segment.getHasSubComps()) {
			compLength = Math.sqrt(MathUtils.square(comp.segment.getSubComps().get(0).segment.mainRotationPointZ) 
					+ MathUtils.square(comp.segment.getSubComps().get(0).segment.mainRotationPointY));
//			System.out.println("1: "+comp.name);
//			System.out.println("2: "+comp.segment.getSubComps().get(0).name);
//			System.out.println("z1 "+comp.segment.mainRotationPointZ);
//			System.out.println("z2 "+comp.segment.getSubComps().get(0).segment.mainRotationPointZ);
//			System.out.println("y1 "+comp.segment.mainRotationPointY);
//			System.out.println("y2 "+comp.segment.getSubComps().get(0).segment.mainRotationPointY);
		} else return;
				
		double prevCompH = inheritedData.getDouble("Tail_1_Comp_H") != null ? inheritedData.getDouble("Tail_1_Comp_H") : 0D;
		double prevCompK = inheritedData.getDouble("Tail_1_Comp_K") != null ? inheritedData.getDouble("Tail_1_Comp_K") : 0D;
		
		double posMinXNonSqrted = MathUtils.square(compLength) - MathUtils.square(amplitude-prevCompK);
		double negMinXNonSqrted = MathUtils.square(compLength) - MathUtils.square(-amplitude-prevCompK);
		double posMinX = posMinXNonSqrted > 0 ? Math.sqrt(posMinXNonSqrted) + prevCompH : prevCompH;
		double negMinX = negMinXNonSqrted > 0 ? Math.sqrt(negMinXNonSqrted) + prevCompH : prevCompH;
		double minX = posMinX < negMinX ? posMinX : negMinX;
		
		double maxX = compLength + prevCompH;
		
		final double incrementConst = 256;
		double xIncrement;
		if(amplitude < wavelength/4) xIncrement = (wavelength/2)/incrementConst;
		else xIncrement = (wavelength/2)/(incrementConst*(amplitude/(wavelength/(4*32))));
		
		boolean initBooleanIsSet = false;
		boolean initCompLengthIsGreater = true;
		boolean compLengthIsGreater;
//		System.out.println("limbSwing "+limbSwing);
//		System.out.println("limbYaw "+limbYaw);
//		System.out.println("amplitude "+amplitude);
//		System.out.println("wavelength "+wavelength);
//		System.out.println("speed "+speed);
//		System.out.println("compLength "+compLength);
//		System.out.println("prevCompH "+prevCompH);
//		System.out.println("prevCompK "+prevCompK);
//		System.out.println("minX "+minX);
//		System.out.println("maxX "+maxX);
//		System.out.println("xIncrement "+xIncrement);
		for(double x = minX; x <= maxX; x += xIncrement) {
			final double speedConst = 0.03D;
			double y = amplitude * Math.sin(x + ((limbSwing*speedConst*speed*360)/wavelength));
			double potentialDistance = Math.sqrt(MathUtils.square(x-prevCompH) + MathUtils.square(y-prevCompK));
			if(compLength > potentialDistance) compLengthIsGreater = true;
			else compLengthIsGreater = false;
			if(x == minX) {
				initCompLengthIsGreater = compLengthIsGreater;
				initBooleanIsSet = true;
			}
			if(compLengthIsGreater != initCompLengthIsGreater && initBooleanIsSet) {
				//TODO: precision
				double angle = Math.atan((y-prevCompK)/(x-prevCompH));
				inheritedData.setDouble("Tail_1_Comp_H", x);
				inheritedData.setDouble("Tail_1_Comp_K", y);
				info.addRotX((float)(angle * limbYaw * (180/Math.PI)));
				return;
			}
		}
//		System.out.println("For loop DIDN'T Work!!!");
	}
	
	public static void tail2(PlayerAnimationData animData, SettingsTail2 settings, RecordedAnimData recordAnimData, PreRenderInfo info) {
		double horizontalMotion = Math.sqrt(MathUtils.square(animData.getEntity().motionX) + MathUtils.square(animData.getEntity().motionZ));
		if(horizontalMotion != 0D) {
			recordAnimData.attemptActivation(animData);
		}
		else {
			recordAnimData.attemptDeactivation(animData);
		}
		float limbSwing = 0F;
		float limbYaw = 0F;
		if(recordAnimData.isActive()) {
			limbSwing = animData.getEntity().limbSwing - recordAnimData.getInitLimbSwing();
			limbYaw = animData.getEntity().limbYaw;
		}
		final double speedConst = 0.03D;
		final float swingConst = 1F;
		float rotY = (float)MathUtils.sin(limbSwing * speedConst * settings.getSpeed().getValue());
		info.addRotY(rotY * limbYaw * swingConst * settings.getSwing().getValue());
	}
	
}
