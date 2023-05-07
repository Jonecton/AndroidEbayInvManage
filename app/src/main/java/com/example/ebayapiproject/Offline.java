package com.example.ebayapiproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Offline extends AppCompatActivity {

    private TextView viewInventoryTextView;
    private TextView viewCurrentListingsTextView;
    private TextView searchEbayTextView;
    private TextView addListingTextView;
    private TextView editListingTextView;
    private TextView messagesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        viewInventoryTextView = findViewById(R.id.btn_view_inventory);
        viewCurrentListingsTextView = findViewById(R.id.btn_view_current_listings);
        searchEbayTextView = findViewById(R.id.btn_search_ebay);
        addListingTextView = findViewById(R.id.btn_add_listing);
        editListingTextView = findViewById(R.id.btn_edit_listing);
        messagesTextView = findViewById(R.id.btn_messages);

        searchEbayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });

        viewInventoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Offline.this, ViewInventory.class);
                startActivity(intent);
            }
        });
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search eBay");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String searchString = input.getText().toString();
                searchEbay(searchString);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void searchEbay(String searchString) {
        //Pass the search string to the SearchResultActivity
        Intent intent = new Intent(Offline.this, SearchResultsActivity.class);
        intent.putExtra("searchString", searchString);
        startActivity(intent);
    }
}
