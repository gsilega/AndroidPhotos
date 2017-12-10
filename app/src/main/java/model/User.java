package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class User implements Serializable {
    // TODO: generate new serialVersionUID at the end for the final version of the photo album app
    private static final long serialVersionUID = -2940757050594448598L;

    private String username;

    public static ArrayList<Album> albumList = new ArrayList<Album>();
     public static ArrayList<String> albumNames = new ArrayList<String>();




    public static ArrayList<Album> getAlbumList() {
        return albumList;
    }
    public static ArrayList<String> getAlbumNames() {
        return albumNames;
    }

    public static Album getAlbum(String g) {
        return albumList.get(albumNames.indexOf(g));
    }

    public static void addAlbum(Album album) {
        albumList.add(album);
        albumNames.add(album.getAlbumName());
    }

    public static String getName(Album b) {
        return albumNames.get(albumList.indexOf(b));
    }



    public static void update(Album b) {
        int x=0;
        for (Album c : albumList) {
            if (c.getAlbumName().equals(b.getAlbumName())) {
                x = albumList.indexOf(c);
                break;
            }
        }
        albumList.remove(x);
        albumList.add(x,b);
    }

    public static void deleteAlbum(Album album) {
        if (albumNames.contains(album.getAlbumName())) {
            albumList.remove(album);
            albumNames.remove(album.getAlbumName());
        }
    }

}