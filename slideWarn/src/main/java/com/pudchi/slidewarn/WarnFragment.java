package com.pudchi.slidewarn;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WarnFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    public static final int posi = 0;
    public static String PAGE = "PAGE";
    public static String cant_get_location = "   Sorry... 無法定位\n請更改手機定位設定!";
    public static String no_internet = "請檢查手機是否連上網路\n或是手機定位設定!";
    static int click_once = 0;
    private static int UPDATE_INTERVAL = 5000; // 5s
    private static int FATEST_INTERVAL = 1000; // 1s
    private static int DISPLACEMENT = 5; // 5m
    protected int mPage;
    TextView location_txt;
    TextView location_lbl;
    Button turn_format;
    Button update_loc;
    double latitude;
    double longitude;
    String locate;
    String TAG_Warn;
    int loc_flag = 0;
    int loc_update_hit = 0;
    int format_button_flag = 0;
    String result_address = "";
    android.os.Handler loc_handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2000:
                    String st = msg.getData().getString("Result");

                    location_lbl.setText("你現在的位置:");

                    if (loc_flag == 0) {
                        location_txt.setText(locate);
                    } else {
                        location_txt.setText(cant_get_location);
                    }
                    break;

                case 3000:
                    String st_second = msg.getData().getString("Result");

                    location_lbl.setText("你相近的地址:");

                    String two_line_address_1 = result_address.substring(0, 11);
                    String two_line_address_2 = result_address.substring(11);

                    location_txt.setText(two_line_address_1 + "\n" + two_line_address_2);
                    location_txt.setGravity(Gravity.CENTER_HORIZONTAL);

                    break;

                case 5000:

                    String st_nw = msg.getData().getString("Result");

                    location_txt.setText(no_internet);
                    location_txt.setGravity(Gravity.CENTER_HORIZONTAL);

                default:
                    break;

            }
        }

    };
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    Runnable Loc_update = new Runnable() {
        @Override
        public void run() {
            try {
                /*createLocationRequest();*/
                PeriodicLocationUpdates();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    };
    private LocationManager locationManager;

	public static WarnFragment newIns(int page)
	{
		Bundle args = new Bundle();
		args.putInt(PAGE, page);
        WarnFragment fragment = new WarnFragment();
        fragment.setArguments(args);
        return fragment;

	}

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("CONNECTED", "CONNECTED TO LOCATION_API!");
        getLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Failed", connectionResult.toString());

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
        {
            mGoogleApiClient.connect();
        }
    }

    public void onResume() {
        super.onResume();

        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates)
        {
            stopLocationUpdates();
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void getLocation()
    {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null)
        {
            Log.d("Distance", "Latitude:" + mLastLocation.getLatitude()
                    +", Longtitude:" + mLastLocation.getLongitude());

            latitude = mLastLocation.getLatitude(); //緯度
            longitude = mLastLocation.getLongitude();  //經度
            locate = "     經度: " + longitude + "\n     緯度:   " + latitude;

            if(locate.length() < 5)
            {
                loc_flag = 1;
            }
            else
            {
                loc_flag = 0;
            }
        }
    }

    private void PeriodicLocationUpdates()
    {
        if(!mRequestingLocationUpdates)
        {
            mRequestingLocationUpdates = true;

            startLocationUpdates();
            Log.d(TAG_Warn, "Periodic location updates started!");
        }
        else
        {
            mRequestingLocationUpdates = false;

            stopLocationUpdates();

            Log.d(TAG_Warn, "Periodic location updates stopped!");
        }
    }

    protected void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void startLocationUpdates()
    {
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) getActivity());
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi
                .removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) getActivity());
    }

    //-----------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPage = getArguments().getInt(PAGE);

        buildGoogleApiClient();
        createLocationRequest();
        getLocation();

        //new Thread(Loc_update).start();
	}

    @Override
    public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_warn, container, false);
		TextView logo = (TextView) view.findViewById(R.id.logo);
        location_lbl = (TextView) view.findViewById(R.id.location_lbl);
        location_txt = (TextView) view.findViewById(R.id.location_txt);
        ImageView marker_txt = (ImageView) view.findViewById(R.id.marker_img);
        update_loc = (Button) view.findViewById(R.id.update_loc);
        turn_format = (Button) view.findViewById(R.id.turn_format);
		Button warn = (Button) view.findViewById(R.id.warn);
		//Button report = (Button) view.findViewById(R.id.lostreport);

        /*createLocationRequest();

        PeriodicLocationUpdates();*/


        update_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (loc_flag == 0) {
                    location_txt.setText(locate);
                } else {
                    location_txt.setText(cantgetlocation);
                }*/
                update_loc.setText("更新");
                get_location_view(2000);

                new Thread(Loc_update).start();
                Log.d("Thread: ", "Update_loc Thread start!");

                loc_update_hit++;
                click_once++;

                if (loc_flag == 0)
                {
                    if (loc_update_hit == 0 || loc_update_hit == 1)
                    {
                        Toast.makeText(getContext(), "已定位你的位置!", Toast.LENGTH_SHORT).show();
                    }
                    else if (loc_update_hit > 1)
                    {
                        Toast.makeText(getContext(), "已更新你的位置!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "  無法定位你的位置\n請更改手機定位設定", Toast.LENGTH_LONG).show();
                }
            }

        });

        /*if(click_once == 0)
        {
            get_location_view();
        }*/

        turn_format.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (format_button_flag == 0 && is_Connecting_to_internet())
                {
                    turn_format.setText("經緯度");
                    Toast.makeText(getContext(), "已轉換成地址!", Toast.LENGTH_SHORT).show();
                    format_button_flag = 1;
                    turn_loc_view(3000);

                } else if (format_button_flag == 1 && is_Connecting_to_internet())
                {
                    turn_format.setText("地址");
                    Toast.makeText(getContext(), "已轉換成經緯度!", Toast.LENGTH_SHORT).show();
                    format_button_flag = 0;
                    get_location_view(2000);

                } else {
                    String result_nw = "Network Problem";
                    Message nw = new Message();
                    nw.what = 5000;
                    Bundle d_nw = new Bundle();

                    try {
                        getLocation();
                    } catch (Exception e_loc) {
                        e_loc.getMessage();
                    }

                    d_nw.putString("Result", result_nw);

                    nw.setData(d_nw);
                    loc_handler.sendMessage(nw);
                }

            }
        });

        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i_warn = new Intent();
                i_warn.setClass(getContext(), warn_pop.class);
                startActivity(i_warn);
                getActivity().overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);

            }
        });

        /*report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), report_pop.class));
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });*/
		/*tv1.setText(": " + mPage);*/
		return view;
    }

    ;

    public String get_loc_turnto_address(Location loc) throws IOException {
            if(loc != null)
            {
                try
                {

                Geocoder gc_loc = new Geocoder(getContext(), Locale.TRADITIONAL_CHINESE);
                    List<Address> lastAddress = gc_loc.getFromLocation(latitude, longitude, 5);
                    result_address = lastAddress.get(0).getAddressLine(0);
                } catch (IOException e_t)
                {
                    e_t.getMessage();

                }
                catch (IllegalArgumentException arg_e)
                {
                    arg_e.getMessage();
                }
             }
        return result_address;

    }

    private void get_location_view(final int what_value)
    {
        Runnable update_loc_view = new Runnable()
        {
            @Override
            public void run()
            {
                String result = "Success";
                Message me = new Message();
                me.what = what_value;
                Bundle d = new Bundle();

                try
                {
                    getLocation();
                } catch (Exception e_loc)
                {
                    e_loc.getMessage();
                }

                d.putString("Result", result);
                me.setData(d);
                loc_handler.sendMessage(me);
            }
        };
        new Thread(update_loc_view).start();
    }

    private void turn_loc_view(final int value)
    {
        Runnable update_format_loc = new Runnable() {
            @Override
            public void run()
            {
                String result = "Success";
                Message me = new Message();
                me.what = value;
                Bundle d = new Bundle();

                try
                {
                    get_loc_turnto_address(mLastLocation);
                } catch (Exception e_loc)
                {
                    e_loc.getMessage();
                }

                d.putString("Result", result);
                me.setData(d);
                loc_handler.sendMessage(me);
            }
        };
        new Thread(update_format_loc).start();
    }

    public boolean is_Connecting_to_internet() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            Toast.makeText(getContext(), "請檢查是否連上網路", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;

    }




    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        Toast.makeText(getContext(), "所在位置已更新!", Toast.LENGTH_LONG).show();

        getLocation();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
