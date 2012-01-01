/**
 *
 */
package agentbase;

/**
 * @author akai
 * 
 */
public class AgentMasterCollusion extends AgentMaster {
	String	targetSeller;
	int		targetRating;

	/**
	 * @param masterConfig
	 */
	public AgentMasterCollusion(AgentMasterConfig masterConfig) {
		super(masterConfig);
	}

	public String getTargetSeller() {
		return targetSeller;
	}

	public void setTargetSeller(String targetSeller) {
		this.targetSeller = targetSeller;
	}

	public int getTargetRating() {
		return targetRating;
	}

	public void setTargetRating(int targetRating) {
		this.targetRating = targetRating;
	}

}
