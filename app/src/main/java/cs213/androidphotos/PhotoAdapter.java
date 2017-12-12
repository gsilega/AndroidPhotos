package cs213.androidphotos;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Context;
import model.Photo;
import java.util.List;
import model.Album;
import model.User;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.ListView;
import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private Context context;
    private Album adapterList;
    public static Photo CurrentImage;
    public static Album CurrentDisplayAlbum;

    public PhotoAdapter(Context context, Album adapateList) {
        this.context = context;

        this.adapterList = adapateList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position ){
        Photo p = adapterList.getPhotos().get(position);
      //  ImageView m =new ImageView;
       // m.setImageBitmap(p.getBt());
        Drawable d = new BitmapDrawable(p.getBt());
       holder.img.setImageDrawable(d);
        holder.setData(adapterList, position);
        holder.setListeners();
    }
 
    @Override
    public int getItemCount() {
        System.out.println(adapterList);
        return adapterList.getPhotos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView img;
        private Button imgDelete, imgCopy, imgDisplay;

        private int position;
        private Album currentObject;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.picturePer);
            imgDelete = (Button) itemView.findViewById(R.id.pictureDelete);
            imgCopy = (Button) itemView.findViewById(R.id.pictureCopy);
            imgDisplay = (Button) itemView.findViewById(R.id.pictureDisplay);
        }
        public void setData(Album currentObject, int position) {
            this.position = position;
            this.currentObject = currentObject;
        }

        public void setListeners() {
            imgDelete.setOnClickListener(ViewHolder.this);
            imgCopy.setOnClickListener(ViewHolder.this);
            imgDisplay.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pictureDelete:
                    removeItem(position);
                    break;

                case R.id.pictureCopy:
                { System.out.println("WIthin Copy bethod");
                    Intent intent = new Intent("cs213.androidphotos.CopyActivity");
                    v.getContext().startActivity(intent);
                    String name = CopyActivity.ReturnAlbumName();
                    CopyItem(position,
                            User.getAlbumList().get(User.getAlbumNames().indexOf(name)));
                    break;}
                case R.id.pictureDisplay:
                {DisplayItem(position, adapterList);
                    Intent intent = new Intent("cs213.androidphotos.displaytags");
                    v.getContext().startActivity(intent);

                    break;}
            }



        }
        }


    public void removeItem(int position) {
        Photo q = adapterList.getPhotos().get(position);
        adapterList.deletePhoto(q);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, adapterList.getPhotos().size());
//		notifyDataSetChanged();
    }

    public void CopyItem(int position, Album i) {

       User.getAlbumList().get(User.getAlbumList().indexOf(i)).addPhoto(adapterList.getPhotos().get(position));
            }


        public void DisplayItem(int position, Album i) {

            CurrentDisplayAlbum =User.getAlbumList().get(User.getAlbumList().indexOf(i));
            CurrentImage = i.getPhotos().get(position);

    }




}


