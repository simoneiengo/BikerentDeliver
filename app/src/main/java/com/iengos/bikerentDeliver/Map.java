package com.iengos.bikerentDeliver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Simone on 25/08/2016.
 * usefull link https://developers.google.com/maps/documentation/android-api/map
 * TODO: ?? contextual menu onInfoWindowLongClick sms or mail
 **/
public class Map extends FragmentActivity implements OnMapReadyCallback {
    private java.util.HashMap<String, String> markers = null;
    private CircularImageView img = null;
    private Marker clickedMarker = null;
    private CountDownTimer countRemainTime = null;
    private Bitmap bitmap = null;
    private ProgressDialog pDialog = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment =  (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        markers = new HashMap<String, String>();
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // enable standard google icons on map
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);

        // get last position
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        // move to your last position
        if(location != null)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 9));

        // Set custom infoWindow
        map.setInfoWindowAdapter(new CustomInfoWindow());

        // set infoWindow on click listener telephone
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+markers.get("telephone"+marker.getTitle())));
                if (ActivityCompat.checkSelfPermission(Map.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        // set infoWindow on long click listener send mail
        map.setOnInfoWindowLongClickListener( new GoogleMap.OnInfoWindowLongClickListener(){
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");                    // set MIME type request
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{markers.get("email"+marker.getTitle())});
                i.putExtra(Intent.EXTRA_SUBJECT, "BikeRent request");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Map.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /// MARKERS TEST
        Vector<String> Id = new Vector<String>();

        Id.add("SimoneIengo");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(41.1, 14))
                .title(Id.elementAt(0).toString())
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        markers.put("image"+Id.elementAt(0).toString(),"https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-1/c0.17.160.160/p160x160/13690788_10208697212181576_1205444387132343999_n.jpg?oh=493fda39cc23ce83c7de647ffcd6f428&oe=584ECDC3");
        markers.put("telephone"+Id.elementAt(0).toString(),"3806910787");
        markers.put("name"+Id.elementAt(0).toString(),"Simone");
        markers.put("surname"+Id.elementAt(0).toString(),"Iengo");
        markers.put("email"+Id.elementAt(0).toString(),"simone.iengo@libero.it");
        markers.put("bikeN"+Id.elementAt(0).toString(),"10");
        markers.put("price"+Id.elementAt(0).toString(),"30€");
        markers.put("date"+Id.elementAt(0).toString(),"09/02/2016 11:49:00 AM");

        Id.add("DavideIengo");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(41, 14.1))
                .title(Id.elementAt(1).toString()));
        markers.put("image"+Id.elementAt(1).toString(),"https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-1/c14.0.160.160/p160x160/10526189_10203597870620971_1733139422695688299_n.jpg?oh=e29688ca2339043ec324dd8285a98266&oe=583D4943");
        markers.put("telephone"+Id.elementAt(1).toString(),"3929224903");
        markers.put("name"+Id.elementAt(1).toString(),"Davide");
        markers.put("surname"+Id.elementAt(1).toString(),"Iengo");
        markers.put("email"+Id.elementAt(1).toString(),"davide.iengo@libero.it");
        markers.put("bikeN"+Id.elementAt(1).toString(),"3");
        markers.put("price"+Id.elementAt(1).toString(),"10€");
        markers.put("date"+Id.elementAt(1).toString(),"09/03/2016 10:00:00 AM");
    }

    /*
    *   Class used to customize and compile infowindow
    */
    public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
        private View mymarkerview = null;
        private TextView name=null, surname=null, email=null, bikeN=null, date=null, telephone=null, price=null, countDown=null;
        private String dateDiff = null; // convert in type date
        private Date prenotation = null;
        private long difference = 0;
        private String hours=null, mins=null;

        CustomInfoWindow(){
            mymarkerview = getLayoutInflater().inflate(R.layout.custom_info_window, null);

            img = (CircularImageView) mymarkerview.findViewById(R.id.accountImage);
            name = (TextView)mymarkerview.findViewById(R.id.markerAccountName);
            surname = (TextView)mymarkerview.findViewById(R.id.markerAccountSurname);
            telephone  = (TextView)mymarkerview.findViewById(R.id.markerAccountNumber);
            email = (TextView)mymarkerview.findViewById(R.id.markerAccountEmail);
            bikeN = (TextView)mymarkerview.findViewById(R.id.markerBikeNum);
            price = (TextView)mymarkerview.findViewById(R.id.markerBikePayment);
            date = (TextView)mymarkerview.findViewById(R.id.markerDate);
            countDown = (TextView)mymarkerview.findViewById(R.id.markerCountDown);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            if (clickedMarker == null || !clickedMarker.getTitle().equals(marker.getTitle())){               // if is first time but not second (break cycle)
                clickedMarker = marker;                 // Set Global marker to redraw infowindow

                // Set infowindow fields
                name.setText(markers.get("name"+marker.getTitle()));
                surname.setText(markers.get("surname"+marker.getTitle()));
                telephone.setText(markers.get("telephone"+marker.getTitle()));
                email.setText(markers.get("email"+marker.getTitle()));
                bikeN.setText("bike #: "+markers.get("bikeN"+marker.getTitle()));
                price.setText("cost: "+markers.get("price"+marker.getTitle()));
                date.setText(markers.get("date"+marker.getTitle()));
                countDown.setTextColor(Color.parseColor("#DDDDDD"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                Date prenotation = new Date();
                try {
                    prenotation = dateFormat.parse(date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //TODO: calcolare il numero di millisecondi tra due date dd/mm/yy hh:mm:ss
                if (countRemainTime != null)
                    countRemainTime.cancel();

                difference = prenotation.getTime()- Calendar.getInstance().getTime().getTime();
                final Date finalPrenotation = prenotation;              // used to "pass" variable prenotation to onTick
                countRemainTime = new CountDownTimer(difference, 60000) {
                    public void onTick(long millisUntilFinished) {
                        difference = finalPrenotation.getTime()- Calendar.getInstance().getTime().getTime();
                        hours = (difference/3600000>=10 ? Long.toString(difference/3600000) : "0"+Long.toString((difference/3600000)));                // 1000*60*60
                        mins = ((difference/60000)%60>=10 ? Long.toString((difference/60000)%60) : "0"+Long.toString((difference/60000)%60));          // 1000*60

                        countDown.setText(hours+":"+mins);
                        if(clickedMarker.isInfoWindowShown())
                            clickedMarker.showInfoWindow();
                    }

                    public void onFinish() {
                        countDown.setText("exipired!");
                        countDown.setTextColor(Color.parseColor("#FF0000"));         // set color = red
                        if(clickedMarker.isInfoWindowShown())
                            clickedMarker.showInfoWindow();
                        this.cancel();
                    }
                }.start();

                String imageUrl = markers.get("image"+marker.getTitle());   // set account image url
                new LoadImage().execute(imageUrl);
            }
            return mymarkerview;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    /*
    *   Class used to account image asyncronous loading
    *
    */
    private class LoadImage extends AsyncTask <String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Map.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();
                clickedMarker.showInfoWindow();
            }else{
                pDialog.dismiss();
                Toast.makeText(Map.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Go to list activity
    public void switchToList(View view) {
        startActivity(new Intent(getApplicationContext(), RequestList.class));
    }
}