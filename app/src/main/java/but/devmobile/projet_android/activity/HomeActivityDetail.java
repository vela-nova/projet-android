package but.devmobile.projet_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import but.devmobile.projet_android.R;
import but.devmobile.projet_android.dto.Box;
import but.devmobile.projet_android.dto.Item;
import but.devmobile.projet_android.dto.ItemAdapter;
import but.devmobile.projet_android.utils.BoxesClientCallback;
import but.devmobile.projet_android.utils.HttpClient;

public class HomeActivityDetail extends AppCompatActivity {

    private Box selectedBox;
    private ItemAdapter itemAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Boolean isBoxOwned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currUser == null){
            startActivity(new Intent(HomeActivityDetail.this, LoginActivity.class));
            finish();
        }
        setContentView(R.layout.activity_box_detail);

        Button buyAndOpenButton = findViewById(R.id.buyAndOpenButton);

        Button backButton = findViewById(R.id.backButton);

        Button saveToFirebaseButtonTop = findViewById(R.id.buyButton);

        Button openButton = findViewById(R.id.openButton);

        // cette ligne sert a recuperer la boite souhaitée selon l'id envoyée par l'intent
        selectedBox = getIntent().getParcelableExtra("selectedBox");
        isBoxOwned = getIntent().getBooleanExtra("isBoxOwned", false);

        if (isBoxOwned){
            saveToFirebaseButtonTop.setVisibility(View.INVISIBLE);
            buyAndOpenButton.setVisibility(View.INVISIBLE);
            openButton.setVisibility(View.VISIBLE);
        }

        TextView boxDetailName = findViewById(R.id.box_detail_name);
        ImageView boxDetailPicture = findViewById(R.id.box_detail_picture);

        // Définir le texte de la vue TextView
        boxDetailName.setText(selectedBox.getBoxName());
        Picasso.get().load(selectedBox.getBoxPicture()).into(boxDetailPicture);


        ListView listView = findViewById(R.id.listViewItems);
        itemAdapter = new ItemAdapter(HomeActivityDetail.this, selectedBox.getItems(), R.layout.item_element);
        listView.setAdapter(itemAdapter);



        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itemObtained = selectedBox.openBox();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(selectedBox.getBoxInstanceReference());
                reference.removeValue();

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                // Récupérer l'identifiant de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Enregistrer l'élément sélectionné dans Firebase sous l'identifiant de l'utilisateur
                database.child("users").child(userId).child("ownedItems").push().setValue(itemObtained);
                Toast.makeText(HomeActivityDetail.this, itemObtained.getItemName() + " obtenu !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivityDetail.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        buyAndOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itemObtained = selectedBox.openBox();

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                // Récupérer l'identifiant de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Enregistrer l'élément sélectionné dans Firebase sous l'identifiant de l'utilisateur
                database.child("users").child(userId).child("ownedItems").push().setValue(itemObtained);
                Toast.makeText(HomeActivityDetail.this, itemObtained.getItemName() + " obtenu !", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        saveToFirebaseButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.animation_buy);

                // Appliquer l'animation au bouton
                saveToFirebaseButtonTop.startAnimation(animation);

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                // Récupérer l'identifiant de l'utilisateur
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Enregistrer l'élément sélectionné dans Firebase sous l'identifiant de l'utilisateur
                database.child("users").child(userId).child("ownedBoxes").push().setValue(selectedBox);
                // Afficher un Toast
                Toast.makeText(HomeActivityDetail.this, "Commande effectuée", Toast.LENGTH_SHORT).show();


            }
        });
    }
}
