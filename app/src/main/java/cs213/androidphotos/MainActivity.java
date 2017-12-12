package cs213.androidphotos;

import android.content.Intent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import android.os.Environment;

import model.Album;
import model.Photo;
import model.User;

public class MainActivity extends AppCompatActivity {
    private static Button button;
    private static Button open;
    private static Button delete;
    private static Button rename;
    public static final String FILE_NAME = "serial.dat";
    private EditText addAlbum;
    private TextView trying;
    String alb;
    public static int CURRENTALBUM;

    public static ListView albumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        albumView = (ListView) findViewById(R.id.AlbumNames);
        try{
           read();
        } catch (IOException e) {
            System.out.println("NOt readingfrom on create " );
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CreateAlbum();
      //  List_View();

    }

    private static File getFilePath() {

        File f = new File(Environment.getExternalStorageDirectory() + "/" + FILE_NAME);
        return f;
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
                                try{ write();}
                                catch (IOException e) {
                                    System.out.println("NOt writing from ADD");
                                }
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
                try{ write();}
                catch (IOException e) {
                    System.out.println("NOt writing from ADD");
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
        try{ write();}
        catch (IOException e) {
            System.out.println("NOt writing from delete");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumView.setAdapter(adapter);

    }

    public void RenameAlbum(String val, int i){
        Album b = User.getAlbumList().get(i);
        User.getAlbumList().get(i).setAlbumName(val);
        User.getAlbumNames().remove(i);
        User.getAlbumNames().add(i,val);
        try{ write();}
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("NOt writing from rename");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumView.setAdapter(adapter);
    } // second dialog box

    public void OpenAlbum(int i){
        final int s =i;
        setCURRENTALBUM(i);
        open.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("cs213.androidphotos.AlbumDisplay");
                        System.out.println("IS IT NULL "+ User.getAlbumList().get(s));
                        startActivity(intent);
                    }
                }
        );


    }
    public static void read() throws IOException, ClassNotFoundException {
        try{

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath()));
       User.albumList = (ArrayList<Album>) ois.readObject();

        for(Album z : User.albumList) {
            User.albumNames.add(z.getAlbumName());
            System.out.println(z.getAlbumName());
            ArrayList<Photo> addList = new ArrayList<Photo>();
            ArrayList<Photo> removeList = new ArrayList<Photo>();
            for (Photo p : z.getPhotos()) {
                Photo temp = new Photo(p.getBt());
                temp.setTags(p.getTags());
                addList.add(temp);
                removeList.add(p);
            }
            z.getPhotos().removeAll(removeList);
            z.getPhotos().addAll(addList);
        }

        ois.close();
        //  System.out.println(UserNames());
			/*   for (User u : UserList) {
				   System.out.println(" Album list " + u.getAlbumNames());
			   }*/
    }
        catch(IOException e){
            System.out.println("File Not Found");
        }
    }

    public static void write() throws IOException {
        try{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath()));
        System.out.println("file path " + getFilePath());
        oos.writeObject(User.albumList);
        oos.close();
    }catch(IOException e){
        System.out.println("Not writing to file");
            System.out.println("file path " + getFilePath());
    }}

    public static int setCURRENTALBUM(int c){
        CURRENTALBUM =c;
        return CURRENTALBUM;
    }
}
