package util;

import java.util.ArrayList;

public class Group {
    String name;
    int id;
    ArrayList<String> friends;
    
    /**
     * Constructor
     * @param name
     * @param id
     * @param friends
     */
    public Group(String name, int id, ArrayList<String> friends) {
	this.name = name;
	this.id = id;
	this.friends = friends;
    }
    
    /**
     * Overloaded constructor for when friends are not known
     * @param name
     * @param id
     */
    public Group(String name, int id){
	this.name = name;
	this.id = id;
    }
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public ArrayList<String> getFriends() {return friends;}
    public void setFriends(ArrayList<String> friends) {this.friends = friends;}
    
    /**
     * Add a user to the friends arraylist
     * @param user
     */
    public void addFriend(String username) {
	this.friends.add(username);
    }
    
    /**
     * Remove a user from the friends arraylist
     * @param user
     */
    public void removeFriend(String username) {
	this.friends.remove(username);
    }
}
