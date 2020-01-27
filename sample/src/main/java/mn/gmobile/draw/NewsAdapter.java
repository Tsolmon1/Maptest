package mn.gmobile.draw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
//import coil.api.ImageLoaders;
//import coil.request.LoadRequest;
import mn.gmobile.draw.NewsModel;
import mn.gmobile.draw.R;
//import coil.ImageLoader;
//import coil.request.GetRequestBuilder;
//import coil.request.LoadRequestBuilder;
//import coil.request.RequestDisposable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<NewsModel> newsModelArrayList;
    private Context mContext;
    //public String URL;
    public NewsAdapter(Context ctx, ArrayList<NewsModel> newsModelArrayList){
        mContext = ctx;
        inflater = LayoutInflater.from(ctx);
        this.newsModelArrayList = newsModelArrayList;
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyViewHolder holder, int position) {

        //String url = "http://mtgroup.mn/";
        NewsModel item = newsModelArrayList.get(position);
//        Picasso.get().load("http://mtgroup.mn"+newsModelArrayList.get(position).getThumb()).into(holder.thumb);
        //Picasso.get().load(url+item.getThumb()).into(holder.thumb);
        //Glide.with(mContext).load(url+item.getThumb()).thumbnail(0.5f).into(holder.thumb);
        try {
            String url = "http://mtgroup.mn/" /* URL of Image */;

            if (url.startsWith("http://"))
                url = url.replace("http://", "https://");

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.placeholder);
            requestOptions.error(R.drawable.placeholder);
            //URL = item.getContent();
            Glide
                    .with(mContext)
                    .setDefaultRequestOptions(requestOptions)
                    .load(url+item.getThumb())
                    .into(holder.thumb);

            holder.thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NewsModel item1 = newsModelArrayList.get(position);
                    //Bundle extras = new Bundle();
                    Context mContext = v.getContext();
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    String strName = "hi";
                    //intent.putExtra("hi", getAdapterPosition());
                    intent.putExtra(DetailActivity.EXTRA_POSITION, item.getContent());
                    //extras.putSerializable("KEY", item1.getContent());
                    //intent.putExtras(extras);
                    //intent.putExtra(DetailActivity.EXTRA_POSITION, item.getContent());
                    //int position = intent.getIntExtra("hi", 0);
                    //NewsModel item = newsModelArrayList.get(position);
                    //Log.d("con","content"+ position);
                    //intent.putExtra(DetailActivity.EXTRA_POSITION, item.getContent());

                    mContext.startActivity(intent);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        //holder.thumb.load("https://www.example.com/image.jpg");
        //Glide.with(con).load("http://goo.gl/gEgYUd").into(holder.thumb);
       // holder.thumb.setImageBitmap(getBitmapFromURL("http://mtgroup.mn/uploads/content/a772d12d9ba36bf47be3e81bf6ffdd527c79caee.jpg"));
        Log.d("newwwss","markerCoordinates"+ item.getThumb());
        Log.d("con","content"+ item.getContent());
        holder.description.setText(newsModelArrayList.get(position).getDescription());


        //Log.d("newwwss","markerCoordinates"+ url+item.getThumb());
//        URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        imageView.setImageBitmap(bmp);
//        holder.content.setText(newsModelArrayList.get(position).getContent());

    }



    public int getItemCount() {
        return newsModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView thumb;
        TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.grid_item_image);
            description = (TextView) itemView.findViewById(R.id.description);

//            content = (TextView) itemView.findViewById(R.id.content);

        }

    }

}
