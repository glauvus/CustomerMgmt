package com.example.admin.computertechnics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 6/1/2017.
 */

public class MyListAdapter extends ArrayAdapter<Client> {

    public MyListAdapter(Context context, int resource, List<Client> clients) {
        super(context, resource, clients);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Inflating the custom card layout "list_row"
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        //Setting the text retrieved form the list to the TextViews
        Client client = getItem(position);

        if (client != null) {
            TextView idText = (TextView) convertView.findViewById(R.id.id_text);
            TextView lastNameText = (TextView) convertView.findViewById(R.id.lastName_text);
            ImageView statusCircle = (ImageView) convertView.findViewById(R.id.status_circle_image);

            if (idText != null) {
                idText.setText("#" + client.getId());
            }
            if (lastNameText != null) {
                lastNameText.setText(client.getLastName());
            }

            if (client.isFinished()) {
                statusCircle.setColorFilter(ContextCompat.getColor(getContext(), R.color.greenStatusCircle));
            }
            else if (!(client.isFinished())) {
                statusCircle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            }
        }

        return convertView;
    }
}
