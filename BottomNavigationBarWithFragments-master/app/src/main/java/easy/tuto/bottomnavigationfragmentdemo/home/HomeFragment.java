package easy.tuto.bottomnavigationfragmentdemo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class HomeFragment extends Fragment {
    private ListView listView;
    private modelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);

        // Créer une liste d'items en dur
        List<model> itemList = new ArrayList<>();
        itemList.add(new model(R.drawable.nounours1, "Boîte 1"));
        itemList.add(new model(R.drawable.nounours2, "Boîte 2"));
        itemList.add(new model(R.drawable.nounours3, "Boîte 3"));
        itemList.add(new model(R.drawable.nounours4, "Boîte 4"));
        itemList.add(new model(R.drawable.nounours5, "Boîte 5"));

        // Créer l'adaptateur et l'associer à la ListView
        adapter = new modelAdapter(requireContext(), itemList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("selectedIndex", position);
                // Ajouter le nom de l'item sélectionné au Bundle
                bundle.putString("selectedItemName", itemList.get(position).getText()); // Remplacer getName() par la méthode que vous utilisez pour obtenir le nom de l'item

                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, detailFragment)
                        .commit();
            }
        });

        return view;
    }
}