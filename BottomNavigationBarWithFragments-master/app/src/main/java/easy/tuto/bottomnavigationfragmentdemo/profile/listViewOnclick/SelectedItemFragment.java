package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import easy.tuto.bottomnavigationfragmentdemo.R;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import easy.tuto.bottomnavigationfragmentdemo.R;
import easy.tuto.bottomnavigationfragmentdemo.profile.ProfileFragment;
import easy.tuto.bottomnavigationfragmentdemo.profile.modelAdapterProfile;
import easy.tuto.bottomnavigationfragmentdemo.profile.modelProfile;

public class SelectedItemFragment extends Fragment {
    private static final String ARG_ITEM_TEXT = "argItemText";
    private static final String ARG_ITEM_IMAGE_RES_ID = "argItemImageResId";

    private String itemText;
    private int itemImageResId;

    private ImageListAdapter adapter;

    public SelectedItemFragment() {
        // Required empty public constructor
    }

    public static SelectedItemFragment newInstance(String itemText, int itemImageResId) {
        SelectedItemFragment fragment = new SelectedItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_TEXT, itemText);
        args.putInt(ARG_ITEM_IMAGE_RES_ID, itemImageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemText = getArguments().getString(ARG_ITEM_TEXT);
            itemImageResId = getArguments().getInt(ARG_ITEM_IMAGE_RES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_profile, container, false);

        TextView textView = view.findViewById(R.id.selectedItemTextView);
        ListView listView = view.findViewById(R.id.selectedItemListView);
        Button backButton = view.findViewById(R.id.backButton);

        textView.setText(itemText);

        ArrayList<modelProfile> items = new ArrayList<>();
        items.add(new modelProfile(R.drawable.boite1, "Boîte 1"));
        items.add(new modelProfile(R.drawable.boite2, "Boîte 2"));
        items.add(new modelProfile(R.drawable.boite3, "Boîte 3"));
        items.add(new modelProfile(R.drawable.boite4, "Boîte 4"));
        items.add(new modelProfile(R.drawable.boite5, "Boîte 5"));

        adapter = new ImageListAdapter(getContext(), items);
        listView.setAdapter(adapter);

        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());


        return view;
    }

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