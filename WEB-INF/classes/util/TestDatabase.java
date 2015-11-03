/**
 * To run test, you MUST enter your Oracle username and password in the setUp method.
 */

package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import userManagement.Group;
import userManagement.User;

public class TestDatabase extends TestCase{
	private Db db;
	private User user;
	private String username;
	private String password;
	private Group group;
	
	protected void setUp() {
		this.db = new Db();
		this.username = "mnaylor";
		this.password = "ravenraven1";
		this.user = new User("mnaylor");
		this.group = new Group("friends", 3);
		db.connect_db(this.username, this.password);
	}
	
	protected void tearDown() {
		db.close_db();
	}
	
	@Test
	public void test_execute_stmt() throws SQLException{
		String query = "SELECT COUNT(*) FROM users " +
						"WHERE user_name IN ('mnaylor', 'awong', 'hhoang')";
		ResultSet rs = db.execute_stmt(query);
		rs.next();
		assertSame(rs.getInt(1), 3);
	}
	
	@Test
	public void test_get_groups() throws SQLException {
		ArrayList<Group> groups;
		groups = db.get_groups(user);
		assertEquals(groups.size(), 1);
		assertEquals(groups.get(0).getId(), 3);
	}
	
	@Test
	public void test_get_users_from_group() {
		ArrayList<User> users;
		users = db.get_users_from_group(group);
		assertEquals(users.size(), 2);
		assertEquals(users.get(0).getUsername(), "awong");
	}
	
    public static void main(String[] args) {
        junit.textui.TestRunner.run(
            TestDatabase.class);
    }
}
