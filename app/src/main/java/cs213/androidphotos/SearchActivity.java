package cs213.androidphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Build;
import model.Album;
import android.support.v4.app.ActivityCompat;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.Serializable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.view.ViewGroup;
import model.Photo;
import model.User;
import model.Tag;
import java.util.List;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
  //  private static Button buttonAdd;
   // private static ImageView imgView;
    private final static int Selected_Picture = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static RecyclerView rv;
    private static AutoCompleteTextView auto;
    private static RadioGroup rg;
    private static RadioButton rb;
    private static Button SearchButton;
   private SearchAdapter  adapter;
    public static int Current;
    private ArrayList<Photo> currPhotos;
    private ArrayList<String> tagList;
    private ArrayList<Tag> tags;
    ArrayAdapter<String> tagadpater;
    String Type;
    String Value;
    public static View promptsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rv = (RecyclerView) findViewById(R.id.SearchedPhotos);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        currPhotos = new ArrayList<Photo>();
        tagList = new ArrayList<String>();
        tags = new ArrayList<Tag>();
        adapter = new SearchAdapter(this,currPhotos);
        SearchButton = (Button)findViewById(R.id.finishSearch);
        for(int i=0; i< User.getAlbumList().size(); i++){

            for(int j=0; j< User.getAlbumList().get(i).getPhotos().size(); j++){

                Photo q = User.getAlbumList().get(i).getPhotos().get(j);
                for (Tag t: q.getTags()){
                    tagList.add(t.getTagValue());
                }
            }
        }
       tagadpater = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
               tagList);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        System.out.println("Before Search Tag");
        searchTag();

    }



    public void searchTag(){
        currPhotos.removeAll(currPhotos);

            SearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            LayoutInflater li = LayoutInflater.from(SearchActivity.this);
                            promptsView = li.inflate(R.layout.searchpopup,null);
                            AlertDialog.Builder a = new AlertDialog.Builder(SearchActivity.this);
                            a.setView(promptsView);
                            //  rbclick(v);
                           auto = (AutoCompleteTextView) promptsView.findViewById(R.id.SearchText);
                    auto.setAdapter(tagadpater);
                            rg = (RadioGroup)promptsView.findViewById(R.id.RadioGroupsearch) ;
                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                public void onCheckedChanged(RadioGroup group, int checkedId) {

                                    int rgID = rg.getCheckedRadioButtonId();
                                    rb = (RadioButton)promptsView.findViewById(rgID);
                                    System.out.println(rg);
                                    System.out.println(rgID);
                                    System.out.println(R.id.NameButton);
                                    System.out.println(R.id.LocationButton);


                                    if(rb.getId()==R.id.namesearch){Type = "Name";
                                        System.out.println("RB = namesearch");}
                                    else if(rb.getId()==R.id.Locationsearch) {Type = "Location";
                                        System.out.println("RB = locationsearch");}
                                    else{
                                        Type = "Name";
                                        System.out.println("RB = default");// Default selection will be name
                                    }
                                }
                            });

                            final AutoCompleteTextView userInputValue = (AutoCompleteTextView) promptsView
                                    .findViewById(R.id.SearchText);


                            a.setCancelable(false);
                            a.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Value = auto.getText().toString();
                                            System.out.println(" my new word variable " + Value);
                                            //        auto.setText(userInputValue.toString());
                                           // Value = auto.getText().toString();

                                             System.out.println("Why is value null ?? " + Value);
                                            dialog.dismiss();
                                            for(int i=0; i< User.getAlbumList().size(); i++){
                                                System.out.println("HERE 1 FOR");
                                                for(int j=0; j< User.getAlbumList().get(i).getPhotos().size(); j++){
                                                    System.out.println("HERE 2TH FOR");
                                                    Photo q = User.getAlbumList().get(i).getPhotos().get(j);
                                                    for (Tag t: q.getTags()){

                                                        System.out.println("Type 1 " +  t.getTagType() + " Value " + t.getTagValue());
                                                        System.out.println("Type 2 " +  Type + " Value " + Value);
                                                        if(t.getTagValue().equalsIgnoreCase(Value) && Type.equalsIgnoreCase(t.getTagType())){
                                                            currPhotos.add(q);
                                                            System.out.println("HERE $TH FOR");
                                                            //   System.out.println("HEre is the toString " +t.toString());
                                                        }
                                                    }
                                                }
                                            }
                                            rv.setAdapter(adapter);
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



                }
            });
    }




}
