package easy.tuto.bottomnavigationfragmentdemo.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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

public class ProfileFragment extends Fragment {
    Activity context;
    private ListView selectedItemsRecyclerView;
    private modelAdapterProfile adapter;
    private List<String> allItems;
    private HashMap<String, Integer> itemImageMap; // HashMap pour la correspondance nom d'élément - ID d'image

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        allItems = new ArrayList<>();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        selectedItemsRecyclerView = view.findViewById(R.id.listviewprofile);

        adapter = new modelAdapterProfile(requireContext(), new ArrayList<>());
        selectedItemsRecyclerView.setAdapter(adapter);

        itemImageMap = createItemImageMap(); // Créer la correspondance nom d'élément - ID d'image :)

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

                        // Obtenir l'ID d'image correspondant au nom d'élément à partir de la HashMap
                        int selectedItemImageResId = itemImageMap.get(selectedItemName);

                        modelProfile selectedModel = new modelProfile(selectedItemImageResId, selectedItemText);
                        adapter.add(selectedModel);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProfileFragment", "onCancelled", databaseError.toException());
            }
        });

        selectedItemsRecyclerView.setOnItemClickListener((parent, view1, position, id) -> {
            modelProfile selectedModel = adapter.getItem(position);
            if (selectedModel != null) {
                // Passer l'élément sélectionné à une nouvelle fragment
                Fragment newFragment = new easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick.SelectedItemFragment();
                Bundle args = new Bundle();
                args.putInt("imageResId", selectedModel.getImageResId());
                args.putString("boite", selectedModel.getText());
                args.putStringArrayList("allItems", (ArrayList<String>) allItems);
                args.putString("idBoite",allItems.get(position*8));
                System.out.println(allItems.get(position*8));
                newFragment.setArguments(args);

                // Remplacer le fragment actuel par la nouvelle fragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btn = view.findViewById(R.id.btnLogout);
        btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        });

        return view;
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
