package util;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import oracle.sql.*;
import oracle.jdbc.*;

import util.Group;
import util.User;
import util.Photo;

public class Db {
    static final String USERNAME = "c391g10";
    static final String PASSWORD = "ravenraven1";
    // JDBC driver name and database URL
    static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = 
	"jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
    
    public Connection conn;
    public Statement stmt;
    
    public int connect_db() {
	try {
	    Class drvClass = Class.forName(DRIVER_NAME);
	    DriverManager.registerDriver((Driver) drvClass.newInstance());
	    this.conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
	    this.stmt = conn.createStatement();
	    return 1;
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
	return 0;
    }
    
    public void close_db() {
	try {
	    this.stmt.close();
	    this.conn.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Adds a group to the database
     * @param String user
     * @param String group_name
     */
    public Integer add_group(String user, String group_name) {
	String query = "insert into groups " + "values ("
	    + "group_id_sequence.nextval, '" + user + "', '" + 
	    group_name + "', sysdate)";
	return execute_update(query);
    }

    /**
     * Deletes a friend from a group
     * @param int group_id
     * @param String friend
     */
    public Integer delete_friend(int group_id, String friend) {
	String query = "delete from group_lists where group_id = " + group_id
	               + " and friend_id = '" + friend + "'";
	return execute_update(query);
    }

    /**
     * Adds a friend to a group
     * @param int group_id
     * @param String friend
     */
    public Integer add_friend(int group_id, String friend) {
	String query = "insert into group_lists values(" + group_id
	               + ", '" + friend + "', sysdate, null)";
	return execute_update(query);
    }

    /**
     * Deletes a group from the group_lists and groups tables
     * Updates images permitted = 2 where permitted = group_id
     * @param String group_id
     */
    public void delete_group(String group_id) {
	// change permissions in images
	String query_images = "update images set permitted = 2 "
	    + "where permitted = " + group_id;
	// delete group_lists where group_id == group_id
	String query_group_lists = "delete from group_lists where group_id = "
	    + group_id;
	// delete groups where group_id == group_id
	String query_groups = "delete groups where group_id = " + group_id;
	execute_update(query_images);
	execute_update(query_group_lists);
	execute_update(query_groups);
    }

    /**
     * Performs the update query execution.
     * Returns 0 when unsuccessful
     * @param query
     * @return Integer
     */
    public Integer execute_update(String query) {
	try {
	    return this.stmt.executeUpdate(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return 0;
    }
    

    /**
     * Fetches all information in database relating to username
     * @param String username
     * @returns User
     */
    public User get_user(String username) {
	ResultSet rs_users;
	ResultSet rs_persons;
	String query_users = "select * from users "
	    + "where user_name = '" + username + "'";
	String query_persons = "select * from persons "
	    + "where user_name = '" + username + "'";
	rs_users = execute_stmt(query_users);
	rs_persons = execute_stmt(query_persons);
	return user_from_resultset(rs_users, rs_persons);
    }

    /**
     * Returns the username's matching password
     * @param String username
     * @returns String password
     */
    public String get_password(String username) {
	String tPassword = "";
	String query = "select password from users where user_name = '" + 
	    username + "'";
	ResultSet rs = execute_stmt(query);
	try {
	    while(rs != null && rs.next()) {
		tPassword = (rs.getString(1)).trim();
	    }
	} catch(Exception e) {
	    e.printStackTrace();
	}
	return tPassword;
    }

    /**
     * Update's a user's password
     * Returns 0 when unsuccessful
     * @param String username
     * @param String password
     * @return int
     */
    public int updatePass(String username, String password) {
	String query = "update users set password = '"+password+
	    "' where user_name = '"+username+"'";
	return execute_update(query);
    }
    
    /**
     * Get's a user's groups
     * @param String username
     * @reutrns ArrayList<Group>
     */
    public ArrayList<Group> get_groups(String username) {
	ResultSet rs;
	String query = "SELECT group_id, group_name "
	    + "FROM groups "
	    + "WHERE user_name = '"
	    + username + "'";
	rs = execute_stmt(query);
	return group_from_resultset(rs);
    }
    
    /**
     * This function returns all the groups that the user is a
     * part of. If another person adds this user to his or her
     * group, it will appear here.
     * If a user created a group, it will also appear here.
     */
    public ArrayList<Group> getParticipantGroups(String username) {
	ResultSet rs;
	String query = "SELECT group_id, group_name FROM groups WHERE " +
	    "group_id in " +
	    "(SELECT group_id FROM group_lists where " +
	    "friend_id = '"+username+"') OR " + 
	    "user_name = '"+username+"'";
	rs = execute_stmt(query);
	return group_from_resultset(rs);
    }

    /**
     * Gets groups (and users in groups) from a resultset
     * @param rs
     * @return
     */
    public ArrayList<Group> group_from_resultset(ResultSet rs) {
	ArrayList<Group> groups;
	groups = new ArrayList<Group>();
	Group temp_group;
	
	try {
	    while (rs.next()) {
		String group_name = rs.getString("group_name");
		int group_id = rs.getInt("group_id");
		temp_group = new Group(group_name, group_id);
		groups.add(temp_group);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	
	for (Group group: groups) {
	    ArrayList<String> users;
	    users = get_users_from_group(group.getId());
	    group.setFriends(users);
	}
	return groups;
    }

    /**
     * Returns all usernames in a group
     * @param int group_id
     * @returns ArrayList<String>
     */
    public ArrayList<String> get_users_from_group(int group_id) {
	ArrayList<String> friend_ids = new ArrayList<String>();
	ResultSet rs;
	String query = "SELECT friend_id "
	    + "FROM group_lists "
	    + "WHERE group_id = "
	    + group_id;
	rs = execute_stmt(query);
	friend_ids =  user_from_resultset_group(rs);
	query = "SELECT user_name from groups where group_id = " +
	    group_id;
	rs = execute_stmt(query);
	try {
	    if (rs.next())
		friend_ids.add(rs.getString("user_name"));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return friend_ids;
    }
    
    /**
     * Returns a User object from two resultsets
     * resulting from the user and persons tables
     * @param ResultSet rs_user
     * @param ResultSet rs_person
     * @returns User
     */
    public User user_from_resultset(ResultSet rs_user, ResultSet rs_person) {
	String user_name = null;
	String password = null;
	String date = null;
	String email = null;
	String fname = null;
	String lname = null;
	String phone = null;
	String address = null;
	User user = null;
	ArrayList<Group> groups = null;

	// Get data from rs_user
	try {
	    while (rs_user.next()) { 
	        user_name = rs_user.getString("user_name");
		password = rs_user.getString("password");
		date = rs_user.getString("date_registered");
	    }
	    while (rs_person.next()) {
       		email = rs_person.getString("email");
		fname = rs_person.getString("first_name");
		lname = rs_person.getString("last_name");
		phone = rs_person.getString("phone");
		address = rs_person.getString("address");
	    }
	    groups = get_groups(user_name);
	    user = new User(user_name, email, fname, lname, phone, address,
			    groups, date);
	
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return user;
    }

    /**
     * Gets users in a group
     * @param rs
     * @return
     */
    public ArrayList<String> user_from_resultset_group(ResultSet rs) {
	ArrayList<String> all_users;
	all_users = new ArrayList<String>();
	
	try {
	    while (rs.next()) {
		String friend_id = rs.getString("friend_id");
		all_users.add(friend_id);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return all_users;
    }
    
    /**
     * 
     * @param query
     * @return
     */
    public ResultSet execute_stmt(String query) {
	try {
	    return this.stmt.executeQuery(query);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Checks if a user with specified username exists within the database
     * Returns true if username exists, otherwise returns false
     *
     * @param String username
     * @return boolean
     */
    public boolean userExists(String username) {
	String userquery = "select * from users where user_name='" + 
	    username + "'";
	ResultSet rset = execute_stmt(userquery);
	try {
	    if (!rset.next()) {
		return false;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return true;
    }
    
    /**
     * Adds a user to the database, given a specified username and password
     * @param String username, String password
     * @return Integer
     */
    public Integer addUser(String username, String password) {
	String query = "insert into users values ('" + 
	    username + "', '" + 
	    password + "', sysdate)";
	return execute_update(query);
    }


    public int get_next_image_id() {
        int pic_id = 0;
	String query = "select image_id_sequence.nextval "
	               + "from dual";
	ResultSet rset1 = execute_stmt(query);
	try {
	    rset1.next();
	    pic_id = rset1.getInt(1);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return pic_id;
    }

    /**
     * Insert a empty Blob into the database
     * @param int photo_id, String owner, int permitted, String subject, 
     * String place, String date, String desc, Blob thumbnail, Blob photo
     * @return Integer
     */
    public Integer addEmptyImage(Photo image) {
	String date;
	if (image.getDate().equals("")) {
	    date = "sysdate";
	}
	else {
	    date = "to_date('" + image.getDate() + "', 'yyyy-mm-dd')";
	}
        String query = "insert into images values (" 
	    + image.getPhotoId() + ", '"  
	    + image.getOwnerName() + "', " 
	    + image.getPermitted() + ", '" 
	    + image.getSubject() + "', '" 
	    + image.getLocation() + "', " 
	    + date + ", '"
	    + image.getDescription()
	    + "', empty_blob(), empty_blob())";
        return execute_update(query);
    }

    /**
     * 
     */
    public Blob getImageById(int image_id, String photo_thumb) {
	ResultSet rs_image;
	Blob image = null;
	String query = "SELECT * FROM images WHERE photo_id = " +
	    image_id + " FOR UPDATE";
	try {
	    rs_image = execute_stmt(query);
	    rs_image.next();
	    image = ((OracleResultSet)rs_image).getBlob(photo_thumb);
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return image;
    }
    
    /**
     * Checks if a person with a given email address already exists
     * @param String email
     * @return boolean
     */
    public boolean emailExists(String email) {
	String query = "select email from persons where email = '" + 
	    email + "'";
	ResultSet rs = execute_stmt(query);
	try {
	    return rs.next();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return true;
    }

    /**
     * Set the email of a user. Handles if does not exist.
     * @param String username, String email
     * @return Integer
     */
    public Integer setEmail(String username, String email) {
	String checkquery = "select user_name, email from persons where " + 
	    "user_name = '" + username + "'";
	ResultSet rscheck = execute_stmt(checkquery);
	try {
	    if (rscheck.next()) {
		// update the email
		String updatequery = "update persons set email = '" + email + 
		    "' where user_name = '" + username + "'";
		return execute_update(updatequery);
	    } else {
		// add a row into the database
		String addquery = "insert into persons (user_name, email)" + 
		    "values ('" + username + "', '" + email + "')";
		return execute_update(addquery);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return 0;
    }
    
    /**
     * Set the first name of a user. Row must exist prior to update.
     * @param String username, String fname
     * @return Integer
     */
    public Integer updateFname(String username, String fname) {
	String query = "update persons set first_name = '" + 
	    fname + "' where user_name = '" + username + "'";
	return execute_update(query);
    }

    /**
     * Set the last name of a user. Row must exist prior to update.
     * @param String username, String lname
     * @return Integer
     */
    public Integer updateLname(String username, String lname) {
	String query = "update persons set last_name = '" + 
	    lname + "' where user_name = '" + username + "'";
	return execute_update(query);
    }
    
    /**
     * Set the address of a user. Row must exist prior to update.
     * @param String username, String address
     * @return Integer
     */
    public Integer updateAddress(String username, String address) {
	String query = "update persons set address = '" + 
	    address + "' where user_name = '" + username + "'";
	return execute_update(query);
    }

    /**
     * Set the phone of a user. Row must exist prior to update.
     * @param String username, String phone
     * @return Integer
     */
    public Integer updatePhone(String username, String phone) {
	String query = "update persons set phone = '" + 
	    phone + "' where user_name = '" + username + "'";
	return execute_update(query);
    }

     /**
     * Fetches all photo ids, owners, and permissions
     * from the database.
     * @return ArrayList<Photo>
     */
    public ArrayList<Photo> getAllPhotos() {
	String query = "select photo_id, owner_name, permitted, subject,"+
	    " place, timing, description from images";
	ResultSet rs = execute_stmt(query);
	return photos_from_resultset(rs);
    }

    /**
     * Gets all photos from the database with specific permission 
     * from the database. If permission is set to private or group,
     * handle the username as well.
     * @return ArrayList<Photo>
     */
    public ArrayList<Photo> getAllPermissionPhotos(int permission, 
						   String username) {
	String query;
	if (permission == 2) {
	    // handle private images
	    query = "select photo_id, owner_name, permitted, subject, "+
		"place, timing, description from images where permitted = '"+
		permission +"' and owner_name = '" + username + "'";
	} else {
	    // Handle public and group images
	    query = "select photo_id, owner_name, permitted, subject,"+
		" place, timing, description from images where permitted = '"+
		permission +"'";
	}
	ResultSet rs = execute_stmt(query);
	return photos_from_resultset(rs);
    }

    /**
     * Retrieve's a photo's descriptors, returning a Photo object
     * @param int id
     * @returns Photo
     */
    public Photo getPhotoDesc(int id) {
	String query = "select photo_id, owner_name, permitted, subject, "
	                + "description, place, timing "
	                + "from images where photo_id = " + id;
	ResultSet rs = execute_stmt(query);
	return photos_from_resultset(rs).get(0);
    }

    /**
     * Converts a ResultSet into an ArrayList of photos
     * @param ResultSet
     * @returns ArrayList<Photo>
     */
    private ArrayList<Photo> photos_from_resultset(ResultSet rs) {
	ArrayList<Photo> photos = new ArrayList<Photo>();
	int photo_id;
	String owner_name;
	int permitted;
	String subject;
	String description;
	String place;
	String timing;
	ResultSetMetaData rsmd = null;
	int columnsNumber = 0;

	try {
	    rsmd = rs.getMetaData();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	try {
	    while (rs.next()) {
		photo_id = rs.getInt("photo_id");
		owner_name = rs.getString("owner_name");
		permitted = rs.getInt("permitted");
		subject = rs.getString("subject");
		description = rs.getString("description");
		place = rs.getString("place");
		timing = rs.getString("timing");		    
		Photo image = new Photo(photo_id, owner_name, timing,
					place, subject, description,
					permitted);
		photos.add(image);	
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return photos;
    }

    /**
     * Updates a photo's descriptors
     * @param Photo
     */
    public void update_photo_desc(Photo photo) {
	String date;
	if (photo.getDate().equals("")) {
	    date = "sysdate";
	}
	else {
	    date = "to_date('" + photo.getDate() + "', 'yyyy-mm-dd')";
	}

	String query = "UPDATE images SET "
	    + "permitted = " + photo.getPermitted() + ", "
	    + "subject = '" + photo.getSubject() + "', "
	    + "place = '" + photo.getLocation() + "', "
	    + "timing = " + date + ", "
	    + "description = '" + photo.getDescription().trim() + "' "
	    + "WHERE photo_id = " + photo.getPhotoId();
	execute_update(query);
    }

    /**
     * Return the result set with the single thumbnail of the photoId
     * @return ResultSet
     */
    public ResultSet getThumbnail(String photoId) {
	String query = "select thumbnail from images where photo_id = '" +
	    photoId +"'";
	return execute_stmt(query);
    }

    /**
     * Returns the resultset with the single photo of the photoId
     * @return ResultSet
     */
    public ResultSet getPhoto(String photoId) {
	String query = "select photo from images where photo_id = '" +
	    photoId + "'";
	return execute_stmt(query);
    }

    /**
     * This function is supposed to be called when an image is viewed.
     * This function returns the number of times an image has been viewed
     * if an image has never been viewed before, it will add it to the 
     * table of viewed images and increment the view count by 1.
     * @return int
     */
    public int imageCountViewInc(String photoId, String username) {
	// Check if user viewed this image before
	String query = "select user_name, photo_id from imagecount where " + 
	    "photo_id = '" + photoId + "' and user_name = '"+username+"'";
	ResultSet rset = execute_stmt(query);
	try {
	    if(!rset.next()) {
		// Add user into the added count view for this image
		query = "insert into imagecount " +
		    "values(count_id_sequence.nextval, '"+photoId+
		    "', '"+username+"')";
		execute_stmt(query);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return imageCountView(photoId);
    }

    /**
     * Returns the number of times an image is viewed, otherwise
     * returns -1.
     */
    public int imageCountView(String photoId) {
	String query = "select count(count_id) from imagecount where "+
	    "photo_id = '" + photoId + "'";
	ResultSet rset = execute_stmt(query);
	int counter = 0;
	try {
	    if(rset.next()) 
		return rset.getInt(1);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
    
    /**
     * returns the top 5 most popular photos of all time
     * if there is a tie, display all ties
     */
    public ArrayList<Photo> getTopFivePublicPhotos() {
	int counter = 0;
	int curpop = 0;
	int oldpop = 0;

	String query = "select images.photo_id, count(imagecount.count_id) "+
	    "from imagecount right join images on imagecount.photo_id = "+
	    "images.photo_id where images.permitted=1"+
	    "group by images.photo_id order by "+
	    "count(count_id) desc";
	ArrayList<Integer> result = new ArrayList<Integer>();
	ResultSet rset = execute_stmt(query);	
	try {
	    while (rset.next()) {
		int photoid = rset.getInt(1);
		curpop = rset.getInt(2);;
		if (curpop!=oldpop) {
		    oldpop = curpop;
		    counter++;
		}
		if (counter > 5)
		    break;
		result.add(photoid);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	// turn the arraylist of photo ids into photos
	ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
	for (int val : result)
	    resultPhotos.add(getPhotoDesc(val));
	return resultPhotos;
    }

    /**
     * This function takes an arraylist of photos and ranks them
     * based on the criteria given in the spec.
     * 
     * where Rank(photo_id) = 6*frequency(subject) + 3*frequency(place) +
     *      frequency(description)
     */
    public ArrayList<Photo> rankPhotos(ArrayList<Photo> inputPhotos) {
	return inputPhotos;
    }

    /**
     * Returns the resultset of the search by keywords and date
     *
     * Rank(photo_id) = 6*frequency(subject) + 3*frequency(location)
     * + frequency(description)
     *
     * @param String fromdate, String todate, String keywords, String order
     * @return ResultSet
     */
    public ResultSet getResultsByDateAndKeywords(String fromdate, 
						 String todate, 
						 String keywords,
                                                 String order) {
        String query = "SELECT score(1)*6 + score(2)*3 + score(3) AS score, "
                        + "photo_id FROM images WHERE "
                        + "((timing BETWEEN '" + fromdate + "' AND '" + todate
                        +  " ') AND (contains(subject, '"+ keywords + "', 1) > "
                        + "0) OR (contains(place, '" + keywords +"', 2) > 0) "
                        + "OR (contains(description, '" + keywords + "', 3) > "
                        + "0)) " + order;
        return execute_stmt(query);
    }

    /**
     * Returns the resultset of the search by keywords
     * @param String keywords, String order
     @ @return ResultSet
     */
    public ResultSet getResultByKeywords(String keywords, String order) {
        String query = "SELECT score(1)*6 + score(2)*3 + score(3) AS score, "
                        + "photo_id FROM images WHERE "
                        + "((contains(subject, '"+ keywords + "', 1) > "
                        + "0) OR (contains(place, '" + keywords +"', 2) > 0) "
                        + "OR (contains(description, '" + keywords + "', 3) > "
                        + "0)) " + order;
        return execute_stmt(query);
     }

    /**
     * Returns resultset of the serach by date
     * @param String fromdate, String todate, String order
     * @return ResultSet
     */
    public ResultSet getResultsByDate(String fromdate, String todate, String
                                        order) {
        String query = "SELECT timing, photo_id FROM images WHERE (timing BETWEEN '"
                      + fromdate + "' AND '" + todate + "') " + order;
        return execute_stmt(query);
    }
}
