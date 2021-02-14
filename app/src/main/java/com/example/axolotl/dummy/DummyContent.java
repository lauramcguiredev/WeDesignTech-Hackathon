package com.example.axolotl.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {
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
        String[] x1 = {"Modular Parts:", "None\n", "Warranty:", "Expires 02/10/25", "Covers spill protection",
                "Covers accidental drops", "Covers hardware failure\n", "Support Contact:", "1-800-263-3394\n",
                "Trade In:", "Available with Apple", "Local Donation Locations:", "http://www.rcto.ca/"};
        addItem(new DummyItem("1111-1111-1111", "Apple Macbook Pro 13 M1 (2021)", x1));
        String[] x2 = {"Modular Parts:", "NVMe SSD\n", "Warranty:", "Expires 11/09/23", "Covers spill protection",
                "Covers accidental drops", "Covers hardware failure\n", "Support Contact:", "1-800-474-6836\n",
                "Trade In:", "Available with HP\n", "Local Donation Locations:", "http://www.rcto.ca/"};
        addItem(new DummyItem("2345-6789-0000", "HP ENVY 14 (2021)", x2));
        String[] x3 = {"Modular Parts:", "NVMe SSD\n", "Warranty:", "Expires 10/10/25", "Covers hardware failure\n",
                "Support Contact:", "1-866-426-0911\n", "Trade In:", "Available with Lenovo\n", "Local Donation Locations",
                "http://www.rcto.ca/"};
        addItem(new DummyItem("6783-3467-9999", "Lenovo X1 Carbon 6th Gen", x3));
        String[] x4 = {"Modular Parts:", "2 DDR4 Slots", "2 NVMe SSD Slots\n", "Warranty:", "Expires 10/10/25", "Covers hardware failure\n",
                "Support Contact:", "1-800-847-4096\n", "Trade In:", "Available with Dell\n", "Local Donation Locations",
                "http://www.rcto.ca/"};
        addItem(new DummyItem("1111-3467-9999", "Dell XPS 15 (2020)", x4));
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