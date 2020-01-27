package mn.gmobile.draw;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import mn.gmobile.draw.NewsAdapter;
import mn.gmobile.draw.NewsModel;
import mn.gmobile.draw.ProductActivity;
import mn.gmobile.draw.R;
import mn.gmobile.draw.UpointActivity;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class NewsActivity extends AppCompatActivity {

    ArrayList<NewsModel> newsModelArrayList;
    private NewsAdapter newsAdapter;
    private static ProgressDialog mProgressDialog;
    private String jsonURL1 = "https://www.mtgroup.mn/";
    private String jsonURL = "https://www.mtgroup.mn/news/jsons";
    private final int jsoncode = 1;

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    private MapboxMap mapboxMap;
    private MapView mapView;
    MapActivity mapa;
//    NewsModel newsModel;
    ArrayList<NewsModel> news1ModelArrayList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);
        //recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView = findViewById(R.id.list);
        fetchJSON();


    }

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }
    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Map();
                //loadFragment(new FirstFragment());
                Log.i("TAG", "Item 1 selected");
                break;
            case R.id.item2:
                News();
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Product();
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Feedback();
                Log.i("TAG", "Item 4 selected");
                break;
            case R.id.item5:
//                Upoint();
                openPlayStore(true, null);
                Log.i("TAG", "Item 5 selected");
                break;
        }
    }

    public void Map(){

        try{
            if (mapView == null) {
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            } else {
                mapa.onStart();
                //mapView.onResume();
                NewsActivity.this.finish();
            }
        } finally {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            //mapView.onResume();
            NewsActivity.this.finish();
        }
    }
    public void News(){

        try{
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } finally {
            NewsActivity.this.finish();
        }
    }
    public void Product(){

        try{
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
        } finally {
            NewsActivity.this.finish();
        }

    }
    public void Feedback(){

        try{
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } finally {
            NewsActivity.this.finish();
        }
    }
    public void Upoint(){

        try{
            Intent intent = new Intent(this, UpointActivity.class);
            startActivity(intent);
        } finally {
            NewsActivity.this.finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(jsonURL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }


    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response.toString());
        switch (serviceCode) {
            case jsoncode:


                removeSimpleProgressDialog();  //will remove progress dialog
                newsModelArrayList = getInfo(response);
                newsAdapter = new NewsAdapter(this, newsModelArrayList);
                //recyclerView.setAdapter(newsAdapter);

                recyclerView.setAdapter(newsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        }
    }


    public ArrayList<NewsModel> getInfo(String response) {
        //final WeakReference<MainActivity> activityRef;
        //MainActivity activity;

        //ArrayList<RogerModel> tennisModelArrayList = new ArrayList<>();
        //LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutInflater inflater = getLayoutInflater();
        final HashMap<String, View> viewMap = new HashMap<>();
        HashMap<String, Bitmap> imagesMap = new HashMap<>();
        //LayoutInflator inflater = getLayoutInflater();
        //Style style = mapboxMap.getStyle();
        ArrayList<NewsModel> news1ModelArrayList = new ArrayList<>();
        try {

//            final List<Feature> markerCoordinates = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray news = jsonObject.getJSONArray("news");

            for (int i = 0; i < news.length(); i++) {

                NewsModel newsModel = new NewsModel();
                //Feature singleLocation = jsonObject.get(i);
                JSONObject dataobj = news.getJSONObject(i);
                newsModel.setThumb(dataobj.getString("thumb"));
//                playersModel.setLatitude(dataobj.getDouble("latitude"));
                newsModel.setDescription(dataobj.getString("description"));

                newsModel.setContent(dataobj.getString("content"));
                Log.d("news","markerCoordinates"+dataobj);
                news1ModelArrayList.add(newsModel);
                //addAirplaneImageToStyle(style);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news1ModelArrayList;
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPlayStore(boolean showPublisherProfile, String publisherID) {

        //Error Handling
        if (publisherID == null || !publisherID.isEmpty()) {
            publisherID = "";
            //Log and continue
            Log.w("openPlayStore Method", "publisherID is invalid");
        }
        String packageName = "mn.unitedalliance.mobile";
        Intent openPlayStoreIntent;
        boolean isGooglePlayInstalled = false;

        if (showPublisherProfile) {
            //Open Publishers Profile on PlayStore
            openPlayStoreIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:" + publisherID));
        } else {
            //Open this App on PlayStore
            openPlayStoreIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageName));
        }

        // find all applications who can handle openPlayStoreIntent
        final List<ResolveInfo> otherApps = getPackageManager()
                .queryIntentActivities(openPlayStoreIntent, 0);
        for (ResolveInfo otherApp : otherApps) {

            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("mn.gmobile.draw")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                // make sure it does NOT open in the stack of your activity
                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                //  this make sure it still go to the app page you requested
                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                openPlayStoreIntent.setComponent(componentName);
                startActivity(openPlayStoreIntent);
                isGooglePlayInstalled = true;
                break;

            }
        }
        // if Google Play is not Installed on the device, open web browser
        if (!isGooglePlayInstalled) {

            Intent webIntent;
            if (showPublisherProfile) {
                //Open Publishers Profile on web browser
                webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            } else {
                //Open this App on web browser
                webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
            }
            startActivity(webIntent);
        }
    }

}
