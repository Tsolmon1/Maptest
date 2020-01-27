package mn.gmobile.draw;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
//    @BindView(R.id.tapBarMenu)
//    TapBarMenu tapBarMenu;

    private MapboxMap mapboxMap;
    private MapView mapView;
    MapActivity mapa;
    private LayoutInflater inflater;
  //  private ArrayList<NewsModel> newsModelArrayList;
    private Context mContext;
    private ImageButton button;
//
//    public DetailActivity(Context ctx, ArrayList<NewsModel> newsModelArrayList){
//        mContext = ctx;
//        inflater = LayoutInflater.from(ctx);
//        this.newsModelArrayList = newsModelArrayList;
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //ArrayList<NewsModel> newsModelArrayList = new ArrayList<NewsModel>();
        ArrayList<NewsModel> news1ModelArrayList = new ArrayList<>();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen

        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        //String url = "http://mtgroup.mn/" /* URL of Image */;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //String url = intent.getStringExtra(EXTRA_POSITION);
        //DetailActivity myData = (DetailActivity) extras.getSerializable(EXTRA_POSITION);
        //String url = extras.getString(EXTRA_POSITION);
        String position = intent.getStringExtra(EXTRA_POSITION);
        Log.d("pos","hi"+position);
        //NewsModel item = news1ModelArrayList.get(position);
        //Log.d("pos","url"+item.getContent());
        Resources resources = getResources();
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle("Мэдээлэл");
//        button = (ImageButton) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                finish();
//
//            }
//        });

    }

//    @OnClick(R.id.tapBarMenu)
//    public void onMenuButtonClick() {
//        tapBarMenu.toggle();
//    }
//    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5 })
//    public void onMenuItemClick(View view) {
//        tapBarMenu.close();
//        switch (view.getId()) {
//            case R.id.item1:
//                Map();
//                //loadFragment(new FirstFragment());
//                Log.i("TAG", "Item 1 selected");
//                break;
//            case R.id.item2:
//                News();
//                Log.i("TAG", "Item 2 selected");
//                break;
//            case R.id.item3:
//                Product();
//                Log.i("TAG", "Item 3 selected");
//                break;
//            case R.id.item4:
//                Feedback();
//                Log.i("TAG", "Item 4 selected");
//                break;
//            case R.id.item5:
////                Upoint();
//                openPlayStore(true, null);
//                Log.i("TAG", "Item 5 selected");
//                break;
//        }
//    }
//
//    public void Map(){
//
//        try{
//            if (mapView == null) {
//                Intent intent = new Intent(this, MapActivity.class);
//                startActivity(intent);
//            } else {
//                mapa.onStart();
//                //mapView.onResume();
//                DetailActivity.this.finish();
//            }
//        } finally {
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//            //mapView.onResume();
//            DetailActivity.this.finish();
//        }
//    }
//    public void News(){
//
//        try{
//            Intent intent = new Intent(this, NewsActivity.class);
//            startActivity(intent);
//        } finally {
//            DetailActivity.this.finish();
//        }
//    }
//    public void Product(){
//
//        try{
//            Intent intent = new Intent(this, ProductActivity.class);
//            startActivity(intent);
//        } finally {
//            DetailActivity.this.finish();
//        }
//
//    }
//    public void Feedback(){
//
//        try{
//            Intent intent = new Intent(this, FeedbackActivity.class);
//            startActivity(intent);
//        } finally {
//            DetailActivity.this.finish();
//        }
//    }

//    public void openPlayStore(boolean showPublisherProfile, String publisherID) {
//
//        //Error Handling
//        if (publisherID == null || !publisherID.isEmpty()) {
//            publisherID = "";
//            //Log and continue
//            Log.w("openPlayStore Method", "publisherID is invalid");
//        }
//        String packageName = "mn.unitedalliance.mobile";
//        Intent openPlayStoreIntent;
//        boolean isGooglePlayInstalled = false;
//
//        if (showPublisherProfile) {
//            //Open Publishers Profile on PlayStore
//            openPlayStoreIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("market://search?q=pub:" + publisherID));
//        } else {
//            //Open this App on PlayStore
//            openPlayStoreIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("market://details?id=" + packageName));
//        }
//
//        // find all applications who can handle openPlayStoreIntent
//        final List<ResolveInfo> otherApps = getPackageManager()
//                .queryIntentActivities(openPlayStoreIntent, 0);
//        for (ResolveInfo otherApp : otherApps) {
//
//            // look for Google Play application
//            if (otherApp.activityInfo.applicationInfo.packageName.equals("mn.gmobile.draw")) {
//
//                ActivityInfo otherAppActivity = otherApp.activityInfo;
//                ComponentName componentName = new ComponentName(
//                        otherAppActivity.applicationInfo.packageName,
//                        otherAppActivity.name
//                );
//                // make sure it does NOT open in the stack of your activity
//                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                // task reparenting if needed
//                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                // if the Google Play was already open in a search result
//                //  this make sure it still go to the app page you requested
//                openPlayStoreIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                // this make sure only the Google Play app is allowed to
//                // intercept the intent
//                openPlayStoreIntent.setComponent(componentName);
//                startActivity(openPlayStoreIntent);
//                isGooglePlayInstalled = true;
//                break;
//
//            }
//        }
//        // if Google Play is not Installed on the device, open web browser
//        if (!isGooglePlayInstalled) {
//
//            Intent webIntent;
//            if (showPublisherProfile) {
//                //Open Publishers Profile on web browser
//                webIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
//            } else {
//                //Open this App on web browser
//                webIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
//            }
//            startActivity(webIntent);
//        }
//    }

}
