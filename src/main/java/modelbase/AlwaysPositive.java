package modelbase;

import productbase.Product;
import simbase.Execution;
import simbase.Rating;
import agentbase.Buyer;
import agentbase.Seller;
import core.BaseObject;

public class AlwaysPositive extends RatingLogic{
	
	public Rating calcRating(Execution execution, Product product) 
	{
		int random = (int) (Math.random() * 10) % 2;
		int rate;
		if (random == 1) 
		{
			rate = 5;
		}
		else
		{
			rate = 4;
		}
		return new Rating(this.buyer.getName(), execution.getSeller().getName(), rate);
	}
	
	
}
