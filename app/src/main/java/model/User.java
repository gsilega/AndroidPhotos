package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    // TODO: generate new serialVersionUID at the end for the final version of the photo album app
    private static final long serialVersionUID = -2940757050594448598L;

    private String username;

    private ArrayList<Album> albumList;
    private ArrayList<String> albumNames;


    public User(String username) {
        this.username = username;
        this.albumList = new ArrayList<Album>();
        this.albumNames = new ArrayList<String>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }


    public ArrayList<Album> getAlbumList() {
        return this.albumList;
    }
    public ArrayList<String> getAlbumNames() {
        return this.albumNames;
    }

    public Album getAlbum(String g) {
        return albumList.get(albumNames.indexOf(g));
    }

    public void addAlbum(Album album) {
        this.albumList.add(album);
        this.albumNames.add(album.getAlbumName());
    }

    public String getName(Album b) {
        return albumNames.get(albumList.indexOf(b));
    }



    public void update(Album b) {
        int x=0;
        for (Album c : this.albumList) {
            if (c.getAlbumName().equals(b.getAlbumName())) {
                x = albumList.indexOf(c);
                break;
            }
        }
        albumList.remove(x);
        albumList.add(x,b);
    }

    public void deleteAlbum(Album album) {
        if (this.albumList.contains(album)) {
            this.albumList.remove(album);
        }
    }
}