# Very hacked together make file for java

# define a custom class path to use
custclass = -classpath ../lib/ojdbc6.jar:../lib/servlet-api-2.3.jar:../lib/commons-fileupload-1.2.2.jar 
# define a custom source path to use
custsource = -sourcepath ./:util 

all: 
	make adminStats
	make login
	make register
	make manage_Groups
	make uploadImage
	make uploadFolder
	make manageUser
	make getUserInfo
	make searchResults
	make browsePicture
	make browsePictures
	make folderDesc
	make imageDesc
adminStats:
	javac $(custsource) $(custclass) adminStats.java
login:
	javac $(custsource) $(custclass) loginservlet.java
register:
	javac $(custsource) $(custclass) registerservlet.java
manage_Groups:
	javac $(custsource) $(custclass) manage_Groups.java
uploadImage:
	javac $(custsource) $(custclass) uploadImage.java
uploadFolder:
	javac $(custsource) $(custclass) uploadFolder.java
manageUser:
	javac $(custsource) $(custclass) manageUser.java
getUserInfo:
	javac $(custsource) $(custclass) getUserInfo.java
searchResults:
	javac $(custsource) $(custclass) searchResults.java
browsePictures:
	javac $(custsource) $(custclass) browsePictures.java
browsePicture:
	javac $(custsource) $(custclass) browsePicture.java
folderDesc:
	javac $(custsource) $(custclass) folderDesc.java
imageDesc:
	javac $(custsource) $(custclass) imageDesc.java

clean:
	rm -rf *.class
	rm -rf ./util/*.class
