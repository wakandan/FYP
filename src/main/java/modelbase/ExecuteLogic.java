/**
 *
 */
package modelbase;

import java.util.HashMap;

import simbase.Execution;
import simbase.Inventory;
import core.BaseObject;

/**
 * @author akai
 * 
 */
public abstract class ExecuteLogic extends BaseObject {
	public abstract void processTransaction(Execution execution,
			HashMap<String, Inventory> myInventory);
}
