/**
 *
 */
package modelbase;

import java.util.Random;

/**
 * @author akai
 * 
 */
public class IdentityLogicSimple extends IdentityLogic {
	double	identityChangeProb;
	Random	random;

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.ActionLogic#config()
	 */
	@Override
	public void config() {
		if (config.getConfigEntry("identityChangeProbability") != null) {
			this.identityChangeProb = Double.parseDouble(config
					.getConfigEntry("identityChangeProbability"));
			agent.setIdentityChangable(true);
			random = new Random();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modelbase.IdentityLogic#requestNewIdentity()
	 */
	@Override
	public boolean requestNewIdentity() {
		if (random.nextDouble() <= identityChangeProb) {
			return true;
		}
		return false;
	}

}
