package com.mediaiqdigital.bi.olap.value;

import java.util.List;

public class MdxResult {

	private List<MdxHierarchy> rowHierarchies;
	private List<MdxHierarchy> columnHierarchies;
	private List<MdxHierarchy> measures;

	private PivotNode pivotNode;

	public MdxResult(List<MdxHierarchy> rowHierarchies,
			List<MdxHierarchy> columnHierarchies, PivotNode pivotNode) {
		super();
		this.rowHierarchies = rowHierarchies;
		this.columnHierarchies = columnHierarchies;
		this.pivotNode = pivotNode;
	}

	public MdxResult(PivotNode pivotNode) {
		super();
		this.pivotNode = pivotNode;
	}

	public List<MdxHierarchy> getRowHierarchies() {
		return rowHierarchies;
	}

	public void setRowHierarchies(List<MdxHierarchy> rowHierarchies) {
		this.rowHierarchies = rowHierarchies;
	}

	public List<MdxHierarchy> getColumnHierarchies() {
		return columnHierarchies;
	}

	public void setColumnHierarchies(List<MdxHierarchy> columnHierarchies) {
		this.columnHierarchies = columnHierarchies;
	}

	public List<MdxHierarchy> getMeasures() {
		return measures;
	}

	public void setMeasures(List<MdxHierarchy> measures) {
		this.measures = measures;
	}

	public PivotNode getPivotNode() {
		return pivotNode;
	}

	public void setPivotNode(PivotNode pivotNode) {
		this.pivotNode = pivotNode;
	}

}
