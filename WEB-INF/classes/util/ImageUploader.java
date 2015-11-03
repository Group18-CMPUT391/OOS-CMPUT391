package util;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import util.User;
import util.Db;
import util.Photo;
import util.ImageUploader;

import org.apache.commons.fileupload.FileItem;

public class ImageUploader{
    private Photo image;
    private FileItem file;
    private Db database = new Db();

    public ImageUploader(Photo image, FileItem file) {
	this.image = image;
	this.file = file;
    }

    public String upload_image() {
	String response_message = "";
	InputStream instream = null;
	
	BufferedImage img = null;
	try {
	    instream = file.getInputStream();
	    img = ImageIO.read(instream);
	} catch (Exception ex) {
	    response_message = ex.getMessage();
	}
	BufferedImage thumbNail = shrink(img);

	database.connect_db();

	int image_id = database.get_next_image_id();
	image.setPhotoId(image_id);
	database.addEmptyImage(image);

	Blob myImage = database.getImageById(image_id, "photo");
	Blob myThumb = database.getImageById(image_id, "thumbnail");
	
	response_message = write_image(img, myImage);
	response_message = write_image(thumbNail, myThumb);
	
	try {
	    instream.close();
	} catch (Exception ex) {
	    response_message = ex.getMessage();
	}

	response_message = " File has been Uploaded!    ";
	database.close_db();
	
	return response_message;

    }

    private BufferedImage shrink(BufferedImage image) {
	int n = 10;
	int w = image.getWidth() / n;
	int h = image.getHeight() / n;
	
	BufferedImage shrunkImage = new BufferedImage(w, h, image.getType());
	
	for (int y=0; y < h; ++y)
	    for (int x=0; x < w; ++x)
		shrunkImage.setRGB(x, y, image.getRGB(x*n, y*n));
	
	return shrunkImage;
    }

    private String write_image(BufferedImage img, Blob myImage) {
	String response_message = "";
	OutputStream outstream = null;
	try {
	    outstream = myImage.setBinaryStream(0);
	    ImageIO.write(img, "jpg", outstream);

	    /*
	     * Write image into a Blob object
	     */	
	    outstream = myImage.setBinaryStream(0);
	    outstream.close();
	} catch (Exception e) {
	    response_message = e.getMessage();
	}

	return response_message;

    }



}