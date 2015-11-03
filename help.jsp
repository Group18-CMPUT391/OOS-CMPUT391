<html>
    <head>
        <title>Help</title>
    <jsp:include page="includes/header.jsp"/>
    </head>
    <body>

    <h1>Table of Contents</h1>
    <a href="#install">Installation Instructions</a><br>
    <a href="#user">User Management</a><br>
    <a href="#security">Setting Photo Permissions</a><br>
    <a href="#upload">Uploading Photos</a><br>
    <a href="#display">Displaying Photos</a><br>
    <a href="#search">Searching for Photos</a><br>
    <a href="#data">Data Analysis</a><br>

    <h1 id="install">Installation Instructions</h1>
    <ol><li>Download the program's tar file.</li>
        <li>Untar the file in the /catalina/webapps directory.  You
        should now have a directory called c391proj.</li>
	<li>$ cd /path/to/catalina/webapps/c391proj/WEB-INF/classes</li>
	<li>$ make all</li>
	<li>$ starttomcat</li>
	<li>Open a browser (preferably one with html5 support) and
	navigate to
	http://ui???.cs.ualberta.ca:<your-first-port-number>/c391proj</li>
	<li>Happy browsing!</li>
    </ol>
    <h1 id="user">User Management</h1>
        <h3>Login</h3>
	  <p>To login, you must first be registered.  You must
	  also enter a valid username and password combination.  If an
	  incorrect combination is entered, you will be prompted
	  to try again.</p>
	<h3>Register</h3>
	  <p>To register, you must enter a unique username and email.
	  If the entered username and/or email is already in use, you
	  will be prompted to try again.  You must also enter your
	  name, phone number, address, and phone number, along with a
	  password.</p>
	<h3>Logout</h3>
	  <p>You may logout of the system by clicking the "logout"
	  link at the top of the page.</p>
    <h1 id="security">Setting Photo Permissions</h1>

    	<h3>Manage Groups</h3> 
	<p>Your groups are used to define which users will have access
	to your photos, on a photo-by-photo basis.  Click on the
	"manage groups" link at the top of the page to manage your
	groups.</p>
	<p>To add a new group, type the group's name into the "New
	Group" box.  Once a group exists, you may add users to it by
	typing the user's username in the "New Friend" box.  If the
	username does not exist, you will be prompted to try
	again.</p>
	<p>To delete a group or a user, click the checkbox next to its
	name.</p>
	<p>Click the "Submit" button for the changes to take effect.</p>

	<h3>Setting a Photo's Security</h3>
	<p>When deciding on who may see your photo, you have three options:
	<ol>
	<li>Private: Only you may see the photo</li>
	<li>Public: Everyone may see the photo</li>
	<li>Group: You may select one group.  Only the users in that
	group will be able to see your photo</li>
	</ol>
	If you fail to choose a security setting, the private setting
	will be selected for you.
	</p>

	<p>You may set a photo's security when it is <a
	href="#upload">uploaded</a>.  You may alter a photo's security
	on the <a href=#display>Browse Pictures</a> page.</p>

    <h1 id="upload">Uploading Files</h1>
        <h3>Individual Files</h3>
	  <p>To upload a photo, you must select the file.  You may
	  enter any information you wish in the subsequent fields.
	  The current date will be used by default, and the security
	  will default to private.</p>
	  <p>If you fail to select a file, you will be prompted to try
	  again.</p>
	<h3>Folder of files</h3>

	<p>To upload a folder of photos, you must select the folder
	using the jUpload applet.  Click the applet's "Upload" button
	to load the photos. You may enter any information you wish in
	the subsequent fields.  The current date will be used by
	default, and the security will default to private.</p>

	<p>If you fail to select a file, you will be prompted to try
	again.</p>

	<p><b>Note: The photos are not fully uploaded to the
	database until the "Upload" button at the bottom of the page
	is clicked.</b></p>

    <h1 id="display">Displaying Photos</h1>
        <h3>Browse Photos</h3>

	<p>You may browse all photos available to you by clicking the
	Browse "Photos" link at the top of the page.</p>
	<p>You must choose to browse through:
	<ul>
	<li>Public: All public photos</li>
	<li>Public Top 5: The 5 most viewed public photos</li>
	<li>Private photos: The photos you designated as private</li>
	<li>Group photos: The groups to which you belong</li>
	</ul></p>

	<h3>Update Descriptors</h3>

	<p>You may update your photo's descriptors, including
	security, by clicking the "Edit Image Description" link to the
	right of the photo's thumbnail.  Clicking the "Upload" button
	will submit your changes.</p>

	<h3>Full Size Photo</h3>
	<p>Clicking an image's thumbnail will display a full-size photo.</p>

    <h1 id="search">Searching for Photos</h1>

        <p>You may search through photos by clicking the "search" link
        at the top of the page.  A search may be performed by typing
        in any number of keywords and/or providing a date range.</p>

    	<h3>Keywords</h3>

	<p>Searching with keyword(s) will limit the search results to
	photos containing the keyword(s) in their Title, Subject, or
	Description.</p>

	<h3>Date</h3>

	<p>Selecting a date range will limit the search results to
	photos with a date set to the selected range.</p>

    <h1 id="data">Data Analysis</h1>

    <p>The admin user is able to access all photos in the database,
    and is able to access the photo analytics.  You may log in as the
    admin user with the password "admin".</p>

    <p>To view data information, you must first choose a data
    timeframe - daily, weekly, monthly, or yearly.  The following data
    summary items will then be displayed for the selected time breakdowns:
    <ul>
    <li>Users registered</li>
    <li>Images uploaded</li>
    <li>Image description word count</li>
    </ul>

    Below the summary, you will find a listing of changes by time
    period.</p>

    </body>
</html>
