package com.iengos.bikerent;

/**
 * Created by Dave on 30/08/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity {

    // Array of Info
    ArrayList<InfoRequest> Row_data = new ArrayList<InfoRequest>();
    private ListView listview = null;
    private View deletedItem  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_request_list);

        //Fill list of item
        Row_data.add(new InfoRequest("Davide", "Iengo", "3929224309", "Davsim@hotmail.it", "19€", "23ore", "1", "17 bycicles"));
        Row_data.add(new InfoRequest("Simone", "Iengo", "3806910787", "Simone.iengo@libero.it", "30€", "23", "2", "30 bycicles"));

        listview = (ListView) findViewById(R.id.contentRequeList).findViewById(R.id.listview);
        listview.setAdapter(new RequestListAdapter(this, Row_data));
    }

    // Go to list activity
    public void switchToMap(View view) {
        startActivity(new Intent(getApplicationContext(), Map.class));
    }

    // Go to list activity
    public void call(View view) {
        View g = (View) view.getParent();
        ListView l = (ListView) g.getParent();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+((InfoRequest)Row_data.get(l.getPositionForView(g))).getNumber()));
        if (ActivityCompat.checkSelfPermission(RequestList.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

    // Open navigation on gmap application
    public void navigate(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=59.915494,30.409456")));
    }

    // Close request and delete item
    public void closeRequest(View view) {
        /// TODO remove on ListView and send update query on database
        /// TODO confirm deletion
        deletedItem = view;

        AlertDialog.Builder ab = new AlertDialog.Builder(RequestList.this);
        ab.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    View g = (View) deletedItem.getParent();
                    ListView l = (ListView) g.getParent();
                    Row_data.remove(l.getPositionForView(g));
                    l.requestLayout();
                    ((RequestListAdapter)l.getAdapter()).notifyDataSetChanged();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}