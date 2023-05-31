package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class SelectedItemFragment extends Fragment {
    private static final String ARG_ITEM_TEXT = "argItemText";
    private static final String ARG_ITEM_IMAGE_RES_ID = "argItemImageResId";
    private static final String ARG_ALL_ITEMS = "allItems";

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


    public SelectedItemFragment() {
        // Required empty public constructor
    }

    public static SelectedItemFragment newInstance() {
        SelectedItemFragment fragment = new SelectedItemFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.usersId = String.valueOf(database.getReference("users"));

        if (getArguments() != null) {
            this.itemBoite = getArguments().getString("boite");
            this.allItems = getArguments().getStringArrayList(ARG_ALL_ITEMS);
            this.idBoite= getArguments().getString("idBoite");
            ItemsBoite = new ArrayList<>();
            for (int i = 0; i < this.allItems.size(); i++) {
                if (this.allItems.get(i).equals(this.idBoite)) {
                    for (int j = i; j < i + 8; j++) {
                        ItemsBoite.add(String.valueOf(this.allItems.get(j)));
                    }
                    break;
                }
            }
            this.nomIntBoite=Math.abs(ItemsBoite.get(1).indexOf(6));
            this.itemBoite=itemBoite;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_profile, container, false);

        TextView textView = view.findViewById(R.id.selectedItemTextView);
        ListView listView = view.findViewById(R.id.selectedItemListView);
        Button backButton = view.findViewById(R.id.backButton);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference();
        AtomicReference<String> usersRef = new AtomicReference<>(String.valueOf(database.getReference("users")));

        textView.setText(itemBoite);

        ArrayList<modelProfileDetail> items = new ArrayList<>();
        items.add(new modelProfileDetail(R.drawable.nounours1, "Nounours 1",this.ItemsBoite.get(1)+"%"));
        items.add(new modelProfileDetail(R.drawable.nounours2, "Nounours 2",this.ItemsBoite.get(2)+"%"));
        items.add(new modelProfileDetail(R.drawable.nounours3, "Nounours 3",this.ItemsBoite.get(3)+"%"));
        items.add(new modelProfileDetail(R.drawable.nounours4, "Nounours 4",this.ItemsBoite.get(4)+"%"));
        items.add(new modelProfileDetail(R.drawable.nounours5, "Nounours 5",this.ItemsBoite.get(5)+"%"));

        adapter = new modelAdapterDetail(getContext(), items);
        listView.setAdapter(adapter);

        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Fonction lotterie
        lotteryButton = view.findViewById(R.id.lotteryButton);
        winningItemTextView = view.findViewById(R.id.textlottery);
        if(this.ItemsBoite.get(7).contentEquals("off")){
            winningItemTextView.setText("");
        }
        else{
            winningItemTextView.setText(ItemsBoite.get(7));
            lotteryButton.setVisibility(View.GONE);
        }
/*
        data.child("users").child(String.valueOf(usersRef)).child("selectedItems").child(this.idBoite).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null && value.equals("off")) {
                    System.out.println("valeur :"+value);
                    Log.d("SelectedItems", "La valeur est off");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("SelectedItems", "Erreur lors de la récupération de la valeur", databaseError.toException());
            }
        });*/
        lotteryButton.setOnClickListener(v -> {
            if(this.ItemsBoite.get(7).equals("off")) {
                Lottery lottery = new Lottery(this.nomIntBoite);
                int winningIndex = lottery.getWinningItemIndex();
                String element = null;
                String winningItemName = lottery.getWinningItemName();
                switch (winningItemName) {
                case "Élément 1":
                    element="nounours1";
                    break;
                case "Élément 2":
                    element="nounours2";
                    break;
                case "Élément 3":
                    element="nounours3";
                    break;
                case "Élément 4":
                    element="nounours4";
                    break;
                case "Élément 5":
                    element="nounours5";
                    break;
                default:
                    System.out.println("Option par défaut sélectionnée");
                    break;
                }

                winningItemTextView.setText("Vous avez gagné le nounours: " + winningItemName);
                ItemsBoite.set(7,winningItemName);

                //Enregistrement dans Firebase
                this.usersRef = database.getReference("users");
                DatabaseReference databaseRef = this.usersRef.child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
                databaseRef.child("selectedItems").child(this.idBoite).child("6").setValue(element);
                lotteryButton.setVisibility(View.GONE);
            }});
        return view;
    }

}