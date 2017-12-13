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
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import model.User;

public class CopyActivity extends AppCompatActivity {
private static ListView albumViewCopy;
private static int  CurrCopy;
private static String CurrAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy);
        albumViewCopy = (ListView) findViewById(R.id.AlbumCopyNames);
        List_View();
    }

    public void List_View(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.album_names, User.getAlbumNames());
        albumViewCopy.setAdapter(adapter);
        albumViewCopy.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                        CurrCopy = position;
                        String value = (String) albumViewCopy.getItemAtPosition(position);
                        Toast.makeText(CopyActivity.this, "Postion: " + position +
                                " Value : " + value, Toast.LENGTH_LONG).show();;
                        LayoutInflater li = LayoutInflater.from(CopyActivity.this);
                        View promptsView = li.inflate(R.layout.renamepopup,null);
                        AlertDialog.Builder a = new AlertDialog.Builder(CopyActivity.this);
                        a.setView(promptsView);
                        a.setMessage("Sure you want to copy photo to this Album?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ReturnAlbumName();

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
                }
        );


    }

    public static String ReturnAlbumName(){
        CurrAlbum = User.getAlbumNames().get(CurrCopy);
        return CurrAlbum;
    }

    public void onDestroy() {
        try{
            System.out.println("on destroy");
            MainActivity.write();}catch(IOException e){System.out.println("Write did not occure during destory in " +
                "copy Activty");}
        super.onDestroy();



    }

}
