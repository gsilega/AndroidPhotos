package cs213.androidphotos;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import model.Photo;
import java.util.List;
import model.Album;
import android.graphics.Bitmap;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private Context context;
    private Album adapaterList;

    public PhotoAdapter(Context context, Album adapateList) {
        this.context = context;

        this.adapaterList = adapateList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position ){
        Photo p = adapaterList.getPhotos().get(position);
      //  ImageView m =new ImageView;
       // m.setImageBitmap(p.getBt());
        Drawable d = new BitmapDrawable(p.getBt());
       holder.img.setImageDrawable(d);
    }
 
    @Override
    public int getItemCount() {
        System.out.println(adapaterList);
        return adapaterList.getPhotos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.picturePer);
        }
    }

}


