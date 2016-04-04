package org.ikov.client;

import org.ikov.client.cache.node.NodeCache;
import org.ikov.client.cache.node.NodeSub;
import org.ikov.client.cache.node.NodeSubList;

public final class List {

	private final NodeSub emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final NodeCache nodeCache;
	private final NodeSubList nodeSubList;

	public List(int size) {
		emptyNodeSub = new NodeSub();
		nodeSubList = new NodeSubList();
		initialCount = size;
		spaceLeft = size;
		nodeCache = new NodeCache();
	}
	
	public void insertIntoCache(NodeSub nodeSub, long l) {
		try {
			if (this.spaceLeft == 0) {
				NodeSub nodeSub_1 = this.nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();
				if (nodeSub_1 == this.emptyNodeSub) {
					NodeSub nodeSub_2 = this.nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				this.spaceLeft -= 1;
			}
			this.nodeCache.removeFromCache(nodeSub, l);
			this.nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			throw new RuntimeException();
		}
	}
	
	public NodeSub getFromCache(long l) {
		NodeSub nodeSub = (NodeSub)this.nodeCache.findNodeByID(l);
		if (nodeSub != null) {
			this.nodeSubList.insertHead(nodeSub);
		}
		return nodeSub;
	}

	public NodeSub insertFromCache(long l) {
		NodeSub nodeSub = (NodeSub) nodeCache.findNodeByID(l);

		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}

		return nodeSub;
	}

	public void removeFromCache(NodeSub nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				NodeSub nodeSub_1 = nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();

				if (nodeSub_1 == emptyNodeSub) {
					NodeSub nodeSub_2 = nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				spaceLeft--;
			}

			nodeCache.removeFromCache(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reportError("47547, " + nodeSub + ", " + l + ", " + (byte) 2 + ", " + runtimeexception.toString());
		}

		throw new RuntimeException();
	}

	void unlinkAll() {
		do {
			NodeSub nodeSub = nodeSubList.popTail();

			if (nodeSub != null) {
				nodeSub.unlink();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

}