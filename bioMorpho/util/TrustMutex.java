package bioMorpho.util;

import java.util.ArrayList;

/**
 * @author Brighton Ancelin
 *
 */
public class TrustMutex {
	
	private ArrayList<ITrustMutexMember> members;
	
	public TrustMutex() {
		this.members = new ArrayList<ITrustMutexMember>();
	}
	
	public void registerNewMember(ITrustMutexMember newMember) {
		this.members.add(newMember);
	}
	
	public void grab(ITrustMutexMember grabber) {
		boolean isGrabberAMember = false;
		for(int i = 0; i < this.members.size(); i++) {
			if(this.members.get(i) == grabber) {
				isGrabberAMember = true;
				break;
			}
		}
		if(!isGrabberAMember) return;
		for(int i = 0; i < this.members.size(); i++) {
			if(this.members.get(i) != grabber) {
				this.members.get(i).onMutexGrabbedBySomeoneElse();
			}
		}
	}
	
	public interface ITrustMutexMember {
		public void onMutexGrabbedBySomeoneElse();
	}
	
}
