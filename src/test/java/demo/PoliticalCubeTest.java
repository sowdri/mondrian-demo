package demo;

import mondrian.olap.CellProperty;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.olap4j.*;
import org.olap4j.layout.CellSetFormatter;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.*;

import com.mediaiqdigital.bi.olap.Olap4jQuery;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Map;

/**
 * Tests the Olap4jQuery class.
 * 
 * @author Bill Back
 */
public class PoliticalCubeTest {

	Olap4jQuery oq;

	@Before
	public final void setup() throws Exception {
		System.out.println("Creating query object.");
		this.oq = new Olap4jQuery("jdbc:mysql://localhost:3306/political_dw",
				"./src/main/resources/political_dw.mondrian.xml");
		System.out.println("\toq ==> " + oq);
	}

	@After
	public final void teardown() throws Exception {
		System.out.println("Closing connection.");
		if (this.oq != null)
			this.oq.closeConnection();
	}

	/**
	 * Test getting cubes.
	 * 
	 * @throws Exception
	 *             Thrown if there is an error getting the cubes.
	 */
	@Test
	public void testGetCubes() throws Exception {
		System.out.println("Getting cubes...");
		NamedList<Cube> cubes = this.oq.getCubes();
		for (Cube cube : cubes) {
			System.out.println("\t" + cube.getName());
		}
	}

	/**
	 * Tests executing a query.
	 * 
	 * @throws Exception
	 *             Thrown if there is an error executing the query.
	 */
	@Test
	public void testExecuteQuery() throws Exception {
		System.out.println("Executing query...");
		CellSet cellset = this.oq
				.executeQuery("select "
						+ "{Measures.[population]} on columns, "
						+ "crossjoin({[country].children}, {[state].children}) on rows "
						+ "from [population]");

		CellSetMetaData csmd = cellset.getMetaData();
		NamedList<CellSetAxisMetaData> axesMetadata = csmd.getAxesMetaData();
		
		// Get axes names.
		List<Hierarchy> columnHeaders = axesMetadata.get("COLUMNS")
				.getHierarchies();
		for (Hierarchy h : columnHeaders) {
			System.out.println("column hierarchy ==> " + h.getCaption());
			NamedList<Level> levels = h.getLevels();
			for (Level l : levels)
				System.out.println(l.getUniqueName());
			;
		}
		List<Hierarchy> rowHeaders = axesMetadata.get("ROWS").getHierarchies();
		for (Hierarchy h : rowHeaders) {
			System.out.println("row hierarchy ==> " + h.getCaption());
		}

		for (Position row : cellset.getAxes().get(1)) {
			for (Position column : cellset.getAxes().get(0)) {
				for (Member member : row.getMembers()) {
					System.out.println(member.getUniqueName());
					for (Member m : member.getChildMembers())
						System.out.println(m.getUniqueName());
				}
				for (Member member : column.getMembers()) {
					System.out.println(member.getUniqueName());
				}
				final Cell cell = cellset.getCell(column, row);
				System.out.println(cell.getFormattedValue());
				System.out.println();
			}
		}

		System.out.println("Performing drillthrough.");

		CellSetFormatter csf = new RectangularCellSetFormatter(true);
		PrintWriter pw = new PrintWriter(System.out);
		csf.format(cellset, pw);
		pw.flush();

	}
}
