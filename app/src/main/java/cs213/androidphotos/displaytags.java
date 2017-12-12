package cs213.androidphotos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.graphics.Bitmap;

import java.io.IOException;

import model.User;

public class displaytags extends AppCompatActivity {

    private static ListView tagView;
    private static Button  delTag;
    private static Button addTag;
    private static Album  currAlbumn;
    private static Photo currPhoto;
   private EditText editType;
   private EditText editValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaytags);
        tagView = (ListView) findViewById(R.id.Tags);
        delTag = (Button) findViewById(R.id.deleteTag);
        addTag = (Button) findViewById(R.id.AddTag);
        currAlbumn = PhotoAdapter.CurrentDisplayAlbum;
        currPhoto = PhotoAdapter.CurrentImage;
        CreateTag();
        List_View();

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
                View promptsView = li.inflate(R.layout.tagalert,null);
                AlertDialog.Builder a = new AlertDialog.Builder(displaytags.this);
                a.setView(promptsView);
                editType = (EditText) promptsView.findViewById(R.id.editTagType) ;
                editValue = (EditText) promptsView.findViewById(R.id.editTagValue) ;
                final EditText userInputType = (EditText) promptsView
                        .findViewById(R.id.editTagType);
                final EditText userInputValue = (EditText) promptsView
                        .findViewById(R.id.editTagValue);


                a.setCancelable(false);
                a.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println(editType + " editType");
                                System.out.println("userInputType" + userInputType);
                                editType.setText(userInputType.getText());
                                String Type = editType.getText().toString();
                                editValue.setText(userInputValue.getText());
                                String Value = editValue.getText().toString();
                                currPhoto.addTag(new Tag(Type, Value));
                                List_View();
                                for (int i =0; i <currPhoto.getTags().size(); i++){
                                    System.out.println(currPhoto.getTagStrings().get(i));
                                }
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

    public void DeletePhoto(int i) {
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
                DeletePhoto(a);

                List_View();

            }
        });
    }

}
