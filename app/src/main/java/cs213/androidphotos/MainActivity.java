package cs213.androidphotos;

import android.content.Intent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import java.io.Serializable;


import model.Album;
import model.User;

public class MainActivity extends AppCompatActivity {
    private static Button button;
    private static Button open;
    private static Button delete;
    private static Button rename;

    private EditText addAlbum;
    private TextView trying;
    String alb;

    public static ListView albumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        albumView = (ListView) findViewById(R.id.AlbumNames);
        try{
            User.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CreateAlbum();
      //  List_View();

    }



    public void List_View(){
        open = findViewById(R.id.buttonOpen);
        rename= findViewById(R.id.buttonRename);
        delete = findViewById(R.id.buttonDelete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumView.setAdapter(adapter);
        albumView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                            String value = (String) albumView.getItemAtPosition(position);
                            Toast.makeText(MainActivity.this, "Postion: " + position +
                           " Value : " + value, Toast.LENGTH_LONG).show();
                            open.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            rename.setVisibility(View.VISIBLE);
                      //    int k=  u.getAlbumList().indexOf(value);
                                DeleteMethod(position);
                               RenameMethod(position);

                            OpenAlbum(position);

                        }
                    }
        );
        open.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        rename.setVisibility(View.GONE);

    }

    public void DeleteMethod(int b){
        final int a =b;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAlbum(a);

                List_View();

            }
        });
    }

    public void RenameMethod(final int b) {
        final int a =b;
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.renamepopup,null);
                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setView(promptsView);
                addAlbum = (EditText) promptsView.findViewById(R.id.RenameAlbumInput);
                trying = promptsView.findViewById(R.id.RenameAlbumTitle);
                System.out.println(addAlbum + "addAlbum");
                System.out.println(trying + "trying");
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.RenameAlbumInput);
                a.setMessage("Change Album Name")
                        .setCancelable(false)
                        .setPositiveButton("Add",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        addAlbum.setText(userInput.getText());
                                        alb = addAlbum.getText().toString();
                                       RenameAlbum(alb, b);

                                        for(int i=0; i<User.getAlbumNames().size(); i++){
                                            System.out.println(User.getAlbumNames().get(i));
                                        }
                                        dialog.dismiss();
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a.create();
                alert.setTitle("Change Album Name");
                alert.show();

            }
        });

    }

    public void CreateAlbum(){
        button=  findViewById(R.id.buttonCreate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.createalert,null);
                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setView(promptsView);
                addAlbum = (EditText) promptsView.findViewById(R.id.editTextAlbumInput);
                trying = promptsView.findViewById(R.id.CreateAlbumTitle);
                System.out.println(addAlbum + "addAlbum");
                System.out.println(trying + "trying");
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextAlbumInput);
                a.setMessage("Input new Album Name")
                        .setCancelable(false)
                        .setPositiveButton("Add",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               addAlbum.setText(userInput.getText());
                                alb = addAlbum.getText().toString();
                                User.addAlbum(new Album(alb));
                                List_View();
                               // System.out.println(alb);

                                for(int i=0; i<User.getAlbumNames().size(); i++){
                                    System.out.println(User.getAlbumNames().get(i));
                                }
                                dialog.dismiss();
                            }
                        }
                        )
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }) ;
                AlertDialog alert = a.create();
                alert.setTitle("Create an Album");
                alert.show();
                List_View();
                try{ User.write();}
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void DeleteAlbum(int i) {
       // System.out.println(u.getAlbumNames().get(i));
        if(i>=0)
        User.deleteAlbum(User.getAlbumList().get(i));
      //  System.out.println(u.getAlbumNames().get(i));
     //   System.out.println(u.getAlbumNames().get(i));
        try{ User.write();}
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumView.setAdapter(adapter);

    }

    public void RenameAlbum(String val, int i){
        Album b = User.getAlbumList().get(i);
        User.getAlbumList().get(i).setAlbumName(val);
        User.getAlbumNames().remove(i);
        User.getAlbumNames().add(i,val);
        try{ User.write();}
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumView.setAdapter(adapter);
    } // second dialog box

    public void OpenAlbum(int i){
        final int s =i;

        open.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("cs213.androidphotos.AlbumDisplay");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ARRAYLIST",(Serializable)User.getAlbumList().get(s));
                        intent.putExtra("BUNDLE",bundle);
                        startActivity(intent);
                    }
                }
        );


    } // new activity
}
