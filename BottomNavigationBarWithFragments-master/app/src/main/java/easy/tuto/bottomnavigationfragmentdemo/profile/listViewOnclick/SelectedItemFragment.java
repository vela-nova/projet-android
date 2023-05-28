/*package easy.tuto.bottomnavigationfragmentdemo.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class SelectedItemFragment extends Fragment {

    private List<Integer> imageResIds;
    private ListView listView;
    private ArrayAdapter<Integer> adapter;

    public static SelectedItemFragment newInstance(List<String> imageResIds) {
        SelectedItemFragment fragment = new SelectedItemFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("imageResIds", new ArrayList<>(imageResIds));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResIds = getArguments().getIntegerArrayList("imageResIds");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_profile, container, false);

        listView = view.findViewById(R.id.selectedItemTextView);
        adapter = new ImageAdapter(imageResIds);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click event if needed
            }
        });

        return view;
    }

    private class ImageAdapter extends ArrayAdapter<Integer> {

        ImageAdapter(List<Integer> imageResIds) {
            super(requireContext(), 0, imageResIds);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_details_profile, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);
            int imageResId = getItem(position);
            imageView.setImageResource(imageResId);

            return convertView;
        }
    }
}
*/