package com.mediaiqdigital.bi.olap.value;

import java.util.HashMap;
import java.util.Map;

public class PivotNode {

	private String name;
	private Map<String, PivotNode> children = new HashMap<String, PivotNode>();
	private String formattedValue;

	public PivotNode(String name) {
		super();
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormattedValue() {
		return formattedValue;
	}

	public void setFormattedValue(String formattedValue) {
		this.formattedValue = formattedValue;
	}

	public Map<String, PivotNode> getChildren() {
		return children;
	}

	public void setChildren(Map<String, PivotNode> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void addChild(String name, PivotNode node) {
		children.put(name, node);
	}

	public PivotNode child(String name) {
		// if not present in map add
		if (!children.containsKey(name))
			addChild(name, new PivotNode(name));

		return children.get(name);
	}
}
