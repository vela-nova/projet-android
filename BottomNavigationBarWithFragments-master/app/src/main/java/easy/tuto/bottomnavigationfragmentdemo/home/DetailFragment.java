package easy.tuto.bottomnavigationfragmentdemo.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;
import easy.tuto.bottomnavigationfragmentdemo.home.HomeFragment;

public class DetailFragment extends Fragment {
    private int selectedIndex;
    private String selectedItemName;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("users");

    private int[][] imageResIds = {
            {R.drawable.nounours1, R.drawable.nounours2, R.drawable.nounours3, R.drawable.nounours4, R.drawable.nounours5},
            {R.drawable.nounours1, R.drawable.nounours2, R.drawable.nounours3, R.drawable.nounours4, R.drawable.nounours5},
            {R.drawable.nounours1, R.drawable.nounours2, R.drawable.nounours3, R.drawable.nounours4, R.drawable.nounours5},
            {R.drawable.nounours1, R.drawable.nounours2, R.drawable.nounours3, R.drawable.nounours4, R.drawable.nounours5},
            {R.drawable.nounours1, R.drawable.nounours2, R.drawable.nounours3, R.drawable.nounours4, R.drawable.nounours5},
            // Include more image resource arrays here for each list item
    };

    private String[][] texts = {
            {"5", "10", "20", "30", "40"},
            {"5", "10", "20", "30", "40"},
            {"5", "10", "15", "30", "40"},
            {"5", "10", "25", "30", "40"},
            {"5", "10", "30", "30", "40"},
            {"5", "10", "40", "50", "60"},
            // Include more text arrays here for each list item
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        if (getArguments() != null) {
            selectedIndex = getArguments().getInt("selectedIndex");
            // Récupérer le nom de l'item sélectionné
            selectedItemName = getArguments().getString("selectedItemName");
        }

        // Récupérer la référence à la vue TextView
        TextView selectedItemNameTextView = view.findViewById(R.id.textNomBoite);
        // Définir le texte de la vue TextView
        selectedItemNameTextView.setText(selectedItemName);

        ImageView[] imageViews = {
                view.findViewById(R.id.imageView1),
                view.findViewById(R.id.imageView2),
                view.findViewById(R.id.imageView3),
                view.findViewById(R.id.imageView4),
                view.findViewById(R.id.imageView5)
        };

        TextView[] textViews = {
                view.findViewById(R.id.textView1),
                view.findViewById(R.id.textView2),
                view.findViewById(R.id.textView3),
                view.findViewById(R.id.textView4),
                view.findViewById(R.id.textView5)
        };


        for (int i = 0; i < 5; i++) {
            imageViews[i].setImageResource(imageResIds[selectedIndex][i]);
            textViews[i].setText(texts[selectedIndex][i]);
        }

        Button button = view.findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
            }
        });

        Button saveToFirebaseButton = view.findViewById(R.id.saveButton);

        saveToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                // Récupérer l'identifiant de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



                // Ajouter le nom de l'item sélectionné en première position de la liste
                List<String> selectedItemText = new ArrayList<>(Arrays.asList(texts[selectedIndex]));

                selectedItemText.add(0, selectedItemName);

                // Enregistrer l'élément sélectionné dans Firebase sous l'identifiant de l'utilisateur
                database.child("users").child(userId).child("selectedItems").push().setValue(selectedItemText);
                // Afficher un Toast
                Toast.makeText(requireContext(), "Commande effectuée", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}