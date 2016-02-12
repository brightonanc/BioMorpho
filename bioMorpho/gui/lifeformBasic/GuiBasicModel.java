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
	
/*
	public int scale;
	public int scaledLeft;
	public int scaledTop;
	public int unscaledLeft;
	public int unscaledTop;
	
	protected int lifeformIndex;
	protected ArrayList<Lifeform> lifeforms;
	
	private boolean wereRepeatEventsEnabled;
	
	protected ModelGuiSelection modelGuiSel;
	protected TEGemOfGenesisHolder tileEntity;
	
	public GuiBasicModel(Container container, TEGemOfGenesisHolder tileEntity) {
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
		this.unscaledTop = (this.scaledTop * this.scale) + 24; // Add 24 to account for the modelGuiSel buttons
		
		LifeformHelper.initializeGemWith1Lifeform(this.getTileEntity().getGem());
		this.lifeforms = LifeformHelper.getLifeforms(this.getTileEntity().getGem()); //TODO: add in an index finder
		// Enable repeat events on the keyboard. This allows for the event when a key is held and it is repeatedly processed.
		// NOTE: The repeat events are and MUST BE reset when the GUI is closed
		this.wereRepeatEventsEnabled = Keyboard.areRepeatEventsEnabled();
		Keyboard.enableRepeatEvents(true);
		
		this.modelGuiSel = new ModelGuiSelection(this, this.unscaledLeft, this.unscaledTop);
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
		LifeformHelper.replaceLifeformAtIndex(this.getTileEntity().getGem(), 0, this.lifeform); //TODO: add in an index finder
		this.getTileEntity().forceSendUpdatePacket();
		// Reset the Keyboard's repeat events to what it was before the GUI was opened
		Keyboard.enableRepeatEvents(this.wereRepeatEventsEnabled);
	}
	
	public Minecraft getMinecraft() {return this.mc;}
	public Lifeform getLifeform() {return this.lifeforms.get(this.lifeformIndex);}
	public ModelCustom getModel() {return this.getLifeform().getModel();}
	public TEGemOfGenesisHolder getTileEntity() {return this.tileEntity;}
	
/*=============================================== START OF API FOR CROSS-OVER BETWEEN GUI's =============================================
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		GuiBasicModel selectedGui = this.modelGuiSel.mousePressedButtons(x, y, button);
		if(selectedGui != null && selectedGui.getClass() != this.getClass()) {
			this.mc.displayGuiScreen(selectedGui);
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		GL11.glPushMatrix();
		this.modelGuiSel.draw(this, this.mc.renderEngine);
		GL11.glPopMatrix();
	}
	*/
}
