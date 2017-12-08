package cs213.androidphotos;

import android.content.Intent;
        import java.io.BufferedReader;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


public class AlbumDisplay extends AppCompatActivity {
    private static Button buttonAdd;
    private static ImageView imgView;
    private final static int Selected_Picture = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_display);

        imgView = (ImageView) findViewById(R.id.imageView1);
        buttonAdd = (Button) findViewById(R.id.buttonAddImage);
        addPic();

    }

    public void addPic(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Selected_Picture);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case Selected_Picture:
                if(resultCode== RESULT_OK){
                    Uri uri = data.getData();
                    String [] projection =  {MediaStore.Images.Media.DATA};
                    Cursor cursor =  getContentResolver().query(uri, projection,null,null,null);
cursor.moveToFirst();
int columnindex = cursor.getColumnIndex(projection[0]);
String filepath = cursor.getString(columnindex);
cursor.close();
Bitmap img = BitmapFactory.decodeFile(filepath);
                    Drawable d = new BitmapDrawable(img);
                    imgView.setBackground(d);
                }
                break;
            default: break;
        }


    }
}
