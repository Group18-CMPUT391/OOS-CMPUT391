# Layout

For each required module, some sample page directories and descriptions. The 'html' extension is used as a placeholder, may be changed.

### User Management Module
Manages users logging in, logging out. Passes session variable to the security module.

* /login.html
	* Allows user to log in using username and password. Simple form for filling out required fields. Will create session for client.

* /signup.html
	* Allows user to register for an account. 
	* Must ask for:
		* unique username
		* password
		* first name
		* last name
		* address
		* email
		* phone number
* /logout.html
	* Will destroy the current session variable
* /account.html
	* Allows user to modify his or her own account information.

All other modules will assume that the user is logged in. 

### Security Module
Manages the session variable(s) and manages the photo viewing permissions. Closely tied in with the user management module.

	public
	designated group
	private

* /groups/makegroup.html
	* Allows a registered user to create a group
	* Must ask for:
		* group name
		* list of users within the group
* /groups/viewgroups.html
	* View all groups associated to the user
* groups/modifygroup.html
	* Allows a registered user to modify a group which the user owns

### Uploading Module
Manages uploading photos, storing the photos into the database.

* /photos/upload.html
	* Allows a user to upload images to the database
		* one image stored as a local file
		* all images stored in a local directory
	* After photo(s) are uploaded, following information may be modified (metadata)
		* where the image was taken
		* when the image was taken
		* image subject
		* image description
		* access permissions

### Display Module
Displays photos, allows editing of own photos (metadata).

* /
	* Will be the root directory (index.html)
	* Displays image thumbnails in a grid, according to current user permissions.
	* Will display top 5 photos guaranteed. In case of a tie, display all tied images. (gah)
* /photos/myphotos.html
	* Displays image thumbnails of the current user.
	* Allows editing the photo descriptors & permissions
	
### Search Module
Searches through all photos, displays based on permissions. Based on key words or time periods.

* /search.html
	* Handles the fetching of photos. Displays the result similar to the way the index displays the thumbnail.

Use the following ranking formula to display search results:

	Rank(photo_id) = 6 * frequency(subject) + 3 * frequency(place) + frequency(description)
	
### Data Analysis Module
Used only my the system administrator. Generate OLAP reports for analysis. Must be logged in as an administrator privlaged account.

* /admin/generalstats.html
	* Accomodate user choosing to display the number of images for each user, subject, and/or period of time.
	* Generalizes and scales for Daily/Weekly/Monthly/Yearly
* /admin/miscstats.html
	* Placeholder for other stats that may become necessary

