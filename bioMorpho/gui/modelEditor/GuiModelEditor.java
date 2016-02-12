package bioMorpho.gui.modelEditor;

import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import bioMorpho.gui.compAndBox.CompAndBoxSetup;
import bioMorpho.gui.lifeformBasic.GuiBasicModel;
import bioMorpho.gui.lifeformBasic.ModelRenderingPort;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.util.TrustMutex;


/**
 * @author Brighton Ancelin
 *
 */
public class GuiModelEditor extends GuiBasicModel {
	
	private TrustMutex textFieldMutex;
	protected CompAndBoxSetup cAndB;
	protected ModelNumberInputSetup mni;
	protected ModelEditorCenterPane mecp;
	protected ModelRenderingPort renderPort;
	
	public GuiModelEditor(Container container, TEGemOfGenesisHolder tileEntity, int lifeformIndex) {
		super(container, tileEntity, lifeformIndex);
		this.textFieldMutex = new TrustMutex();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.cAndB = new CompAndBoxSetup(this, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.getModel(), this.textFieldMutex);
		this.mni = new ModelNumberInputSetup(this, this.cAndB, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.textFieldMutex);
		this.mecp = new ModelEditorCenterPane(this.cAndB, this.unscaledLeft, this.unscaledTop);
		this.renderPort = new ModelRenderingPort(this.unscaledLeft, this.unscaledTop, this.getModel(), this.mc.renderEngine);
	}

	@Override
	public void keyTyped(char keyChar, int keyCode) {
		if(this.cAndB.keyPressed(keyChar, keyCode)) return;
		if(this.mni.keyPressed(keyChar, keyCode)) return;
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
		this.mni.mousePressedTextFields(x, y, button);
		this.mecp.mousePressedTextFields(x, y, button);
		if(this.cAndB.mousePressed(x, y, button)) return;
		if(this.mni.mousePressed(x, y, button)) return;
		if(this.mecp.mousePressed(x, y, button)) return;
		super.mouseClicked(x, y, button);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.cAndB.onGuiUpdate();
		this.mni.onGuiUpdate();
		this.mecp.onGuiUpdate();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		GL11.glPushMatrix();
		this.cAndB.draw(this, this.mc.renderEngine, this.fontRenderer);
		this.mni.draw(this, this.mc.renderEngine);	
		this.mecp.draw(this, this.mc.renderEngine);
		
		this.renderPort.draw(this, this.mc, this.cAndB);
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		
	}
	
}
