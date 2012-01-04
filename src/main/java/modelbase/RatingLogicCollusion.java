/**
 *
 */
package modelbase;

import agentbase.AgentMasterConfig;
import configbase.Config;
import productbase.Product;
import simbase.Execution;
import simbase.Rating;

/**
 * This class will always return a
 * 
 * @author akai
 */
public class RatingLogicCollusion extends RatingLogic {
	int	targetRating;

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.RatingLogic#calcRating(simbase.Execution,
	 * productbase.Product)
	 */
	@Override
	public Rating calcRating(Execution execution, Product product) {
		return new Rating(this.buyer.getName(), execution.getSeller().getName(), targetRating);
	}

	public int getTargetRating() {
		return targetRating;
	}

	public void setTargetRating(int targetRating) {
		this.targetRating = targetRating;
	}

	public void config() {
		this.targetRating = Integer.parseInt(((AgentMasterConfig) config)
				.getConfigEntry("targetRating"));
	}

}
