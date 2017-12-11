package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.Photo;


public class Album implements Serializable{
    // TODO: Compute serial version UID for final version of photo album app
    private static final long serialVersionUID = 5035981049716162638L;

    private String albumName;

    private int PhotoCount;

    private Calendar EarliestDate;
    private Calendar LatestDate;

    private ArrayList<Photo> photoList;

    public Album(String albumName) {
        this.albumName = albumName;
        photoList = new ArrayList<Photo>();
        setPhotoCount(0);
    }
    /**
     * returns the name of the album
     * @return
     */
    public String getAlbumName() {
        return this.albumName;
    }
    public int getPhotoCount() {
        return photoList.size();
    }
    /**
     * sets the name of the album to newName
     * @param newName
     */
    public void setAlbumName(String newName) {
        this.albumName = newName;
    }

    /**
     * returns the list of photos contained in this album
     * @return
     */
    public ArrayList<Photo> getPhotos() {
        return this.photoList;
    }
    /**
     * adds the given Photo object to this album
     * @param photo
     */
    public void addPhoto(Photo photo) {
        this.photoList.add(photo);
        setPhotoCount(getPhotoCount() + 1);
    }
    /**
     * deletes the given Photo object from this album, if the album contains it
     * @param photo
     */
    public void deletePhoto(Photo photo) {
        if (photoList.contains(photo)) {
            photoList.remove(photo);
            setPhotoCount(getPhotoCount() - 1);
        }
    }


    public void setPhotoCount(int photoCount) {
        PhotoCount = photoCount;
    }
    public void setEarliestDate(Calendar earliestDate) {
        EarliestDate = earliestDate;
    }

    public void setLatestDate(Calendar earliestDate) {
        LatestDate = earliestDate;
    }

    public Photo getPhoto(Photo photo) {
        return this.getPhotos().get(this.getPhotos().indexOf(photo));


    }
   // public int describeContents() {return 0;}
}

