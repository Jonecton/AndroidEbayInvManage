package com.example.ebayapiproject;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class ViewInventory extends AppCompatActivity {
    private static final String PREFS_NAME = "inventory_prefs";
    private static final String INVENTORY_DATA_KEY = "inventory_data";
    private List<InventoryItem> inventoryItems;

    private void applyFilter(String filter) {
        List<InventoryItem> filteredItems = new ArrayList<>();

        if (!filter.isEmpty()) {
            for (InventoryItem item : inventoryItems) {
                if (item.getTitle().toLowerCase().contains(filter.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
        } else {
            filteredItems = inventoryItems;
        }

        TableLayout table = findViewById(R.id.inventory_table);
        populateTable(table, filteredItems);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        loadInventoryData();

        TableLayout table = findViewById(R.id.inventory_table);
        populateTable(table, inventoryItems);

        setupFilterButton();
        setupSortButton();
        setupSearchBar();
        setupFilterButton();
        setupSortButton();
        setupSearchBar();
        setupRemoveButton();
        setupAddItemButton();
    }
    private void saveInventoryData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(inventoryItems);
        editor.putString(INVENTORY_DATA_KEY, json);
        editor.apply();
    }
    private void loadInventoryData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(INVENTORY_DATA_KEY, null);
        Type type = new TypeToken<ArrayList<InventoryItem>>() {}.getType();
        inventoryItems = gson.fromJson(json, type);

        if (inventoryItems == null) {
            inventoryItems = new ArrayList<>();
        }
    }

    private void populateTable(TableLayout table, List<InventoryItem> items) {
        // Remove all existing rows except the header row
        int childCount = table.getChildCount();
        for (int i = 1; i < childCount; i++) {
            table.removeViewAt(1);
        }

        for (InventoryItem item : items) {
            TableRow row = new TableRow(this);

            TextView id = new TextView(this);
            id.setText(String.valueOf(item.getId()));
            id.setMaxWidth(60);
            row.addView(id);

            TextView title = new TextView(this);
            String truncatedTitle = item.getTitle().length() > 8 ? item.getTitle().substring(0, 8) + "…" : item.getTitle();
            title.setText(truncatedTitle);
            title.setMaxWidth(80);
            row.addView(title);

            TextView type = new TextView(this);
            type.setText(item.getType());
            row.addView(type);

            TextView brand = new TextView(this);
            brand.setText(item.getBrand());
            row.addView(brand);

            TextView size = new TextView(this);
            size.setText(item.getSize());
            row.addView(size);

            TextView cost = new TextView(this);
            cost.setText(String.format("%.2f", item.getCost()));
            row.addView(cost);

            TextView listPrice = new TextView(this);
            listPrice.setText(String.format("%.2f", item.getListPrice()));
            row.addView(listPrice);

            CheckBox remove = new CheckBox(this);
            row.addView(remove);

            table.addView(row);
        }
    }
    private void addRowToTable(TableLayout table, InventoryItem item) {
        TableRow row = new TableRow(this);

        TextView id = new TextView(this);
        id.setText(String.valueOf(item.getId()));
        row.addView(id);

        TextView title = new TextView(this);
        title.setText(item.getTitle());
        row.addView(title);

        TextView type = new TextView(this);
        type.setText(item.getType());
        row.addView(type);

        TextView brand = new TextView(this);
        brand.setText(item.getBrand());
        row.addView(brand);

        TextView size = new TextView(this);
        size.setText(item.getSize());
        row.addView(size);

        TextView cost = new TextView(this);
        cost.setText(String.format("%.2f", item.getCost()));
        row.addView(cost);

        TextView listPrice = new TextView(this);
        listPrice.setText(String.format("%.2f", item.getListPrice()));
        row.addView(listPrice);

        CheckBox remove = new CheckBox(this);
        row.addView(remove);

        table.addView(row);
    }

    private void removeFromInventory(int itemId) {
        Iterator<InventoryItem> iterator = inventoryItems.iterator();

        while (iterator.hasNext()) {
            InventoryItem item = iterator.next();
            if (item.getId() == itemId) {
                iterator.remove();
                break;
            }
        }
    }

    private void removeSelectedItems() {
        TableLayout table = findViewById(R.id.inventory_table);
        int childCount = table.getChildCount();

        for (int i = childCount - 1; i >= 1; i--) {
            TableRow row = (TableRow) table.getChildAt(i);
            CheckBox removeCheckBox = (CheckBox) row.getChildAt(row.getChildCount() - 1);

            if (removeCheckBox.isChecked()) {
                int itemId = Integer.parseInt(((TextView) row.getChildAt(0)).getText().toString());
                removeFromInventory(itemId);
                table.removeView(row);
            }
        }
        saveInventoryData();
    }

    private void showRemoveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Selected Items")
                .setMessage("Are you sure you want to remove the selected items?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeSelectedItems();
                    }
                })
                .setNegativeButton("No", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupRemoveButton() {
        Button removeButton = findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveConfirmationDialog();
            }
        });
    }

    private void applyCategoryFilter(String filter) {
        List<InventoryItem> filteredItems = new ArrayList<>();

        if (!filter.equalsIgnoreCase("All")) {
            for (InventoryItem item : inventoryItems) {
                if (item.getType().equalsIgnoreCase(filter)) {
                    filteredItems.add(item);
                }
            }
        } else {
            filteredItems = inventoryItems;
        }

        TableLayout table = findViewById(R.id.inventory_table);
        populateTable(table, filteredItems);
    }


    private void setupFilterButton() {
        Spinner filterSpinner = findViewById(R.id.filter_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String filterOption = parent.getItemAtPosition(position).toString();
                applyCategoryFilter(filterOption);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void setupSortButton() {
        Button sortButton = findViewById(R.id.sort_button);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortOptionsDialog();
            }
        });
    }
    private void applySort(String sortOption) {
        List<InventoryItem> sortedItems = new ArrayList<>(inventoryItems);

        switch (sortOption) {
            case "ID":
                Collections.sort(sortedItems, new Comparator<InventoryItem>() {
                    @Override
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                });
                break;
            case "Type":
                Collections.sort(sortedItems, new Comparator<InventoryItem>() {
                    @Override
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return o1.getType().compareTo(o2.getType());
                    }
                });
                break;
            case "Brand":
                Collections.sort(sortedItems, new Comparator<InventoryItem>() {
                    @Override
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return o1.getBrand().compareTo(o2.getBrand());
                    }
                });
                break;
            case "Size":
                Collections.sort(sortedItems, new Comparator<InventoryItem>() {
                    @Override
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return o1.getSize().compareTo(o2.getSize());
                    }
                });
                break;
            case "Cost":
                Collections.sort(sortedItems, new Comparator<InventoryItem>() {
                    @Override
                    public int compare(InventoryItem o1, InventoryItem o2) {
                        return Double.compare(o1.getCost(), o2.getCost());
                    }
                });
                break;
        }

        TableLayout table = findViewById(R.id.inventory_table);
        populateTable(table, sortedItems);
    }


    private void showSortOptionsDialog() {
        final String[] sortOptions = new String[]{"ID", "Type", "Brand", "Size", "Cost"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOption = sortOptions[which];
                        applySort(selectedOption);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addNewItem(View addItemView) {
        EditText titleInput = addItemView.findViewById(R.id.title_input);
        Spinner typeSpinner = addItemView.findViewById(R.id.type_spinner);
        EditText brandInput = addItemView.findViewById(R.id.brand_input);
        EditText sizeInput = addItemView.findViewById(R.id.size_input);
        EditText costInput = addItemView.findViewById(R.id.cost_input);

        String title = titleInput.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String brand = brandInput.getText().toString();
        String size = sizeInput.getText().toString();
        double cost = Double.parseDouble(costInput.getText().toString());

        int newId = generateNewId();
        InventoryItem newItem = new InventoryItem(newId, title, type, brand, size, cost, 0);
        inventoryItems.add(newItem);
        saveInventoryData();
        TableLayout table = findViewById(R.id.inventory_table);
        addRowToTable(table, newItem);
    }

    private int generateNewId() {
        int maxId = 0;
        for (InventoryItem item : inventoryItems) {
            maxId = Math.max(maxId, item.getId());
        }
        return maxId + 1;
    }

    private void showAddItemDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View addItemView = inflater.inflate(R.layout.dialog_add_item, null);

        Spinner typeSpinner = addItemView.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Item")
                .setView(addItemView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addNewItem(addItemView);
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupAddItemButton() {
        Button addItemButton = findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });
    }



    private void setupSearchBar() {
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                applyFilter(s.toString());
            }
        });
    }

}
