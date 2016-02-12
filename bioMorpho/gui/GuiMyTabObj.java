package bioMorpho.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;
import bioMorpho.util.IdGenerator;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiMyTabObj {
	
	protected int unscaledLeft;
	protected int unscaledTop;
	protected IdGenerator ids = new IdGenerator();
	
	private int currentPageNum;
	public int membersPerPage;
	public ArrayList members;
	public Object currentObj;
	
	protected GuiMyButton btnPageUp;
	protected GuiMyButton btnPageDown;
	
	public GuiMyTabObj(int unscaledLeft, int unscaledTop, int membersPerPage) {
		this.currentPageNum = 1;
		this.membersPerPage = membersPerPage;
		this.currentObj = null;
		this.unscaledLeft = unscaledLeft;
		this.unscaledTop = unscaledTop;
		this.initButtons(unscaledLeft, unscaledTop);
	}
	
	protected abstract void initButtons(int unscaledLeft, int unscaledTop);
	protected abstract boolean mousePressed(int x, int y, int button);
	public abstract Object getMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException;
	public abstract Object getCurrent() throws BioMorphoNoCurrentObjectException;
	public abstract void draw(Gui gui, TextureManager renderEngine, FontRenderer fontRenderer);
	
	public void setPageNum(int num) {
		if(num < this.pageMin()) {
			this.currentPageNum = this.pageMin();
			return;
		}
		if(num > this.pageMax()) {
			this.currentPageNum = this.pageMax();
			return;
		}
		this.currentPageNum = num;
	}
	
	public int getCurrentPageNum() {
		// If the currentPageNum is below the pageMin
		if(this.currentPageNum < this.pageMin()) {
			this.currentPageNum = this.pageMin();
		}
		// If the currentPageNum is above the pageMax
		if(this.currentPageNum > this.pageMax()) {
			this.currentPageNum = this.pageMax();
		}
		return this.currentPageNum;
	}
	
	public void pageDownOne() {
		if(this.getCurrentPageNum()+1 > this.pageMax()) {
			return;
		}
		this.currentPageNum++;
	}
	
	public void pageUpOne() {
		if(this.getCurrentPageNum()-1 < this.pageMin()) {
			return;
		}
		this.currentPageNum--;
	}
	
	public int pageMin() {
		return 1;
	}
	
	public int pageMax() {
		if(this.members.size() == 0) {
			return 1;
		}
		return ((this.members.size() - 1) / this.membersPerPage) + 1;
	}
	
	public int getElementsOnCurrentPage() {
		if(this.getCurrentPageNum() != this.pageMax()) {
			return this.membersPerPage;
		}
		return this.members.size() - ((this.getCurrentPageNum() - 1) * this.membersPerPage);
	}
	
	public int minIndexForPage() {
		return (this.getCurrentPageNum() - 1) * this.membersPerPage;
	}
	
	public int maxIndexForPage() {
		return (this.getCurrentPageNum() * this.membersPerPage) - 1;
	}
	
	public void setCurrentObjByPageIndex(int pageIndex) {
		int index = this.minIndexForPage() + pageIndex;
		if(index < this.members.size()) {
			this.currentObj = this.members.get(index);
		}
		else {
			this.currentObj = null;
		}
	}
	
	protected boolean isCurrentObjOnPage() {
		return this.minIndexForPage() <= this.members.indexOf(this.currentObj) && this.members.indexOf(this.currentObj) <= this.maxIndexForPage();
	}
	/*
	 * Will throw and error/exception if the current is not on the page
	 */
	protected int getIndexOfCurrentOnPage() throws BioMorphoNoCurrentObjectException {
		if(this.isCurrentObjOnPage()) {
			return this.members.indexOf(this.currentObj) - this.minIndexForPage();
		}
		throw new BioMorphoNoCurrentObjectException();
	}
	
	protected int pageIndexToAbsIndex(int pageIndex) {
		return this.minIndexForPage() + pageIndex;
	}
	
}
