package application;

import java.util.ArrayList;



import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import interfaces.UnionFindInterface;

public class QuickUnionFind  implements UnionFindInterface{

	private int numberOfNodesInFlock;
	private int parentNodeId[];
	private int numberOfNodesInATree[] ;


	public QuickUnionFind(int node)
	{
		numberOfNodesInFlock = node;
		parentNodeId = new int[numberOfNodesInFlock];
		numberOfNodesInATree = new int[numberOfNodesInFlock];

		for (int i = 0; i < numberOfNodesInFlock; i++) {
			parentNodeId[i] = i;
			numberOfNodesInATree[i] =1;
		}
	}

	public int root(int i) {
		while(i != parentNodeId[i]){
			parentNodeId[i] = parentNodeId[parentNodeId[i]]; //path compression
			i = parentNodeId[i];
		}
		return i;
	}

	public void unite(int p, int q) {
		
		int rootOfP = root(p);
		int rootOfQ = root(q);
		
		if( rootOfP != rootOfQ) {
			if(numberOfNodesInATree[rootOfP] < numberOfNodesInATree[rootOfQ]) {
				parentNodeId[rootOfP] = rootOfQ;
				numberOfNodesInATree[rootOfQ] += numberOfNodesInATree[rootOfP];
			}else {
				parentNodeId[rootOfQ] = rootOfP;
				numberOfNodesInATree[rootOfP] += numberOfNodesInATree[rootOfQ];
			}
		}
	}
	
	public boolean connectedNode(int p, int q) {
		return root(p) == root(q);
	}
	
	
	public int getNumberOfTrees(final int noiseReductionValue) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i=0; i < numberOfNodesInFlock; i++) {
			if(numberOfNodesInATree[i] > noiseReductionValue) {
				set.add(root(i));
			}
		}
		return set.size();
	}
	
	public Set<Integer> getRoots(final int noiseReductionValue){
		Set<Integer> parentSet = new HashSet<Integer>();
		for ( int i = 0; i < numberOfNodesInFlock; i++) {
			if(numberOfNodesInATree[i] > noiseReductionValue) {
				parentSet.add(root(i));
			}
		}
		return parentSet;
	}
	
	public List<Integer> getElementsOfTree(final int childNode){
		final int rootNode = root(childNode);
		if( rootNode == childNode && numberOfNodesInATree[childNode]==1) {
			return Arrays.asList(rootNode);
		}
		final List<Integer> elementsInTree = new ArrayList<Integer>();
		for (int i = 0; i < numberOfNodesInFlock; i++) {
			if ( root(i) == rootNode) {
				elementsInTree.add(i);
			}
		}
		return elementsInTree;
		
	}
}
