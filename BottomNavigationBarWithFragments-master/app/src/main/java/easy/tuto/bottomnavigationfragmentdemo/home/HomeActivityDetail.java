package easy.tuto.bottomnavigationfragmentdemo.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class HomeActivityDetail extends AppCompatActivity {
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
    };

    private String[][] texts = {
            {"5", "10", "15", "30", "40"},
            {"5", "10", "20", "30", "40"},
            {"5", "10", "15", "30", "40"},
            {"5", "10", "25", "30", "40"},
            {"5", "10", "30", "30", "40"},
            {"5", "10", "40", "50", "60"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);


        // Récupérer les valeurs de l'Intent
        selectedIndex = getIntent().getIntExtra("selectedIndex", 0);
        selectedItemName = getIntent().getStringExtra("selectedItemName");
        System.out.println("selectedItem"+selectedItemName);
        // Récupérer la référence à la vue TextView
        TextView selectedItemNameTextView = findViewById(R.id.textNomBoite);
        // Définir le texte de la vue TextView
        selectedItemNameTextView.setText("boite "+selectedIndex);

        ImageView[] imageViews = {
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5)
        };

        TextView[] textViews = {
                findViewById(R.id.textView1),
                findViewById(R.id.textView2),
                findViewById(R.id.textView3),
                findViewById(R.id.textView4),
                findViewById(R.id.textView5)
        };


        for (int i = 0; i < 5; i++) {
            imageViews[i].setImageResource(imageResIds[selectedIndex][i]);
            textViews[i].setText(texts[selectedIndex][i]);
        }

        Button button = findViewById(R.id.backButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityDetail.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Button saveToFirebaseButton = findViewById(R.id.saveButton);

        saveToFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                // Récupérer l'identifiant de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Ajouter le nom de l'item sélectionné en première position de la liste
                List<String> selectedItemText = new ArrayList<>(Arrays.asList(texts[selectedIndex]));
                selectedItemText.add(0, selectedItemName);

                // Ajouter l'élément lottery
                selectedItemText.add(6,"off");

                // Enregistrer l'élément sélectionné dans Firebase sous l'identifiant de l'utilisateur
                database.child("users").child(userId).child("selectedItems").push().setValue(selectedItemText);
                // Afficher un Toast
                Toast.makeText(HomeActivityDetail.this, "Commande effectuée", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
