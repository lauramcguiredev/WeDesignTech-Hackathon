package com.example.axolotl.dummy;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dataRef = database.getReference("users");
    private FirebaseUser user = mAuth.getCurrentUser();

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        //Query Devices from Database
        String[] x = {"Hello", "Test"};
        addItem(new DummyItem("1111-1111-1111", "Apple Macbook Pro", x));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String deviceType;
        public String[] modular;

        public DummyItem(String id, String type, String[] modularParts) {
            this.id = id;
            this.deviceType = type;
            this.modular = modularParts;
        }

        //NOT SURE ABOUT THIS
        @Override
        public String toString() {
            return deviceType;
        }
    }
}