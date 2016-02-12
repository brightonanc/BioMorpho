package bioMorpho.gui.lifeformSelection;

import net.minecraft.client.renderer.texture.TextureManager;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.data.TextureData.GuiTextures;
import bioMorpho.gui.GuiBasic;
import bioMorpho.lifeform.model.ModelCustom;
import bioMorpho.util.RenderUtils;

/**
 * @author Brighton Ancelin
 *
 */
public class MainRenderPort {
	
	private int unscaledLeft;
	private int unscaledTop;
	
	private TabLifeform tabLifeform;
	
	protected CustomModelRenderingPort renderPort;
	
	public MainRenderPort(int unscaledLeft, int unscaledTop, TabLifeform tabLifeform, TextureManager renderEngine) {
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.tabLifeform = tabLifeform;
		this.renderPort = new CustomModelRenderingPort(this.unscaledLeft, this.unscaledTop, null, renderEngine);
		this.renderPort.initCustomAttributes(11, 9, 308, 231, 7D);
		this.renderPort.setInteractivity(false);
	}
	
	public void draw(GuiBasic gui) {
		RenderUtils.bindTexture(gui.getMinecraft().renderEngine, GuiTextures.LIFEFORM_SELECTION_MAIN_PORT_FRAME);
		gui.drawTexturedRect(this.unscaledLeft+10, this.unscaledTop+8, 310, 233, 0, 0, 512, 512);
		
		ModelCustom model;
		try {model = this.tabLifeform.getCurrent().getModel();}
		catch(BioMorphoNoCurrentObjectException e) {model = new ModelCustom(false);}
		this.renderPort.setModel(model);
		this.renderPort.setRotation(-(-30F), gui.getMinecraft().theWorld.getTotalWorldTime() % 360F);
		this.renderPort.drawWithoutRotPoints(gui, gui.getMinecraft());
	}
	
}
