package cs213.androidphotos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import model.Photo;
import java.util.List;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{
    private Context context;
    private List<Photo> adapaterList;

    public PhotoAdapter(Context context, List<Photo> adapaterList) {
        this.context = context;
        this.adapaterList = adapaterList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position ){
        Photo p = adapaterList.get(position);
      //  holder.img.setImageDrawable();
    }
 
    @Override
    public int getItemCount() {
        return adapaterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public ViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.picturePer);
        }
    }

}


