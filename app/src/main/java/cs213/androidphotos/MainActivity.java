package cs213.androidphotos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.content.DialogInterface;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity {
    private static Button button;
    private EditText addAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateAlbum();

    }

    public void CreateAlbum(){
        button=  findViewById(R.id.buttonCreate);
        addAlbum = findViewById(R.id.editTextAlbumInput);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.createalert,null);
                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextAlbumInput);
                a.setMessage("Input new Album Name")
                        .setCancelable(false)
                        .setPositiveButton("Add",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addAlbum.setText(userInput.getText());
                            }
                        })
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
