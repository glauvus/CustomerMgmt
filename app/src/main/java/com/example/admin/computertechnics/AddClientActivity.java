package com.example.admin.computertechnics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddClientActivity extends AppCompatActivity {

    EditText idField, lastNameField, firstNameField, addressField, cityField,
            phoneField, phone2Field, deviceField, serialNumberField, commentsField;
    CheckBox bagCheckBox, powerSupplyCheckBox, batteryCheckBox;
    Button addClientButton;
    String id, lastName, firstName, address, city, phone, phone2,
            device, serialNumber, bag, powerSupply, battery, comments, entryDate;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        idField = (EditText) findViewById(R.id.id_field);
        lastNameField = (EditText) findViewById(R.id.lastName_field);
        firstNameField = (EditText) findViewById(R.id.firstName_field);
        addressField = (EditText) findViewById(R.id.address_field);
        cityField = (EditText) findViewById(R.id.city_field);
        phoneField = (EditText) findViewById(R.id.phone_field);
        phone2Field = (EditText) findViewById(R.id.phone2_field);
        deviceField = (EditText) findViewById(R.id.device_field);
        serialNumberField = (EditText) findViewById(R.id.serialNumber_field);
        bagCheckBox = (CheckBox) findViewById(R.id.bag_checkBox);
        powerSupplyCheckBox = (CheckBox) findViewById(R.id.power_supply_checkBox);
        batteryCheckBox = (CheckBox) findViewById(R.id.battery_checkBox);
        commentsField = (EditText) findViewById(R.id.comments_field);
        addClientButton = (Button) findViewById(R.id.add_client_button);
        dbHelper = new DatabaseHelper(this);

        addClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idField.getText().toString();
                lastName = lastNameField.getText().toString();
                firstName = firstNameField.getText().toString();
                address = addressField.getText().toString();
                city = cityField.getText().toString();
                phone = phoneField.getText().toString();
                phone2 = phone2Field.getText().toString();
                device = deviceField.getText().toString();
                serialNumber = serialNumberField.getText().toString();
                comments = commentsField.getText().toString();
                if(bagCheckBox.isChecked())
                    bag = "Y";
                if(powerSupplyCheckBox.isChecked())
                    powerSupply = "Y";
                if(batteryCheckBox.isChecked())
                    battery = "Y";

                Date today = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                entryDate = sdf.format(today);

                dbHelper.addInfo(id, lastName, firstName, address, city, phone, phone2,
                        device, serialNumber, bag, powerSupply, battery, comments, entryDate, "Όχι ακόμα!", 0);


                Toast.makeText(getApplicationContext(), R.string.toast_added, Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}
