package com.mediaiqdigital.bi.olap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.olap4j.OlapConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OlapConnectionFactory {

	@Bean
	public OlapConnection olapConnection(
			@Value("${olap.db.jdbc.url}") String jdbcUrl,
			@Value("${olap.db.username}") String dbUsername,
			@Value("${olap.db.password}") String dbPassword,
			@Value("${olap.db.driver}") String dbDriver,
			@Value("${olap.schema.file}") String schemaFile)
			throws ClassNotFoundException, SQLException {
		return createConnection(schemaFile, jdbcUrl, dbDriver, dbUsername,
				dbPassword);
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

}
