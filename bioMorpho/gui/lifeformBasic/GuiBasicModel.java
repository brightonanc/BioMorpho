package bioMorpho.gui.lifeformBasic;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import bioMorpho.gui.GuiBasic;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.manager.ModelManager;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;


/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiBasicModel extends GuiBasicLifeform {
	
	public final int lifeformIndex;

	public GuiBasicModel(Container container, TEGemOfGenesisHolder tileEntity, int lifeformIndex) {
		super(container, tileEntity);
		this.lifeformIndex = lifeformIndex;
	}
	
	public void onGuiClosed() {
		modelReconfigFunc: {
			for(int i = 0; i < this.getModel().components.size(); i++) {
				if(this.getModel().components.get(i).segment.cubeList.size() > 0) {
					this.getModel().isDefaultBipedModel = false;
					break modelReconfigFunc;
				}
			}
			this.getModel().isDefaultBipedModel = true;
		}
		super.onGuiClosed();
		//LifeformHelper.replaceLifeformAtIndex(this.getTileEntity().getGem(), this.lifeformIndex, this.getLifeform());
	}
	
	public Lifeform getLifeform() {return this.lifeforms.get(this.lifeformIndex);}
	public ModelCustom getModel() {return this.getLifeform().getModel();}
	
}
