package bioMorpho.gui.modelTexturing;

import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.gui.compAndBox.CompOnlySetup;
import bioMorpho.gui.lifeformBasic.GuiBasicModel;
import bioMorpho.gui.lifeformBasic.ModelRenderingPort;
import bioMorpho.gui.modelAnimation.AnimationSelectionSetup;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiModelTexturing extends GuiBasicModel {
	
	private TrustMutex textFieldMutex;
	protected CompAndBoxSetup cAndB;
	protected ModelRenderingPort renderPort;
	
	public GuiModelTexturing(Container container, TEGemOfGenesisHolder tileEntity, int lifeformIndex) {
		super(container, tileEntity, lifeformIndex);
		this.textFieldMutex = new TrustMutex();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.cAndB = new CompAndBoxSetup(this, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.getModel(), this.textFieldMutex);
		this.renderPort = new ModelRenderingPort(this.unscaledLeft, this.unscaledTop, this.getModel(), this.mc.renderEngine);
	}
	
	@Override
	public void keyTyped(char keyChar, int keyCode) {
		if(this.cAndB.keyPressed(keyChar, keyCode)) return;
		super.keyTyped(keyChar, keyCode);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		this.renderPort.handleMouseInput(this.width, this.height, this.mc);
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		this.cAndB.mousePressedTextFields(x, y, button);
		if(this.cAndB.mousePressed(x, y, button)) return;
		super.mouseClicked(x, y, button);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.cAndB.onGuiUpdate();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glPushMatrix();
		this.cAndB.draw(this, this.mc.renderEngine, this.fontRenderer);
		
		this.renderPort.draw(this, this.mc, this.cAndB);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		
	}
	
}
