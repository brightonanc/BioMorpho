package bioMorpho.gui;

import java.util.ArrayList;

import bioMorpho.data.ModData.BioMorphoNoCurrentObjectException;

/**
 * @author Brighton Ancelin
 *
 */
public abstract class GuiMyTabSearchable extends GuiMyTabObj {
	
	public ArrayList baseMembers;
	
	public GuiMyTabSearchable(int unscaledLeft, int unscaledTop, int membersPerPage) {
		super(unscaledLeft, unscaledTop, membersPerPage);
		this.baseMembers = new ArrayList();
		this.members = new ArrayList();
	}
	
	public abstract Object getBaseMemberAtIndex(int index) throws BioMorphoNoCurrentObjectException;

}
