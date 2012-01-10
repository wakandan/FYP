/**
 *
 */
package simbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import configbase.AgentConfig;

import modelbase.Entity;
import agentbase.Agent;
import agentbase.Buyer;
import core.BaseObject;
import core.Pair;

/**
 * @author akai
 * 
 */
public class ResultAnalyzer extends BaseObject {
	Sim									sim;
	ArrayList<Buyer>					buyers;
	HashMap<String, ArrayList<Double>>	dataByTrustModel;
	HashMap								resultByTrustModel;
	Collection<Entity>					sellers;

	public ResultAnalyzer(Sim sim) {
		super();
		this.sim = sim;
	}

	private void resetVars() {
		resultByTrustModel = new HashMap();
		dataByTrustModel = new HashMap<String, ArrayList<Double>>();
		for (Buyer buyer : buyers) {
			String trustModelName = buyer.getPurchaseLogic().trustModel.getClass().getName();
			if (dataByTrustModel.get(trustModelName) == null)
				dataByTrustModel.put(trustModelName, new ArrayList<Double>());
		}
	}

	public void initVars() {
		HashMap<String, Agent> customAgents = sim.getAgentManager().customAgents;
		buyers = new ArrayList<Buyer>();
		resultByTrustModel = new HashMap();
		dataByTrustModel = new HashMap<String, ArrayList<Double>>();
		for (Agent agent : customAgents.values()) {
			if (((Buyer) agent).getPurchaseLogic().trustModel != null) {
				String trustModelName = ((Buyer) agent).getPurchaseLogic().trustModel.getClass()
						.getName();
				buyers.add((Buyer) agent);
				if (dataByTrustModel.get(trustModelName) == null)
					dataByTrustModel.put(trustModelName, new ArrayList());
			}
		}
		sellers = sim.getAgentManager().getSellers().getAll();

	}

	/* Trust difference between calculated v/s predetermined. Honest sellers
	 * have trust value of 1, dishonest sellers have trust value of 0 */
	public void calcTrustDifference() {
		resetVars();
		for (Buyer buyer : buyers) {
			String trustModelName = buyer.getPurchaseLogic().trustModel.getClass().getName();
			ArrayList<Double> result = dataByTrustModel.get(trustModelName);
			for (Entity e : sellers) {
				double trustValue = buyer.getPurchaseLogic().trustModel.calcTrust(buyer.getName(),
						e.getName());
				double assignValue = ((AgentConfig) sim.getAgentManager().agentModel.getConfig())
						.checkHonest((Agent) e);
				result.add(trustValue - assignValue);
			}
		}
		System.out.println("*** Trust difference ***");
		aggregateResult(dataByTrustModel);
	}

	public void reportResult() {
		initVars();
		calcAvgRating();
		calcTrustDifference();
		calcSuccessTrans();
	}

	/* Calc avg ratings that custom agents left. The higher the ratings, the
	 * better sellers they have chosen */
	public void calcAvgRating() {
		resetVars();
		for (Buyer buyer : buyers) {
			/* Only check for agents with attached trust model to see if it's
			 * effective */
			String trustModelName = buyer.getPurchaseLogic().trustModel.getClass().getName();
			ArrayList<Double> result = dataByTrustModel.get(trustModelName);
			for (Entity e : sellers) {
				ArrayList<Rating> ratings = sim.ratingManager.getRating(buyer.getName(),
						e.getName());
				if (ratings != null) {
					double sum = 0;
					for (Rating rating : ratings) {
						sum += rating.rating;
					}
					if (ratings.size() > 0)
						result.add(sum * 1.0 / ratings.size());
				}
			}

		}

		System.out.println("*** Total rating ***");
		aggregateResult(dataByTrustModel);
	}

	/* Count the number of successful transactions made. In the future will have
	 * the counts weighted with transaction's value */
	public void calcSuccessTrans() {
		resetVars();
		HashMap<String, Double> transCounts = new HashMap<String, Double>();
		for (Buyer buyer : buyers) {
			/* Only check for agents with attached trust model to see if it's
			 * effective */
			String trustModelName = buyer.getPurchaseLogic().trustModel.getClass().getName();
			Double result = 0.0;
			if (transCounts.containsKey(trustModelName)) {
				result = transCounts.get(trustModelName);
			}

			for (Entity e : sellers) {
				int ratingCount = 0;
				ArrayList<Rating> ratings = sim.ratingManager.getRating(buyer.getName(),
						e.getName());
				if (ratings != null) {
					for (Rating rating : ratings) {
						if (rating.rating >= 3)
							result++;
					}
				}
				transCounts.put(trustModelName, result);
			}

		}

		System.out.println("*** # Success transactions ***");
		for (String trustModelName : transCounts.keySet()) {
			System.out.println(String.format("%s: %.3f", trustModelName,
					transCounts.get(trustModelName)));
		}
	}

	public void aggregateResult(HashMap<String, ArrayList<Double>> dataByTrustModel) {
		for (String trustModelName : dataByTrustModel.keySet()) {
			System.out.print(trustModelName + " :");
			ArrayList<Double> data = dataByTrustModel.get(trustModelName);
			double[] resultArray = new double[data.size()];
			int i = 0;
			for (double x : data) {
				resultArray[i++] = x;
			}
			DescriptiveStatistics statistics = new DescriptiveStatistics(resultArray);
			Pair<Double, Double> finalResult = new Pair<Double, Double>(statistics.getMean(),
					statistics.getStandardDeviation());
			System.out.println(String.format("Mean: %.3f, Std. variance: %.3f", finalResult.val1,
					finalResult.val2));
		}
	}

}
