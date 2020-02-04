package com.obi.yogyakartasmartcity.Account;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.obi.yogyakartasmartcity.CircleTransform;
import com.obi.yogyakartasmartcity.MainActivity;
import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Auth.SessionUser;
import com.obi.yogyakartasmartcity.Network.DataSources;
import com.obi.yogyakartasmartcity.Network.ObjectSourcesCallback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.obi.yogyakartasmartcity.Network.DataSources.URL_CHANGEIMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener, ObjectSourcesCallback, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private MapView mapView;
    Button btn_edit;
    RelativeLayout btn_help, btn_terms, btn_about, btn_logout;
    ImageView img_profile, btn_image;
    TextView txt_name, txt_join, txt_email, txt_phone, txt_location;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static RelativeLayout relativeLayout_pb;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    LocationManager locationManager;

    private GoogleMap mMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    double latitude, longitude;

    boolean GpsStatus ;

    private UiSettings mUiSettings;

    GalleryPhoto galleryPhoto;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private String TAG = "Profile";

    SessionUser sessionUser;

    private static String token;
    private static String user_id;


    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage((FragmentActivity) getContext(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sessionUser = new SessionUser(getContext());

        galleryPhoto = new GalleryPhoto(getContext());

        HashMap<String, String> user = sessionUser.getUserDetails();

        token = user.get(SessionUser.KEY_TOKEN);
        user_id = user.get(SessionUser.KEY_ID);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        CheckGpsStatus();

        relativeLayout_pb = view.findViewById(R.id.relativeLayout_pb_account);

        btn_image = view.findViewById(R.id.btn_image_account);
        btn_image.setOnClickListener(this);
        btn_edit = view.findViewById(R.id.btn_edit_account);
        btn_edit.setOnClickListener(this);
        btn_help = view.findViewById(R.id.btn_help_account);
        btn_help.setOnClickListener(this);
        btn_terms = view.findViewById(R.id.btn_terms_account);
        btn_terms.setOnClickListener(this);
        btn_about = view.findViewById(R.id.btn_about_account);
        btn_about.setOnClickListener(this);
        btn_logout = view.findViewById(R.id.btn_logout_account);
        btn_logout.setOnClickListener(this);

        img_profile = view.findViewById(R.id.img_account);

        txt_name = view.findViewById(R.id.txt_name_account);
        txt_join = view.findViewById(R.id.txt_join_account);
        txt_email = view.findViewById(R.id.txt_email_account);
        txt_phone = view.findViewById(R.id.txt_phone_account);
        txt_location = view.findViewById(R.id.txt_location_account);

        getDataSources().getProfile(DataSources.URL_PROFILE, token, this);

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_account);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataSources().getProfile(DataSources.URL_PROFILE, token, AccountFragment.this);
            }
        });

        relativeLayout_pb.setVisibility(View.VISIBLE);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_detail_tempat_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_image_account:
                if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
                    GetImageFromGallery();
                } else {
                    checkPermissionREAD_EXTERNAL_STORAGE(getContext());
                }

                break;
            case R.id.btn_edit_account:
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("NAME", txt_name.getText().toString());
                intent.putExtra("EMAIL", txt_email.getText().toString());
                intent.putExtra("PHONE", txt_phone.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

                break;
            case R.id.btn_help_account:

                break;
            case R.id.btn_terms_account:

                break;
            case R.id.btn_about_account:

                break;
            case R.id.btn_logout_account:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Yakin ingin keluar?")
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                sessionUser.clearData();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
        }
    }

    public void GetImageFromGallery(){
       /*GalIntent = new Intent(Intent.ACTION_PICK,
               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(Intent.createChooser(GalIntent, "Select Image From"), 2);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Perlu izin");
        alertBuilder.setMessage("Izin penyimpanan eksternal diperlukan");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                        /*Intent intent = new Intent(getApplicationContext(), EditAkun.class);

                        startActivity(intent);*/
                    }
                });
        android.app.AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            galleryPhoto.setPhotoUri(data.getData());
            String photoPath = galleryPhoto.getPath();

            AndroidNetworking.upload(URL_CHANGEIMAGE)
                    .addHeaders("Accept", "application/json")
                    .addHeaders("Authorization", "Bearer "+token)
                    .addMultipartParameter("user_id", user_id)
                    .addMultipartFile("image", new File(photoPath))
                    .setTag(DataSources.class)
                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            relativeLayout_pb.setVisibility(View.VISIBLE);
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            relativeLayout_pb.setVisibility(View.GONE);
                            Log.d(TAG+"Image", String.valueOf(response));

                            try {
                                String j_status = response.getString("status");

                                Toast.makeText(getContext(), j_status,
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("FRAGMENT", "Account");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                ((Activity)getContext()).finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });

            Log.d(TAG+"photoPath", photoPath);
        }
    }

    public DataSources getDataSources() {
        return new DataSources();
    }

    @Override
    public void onSuccess(JSONObject respone) {
        relativeLayout_pb.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, String.valueOf(respone));

        try {
            JSONObject data = respone.getJSONObject("data");
            String j_id = data.getString("id");
            String j_name = data.getString("name");
            String j_slug = data.getString("slug");
            String j_email = data.getString("email");
            String j_phone = data.getString("phone");
            String j_image = data.getString("image");
            String j_created_at = data.getString("created_at");

            txt_name.setText(j_name);
            txt_email.setText(j_email);
            txt_phone.setText(j_phone);

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");

            try {
                Date date = format1.parse(j_created_at);
                String str = format2.format(date);

                txt_join.setText("Bergabung pada "+str);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Picasso.get().load(j_image).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).into(img_profile);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        relativeLayout_pb.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        Toast.makeText(getContext() , error, Toast.LENGTH_SHORT).show();
    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Kamu Disini...");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String addressName = addresses.get(0).getAddressLine(0);

            txt_location.setText(addressName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
