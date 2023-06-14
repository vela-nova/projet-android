package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class SelectedItemActivity extends AppCompatActivity {
    private String itemBoite;
    private String idBoite;
    private ArrayList<String> ItemsBoite;
    private ArrayList<String> allItems;
    private modelAdapterDetail adapter;
    private Button lotteryButton;
    private TextView winningItemTextView;
    private String usersId;
    private Integer nomIntBoite;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details_profile);

        if (getIntent().getExtras() != null) {
            this.itemBoite = getIntent().getStringExtra("boite");
            this.allItems = getIntent().getStringArrayListExtra("allItems");
            this.idBoite = getIntent().getStringExtra("idBoite");
            ItemsBoite = new ArrayList<>();
            for (int i = 0; i < this.allItems.size(); i++) {
                if (Objects.equals(this.allItems.get(i), this.idBoite)) {
                    for (int j = i; j < i + 8; j++) {
                        ItemsBoite.add(String.valueOf(this.allItems.get(j)));
                    }
                    break;
                }
            }
            this.nomIntBoite = Math.abs(ItemsBoite.get(1).indexOf(6));
            this.itemBoite = itemBoite;
        }

        TextView textView = findViewById(R.id.selectedItemTextView);
        ListView listView = findViewById(R.id.selectedItemListView);
        Button backButton = findViewById(R.id.backButton);

        textView.setText(itemBoite);

        ArrayList<modelProfileDetail> items = new ArrayList<>();
        items.add(new modelProfileDetail(R.drawable.nounours1, "Nounours 1", this.ItemsBoite.get(1) + "%"));
        items.add(new modelProfileDetail(R.drawable.nounours2, "Nounours 2", this.ItemsBoite.get(2) + "%"));
        items.add(new modelProfileDetail(R.drawable.nounours3, "Nounours 3", this.ItemsBoite.get(3) + "%"));
        items.add(new modelProfileDetail(R.drawable.nounours4, "Nounours 4", this.ItemsBoite.get(4) + "%"));
        items.add(new modelProfileDetail(R.drawable.nounours5, "Nounours 5", this.ItemsBoite.get(5) + "%"));

        adapter = new modelAdapterDetail(this, items);
        listView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        // Fonction lottery
        lotteryButton = findViewById(R.id.lotteryButton);
        winningItemTextView = findViewById(R.id.textlottery);
        if (this.ItemsBoite.get(7).contentEquals("off")) {
            winningItemTextView.setText("");
        } else {
            winningItemTextView.setText(ItemsBoite.get(7));
            lotteryButton.setVisibility(View.GONE);
        }

        // Onclick Lottery
        lotteryButton.setOnClickListener(v -> {
            if (this.ItemsBoite.get(7).equals("off")) {
                Lottery lottery = new Lottery(this.nomIntBoite);
                int winningIndex = lottery.getWinningItemIndex();
                String element = null;
                String winningItemName = lottery.getWinningItemName();
                switch (winningItemName) {
                    case "Élément 1":
                        element = "nounours1";
                        break;
                    case "Élément 2":
                        element = "nounours2";
                        break;
                    case "Élément 3":
                        element = "nounours3";
                        break;
                    case "Élément 4":
                        element = "nounours4";
                        break;
                    case "Élément 5":
                        element = "nounours5";
                        break;
                    default:
                        System.out.println("Option par défaut sélectionnée");
                        break;
                }

                winningItemTextView.setText("Vous avez gagné le nounours: " + winningItemName);
                ItemsBoite.set(7, winningItemName);

                // Enregistrement dans Firebase
                this.usersRef = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference databaseRef = this.usersRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                databaseRef.child("selectedItems").child(this.idBoite).child("6").setValue(element);
                lotteryButton.setVisibility(View.GONE);
            }
        });
    }
}
