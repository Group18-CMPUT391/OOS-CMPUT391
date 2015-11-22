# Ocean Observation System

Ocean Observation System is a JSP HTML5 website that is connected to Oracle sql Database. This is connected through JDBC.The webserver is connected by TomCat.

>An Ocean Observation System (OOS) is a computer information system that stores and processes different types of data for an ocean observatory. Those data are produced by a wide range of different sensors, placed at different locations. The sensor types include underwater cameras that capture images, underwater microphones that captures sound data, and a number of different sensors for scalar measurement of water temperature, oxygen concentration, salinity, conductivity, and others. Images and sound data are collected at irregular time points (when requested by a scientist or triggered by a motion sensor); scalar data is collected every hour.

- [Table of Contents]
	- [Modules](#modules)
		- [Login Module](#login-module)
		- [Search Module](#search-module)
		- [Sensor and User Management Module](#sensor-and-user-management-module)
		- [Subscribe Module](#)
		- [Uploading Module](#)
		
	- [Installation](#installation)
	- [Sources Used](#sources-used)

##Modules
###Login Module

The login module is contains two parts, the login section and personal/password change section.

In the login section the user is given inputs to input a username and password. Depending on what type the user is they are given certain areas of the website to view. For example the if the user is administrator then they are given the view to create and change users, but this view is not available to the Scientist or data manager.
###Search Module

This module allows the user to search from the database for sensors that they are subscribed to between different dates. The first field is the inputing keywords for searching audio and image description or seaching scalar values. The second is searching location of sensors. The sensor type field allow the search of different sensors. Finally the last field is setting the range of dates you are searching between. 

They can either input keyword/value, sensor type or location or all of them.

This is all done in one page, the search page.
###Sensor and User Management Module
###Subscribe Module
###Uploading Module


## Installation
***Step 1:*** Since this site uses TomCat please place the souce code in the webbapps folder in TomCat. 

***Step 2:*** Once this is done please makes sure you edit Db.java to your database information.
```sh
oos-cmput391/WEB-INF/classes/util/Db.java
```
```java
32    static final String USERNAME = "Username";
33    static final String PASSWORD = "*****";
34    // JDBC driver name and database URL
35    static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
36    static final String DB_URL = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
```

***Step 3:*** (Note this is optional) Edit your class path to include all of the following files:
```sh
oos-cmput391/WEB-INF/lib/servlet-api.jar
oos-cmput391/WEB-INF/lib/commons-fileupload-1.3.1.jar
oos-cmput391/WEB-INF/lib/commons-io-2.4.jar
oos-cmput391/WEB-INF/lib/ojdbc6.jar
oos-cmput391/WEB-INF/lib/thumbnailator-0.4.8.jar
```

***Step 4:*** In the classe folder run the make file to compile all the java codes. The classes folder is located in: 
```sh
oos-cmput391/WEB-INF/classes 
```
```sh
$ make all
```

***Step 5:*** Start Tomcat 
```sh
$ catalina start 
$ catalina stop
```
Note : You may need to run the server from the location catalina is installed and in that case you would run it. 
(to stop <kbd>ctrl</kbd>+<kbd>C</kbd> in the terminal where tomcat is and then call the shutdown)
```sh
$ ./catalina.sh start
$ ./catalina.sh stop
```
or
```sh
$ starttomcat
```

Note: Only available in the university of Alerta computer labs. (to stop <kbd>ctrl</kbd>+<kbd>C</kbd> in the terminal where tomcat is)

***Step 6:*** Now you can go to web and view the site
```sh
http://[serverURL]/oos-cmput391/
```

## Sources Used

Used the example code from class
* Audio Stream to file: https://forum.processing.org/two/discussion/4339/how-to-save-a-wav-file-using-audiosystem-and-audioinputstream-of-javasound
* Blob to byte-array: http://stackoverflow.com/questions/6662432/easiest-way-to-convert-a-blob-into-a-byte-array
* Blob to image to display on jsp: http://stackoverflow.com/questions/2438375/how-to-convert-bufferedimage-to-image-to-display-on-jsp
* Bufferedimage to blob: http://stackoverflow.com/questions/7645068/how-can-i-convert-a-bufferedimage-object-into-an-inputstream-or-a-blob
* Create thumbnail (thumbnailer lib): https://github.com/coobird/thumbnailator/wiki/Examples
* Dialog box: https://jqueryui.com/dialog/
* Files to database: http://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
* Inputstream for html file upload: http://stackoverflow.com/questions/5730532/values-of-input-text-fields-in-a-html-multipart-form
* Write text lines to Outputstream: http://www.tutorialspoint.com/java/io/outputstreamwriter_write_string.htm
