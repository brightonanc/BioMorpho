package bioMorpho.gui.lifeformSelection;

import net.minecraft.inventory.Container;

import org.lwjgl.opengl.GL11;

import bioMorpho.gui.lifeformBasic.GuiBasicLifeform;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;
import bioMorpho.util.TrustMutex;

/**
 * @author Brighton Ancelin
 *
 */
public class GuiLifeformSelection extends GuiBasicLifeform {
	
	protected TabLifeform tabLifeform;
	public final int initialLifeformTabIndex;
	
	protected LifeformSelectionExtra lfSelExtra;
	protected MainRenderPort mainRenderPort;
	
	protected TrustMutex textFieldMutex;
	protected LifeformDataPanel dataPanel;
	
	public GuiLifeformSelection(Container container, TEGemOfGenesisHolder tileEntity) {
		this(container, tileEntity, 0);
	}
	
	public GuiLifeformSelection(Container container, TEGemOfGenesisHolder tileEntity, int initialLifeformIndex) {
		super(container, tileEntity);
		this.initialLifeformTabIndex = initialLifeformIndex;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.tabLifeform = new TabLifeform(this.unscaledLeft, this.unscaledTop, 5, this.lifeforms);
		if(this.initialLifeformTabIndex < this.tabLifeform.members.size()) this.tabLifeform.currentObjIndex = this.initialLifeformTabIndex;
		else this.tabLifeform.currentObjIndex = -1;
		this.tabLifeform.setPageToCurrentObjPage();
		
		this.lfSelExtra = new LifeformSelectionExtra(this, this.unscaledLeft, this.unscaledTop, this.tabLifeform);
		this.mainRenderPort = new MainRenderPort(this.unscaledLeft, this.unscaledTop, this.tabLifeform, this.mc.renderEngine);
		
		this.textFieldMutex = new TrustMutex();
		this.dataPanel = new LifeformDataPanel(this, this.unscaledLeft, this.unscaledTop, this.fontRenderer, this.textFieldMutex);
	}

	@Override
	public void keyTyped(char keyChar, int keyCode) {
		if(this.lfSelExtra.keyPressed(keyChar, keyCode)) return;
		if(this.dataPanel.keyPressed(keyChar, keyCode)) return;
		super.keyTyped(keyChar, keyCode);
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		this.dataPanel.mousePressedTextFields(x, y, button);
		if(this.tabLifeform.mousePressed(x, y, button)) return;
		if(this.lfSelExtra.mousePressed(x, y, button)) return;
		if(this.dataPanel.mousePressed(x, y, button)) return;
		super.mouseClicked(x, y, button);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.lfSelExtra.onGuiUpdate();
		this.dataPanel.onGuiUpdate();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		super.drawScreen(mouseX, mouseY, par3);
		GL11.glPushMatrix();
		this.tabLifeform.draw(this, this.mc.renderEngine, this.fontRenderer);
		this.lfSelExtra.draw(this);
		this.mainRenderPort.draw(this);
		this.dataPanel.draw(this);
		GL11.glPopMatrix();
	}
	
	public int getCurrentLifeformIndex() {return this.tabLifeform.currentObjIndex;}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		
	}
	
}
