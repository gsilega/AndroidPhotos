package cs213.androidphotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;

import model.Album;
import model.User;

public class MainActivity extends AppCompatActivity {
    private static Button button;
    private EditText addAlbum;
    private TextView trying;
    String alb;
    public static User u  = new User("Main");
    public static ListView albumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        albumView = (ListView) findViewById(R.id.AlbumNames);
       List_View();
        CreateAlbum();

    }

    public void List_View(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, u.getAlbumNames());
        albumView.setAdapter(adapter);
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
                                u.addAlbum(new Album(alb));
                                List_View();
                               // System.out.println(alb);

                                for(int i=0; i<u.getAlbumNames().size(); i++){
                                    System.out.println(u.getAlbumNames().get(i));
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

            }
        });
    }
}
