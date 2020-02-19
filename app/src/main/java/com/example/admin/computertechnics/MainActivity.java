package com.example.admin.computertechnics;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton mainAddButton, mainMessagesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainAddButton = (ImageButton) findViewById(R.id.main_add_button);
        mainAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddClientActivity.class);
                startActivity(intent);
            }
        });

        mainMessagesButton = (ImageButton) findViewById(R.id.main_messages_button);
        mainMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayClientsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Κωδικός ή Επίθετο");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String id = null;
                String lastName = null;
                String firstName = null;
                String address = null;
                String city = null;
                String phone = null;
                String phone2 = null;
                String device = null;
                String serialNumber = null;
                String entryDate = null;
                String messageDate = null;
                try {
                    Cursor cursor = dbHelper.getClientInfo(db, query);
                    cursor.moveToFirst();
                    id = cursor.getString(0);
                    lastName = cursor.getString(1);
                    firstName = cursor.getString(2);
                    address = cursor.getString(3);
                    city = cursor.getString(4);
                    phone = cursor.getString(5);
                    phone2 = cursor.getString(6);
                    device = cursor.getString(7);
                    serialNumber = cursor.getString(8);
                    entryDate = cursor.getString(9);
                    messageDate = cursor.getString(10);

                    Intent profileIntent = new Intent(getApplicationContext(), ViewProfileActivity.class);
                    profileIntent.putExtra("CLIENT_ID", id);
                    profileIntent.putExtra("CLIENT_LAST_NAME", lastName);
                    profileIntent.putExtra("CLIENT_FIRST_NAME", firstName);
                    profileIntent.putExtra("CLIENT_ADDRESS", address);
                    profileIntent.putExtra("CLIENT_CITY", city);
                    profileIntent.putExtra("CLIENT_PHONE", phone);
                    profileIntent.putExtra("CLIENT_PHONE2", phone2);
                    profileIntent.putExtra("CLIENT_DEVICE", device);
                    profileIntent.putExtra("CLIENT_SERIAL_NUMBER", serialNumber);
                    profileIntent.putExtra("CLIENT_ENTRY_DATE", entryDate);
                    profileIntent.putExtra("CLIENT_MESSAGE_DATE", messageDate);
                    startActivity(profileIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.toast_no_such_entry, Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
