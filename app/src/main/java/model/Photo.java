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
import java.io.ByteArrayOutputStream;
public class Photo implements Serializable{
    // TODO: UPDATE GENERATED SERIALUID AFTER PHOTO IS TOTALLY IMPLEMENTED
    private static final long serialVersionUID = 8464047780639736628L;

    private String caption;


  //  transient private ImageView imageview = new ImageView();
    private ArrayList<Tag> tagList;
    private ArrayList<String> tagListConverter;


    private transient Bitmap Bt;
    public byte[] imageByteArray;


    public Photo(Bitmap bt) {

        this.caption = caption;
       Bt = bt;
        tagList = new ArrayList<Tag>();
        tagListConverter = new ArrayList<String>();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bt.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imageByteArray = stream.toByteArray();


    }




    public ArrayList<Tag>getTags() {
        return this.tagList;
    }
    public ArrayList<String>getTagStrings() {
        return this.tagListConverter;
    }
    public String TagtoString() {
        String fin = "";
        //	System.out.println(tagList.size());
        for (Tag t: tagList) {
            String temp = t.getTagType() + " " + t.getTagValue() + "\n";
            tagListConverter.add(temp);
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
        tagListConverter.add(tag.getTagType() + " " + tag.getTagValue());


    }

    public void removeTag(Tag tag) { Tag l = new Tag("",""); boolean found = false; int count =-1;
        for (Tag g : tagList) {
            if(g.equals(tag)) {
                l = g;
                found = true;
                count++;
            }
        }
        System.out.println("Found : " + found);
        if(found==true)
        {  tagList.remove(l);
            tagListConverter.remove(count);}
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
        Bt = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        return Bt;
    }





}