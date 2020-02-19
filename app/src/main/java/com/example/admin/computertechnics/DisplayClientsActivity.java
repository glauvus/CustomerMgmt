package com.example.admin.computertechnics;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DisplayClientsActivity extends AppCompatActivity {

    ListView listView;
    DatabaseHelper dbHelper;
    ArrayList<Client> clients;
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clients);

        listView = (ListView) findViewById(R.id.list_view);
        dbHelper = new DatabaseHelper(this);
        clients = new ArrayList<>();

        //Opening database and getting all table values by getInfo
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getInfo(db);

        //Getting today's date
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedToday = sdf.format(today);

        //Scanning database row by row and getting columns' values
        while (cursor.moveToNext()) {
            String entryDate = cursor.getString(13);
            int finished = cursor.getInt(15);
            if (entryDate.equals(formattedToday) || finished == 0) {
                String id = cursor.getString(0);
                String lastName = cursor.getString(1);
                String phone = cursor.getString(5);

                //Creating a client with each row's values and adding them to a list
                Client client = new Client(id, lastName, phone, finished);
                clients.add(client);
            }
        }

        //Instantiating our custom adapter and setting it to the listView
        myListAdapter = new MyListAdapter(this, R.layout.list_row, clients);
        listView.setAdapter(myListAdapter);

        registerForContextMenu(listView);

        db.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.client_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Instance to get the position of the clicked item
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Getting selected client's data
        final Client selectedClient = clients.get(info.position);

        //Click handling
        switch(item.getItemId()) {
            case R.id.message_client:
                String SMS_SENT = "SMS_SENT";
                String SMS_DELIVERED = "SMS_DELIVERED";
                String smsToBeSent = "αβγ δέζ ηδθί";

                PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
                PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

                //For when the sms has been sent
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(context, R.string.toast_sms_sent, Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(context, R.string.toast_sms_not_sent, Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(context, R.string.toast_service_unavailable, Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                Toast.makeText(context, R.string.toast_sms_not_sent, Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_RADIO_OFF:
                                Toast.makeText(context, R.string.toast_service_unavailable, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter(SMS_SENT));

                //For when the sms has been delivered
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                selectedClient.setFinished();
                                dbHelper.updateStatus(selectedClient.getId());
                                myListAdapter.notifyDataSetChanged();

                                dbHelper.addMessageDate(selectedClient.getId());

                                Toast.makeText(getBaseContext(), R.string.toast_sms_delivered, Toast.LENGTH_SHORT).show();
                                break;
                            case Activity.RESULT_CANCELED:
                                Toast.makeText(getBaseContext(), R.string.toast_sms_not_delivered, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter(SMS_DELIVERED));

                //Getting the default instance of SmsManager and sending the sms
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(selectedClient.getPhone(), null, smsToBeSent, sentPendingIntent, deliveredPendingIntent);
                return true;


            case R.id.call_client:
                Uri number = Uri.parse("tel:" + selectedClient.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                return true;

            case R.id.delete_client:
                dbHelper.deleteClient(selectedClient.getId());
                clients.remove(clients.get(info.position));
                myListAdapter.notifyDataSetChanged();
                Toast.makeText(this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.view_profile:
                //Getting values of selected client from database
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = dbHelper.getClientInfo(db, selectedClient.getId());
                cursor.moveToFirst();
                String id = cursor.getString(0);
                String lastName = cursor.getString(1);
                String firstName = cursor.getString(2);
                String address = cursor.getString(3);
                String city = cursor.getString(4);
                String phone = cursor.getString(5);
                String phone2 = cursor.getString(6);
                String device = cursor.getString(7);
                String serialNumber = cursor.getString(8);
                String bag = cursor.getString(9);
                String powerSupply = cursor.getString(10);
                String battery = cursor.getString(11);
                String comments = cursor.getString(12);
                String entryDate = cursor.getString(13);
                String messageDate = cursor.getString(14);
                db.close();

                //Passing above values to ViewProfileActivity and start that activity
                Intent profileIntent = new Intent(this, ViewProfileActivity.class);
                profileIntent.putExtra("CLIENT_ID", id);
                profileIntent.putExtra("CLIENT_LAST_NAME", lastName);
                profileIntent.putExtra("CLIENT_FIRST_NAME", firstName);
                profileIntent.putExtra("CLIENT_ADDRESS", address);
                profileIntent.putExtra("CLIENT_CITY", city);
                profileIntent.putExtra("CLIENT_PHONE", phone);
                profileIntent.putExtra("CLIENT_PHONE2", phone2);
                profileIntent.putExtra("CLIENT_DEVICE", device);
                profileIntent.putExtra("CLIENT_SERIAL_NUMBER", serialNumber);
                profileIntent.putExtra("CLIENT_BAG", bag);
                profileIntent.putExtra("CLIENT_POWER_SUPPLY", powerSupply);
                profileIntent.putExtra("CLIENT_BATTERY", battery);
                profileIntent.putExtra("CLIENT_COMMENTS", comments);
                profileIntent.putExtra("CLIENT_ENTRY_DATE", entryDate);
                profileIntent.putExtra("CLIENT_MESSAGE_DATE", messageDate);
                startActivity(profileIntent);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
