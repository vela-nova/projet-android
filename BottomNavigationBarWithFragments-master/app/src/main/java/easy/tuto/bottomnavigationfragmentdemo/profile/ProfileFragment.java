package easy.tuto.bottomnavigationfragmentdemo.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.LoginActivity;
import easy.tuto.bottomnavigationfragmentdemo.R;
import easy.tuto.bottomnavigationfragmentdemo.profile.modelAdapterProfile;
import easy.tuto.bottomnavigationfragmentdemo.profile.modelProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.profile.modelAdapterProfile;
import easy.tuto.bottomnavigationfragmentdemo.profile.modelProfile;

public class ProfileFragment extends Fragment {
    Activity context;
    private ListView selectedItemsRecyclerView;
    private modelAdapterProfile adapter;
    private HashMap<String, Integer> itemImageMap; // HashMap pour la correspondance nom d'élément - ID d'image

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        selectedItemsRecyclerView = view.findViewById(R.id.listviewprofile);

        adapter = new modelAdapterProfile(requireContext(), new ArrayList<>());
        selectedItemsRecyclerView.setAdapter(adapter);

        itemImageMap = createItemImageMap(); // Créer la correspondance nom d'élément - ID d'image

        String userId = auth.getCurrentUser().getUid();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("users").child(userId).child("selectedItems").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    List<String> selectedItem = snapshot.getValue(typeIndicator);

                    if (selectedItem != null && selectedItem.size() > 0) {
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

        Button btn = view.findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
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
