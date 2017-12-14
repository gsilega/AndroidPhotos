package cs213.androidphotos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.Button;
import model.Album;
import model.*;
import android.widget.ImageView;
import android.graphics.Bitmap;

import java.io.IOException;

import model.User;

public class displaytags extends AppCompatActivity {

    private static ListView tagView;
    private static Button  delTag;
    private static Button addTag;
    private static Button slideshow;
    private static ImageView imgview;
    private static Album  currAlbumn;
    private static Photo currPhoto;
   private EditText editValue;
   private static RadioGroup rg;
   private static RadioButton rb;
    private static RadioButton rn;
    private static RadioButton rl;
   public String Type;
   private static View promptsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaytags);
        tagView = (ListView) findViewById(R.id.Tags);
        delTag = (Button) findViewById(R.id.deleteTag);
        addTag = (Button) findViewById(R.id.AddTag);
        imgview = (ImageView)findViewById(R.id.ImgForTag);
        currAlbumn = PhotoAdapter.CurrentDisplayAlbum;
       currPhoto = PhotoAdapter.CurrentImage;
        Drawable d = new BitmapDrawable(currPhoto.getBt());
        imgview.setImageDrawable(d);
        slideshow = (Button) findViewById(R.id.SlideShowbutton);
        List_View();
        CreateTag();
        Slideshow();


    }
    public void List_View(){


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tags, currPhoto.getTagStrings());
        tagView.setAdapter(adapter);
       tagView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                        String value = (String) tagView.getItemAtPosition(position);
                        Toast.makeText(displaytags.this, "Postion: " + position +
                                " Value : " + value, Toast.LENGTH_LONG).show();
                        delTag.setVisibility(View.VISIBLE);
                        DeleteMethod(position);
                    }
                }
        );delTag.setVisibility(View.GONE);
    }

    public void CreateTag(){
      addTag=  findViewById(R.id.AddTag);

       addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(displaytags.this);
           promptsView = li.inflate(R.layout.tagalert,null);
                AlertDialog.Builder a = new AlertDialog.Builder(displaytags.this);
                a.setView(promptsView);
              //  rbclick(v);
                rg = (RadioGroup)promptsView.findViewById(R.id.RadioGroup) ;
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        int rgID = rg.getCheckedRadioButtonId();
                        rb = (RadioButton)promptsView.findViewById(rgID);
                        System.out.println(rg);
                        System.out.println(rgID);
                        System.out.println(R.id.NameButton);
                        System.out.println(R.id.LocationButton);


                        if(rb.getId()==R.id.NameButton){Type = "Name";}
                        else if(rb.getId()==R.id.LocationButton) {Type = "Location";}
                        else{
                            Type = "Name"; // Default selection will be name
                        }
                    }
                    });
                editValue = (EditText) promptsView.findViewById(R.id.editTagValue) ;
                final EditText userInputValue = (EditText) promptsView
                        .findViewById(R.id.editTagValue);


                a.setCancelable(false);
                a.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                editValue.setText(userInputValue.getText());
                                String Value = editValue.getText().toString();
                                currPhoto.addTag(new Tag(Type, Value));

                                List_View();
                                // System.out.println(alb);
                                dialog.dismiss();
                            }
                        }
                );
                a.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = a.create();
                alert.setTitle("Create a Tag");
                alert.show();
                List_View();

            }
        });
    }

    public void DeleteTag(int i) {
        if(i>=0)
        {currPhoto.removeTag(currPhoto.getTags().get(i));}


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tags, currPhoto.getTagStrings());
        tagView.setAdapter(adapter);
    }
    public void DeleteMethod(int b){
        final int a =b;
        delTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteTag(a);

                List_View();

            }
        });
    }

    public void Slideshow(){
        slideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("cs213.androidphotos.SlideShowActivity");
                startActivity(intent);
    }
        });
    }

    public void onDestroy() {
        try{
            System.out.println("on destroy disyplay tags");
            MainActivity.write();}catch(IOException e){System.out.println("Write did not occure during destory in " +
                "displaytags");}
        super.onDestroy();


    }

    public static Photo getCurrPhoto(){
        return currPhoto;
    }
    public static Album getCurrAlbumn(){
        return currAlbumn;
    }

  /*  public void rbclick(View v){
        rg = (RadioGroup)promptsView.findViewById(R.id.RadioGroup) ;
        rn = (RadioButton) promptsView.findViewById(R.id.NameButton);
        rl = (RadioButton) findViewById(R.id.LocationButton);
        int rgID = rg.getCheckedRadioButtonId();
        rb = (RadioButton)promptsView.findViewById(rgID);

        String Type = "";
        if(rg.getId()==R.id.NameButton)Type = "Name";
        else if(rg.getId()==R.id.LocationButton) Type = "Location";
        else{
            Type = "Name"; // Default selection will be name
        }
    }*/

}
