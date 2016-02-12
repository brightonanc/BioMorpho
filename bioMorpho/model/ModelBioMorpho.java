package bioMorpho.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * @author Brighton Ancelin
 *
 */
public class ModelBioMorpho implements IModelCustom {
	
	protected IModelCustom model;
	
	public ModelBioMorpho(String modelLocation) {
		this.model = AdvancedModelLoader.loadModel(modelLocation);
	}
	
	public String getType() {return this.model.getType();}
	public void renderAll() {this.model.renderAll();}
	public void renderPart(String partName) {this.model.renderPart(partName);}
	
}
