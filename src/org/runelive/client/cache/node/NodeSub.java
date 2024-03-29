package org.runelive.client.cache.node;

public class NodeSub extends Node {

	NodeSub nextNodeSub;
	NodeSub prevNodeSub;

	public NodeSub() {
	}

	public final void unlinkCacheable() {
		if (nextNodeSub != null) {
			nextNodeSub.prevNodeSub = prevNodeSub;
			prevNodeSub.nextNodeSub = nextNodeSub;
			prevNodeSub = null;
			nextNodeSub = null;
		}
	}

}