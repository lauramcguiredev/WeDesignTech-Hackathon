package com.example.axolotl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.axolotl.dummy.DummyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * An activity representing a list of Devices. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DeviceDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DeviceListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    TextView deviceHeader;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference dataRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        if (findViewById(R.id.device_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.device_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        final Button logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        final ImageView home = findViewById(R.id.homeIcon);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        deviceHeader = findViewById(R.id.deviceHeader);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("users");
        getName(dataRef.child(user.getUid()).child("name"));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final DeviceListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(DeviceDetailFragment.ARG_ITEM_ID, item.id);
                    DeviceDetailFragment fragment = new DeviceDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.device_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DeviceDetailActivity.class);
                    intent.putExtra(DeviceDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(DeviceListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.device_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).deviceType);
            holder.mContentView.setText(mValues.get(position).id);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    private void getName(DatabaseReference dataReference) {
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.getValue(String.class);
                deviceHeader.setText(uName + "'s Devices");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        };
        dataReference.addValueEventListener(nameListener);
    }
}