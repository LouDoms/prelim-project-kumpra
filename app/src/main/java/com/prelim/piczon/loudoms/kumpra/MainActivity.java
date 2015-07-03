package com.prelim.piczon.loudoms.kumpra;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    EditText edtItem;
    EditText edtQuantity;
    Button send;

    ListView listahan;

    ArrayList<GroceryList> listThingsToBuy = new ArrayList<GroceryList>();
    DatabaseHelper db;

    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listahan = (ListView) findViewById(R.id.listahan);
        edtItem = (EditText) findViewById(R.id.edtItem);
        edtQuantity = (EditText) findViewById(R.id.edtQuantity);

        registerForContextMenu(listahan);
        populateListView();

        send = (Button) findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Uri uri = Uri.parse("smsto:09331336116");
                    Intent list = new Intent(Intent.ACTION_SENDTO, uri);
                    list.putExtra("sms_body", listThingsToBuy.toString());
                    startActivity(list);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_contact:
                deleteItem(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }




    public void populateListView() {
        db = new DatabaseHelper(this);
        listThingsToBuy = db.getList();
        db.close();
        GroceryListAdapter adapter = new GroceryListAdapter(listThingsToBuy, this);
        listahan.setAdapter(adapter);

    }

    public void add(View v) {
        String item = edtItem.getText().toString();
        String quantity = edtQuantity.getText().toString();

        addToDatabase(item, quantity);
        populateListView();
        edtItem.setText("");
        edtQuantity.setText("");
    }

    public void deleteItem(long id) {
        GroceryList c = listThingsToBuy.get((int) id);
        db.deleteItem(String.valueOf(c.getId()));
        populateListView();
        Toast.makeText(this, "Successfully deleted", Toast.LENGTH_LONG).show();
    }

    public void addToDatabase(String item, String quantity) {

        GroceryList list = new GroceryList(item, quantity);
        DatabaseHelper db = new DatabaseHelper(this);

        db.addItem(list);
        Toast.makeText(this, "Successfully Added Item", Toast.LENGTH_LONG).show();

        db.close();
    }

}