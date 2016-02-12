package bioMorpho.gui.modelAnimation;

import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import bioMorpho.gui.compAndBox.CompOnlySetup;
import bioMorpho.gui.lifeformBasic.GuiBasicModel;
import bioMorpho.gui.lifeformBasic.ModelRenderingPort;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiModelAnimation extends GuiBasicModel {

	private TrustMutex textFieldMutex;
	protected CompOnlySetup compOnly;
	protected ModelRenderingPort renderPort;
	protected AnimationSelectionSetup animSel;
	
	public GuiModelAnimation(Container container, TEGemOfGenesisHolder tileEntity, int lifeformIndex) {
		super(container, tileEntity, lifeformIndex);
		this.textFieldMutex = new TrustMutex();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.compOnly = new CompOnlySetup(this, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.getModel(), this.textFieldMutex);
		this.renderPort = new ModelRenderingPort(this.unscaledLeft, this.unscaledTop, this.getModel(), this.mc.renderEngine);
		this.animSel = new AnimationSelectionSetup(this, this.compOnly, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.getModel(), this.textFieldMutex);
	}
	
	@Override
	public void keyTyped(char keyChar, int keyCode) {
		if(this.compOnly.keyPressed(keyChar, keyCode)) return;
		if(this.animSel.keyPressed(keyChar, keyCode)) return;
		super.keyTyped(keyChar, keyCode);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		this.renderPort.handleMouseInput(this.width, this.height, this.mc);
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		this.compOnly.mousePressedTextFields(x, y, button);
		this.animSel.mousePressedTextFields(x, y, button);
		if(this.compOnly.mousePressed(x, y, button)) return;
		if(this.animSel.mousePressed(x, y, button)) return;
		super.mouseClicked(x, y, button);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.compOnly.onGuiUpdate();
		this.animSel.onGuiUpdate();
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glPushMatrix();
		this.compOnly.draw(this, this.mc.renderEngine, this.fontRenderer);
		this.animSel.draw(this, this.mc.renderEngine, this.fontRenderer);
		
		this.renderPort.draw(this, this.mc, this.compOnly);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		
	}
	
}
