package bioMorpho.gui.lifeformBasic;

import java.util.ArrayList;

import net.minecraft.inventory.Container;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import bioMorpho.etc.Nucleobases;
import bioMorpho.gui.GuiBasic;
import bioMorpho.helper.LifeformHelper;
import bioMorpho.lifeform.Lifeform;
import bioMorpho.tileEntity.TEGemOfGenesisHolder;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiBasicLifeform extends GuiBasic {

	public int scale;
	public int scaledLeft;
	public int scaledTop;
	public int unscaledLeft;
	public int unscaledTop;
	
	protected ArrayList<Lifeform> initLifeforms;
	protected ArrayList<Lifeform> lifeforms;
	protected int[] indexPointers;
	
	private boolean wereRepeatEventsEnabled;
	
	protected LifeformGuiButtonSelection lifeformGuiSel;
	protected TEGemOfGenesisHolder tileEntity;
	
	protected boolean isSwitchingToOtherLifeformGui = false;
		
	public GuiBasicLifeform(Container container, TEGemOfGenesisHolder tileEntity) {
		super(container);
		// Since Minecraft reads the texture file as 256 pixels wide and my texture file is 512 wide,
		// I have to divide the pixel size in the texture file by 2
		this.xSize = 512 / 2;
		this.ySize = (320 + 24) / 2;
		this.scale = 2;
		
		this.tileEntity = tileEntity;
	}
	
	public void initGui() {
		super.initGui();
		// Set the scaled positions for the GUI
		this.scaledLeft = (this.guiLeft / this.scale) - ((this.xSize / this.scale) / 2);
		this.scaledTop = (this.guiTop / this.scale) - ((this.ySize / this.scale) / 2);
		this.unscaledLeft = this.scaledLeft * this.scale;
		this.unscaledTop = (this.scaledTop * this.scale) + 24; // Add 24 to account for the lifeformGuiSel buttons
		
		LifeformHelper.attemptInitialization(this.getTileEntity().getGem());
		// If is used to ensure that we don't reset the initLifeforms and lifeforms fields, 
		// which would cause improper cost calculations and confuse the Tile Entity a bit
		if(this.initLifeforms == null && this.lifeforms == null && this.indexPointers == null) {
			ArrayList<Lifeform> baseLifeforms = LifeformHelper.getLifeforms(this.getTileEntity().getGem());
			this.initLifeforms = new ArrayList<Lifeform>();
			for(Lifeform curLifeform : baseLifeforms) this.initLifeforms.add(curLifeform.copy());
			this.lifeforms = new ArrayList<Lifeform>();
			for(Lifeform curLifeform : baseLifeforms) this.lifeforms.add(curLifeform.copy());
			
			this.indexPointers = LifeformHelper.getIndexPointers(this.getTileEntity().getGem());
		}
		// Enable repeat events on the keyboard. This allows for the event when a key is held and it is repeatedly processed.
		// NOTE: The repeat events are and MUST BE reset when the GUI is closed
		this.wereRepeatEventsEnabled = Keyboard.areRepeatEventsEnabled();
		Keyboard.enableRepeatEvents(true);
		
		this.lifeformGuiSel = new LifeformGuiButtonSelection(this, this.unscaledLeft, this.unscaledTop);
	}
	
	public void onGuiClosed() {
		super.onGuiClosed();
		if(!this.isSwitchingToOtherLifeformGui) this.getTileEntity().beginAbsorbingForChanges(this.initLifeforms, this.lifeforms, this.indexPointers);
		// Reset the Keyboard's repeat events to what it was before the GUI was opened
		Keyboard.enableRepeatEvents(this.wereRepeatEventsEnabled);
	}
	
	public TEGemOfGenesisHolder getTileEntity() {return this.tileEntity;}
	
	/** Used for switching gui initialization */
	public void setInitLifeforms(ArrayList<Lifeform> initLifeforms) {this.initLifeforms = initLifeforms;}
	/** Used for switching gui initialization */
	public void setLifeforms(ArrayList<Lifeform> lifeforms) {this.lifeforms = lifeforms;}
	
	public ArrayList<Lifeform> getInitLifeforms() {return this.initLifeforms;}
	public ArrayList<Lifeform> getLifeforms() {return this.lifeforms;}
	
	/** Used only for gui-changes between GuiBasicLifeform guis */
	public void setIndexPointers(int[] indexPointers) {this.indexPointers = indexPointers;}
	public int[] getIndexPointers() {return this.indexPointers;}
	
/*=============================================== START OF API FOR CROSS-OVER BETWEEN GUI's =============================================*/
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		GuiBasicLifeform selectedGui = this.lifeformGuiSel.mousePressedButtons(x, y, button);
		if(selectedGui != null && selectedGui.getClass() != this.getClass()) {
			this.isSwitchingToOtherLifeformGui = true;
			this.mc.displayGuiScreen(selectedGui);
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glPushMatrix();
		this.lifeformGuiSel.draw(this, this.mc.renderEngine);
		GL11.glPopMatrix();
	}
	
}
