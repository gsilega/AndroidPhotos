package cs213.androidphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.Button;
import model.Album;
import model.*;
import android.widget.ImageView;
import android.graphics.Bitmap;

import java.io.IOException;


public class SlideShowActivity extends AppCompatActivity {
    private static Button buttonLeft;
    private static Button buttonRight;
    private static ImageView imgSlide;
    public static Photo p;
    public static Album a;
    public static int  currIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        buttonLeft = (Button) findViewById(R.id.leftSlideShow);
        buttonRight = (Button) findViewById(R.id.rightSlideShow);
        imgSlide = (ImageView) findViewById(R.id.ImgSlideShow);
        p = displaytags.getCurrPhoto();
        a = displaytags.getCurrAlbumn();
        display();
        move();
    }

    public void display(){
        Drawable d = new BitmapDrawable(p.getBt());
        imgSlide.setImageDrawable(d);
    }

    public void move(){
      currIndex = a.getPhotos().indexOf(p);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             currIndex--;
             if(currIndex<-(a.getPhotoCount())) {currIndex =0;}
            currIndex = Math.abs(currIndex);
                Drawable d = new BitmapDrawable(a.getPhotos().get(currIndex).getBt());
                imgSlide.setImageDrawable(d);



    }
});

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currIndex++;
                if(currIndex >(a.getPhotoCount())) {currIndex =0;}
                currIndex = Math.abs(currIndex);
                Drawable d = new BitmapDrawable(a.getPhotos().get(currIndex).getBt());
                imgSlide.setImageDrawable(d);

            }
        });
    }
}
