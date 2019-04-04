package interfaces;

/**
 * This is an interface that is implemented in the Quick Union Find class.
 * 
 * @author Niall Jordan 
 *
 */
public interface UnionFindInterface {
	public void unite(int p , int q);
	
	public boolean connectedNode(int p, int q);
	
	public int getNumberOfTrees(int noiseReductionValue);
	
}
