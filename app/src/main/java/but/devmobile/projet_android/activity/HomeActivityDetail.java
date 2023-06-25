package but.devmobile.projet_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class HomeActivityDetail extends AppCompatActivity  implements BoxesClientCallback {
    private Integer selectedBoxId;
    private Box selectedBox;
    private ItemAdapter itemAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Boolean isBoxOwned;
    private String selectedBoxInstanceReference;

    private void getBoxes(){
        HttpClient httpClient = new HttpClient();
        httpClient.getBoxData(this);
    }

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
        selectedBoxId = getIntent().getIntExtra("selectedBoxId", 0);
        isBoxOwned = getIntent().getBooleanExtra("isBoxOwned", false);

        if (isBoxOwned){
            saveToFirebaseButtonTop.setVisibility(View.INVISIBLE);
            buyAndOpenButton.setVisibility(View.INVISIBLE);
            openButton.setVisibility(View.VISIBLE);
            selectedBoxInstanceReference = getIntent().getStringExtra("selectedBoxInstanceReference");
        }


        getBoxes();


        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item itemObtained = selectedBox.openBox();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(selectedBoxInstanceReference);
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

    @Override
    public void onSuccess(ArrayList<Box> result) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    selectedBox = result.get(selectedBoxId);

                    TextView boxDetailName = findViewById(R.id.box_detail_name);
                    ImageView boxDetailPicture = findViewById(R.id.box_detail_picture);

                    // Définir le texte de la vue TextView
                    boxDetailName.setText(selectedBox.getBoxName());
                    Picasso.get().load(selectedBox.getBoxPicture()).into(boxDetailPicture);


                    ListView listView = findViewById(R.id.listViewItems);
                    itemAdapter = new ItemAdapter(HomeActivityDetail.this, selectedBox.getItems(), R.layout.item_element);
                    listView.setAdapter(itemAdapter);
                }
                finally {
                    System.out.println("HomeActivity : Fin du thread qui crée la liste des boites");
                }
            }
        });
    }

    @Override
    public void onFailure(Exception e) {

    }
}
