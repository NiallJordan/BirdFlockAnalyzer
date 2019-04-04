package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuickUnionFindTest {
	
	private static int sampleUnionFindSize = 10;
	private QuickUnionFind quf;
	
	
	@BeforeEach
	void setUp() {
		this.quf = new QuickUnionFind(sampleUnionFindSize);
	}
	
	
	/**
	 * Tests the connectedNodesMethod, to check if 
	 * two nodes are connected.
	 */
	@Test
	void testConnectedNodes() {
		assertTrue(!quf.connectedNode(0,1));
		quf.unite(0, 1);
		assertTrue(quf.connectedNode(0, 1));
	}
	
	@Test
	void testGetNumberOfTrees() {
		assertEquals(quf.getNumberOfTrees(0), sampleUnionFindSize);
		quf.unite(0, 1);
		assertEquals(quf.getNumberOfTrees(0),9);
		assertEquals(quf.getNumberOfTrees(1),1);
	}
	
	@Test
	void testGetRoots() {
		assertEquals(quf.getRoots(0).size(), sampleUnionFindSize);
		quf.unite(0, 1);
		assertEquals(quf.getRoots(1).size(), 1);
	}

	@Test
	void testGetElementsOfTree() {
		quf.unite(0, 1);
		assertEquals(quf.getElementsOfTree(1).size(), 2);
	}

}
