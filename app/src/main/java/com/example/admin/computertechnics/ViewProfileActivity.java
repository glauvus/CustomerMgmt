package com.example.admin.computertechnics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewProfileActivity extends AppCompatActivity {
    TextView profileId, profileLastName, profileFirstName, profileAddress, profileCity, profilePhone,
            profilePhone2, profileDevice, profileSerialNumber, profileEntryDate, profileMessageDate, profileComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        profileId = (TextView) findViewById(R.id.profile_id);
        profileLastName = (TextView) findViewById(R.id.profile_lastName);
        profileFirstName = (TextView) findViewById(R.id.profile_firstName);
        profileAddress = (TextView) findViewById(R.id.profile_address);
        profileCity = (TextView) findViewById(R.id.profile_city);
        profilePhone = (TextView) findViewById(R.id.profile_phone);
        profilePhone2 = (TextView) findViewById(R.id.profile_phone2);
        profileDevice = (TextView) findViewById(R.id.profile_device);
        profileSerialNumber = (TextView) findViewById(R.id.profile_serialNumber);
        profileEntryDate = (TextView) findViewById(R.id.profile_entryDate);
        profileMessageDate = (TextView) findViewById(R.id.profile_messageDate);
        profileComments = (TextView) findViewById(R.id.profile_comments);

        Intent intent = getIntent();

        String clientId = intent.getStringExtra("CLIENT_ID");
        String clientLastName = intent.getStringExtra("CLIENT_LAST_NAME");
        String clientFirstName = intent.getStringExtra("CLIENT_FIRST_NAME");
        String clientAddress = intent.getStringExtra("CLIENT_ADDRESS");
        String clientCity = intent.getStringExtra("CLIENT_CITY");
        String clientPhone = intent.getStringExtra("CLIENT_PHONE");
        String clientPhone2 = intent.getStringExtra("CLIENT_PHONE2");
        String clientDevice = intent.getStringExtra("CLIENT_DEVICE");
        String clientSerialNumber = intent.getStringExtra("CLIENT_SERIAL_NUMBER");
        String clientEntryDate = intent.getStringExtra("CLIENT_ENTRY_DATE");
        String clientMessageDate = intent.getStringExtra("CLIENT_MESSAGE_DATE");
        String clientComments = intent.getStringExtra("CLIENT_COMMENTS");

        profileId.setText(clientId);
        profileLastName.setText(clientLastName);
        profileFirstName.setText(clientFirstName);
        profileAddress.setText(clientAddress);
        profileCity.setText(clientCity);
        profilePhone.setText(clientPhone);
        profilePhone2.setText(clientPhone2);
        profileDevice.setText(clientDevice);
        profileSerialNumber.setText(clientSerialNumber);
        profileEntryDate.setText(clientEntryDate);
        profileMessageDate.setText(clientMessageDate);
        profileComments.setText(clientComments);
    }
}
