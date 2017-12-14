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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Photo> adapterList;
    public static Photo CurrentImage;
    public static Album CurrentDisplayAlbum;

    public SearchAdapter(Context context, ArrayList<Photo> p) {
        this.context = context;
        adapterList = p;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position ){
        Photo p = adapterList.get(position);
        Drawable d = new BitmapDrawable(p.getBt());
        holder.img.setImageDrawable(d);
        holder.setData(adapterList, position);
    }

    @Override
    public int getItemCount() {
        System.out.println(adapterList);
        return adapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView img;

        private int position;
        private ArrayList<Photo> currentObject;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.pictureFound);
        }
        public void setData(ArrayList<Photo> currentObject, int position) {
            this.position = position;
            this.currentObject = currentObject;
        }


        @Override
        public void onClick(View v) {

        }
    }





}


