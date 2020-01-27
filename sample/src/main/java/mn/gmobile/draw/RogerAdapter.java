package mn.gmobile.draw;

import android.content.Context;
import android.view.LayoutInflater;

import mn.gmobile.draw.RogerModel;

import java.util.ArrayList;

//import android.support.v7.widget.RecyclerView;

public class RogerAdapter {

    private LayoutInflater inflater;
    private ArrayList<RogerModel> rogerModelArrayList;

    public RogerAdapter(Context ctx, ArrayList<RogerModel> rogerModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.rogerModelArrayList = rogerModelArrayList;
    }

//    @Override
//    public RogerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = inflater.inflate(R.layout.activity_main, parent, false);
//        MyViewHolder holder = new MyViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RogerAdapter.MyViewHolder holder, int position) {
//
//        Picasso.get().load(rogerModelArrayList.get(position).getContent());
//        holder.content.setText(rogerModelArrayList.get(position).getContent());
//        holder.longitude.setText(rogerModelArrayList.get(position).getLongitude());
//        holder.shts_name.setText(rogerModelArrayList.get(position).getShts_name());
//        holder.latitude.setText(rogerModelArrayList.get(position).getLatitude());
//    }


    public int getItemCount() {
        return rogerModelArrayList.size();
    }


}
