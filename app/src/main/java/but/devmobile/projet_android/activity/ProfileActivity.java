package but.devmobile.projet_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import but.devmobile.projet_android.R;
import but.devmobile.projet_android.dto.Box;
import but.devmobile.projet_android.dto.BoxAdapter;
import but.devmobile.projet_android.dto.Item;
import but.devmobile.projet_android.dto.ItemAdapter;


public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private ItemAdapter itemAdapter;
    private BoxAdapter boxAdapter;
    private HashMap<String, Integer> itemImageMap; // HashMap pour la correspondance nom d'élément - ID d'image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currUser == null){
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_profile); // Changer avec le nom de votre layout
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(true);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        TextView boxesTitle = findViewById(R.id.boxes_title);

        TextView itemsTitle = findViewById(R.id.items_title);

        ListView ownedBoxesList = findViewById(R.id.list_owned_boxes);

        ListView ownedItemsList = findViewById(R.id.list_owned_items);



        boxAdapter = new BoxAdapter(this, new ArrayList<>());
        ownedBoxesList.setAdapter(boxAdapter);

        itemAdapter = new ItemAdapter(this, new ArrayList<>(), R.layout.item_element_profile);
        ownedItemsList.setAdapter(itemAdapter);

        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        SwitchMaterial switchMaterial = findViewById(R.id.switch_material);

        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boxesTitle.setVisibility(View.INVISIBLE);
                    ownedBoxesList.setVisibility(View.INVISIBLE);

                    itemsTitle.setVisibility(View.VISIBLE);
                    ownedItemsList.setVisibility(View.VISIBLE);
                } else {
                    itemsTitle.setVisibility(View.INVISIBLE);
                    ownedItemsList.setVisibility(View.INVISIBLE);

                    boxesTitle.setVisibility(View.VISIBLE);
                    ownedBoxesList.setVisibility(View.VISIBLE);
                }
            }
        });

        database.child("users").child(userId).child("ownedBoxes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Box selectedBox = snapshot.getValue(Box.class);

                    if (selectedBox != null) {
                        System.out.println(snapshot.getRef().toString());
                        selectedBox.setBoxInstanceReference(snapshot.getRef().toString());
                        boxAdapter.add(selectedBox);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileActivity", "onCancelled", databaseError.toException());
            }
        });

        database.child("users").child(userId).child("ownedItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Item selectedItem = snapshot.getValue(Item.class);

                    if (selectedItem != null) {
                        System.out.println(snapshot.getRef().toString());
                        itemAdapter.add(selectedItem);


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileActivity", "onCancelled", databaseError.toException());
            }
        });

        ownedBoxesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Box selectedBox = boxAdapter.getItem(position);
                if (selectedBox != null) {
                    // Passer l'élément sélectionné à une nouvelle Activity
                    Intent intent = new Intent(ProfileActivity.this, HomeActivityDetail.class);
                    intent.putExtra("selectedBoxId", selectedBox.getBoxId());
                    intent.putExtra("isBoxOwned", true);
                    intent.putExtra("selectedBoxInstanceReference", selectedBox.getBoxInstanceReference());
                    startActivity(intent);
                }
            }
        });

        Button btn = findViewById(R.id.btnLogout);
        btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home:
                        intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });
    }
}