# Ocean Observation System

Ocean Observation System is a JSP HTML5 website that is connected to Oracle sql Database. This is connected through JDBC.The webserver is connected by TomCat.

>An Ocean Observation System (OOS) is a computer information system that stores and processes different types of data for an ocean observatory. Those data are produced by a wide range of different sensors, placed at different locations. The sensor types include underwater cameras that capture images, underwater microphones that captures sound data, and a number of different sensors for scalar measurement of water temperature, oxygen concentration, salinity, conductivity, and others. Images and sound data are collected at irregular time points (when requested by a scientist or triggered by a motion sensor); scalar data is collected every hour.

- [Ocean Observation System]
	- [Modules](#modules)
		- [login module](#login-module)
		- [search module](#search-module)
		- [sensor and user management module](#)
		- [subscribe module](#)
		- [uploading module](#)
		
	- [Installation](#)
	- [Sources Used](#)

##Modules
###login module
###search module
###sensor and user management module
###subscribe module
###uploading module


## Installation
***Step 1:*** Since this site uses TomCat please place the souce code in the webbapps folder in TomCat. 

***Step 2:*** Once this is done please makes sure you edit Db.java to your database information.
````sh
oos-cmput391/WEB-INF/classes/util/Db.java
````
````java
32    static final String USERNAME = "Username";
33    static final String PASSWORD = "*****";
34    // JDBC driver name and database URL
35    static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
36    static final String DB_URL = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";
````

***Step 3:*** (Note this is optional) Edit your class path to include all of the following files:
````sh
oos-cmput391/WEB-INF/lib/servlet-api.jar
oos-cmput391/WEB-INF/lib/commons-fileupload-1.3.1.jar
oos-cmput391/WEB-INF/lib/commons-io-2.4.jar
oos-cmput391/WEB-INF/lib/ojdbc6.jar
oos-cmput391/WEB-INF/lib/thumbnailator-0.4.8.jar

````

***Step 4:*** In the classe folder run the make file to compile all the java codes. The classes folder is located in oos-cmput391/WEB-INF/classes 

````sh
make all
````
## Sources Used





Markdown is a lightweight markup language based on the formatting conventions that people naturally use in email.  As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.



### Version
3.2.0

### Tech

Dillinger uses a number of open source projects to work properly:

* [AngularJS] - HTML enhanced for web apps!
* [Ace Editor] - awesome web-based text editor
* [Marked] - a super fast port of Markdown to JavaScript
* [Twitter Bootstrap] - great UI boilerplate for modern web apps
* [node.js] - evented I/O for the backend
* [Express] - fast node.js network app framework [@tjholowaychuk]
* [Gulp] - the streaming build system
* [keymaster.js] - awesome keyboard handler lib by [@thomasfuchs]
* [jQuery] - duh

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

### Installation

You need Gulp installed globally:

```sh
$ npm i -g gulp
```

```sh
$ git clone [git-repo-url] dillinger
$ cd dillinger
$ npm i -d
$ mkdir -p downloads/files/{md,html,pdf}
$ gulp build --prod
$ NODE_ENV=production node app
```

### Plugins

Dillinger is currently extended with the following plugins

* Dropbox
* Github
* Google Drive
* OneDrive

Readmes, how to use them in your own application can be found here:

* [plugins/dropbox/README.md] [PlDb]
* [plugins/github/README.md] [PlGh]
* [plugins/googledrive/README.md] [PlGd]
* [plugins/onedrive/README.md] [PlOd]

### Development

Want to contribute? Great!

Dillinger uses Gulp + Webpack for fast developing.
Make a change in your file and instantanously see your updates!

Open your favorite Terminal and run these commands.

First Tab:
```sh
$ node app
```

Second Tab:
```sh
$ gulp watch
```

(optional) Third:
```sh
$ karma start
```

### Todos

 - Write Tests
 - Rethink Github Save
 - Add Code Comments
 - Add Night Mode

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [@thomasfuchs]: <http://twitter.com/thomasfuchs>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [marked]: <https://github.com/chjj/marked>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [keymaster.js]: <https://github.com/madrobby/keymaster>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>
   
   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]:  <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>


