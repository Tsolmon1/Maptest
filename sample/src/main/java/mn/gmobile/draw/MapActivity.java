package mn.gmobile.draw;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mn.gmobile.draw.NewsActivity;
import mn.gmobile.draw.ProductActivity;
import mn.gmobile.draw.R;
import mn.gmobile.draw.RogerAdapter;
import mn.gmobile.draw.RogerModel;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.JsonElement;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.plugins.annotation.Fill;
import com.mapbox.mapboxsdk.plugins.annotation.FillManager;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, OnCameraTrackingChangedListener,  PermissionsListener, OnLocationClickListener {
    private FillManager fillManager;
    private final Random random = new Random();
    private MapView mapView;
    private DirectionsRoute currentRoute;
    private MapboxMap mapboxMap;
    private static final String GEOJSON_SOURCE_ID = "GEOJSONFILE";
    private String jsonURL = "https://www.mtgroup.mn/location/maps";
    private final int jsoncode = 1;
    //    private RecyclerView recyclerView;
    ArrayList<RogerModel> rogerModelArrayList;
    private RogerAdapter rogerAdapter;
    private static ProgressDialog mProgressDialog;
    //    List<Feature> markerCoordinates;
    List<Feature> markerCoordinates = new ArrayList<>();
    private Button button;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    //private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;
    private boolean isInTrackingMode;
    List<LatLng> innerLatLngs = new ArrayList<>();
    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
    private SymbolManager symbolManager;
    //    private static final LatLngBounds LOCKED_MAP_CAMERA_BOUNDS = new LatLngBounds.Builder()
//            .include(new com.mapbox.mapboxsdk.geometry.LatLng(47.9168609, 106.6877968))
//            .include(new com.mapbox.mapboxsdk.geometry.LatLng(47.9104355,
//                    106.9783978)).build();
//    private final WeakReference<MainActivity> activityRef;
//    private final boolean refreshSource;
//    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
//    private static final String MARKER_IMAGE_ID = "MARKER_IMAGE_ID";
//    private static final String MARKER_LAYER_ID = "MARKER_LAYER_ID";
//    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
//    private static final String PROPERTY_SELECTED = "selected";
    private static final String PROPERTY_NAME = "shts_name";
    private static final String PROPERTY_CAPITAL = "content";
    private FeatureCollection featureCollection;
    private static final String PROPERTY_SELECTED = "selected";
    private static final String ID_ICON_AIRPORT = "airport";
    private GeoJsonSource source;
    private Symbol symbol;
    //    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);
    private LocationEngine locationEngine;
    RogerModel playersModel = new RogerModel();
    ArrayList<RogerModel> tennisModelArrayList = new ArrayList<>();

    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        fetchJSON();
        //setTheme(R.style.customInstructionView);
    }

    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }
    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
//            case R.id.item1:
//                Map();
//                //loadFragment(new FirstFragment());
//
//                Log.i("TAG", "Item 1 selected");
//                break;
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

//    public void Map(){
//
//        try{
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//        } finally {
//            MapActivity.this.finish();
//        }
//    }
    public void News(){

        try{
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } finally {
            //MapActivity.this.onPause();
            MapActivity.this.finish();
        }
    }
    public void Product(){

        try{
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
        } finally {
            MapActivity.this.finish();
        }

    }
    public void Feedback(){

        try{
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } finally {
            MapActivity.this.finish();
        }
    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
//                        fillManager = new FillManager(mapView, mapboxMap, style);
//                        fillManager.addClickListener(fill -> Toast.makeText(MapActivity.this,
//                                String.format("Fill clicked %s with title: %s", fill.getId(), getTitleFromFill(fill)),
//                                Toast.LENGTH_SHORT
//                        ).show());


                    }
                }
        );
    }

    private LatLng createRandomLatLng() {
        return new LatLng(playersModel.getLatitude(),
                playersModel.getLongitude());
    }
    public ArrayList<RogerModel> getInfo(String response) {
        //final WeakReference<MainActivity> activityRef;
        //MainActivity activity;

        //ArrayList<RogerModel> tennisModelArrayList = new ArrayList<>();
        //LayoutInflater inflater = LayoutInflater.from(activity);
        LayoutInflater inflater = getLayoutInflater();
        final HashMap<String, View> viewMap = new HashMap<>();
        HashMap<String, Bitmap> imagesMap = new HashMap<>();
        //LayoutInflator inflater = getLayoutInflater();
        //Style style = mapboxMap.getStyle();
        try {

//            final List<Feature> markerCoordinates = new ArrayList<>();
            JSONArray jsonObject = new JSONArray(response);
            for (int i = 0; i < jsonObject.length(); i++) {

                RogerModel rmodel = new RogerModel();
                //Feature singleLocation = jsonObject.get(i);
                JSONObject dataobj = jsonObject.getJSONObject(i);
                rmodel.setLatitude(dataobj.getDouble("latitude"));
//                playersModel.setLatitude(dataobj.getDouble("latitude"));
                rmodel.setLongitude(dataobj.getDouble("longitude"));
                rmodel.setShts_name(dataobj.getString("shts_name"));
                rmodel.setContent(dataobj.getString("content"));

                tennisModelArrayList.add(rmodel);
                //addAirplaneImageToStyle(style);



                innerLatLngs.add(new LatLng(rmodel.getLatitude(), rmodel.getLongitude()));

                // Get the single location's LatLng coordinates
//                Point singleLocationPosition = (Point) playersModel.geometry();
                // Create a new LatLng object with the Position object created above
//                LatLng singleLocationLatLng = new LatLng(.latitude(),
//                        dataobj.longitude());
                markerCoordinates.add(Feature.fromGeometry(
                        Point.fromLngLat(rmodel.getLongitude(), rmodel.getLatitude()))); // Boston Common Park
                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocationComponent(style);
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(47.9180, 106.9132)) // Sets the new camera position
                                .zoom(14) // Sets the zoom
                                .bearing(0) // Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder

                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 3000);
//                                symbolManager = new SymbolManager(mapView, mapboxMap, style, null, null);
                        //SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);

                        // create symbol manager
//                        GeoJsonOptions geoJsonOptions = new GeoJsonOptions().withTolerance(0.4f);
//                        symbolManager = new SymbolManager(mapView, mapboxMap, style, null, geoJsonOptions);
                        // create a symbol

//                                SymbolOptions symbolOptions = new SymbolOptions()
//                                        .withLatLng(new LatLng(playersModel.getLatitude(), playersModel.getLongitude()))
//                                        .withIconImage(ID_ICON_AIRPORT)
//                                        .withIconSize(1f)
//                                        .withSymbolSortKey(5.0f)
//                                        .withDraggable(false);
//                                symbol = symbolManager.create(symbolOptions);
//                                addAirplaneImageToStyle(style);
//
//                                symbolManager.setIconAllowOverlap(true);
//                                symbolManager.setIconIgnorePlacement(true);

                        mapboxMap.addOnMapClickListener(MapActivity.this);
                        style.addSource(new GeoJsonSource("marker-source",
                                FeatureCollection.fromFeatures(markerCoordinates)));

// Add the marker image to map
                        style.addImage("my-marker-image", BitmapFactory.decodeResource(
                                MapActivity.this.getResources(), R.drawable.mtannotation));
// Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
// middle of the icon being fixed to the coordinate point.
                        style.addLayer(new SymbolLayer("marker-layer", "marker-source")
                                .withProperties(PropertyFactory.iconImage("my-marker-image"),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[]{0f, -9f})));

// Add the selected marker source and layer
                        style.addSource(new GeoJsonSource("selected-marker"));

// Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
// middle of the icon being fixed to the coordinate point.
                        style.addLayer(new SymbolLayer("selected-marker-layer", "selected-marker")
                                .withProperties(PropertyFactory.iconImage("my-marker-image"),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[]{0f, -9f})));
                        button = findViewById(R.id.startButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean simulateRoute = true;
                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                        .directionsRoute(currentRoute)
                                        .shouldSimulateRoute(simulateRoute)
                                        .build();
// Call this method with Context from within an Activity
                                NavigationLauncher.startNavigation(MapActivity.this, options);
                            }
                        });

                    }
                } );
//                SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);
//
//                symbolManager.setIconAllowOverlap(true);
//                symbolManager.setIconIgnorePlacement(true);
//                List<List<LatLng>> latLngs = new ArrayList<>();
//                latLngs.add(innerLatLngs);
// Add symbol at specified lat/lon
//                Symbol symbol = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(playersModel.getLatitude(), playersModel.getLongitude()))
//                        .withIconImage(ICON_ID)
//                        .withIconSize(2.0f));


// random add fills across the globe
                //List<FillOptions> fillOptionsList = new ArrayList<>();

//                for (int j = 0; j < 20; j++) {
//                    int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
//                    fillOptionsList.add(new FillOptions()
//                            .withLatLngs(createRandomLatLngs())
//                            .withFillColor(ColorUtils.colorToRgbaString(color))
//
//
//                    );
//                }

                // fillManager.create(fillOptionsList);
                //Point singleLocationPosition = (Point) markerCoordinates;
//                mapView.addMarker(new MarkerOptions()
//                        .position(new LatLng(41.327752, 19.818666))
//                        .title("MapBox Marker!")
//                        .snippet("Welcome to my marker."));

                //getInformationFromDirectionsApi(, false, i);
                //Log.d("newwwss", "json" + playersModel.getLongitude());
                Log.d("newwwss","markerCoordinates");
//                Feature singleLocation = new LatLng(playersModel.getLongitude(),
//                        playersModel.getLatitude());
//                Point singleLocationPosition = (Point) singleLocation.geometry();
//                LatLng singleLocationLatLng = new LatLng(playersModel.getLongitude(),
//                        playersModel.getLatitude());
//                getInformationFromDirectionsApi(singleLocationLatLng, false, i);

//                BubbleLayout bubbleLayout = (BubbleLayout)
//                        inflater.inflate(R.layout.symbol_layer_info_window_layout_callout, null);
//
//                //String name = feature.getStringProperty(PROPERTY_NAME);
//                String name = dataobj.getString(PROPERTY_NAME);
//                TextView titleTextView = bubbleLayout.findViewById(R.id.info_window_title);
//                titleTextView.setText(name);
//
//                //String style = feature.getStringProperty(PROPERTY_CAPITAL);
//                String style = dataobj.getString(PROPERTY_CAPITAL);
//                TextView descriptionTextView = bubbleLayout.findViewById(R.id.info_window_description);
//                descriptionTextView.setText(style);
//
//                int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//                bubbleLayout.measure(measureSpec, measureSpec);
//
//                float measuredWidth = bubbleLayout.getMeasuredWidth();
//
//                bubbleLayout.setArrowPosition(measuredWidth / 2 - 5);
//
//                Bitmap bitmap = SymbolGenerator.generate(bubbleLayout);
//                imagesMap.put(name, bitmap);
//                viewMap.put(name, bubbleLayout);
            }




//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tennisModelArrayList;
    }

    private void addAirplaneImageToStyle(Style style) {
        style.addImage(ID_ICON_AIRPORT,
                BitmapUtils.getBitmapFromDrawable(getResources().getDrawable(R.drawable.blue_marker)),
                true);
    }

    @Override
    public void onLocationComponentClick() {
        if (locationComponent.getLastKnownLocation() != null) {
//            Toast.makeText(this, String.format(getString(R.string.current_location),
//                    locationComponent.getLastKnownLocation().getLatitude(),
//                    locationComponent.getLastKnownLocation().getLongitude()), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCameraTrackingDismissed() {
        isInTrackingMode = false;
    }

    @Override
    public void onCameraTrackingChanged(int currentMode) {
// Empty on purpose
    }

//    private void createGeoJsonSource(@NonNull Style loadedMapStyle) {
//        try {
//// Load data from GeoJSON file in the assets folder
//            loadedMapStyle.addSource(new GeoJsonSource(GEOJSON_SOURCE_ID,
//                    new URI("assets://list_of_locations.geojson")));
//        } catch (URISyntaxException exception) {
//            Timber.d(exception);
//        }
//    }

//    private void addPolygonLayer(@NonNull Style loadedMapStyle) {
//// Create and style a FillLayer that uses the Polygon Feature's coordinates in the GeoJSON data
//        FillLayer countryPolygonFillLayer = new FillLayer("polygon", GEOJSON_SOURCE_ID);
//        countryPolygonFillLayer.setProperties(
//                PropertyFactory.fillColor(Color.RED),
//                PropertyFactory.fillOpacity(.4f));
//        countryPolygonFillLayer.setFilter(eq(literal("$type"), literal("Polygon")));
//        loadedMapStyle.addLayer(countryPolygonFillLayer);
//    }
//
//    private void addPointsLayer(@NonNull Style loadedMapStyle) {
//// Create and style a CircleLayer that uses the Point Features' coordinates in the GeoJSON data
//        CircleLayer individualCirclesLayer = new CircleLayer("points", GEOJSON_SOURCE_ID);
//        individualCirclesLayer.setProperties(
//                PropertyFactory.circleColor(Color.YELLOW),
//                PropertyFactory.circleRadius(3f));
//        individualCirclesLayer.setFilter(eq(literal("$type"), literal("Point")));
//        loadedMapStyle.addLayer(individualCirclesLayer);
//    }

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
                rogerModelArrayList = getInfo(response);
                rogerAdapter = new RogerAdapter(this,rogerModelArrayList);

                //recyclerView.setAdapter(rogerAdapter);
                // recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        }
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


    private String getTitleFromFill(Fill fill) {
        String title = "unknown";
        JsonElement customData = fill.getData();
        if (!(customData.isJsonNull())) {
            title = customData.getAsString();
        }
        return title;
    }
    private List<List<LatLng>> createRandomLatLngs() {
        List<LatLng> latLngs = new ArrayList<>();
        LatLng firstLast = new LatLng((random.nextDouble() * -180.0) + 90.0,
                (random.nextDouble() * -360.0) + 180.0);
        latLngs.add(firstLast);
        for (int i = 0; i < random.nextInt(10); i++) {
            latLngs.add(new LatLng((random.nextDouble() * -180.0) + 90.0,
                    (random.nextDouble() * -360.0) + 180.0));
        }
        latLngs.add(firstLast);

        List<List<LatLng>> resulting = new ArrayList<>();
        resulting.add(latLngs);
        return resulting;
    }

    /**
     * Utility class to generate Bitmaps for Symbol.
     */
//    private static class SymbolGenerator {
//
//        /**
//         * Generate a Bitmap from an Android SDK View.
//         *
//         * @param view the View to be drawn to a Bitmap
//         * @return the generated bitmap
//         */
//        static Bitmap generate(@NonNull View view) {
//            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            view.measure(measureSpec, measureSpec);
//
//            int measuredWidth = view.getMeasuredWidth();
//            int measuredHeight = view.getMeasuredHeight();
//
//            view.layout(0, 0, measuredWidth, measuredHeight);
//            Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
//            bitmap.eraseColor(Color.TRANSPARENT);
//            Canvas canvas = new Canvas(bitmap);
//            view.draw(canvas);
//            return bitmap;
//        }
//    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = mapboxMap.getStyle();
        if (style != null) {
            Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
            Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                    locationComponent.getLastKnownLocation().getLatitude());

            GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
            final SymbolLayer selectedMarkerSymbolLayer =
                    (SymbolLayer) style.getLayer("selected-marker-layer");

            final PointF pixel = mapboxMap.getProjection().toScreenLocation(point);
            List<Feature> features = mapboxMap.queryRenderedFeatures(pixel, "marker-layer");
            Log.d("newwwss","feature"+features);
//            List<Feature> selectedFeature = mapboxMap.queryRenderedFeatures(
//                    pixel, "selected-marker-layer");
//
//            if (selectedFeature.size() > 0 && markerSelected) {
//                return false;
//            }

//            if (features.isEmpty()) {
//                if (markerSelected) {
//                    deselectMarker(selectedMarkerSymbolLayer);
//                }
//                return false;
//            }
//            else {
//                String shts_name = features.get(0).getStringProperty(PROPERTY_NAME);
//                for (int i = 0; i < features.size(); i++) {
////                    if (features.get(i).getStringProperty(PROPERTY_NAME).equals(shts_name)) {
//                        if (featureSelectStatus(i)) {
//                            setFeatureSelectState(features.get(i), false);
//                        } else {
//                            setSelected(i);
//                        }
//                    //}
//                }
//            }
            if (source != null) {
                //source.setGeoJson(Feature.fromGeometry(destinationPoint));
                source.setGeoJson(FeatureCollection.fromFeatures(
                        new Feature[]{Feature.fromGeometry(features.get(0).geometry())}));
            }

            getRoute(originPoint, destinationPoint);

            button.setEnabled(true);
            button.setBackgroundResource(R.color.mapboxBlue);





//            GeoJsonSource source = style.getSourceAs("selected-marker");
//            if (source != null) {
//                source.setGeoJson(FeatureCollection.fromFeatures(
//                        new Feature[]{Feature.fromGeometry(features.get(0).geometry())}));
//            }

//            if (markerSelected) {
//                deselectMarker(selectedMarkerSymbolLayer);
//            }
//            if (features.size() > 0) {
//                selectMarker(selectedMarkerSymbolLayer);
//            }
            //return handleClickIcon(mapboxMap.getProjection().toScreenLocation(point));
        }

        return true;
    }



    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }


                        currentRoute = response.body().routes().get(0);
                        // Draw the route on the map
                        //drawRoute(currentRoute);
// Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
// Activate the MapboxMap LocationComponent to show user location
// Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING,   750L /*duration*/,
                    16.0 /*zoom*/,
                    null /*bearing, use current/determine based on the tracking mode*/,
                    40.0 /*tilt*/,
                    null /*transition listener*/);
            locationComponent.addOnLocationClickListener(this);
            findViewById(R.id.back_to_camera_tracking_mode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInTrackingMode) {
                        isInTrackingMode = true;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(MapActivity.this, getString(R.string.tracking_enabled),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        isInTrackingMode = false;
                        locationComponent.setCameraMode(CameraMode.TRACKING);
                        locationComponent.zoomWhileTracking(16f);
                        Toast.makeText(MapActivity.this, getString(R.string.tracking_already_enabled),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
            fetchJSON();
        } else {
            fetchJSON();
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void drawNavigationPolylineRoute(DirectionsRoute route) {
        // Retrieve and update the source designated for showing the store location icons
        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("navigation-route-source-id");
        if (source != null) {
            source.setGeoJson(FeatureCollection.fromFeature(Feature.fromGeometry(
                    LineString.fromPolyline(route.geometry(), PRECISION_6))));
        }
    }



    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }




    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();
        //fetchJSON();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        //fetchJSON();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
            mapboxMap.removeAnnotations();
        }
        mapView.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        //fetchJSON();
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
