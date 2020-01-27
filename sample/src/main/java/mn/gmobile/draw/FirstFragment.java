package mn.gmobile.draw;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mn.gmobile.draw.R;

//import com.mapbox.android.core.location.LocationEngine;
//import com.mapbox.android.core.permissions.PermissionsManager;
//import com.mapbox.api.directions.v5.models.DirectionsRoute;
//import com.mapbox.geojson.Feature;
//import com.mapbox.geojson.FeatureCollection;
//import com.mapbox.geojson.LineString;
//import com.mapbox.mapboxsdk.geometry.LatLng;
//import com.mapbox.mapboxsdk.location.LocationComponent;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.plugins.annotation.FillManager;
//import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
//import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
//import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
//import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import static com.mapbox.core.constants.Constants.PRECISION_6;

//import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {


    View view;
    Button firstButton;

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


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map);
//        ButterKnife.bind(this);
//    }

//    @Nullable
//    @Override
//    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return super.onCreateView(parent, name, context, attrs);
//    }

//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        //ButterKnife.bind(this);
        view = inflater.inflate(R.layout.activity_main, container, false);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.map);
//        Mapbox.getInstance(getContext(), getString(R.string.access_token));
// get the reference of Button
//        firstButton = (Button) view.findViewById(R.id.firstButton);
//// perform setOnClickListener on first Button
//        firstButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//// display a message by using a Toast
//                Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
//            }
//        });

//        mapView = view.findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);


        return view;
    }










//    private List<List<LatLng>> createRandomLatLngs() {
//        List<LatLng> latLngs = new ArrayList<>();
//        LatLng firstLast = new LatLng((random.nextDouble() * -180.0) + 90.0,
//                (random.nextDouble() * -360.0) + 180.0);
//        latLngs.add(firstLast);
//        for (int i = 0; i < random.nextInt(10); i++) {
//            latLngs.add(new LatLng((random.nextDouble() * -180.0) + 90.0,
//                    (random.nextDouble() * -360.0) + 180.0));
//        }
//        latLngs.add(firstLast);
//
//        List<List<LatLng>> resulting = new ArrayList<>();
//        resulting.add(latLngs);
//        return resulting;
//    }
//
//
//
//
//
//
//
//
//
//    private void drawNavigationPolylineRoute(DirectionsRoute route) {
//        // Retrieve and update the source designated for showing the store location icons
//        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("navigation-route-source-id");
//        if (source != null) {
//            source.setGeoJson(FeatureCollection.fromFeature(Feature.fromGeometry(
//                    LineString.fromPolyline(route.geometry(), PRECISION_6))));
//        }
//    }

//    private void drawRoute(DirectionsRoute route) {
//        // Convert List<Waypoint> into LatLng[]
//        List<Waypoint> waypoints = route.geometry().getGeometry().getWaypoints();
//        LatLng[] point = new LatLng[waypoints.size()];
//        for (int i = 0; i < waypoints.size(); i++) {
//            point[i] = new LatLng(
//                    waypoints.get(i).getLatitude(),
//                    waypoints.get(i).getLongitude());
//        }
//
//        // Draw Points on MapView
//        mapView.addPolyline(new PolylineOptions()
//                .add(point)
//                .color(Color.parseColor("#38afea"))
//                .width(5));
//    }






    // Add the mapView lifecycle to the activity's lifecycle methods



}
