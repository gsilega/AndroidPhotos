package model;

import java.io.Serializable;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.Image;
import android.widget.ImageView;

public class Photo implements Serializable{
    // TODO: UPDATE GENERATED SERIALUID AFTER PHOTO IS TOTALLY IMPLEMENTED
    private static final long serialVersionUID = 8464047780639736628L;

    private String caption;

  //  transient private ImageView imageview = new ImageView();
    private ArrayList<Tag> tagList;




    private Bitmap Bt;


    public Photo(Bitmap bt) {

        this.caption = caption;
       Bt = bt;
        tagList = new ArrayList<Tag>();

    }




    public ArrayList<Tag>getTags() {
        return this.tagList;
    }

    public String TagtoString() {
        String fin = "";
        //	System.out.println(tagList.size());
        for (Tag t: tagList) {
            //System.out.println(t);
            fin+=t.getTagType() + " " + t.getTagValue() + "\n";
        }
        System.out.println(fin);
        return fin;

    }

    public void setTags(ArrayList<Tag> tags) {
        tagList = tags;
    }

    public void addTag(Tag tag) {
        for (Tag t: tagList) {
            if(t.equals(tag)) return;
        }
        tagList.add(tag);
    }

    public void removeTag(Tag tag) { Tag l = new Tag("",""); boolean found = false;
        for (Tag g : tagList) {
            if(g.equals(tag)) {
                l = g;
                found = true;
            }
        }
        System.out.println("Found : " + found);
        if(found==true)
            tagList.remove(l);
        else return;
    }





    public static Date getDateFromString(String date) throws ParseException {
        if (isValidFormat(date)) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
            Date d = dateFormatter.parse(date);
            return d;
        }
        else return null;
    }

    public static boolean isValidFormat(String value) { // Use for search Date Method
        Date date = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
        try {

            date = dateFormatter.parse(value);
            if (!value.equals(dateFormatter.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }




    public void setBt(Bitmap b){
        Bt = b;
    }

    public Bitmap getBt(){
        return Bt;
    }

}