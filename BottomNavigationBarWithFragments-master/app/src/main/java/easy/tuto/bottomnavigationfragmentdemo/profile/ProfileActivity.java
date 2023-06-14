package easy.tuto.bottomnavigationfragmentdemo.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import easy.tuto.bottomnavigationfragmentdemo.LoginActivity;
import easy.tuto.bottomnavigationfragmentdemo.R;
import easy.tuto.bottomnavigationfragmentdemo.home.HomeActivity;
import easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick.SelectedItemActivity;

public class ProfileActivity extends AppCompatActivity {
    private ListView selectedItemsRecyclerView;
    BottomNavigationView bottomNavigationView;
    private BoxeAdapterProfile adapter;
    private List<String> allItems;
    private HashMap<String, Integer> itemImageMap; // HashMap pour la correspondance nom d'élément - ID d'image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Changer avec le nom de votre layout
        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        allItems = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        selectedItemsRecyclerView = findViewById(R.id.listviewprofile);

        adapter = new BoxeAdapterProfile(this, new ArrayList<>());
        selectedItemsRecyclerView.setAdapter(adapter);

        itemImageMap = createItemImageMap(); // Créer la correspondance nom d'élément ID d'image

        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).child("selectedItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<String> selectedItem = snapshot.getValue(typeIndicator);

                    if (selectedItem != null && selectedItem.size() > 0) {
                        // Ajouter tous les éléments récupérés à la liste allItems

                        String selectedItemID = snapshot.getKey();
                        allItems.add(selectedItemID);
                        allItems.addAll(selectedItem);
                        String selectedItemName = selectedItem.get(0);
                        String selectedItemText = selectedItem.get(0);
                        System.out.println("selectedItemName"+selectedItemName);

                        // Obtenir l'ID d'image correspondant au nom d'élément à partir de la HashMap
                        String selectedItemImageResId = selectedItemName;

                        Box selectedModel = new Box(selectedItemImageResId, selectedItemText);
                        adapter.add(selectedModel);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileActivity", "onCancelled", databaseError.toException());
            }
        });

        selectedItemsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Box selectedModel = adapter.getItem(position);
                if (selectedModel != null) {
                    // Passer l'élément sélectionné à une nouvelle Activity
                    Intent intent = new Intent(ProfileActivity.this, SelectedItemActivity.class);
                    intent.putExtra("imageResId", selectedModel.getImageResId());
                    intent.putExtra("boite", selectedModel.getText());
                    intent.putStringArrayListExtra("allItems", (ArrayList<String>) allItems);
                    intent.putExtra("idBoite",allItems.get(position*8));
                    startActivity(intent);
                }
            }
        });

        Button btn = findViewById(R.id.btnLogout);
        btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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
                        //bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(false);
                        return true;
                }
                return false;
            }
        });
    }

    // Méthode pour créer la correspondance nom d'élément - ID d'image
    private HashMap<String, Integer> createItemImageMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Boîte 1", R.drawable.boite1);
        map.put("Boîte 2", R.drawable.boite2);
        map.put("Boîte 3", R.drawable.boite3);
        map.put("Boîte 4", R.drawable.boite4);
        map.put("Boîte 5", R.drawable.boite5);
        return map;
    }
}