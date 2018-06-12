/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.tests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.Identity;
import fr.epita.iam.IdentityJDBCDAO;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestIdentityJDBCDAO {

	private static final Logger LOGGER = LogManager.getLogger(TestIdentityJDBCDAO.class);

	@Inject
	private DataSource ds;

	@Inject
	private IdentityJDBCDAO dao;

	@BeforeClass
	public static void globalSetup() {
		LOGGER.info("globalSetup");
	}

	@Before
	public void setUp() {
		try {
			LOGGER.info("Before method");

			final String createInstruction = "CREATE TABLE IDENTITIES\r\n" + "	(\r\n"
					+ "	IDENTITY_UID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITIES_PK PRIMARY KEY, \r\n"
					+ "	IDENTITY_DISPLAYNAME VARCHAR(255),\r\n" + "	IDENTITY_EMAIL  VARCHAR(255)\r\n" + "	)\r\n";

			final Connection connection = ds.getConnection();
			final PreparedStatement prepareStatement = connection.prepareStatement(createInstruction);
			prepareStatement.execute();
			prepareStatement.close();
			connection.close();
		} catch (final Exception e) {
			LOGGER.error("problem while initializing the database", e);
		}
	}

	@Test
	public void testCreate() {
		// GIVEN
		Assert.assertNotNull(dao);
		final Identity identity = new Identity("thomas", "tbr@tbr.com", "1234");

		// WHEN
		dao.save(identity);

		// THEN
		Connection connection = null;
		try {
			connection = ds.getConnection();
			final String verificationQuery = "select * from IDENTITIES where IDENTITY_DISPLAYNAME='thomas'";
			final PreparedStatement prepareStatement = connection.prepareStatement(verificationQuery);
			final ResultSet resultSet = prepareStatement.executeQuery();
			int i = 0;
			String string = null;
			while (resultSet.next()) {
				string = resultSet.getString("IDENTITY_DISPLAYNAME");
				i++;
			}
			Assert.assertEquals("thomas", string);
			Assert.assertEquals(1, i);
			prepareStatement.close();
			resultSet.close();
		} catch (final Exception e) {
			Assert.fail();
			LOGGER.error("problem while using the ds connection", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (final SQLException e) {
					LOGGER.error("problem while closing the ds connection", e);
				}
			}
		}
		LOGGER.info("test3");
	}

	@After
	public void tearDown() {
		// TODO delete the table

	}

}
