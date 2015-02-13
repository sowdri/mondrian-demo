package demo;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.metadata.Cube;
import org.olap4j.metadata.NamedList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Olap4jQuery {

	/** The connection to Mondrian. */
	private OlapConnection connection = null;

	/**
	 * Constructs a new olap4j connection for querying.
	 * 
	 * @param jdbcURL
	 *            URL to the database.
	 * @param schemaFile
	 *            File containing the Mondrian schema.
	 * @throws ClassNotFoundException
	 *             Thrown if the OLAP4J driver isn't found.
	 * @throws SQLException
	 *             Thrown if there is an error connecting to the database.
	 */
	public Olap4jQuery(String jdbcURL, String schemaFile)
			throws ClassNotFoundException, SQLException {

		this.connection = this.createConnection(schemaFile, jdbcURL,
				"com.mysql.jdbc.Driver", "root", "root");
	}

	/**
	 * Creates a connection to Mondrian.
	 * 
	 * @param schemaFile
	 *            File containing the schema.
	 * @param jdbcURL
	 *            URL for the database connection.
	 * @param jdbcDriver
	 *            Driver for the database.
	 * @param username
	 *            Username to connect to the database.
	 * @param password
	 *            Password for the database.
	 * @return A connection to the database.
	 * @throws ClassNotFoundException
	 *             Thrown if the OLAP4J driver isn't found.
	 * @throws SQLException
	 *             Thrown if there is an error connecting to the database.
	 */
	private synchronized OlapConnection createConnection(String schemaFile,
			String jdbcURL, String jdbcDriver, String username, String password)
			throws ClassNotFoundException, SQLException {

		Class.forName("mondrian.olap4j.MondrianOlap4jDriver");

		String cnxURL = "jdbc:mondrian:Jdbc=" + jdbcURL + ";";
		cnxURL += "JdbcDrivers=" + jdbcDriver + ";";
		
		if (username != null) {
			cnxURL += "JdbcUser=" + username + ";";
		}
		
		if (password != null) {
			cnxURL += "JdbcPassword=" + password + ";";
		}
		
		cnxURL += "Catalog=file:" + schemaFile + ";";

		System.out.println("cnxURL ==> " + cnxURL);

		Connection connection = DriverManager.getConnection(cnxURL);
		OlapConnection olapConnection = connection.unwrap(OlapConnection.class);

		return olapConnection;
	}

	/**
	 * Returns a list of all of the cubes.
	 * 
	 * @return A list of all of the cubes.
	 * @throws OlapException
	 *             Thrown if there is an error getting the cubes.
	 */
	public NamedList<Cube> getCubes() throws OlapException {
		NamedList<Cube> cubes = connection.getOlapSchema().getCubes();
		return cubes;
	}

	/**
	 * Executes an MDX query.
	 * 
	 * @param mdx
	 *            The MDX query to execute.
	 * @return The results of the query.
	 * @throws Exception
	 *             thrown if there is an error executing the call.
	 */
	public CellSet executeQuery(String mdx) throws Exception {
		System.out.println(mdx);

		CellSet cellSet = this.connection.createStatement().executeOlapQuery(
				mdx);
		return cellSet;
	}

	/**
	 * Closes the connection to the database.
	 * 
	 * @throws SQLException
	 *             Thrown if there is an error closing the connection.
	 */
	public void closeConnection() throws SQLException {
		this.connection.close();
	}
}
