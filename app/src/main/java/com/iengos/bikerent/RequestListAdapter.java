package com.iengos.bikerent;

/**
 * Created by Dave on 30/08/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestListAdapter extends BaseAdapter {
    Context context;
    ArrayList <InfoRequest> data;
    private static LayoutInflater inflater = null;

    public RequestListAdapter(RequestList context, ArrayList <InfoRequest> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.activity_request_list, null);

        InfoRequest CurrentRow = data.get(position);
        TextView Name = (TextView) vi.findViewById(R.id.Name);
        Name.setText(CurrentRow.getName());

        TextView Surname = (TextView) vi.findViewById(R.id.Surname);
        Surname.setText(CurrentRow.getSurname());

        TextView Number = (TextView) vi.findViewById(R.id.Number);
        Number.setText(CurrentRow.getNumber());

        TextView Time = (TextView) vi.findViewById(R.id.Time);
        Time.setText(CurrentRow.getTime());

        TextView Price = (TextView) vi.findViewById(R.id.Price);
        Price.setText(CurrentRow.getPrice());

        TextView Email = (TextView) vi.findViewById(R.id.Email);
        Email.setText(CurrentRow.getEmail());

        TextView NumBike = (TextView) vi.findViewById(R.id.BikeNum);
        NumBike.setText(CurrentRow.getNumBike());

        TextView ReqNum = (TextView) vi.findViewById(R.id.NumReq);
        ReqNum.setText(CurrentRow.getReqNum());

        return vi;
    }
}
