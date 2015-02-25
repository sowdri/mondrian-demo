package com.mediaiqdigital.bi.olap.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxisMetaData;
import org.olap4j.CellSetMetaData;
import org.olap4j.OlapException;
import org.olap4j.Position;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.NamedList;
import org.springframework.stereotype.Component;

import com.mediaiqdigital.bi.olap.service.CellSetToMdxResultConverter;
import com.mediaiqdigital.bi.olap.value.MdxHierarchy;
import com.mediaiqdigital.bi.olap.value.MdxResult;
import com.mediaiqdigital.bi.olap.value.PivotNode;

@Component
public class CellSetToMdxResultConverterImpl implements
		CellSetToMdxResultConverter {

	@Override
	public MdxResult convert(CellSet cellset) {

		CellSetMetaData csmd;
		try {
			csmd = cellset.getMetaData();
		} catch (OlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebApplicationException(e);
		}

		NamedList<CellSetAxisMetaData> axesMetadata = csmd.getAxesMetaData();

		// get row hierarchies
		List<Hierarchy> rowHeaders = axesMetadata.get("ROWS").getHierarchies();
		List<MdxHierarchy> rowHierarchies = new ArrayList<MdxHierarchy>();
		for (Hierarchy h : rowHeaders) {
			rowHierarchies.add(new MdxHierarchy(h.getCaption()));
		}

		// get column hierarchies
		List<Hierarchy> columnHeaders = axesMetadata.get("COLUMNS")
				.getHierarchies();
		List<MdxHierarchy> columnHierarchies = new ArrayList<MdxHierarchy>();
		for (Hierarchy h : columnHeaders) {
			columnHierarchies.add(new MdxHierarchy(h.getCaption()));
		}

		// get measures

		// get values

		// Get axes names.
		PivotNode root = new PivotNode("ROOT");
		for (Position row : cellset.getAxes().get(1)) {
			for (Position column : cellset.getAxes().get(0)) {
				PivotNode current = root;
				// traverse tree here
				for (Member member : row.getMembers()) {
					current = current.child(member.getName());
				}

				for (Member member : column.getMembers()) {
					current = current.child(member.getName());
				}

				final Cell cell = cellset.getCell(column, row);
				// System.out.println(cell.getFormattedValue());
				// System.out.println();
				current.setFormattedValue(cell.getFormattedValue());
			}
		}

		return new MdxResult(rowHierarchies, columnHierarchies, root);
	}

	private String[] splitUniqueName(String uniqueName) {
		return uniqueName.substring(1, uniqueName.length() - 1).split(
				"\\]\\.\\[");
	}
}
