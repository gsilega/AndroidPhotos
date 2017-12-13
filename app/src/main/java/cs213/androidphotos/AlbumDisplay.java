package cs213.androidphotos;

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
import android.view.ViewGroup;
import model.Photo;
import model.User;

import java.util.List;
import java.util.ArrayList;

public class AlbumDisplay extends AppCompatActivity {
    private static Button buttonAdd;
    private static ImageView imgView;
    PhotoAdapter adapter;
    private final static int Selected_Picture = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static RecyclerView rv;
    Album p;
    public static int Current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_display);
        Current = MainActivity.CURRENTALBUM;
      //  imgView = (ImageView) findViewById(R.id.imageView1);
        buttonAdd = (Button) findViewById(R.id.buttonAddImage);
        rv = (RecyclerView) findViewById(R.id.photos);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
       // p = (Album)getIntent().getSerializableExtra("ARRAYLIST");
        p = User.getAlbumList().get(Current);
        adapter = new PhotoAdapter(this,p);
        rv.setAdapter(adapter);

      //  rvImage = (ImageView) findViewById(R.id.picturePer);
        requestPermission();
        rv.setItemAnimator(new DefaultItemAnimator());

    }
 //   @Override
 //   protected View onCreateView(LayoutInflater inflater, ViewGroup Cointainer, Bundle savedInstanceStates){
      //  View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
     //   return layout;
   // }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
           addPic();
        }
    }


    public void addPic() {

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Selected_Picture);



            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           addPic();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Selected_Picture:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnindex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnindex);
                    cursor.close();
                    Bitmap img = BitmapFactory.decodeFile(filepath);
                    Photo s = new Photo(img);
                    // need to figure out how to add images to a certain album
                    Drawable d = new BitmapDrawable(img);
                    p.addPhoto(s);
                    rv.setAdapter(adapter);
                }
                break;
            default:
                break;
        }


    }
    @Override
    public void onResume() {
        super.onResume(); // Always call the superclass method first

    }

  /*  @Override
    public void onPause() {
        super.onPause();
        int cur=  User.getAlbumNames().indexOf(p.getAlbumName());
     User.getAlbumList().remove(cur);
     User.getAlbumNames().remove(cur);
     User.addAlbum(p);
        try{
            MainActivity.write();}
        catch(IOException e){
            System.out.println("Couldnt write");
        }
    }*/

    public void onDestroy() {
        try{
            System.out.println("on destroy");
            MainActivity.write();}catch(IOException e){System.out.println("Write did not occure during destory in " +
                "Album display");}
        super.onDestroy();



    }

}
