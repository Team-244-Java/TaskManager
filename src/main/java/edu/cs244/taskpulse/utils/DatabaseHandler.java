package edu.cs244.taskpulse.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHandler {
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		Properties props = new Properties();
		try (InputStream is = DatabaseHandler.class.getResourceAsStream("/config.properties")) {
			props.load(is);

			config.setJdbcUrl(props.getProperty("db.url"));
			config.setUsername(props.getProperty("db.user"));
			config.setPassword(props.getProperty("db.pass"));

			// Configuration settings for HikariCP and MySQL specifics
			config.setMaximumPoolSize(100);
			config.setAutoCommit(false);
			config.setTransactionIsolation("TRANSACTION_REPEATABLE_READ");
			config.addDataSourceProperty("cachePrepStmts", "true");
			config.addDataSourceProperty("prepStmtCacheSize", "250");
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			ds = new HikariDataSource(config);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load database configuration", e);
		}
	}

	private DatabaseHandler() {
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
