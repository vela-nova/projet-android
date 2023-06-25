package but.devmobile.projet_android.dto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import but.devmobile.projet_android.R;

public class BoxAdapter extends ArrayAdapter<Box> {

    public BoxAdapter(Context context, List<Box> boxes) {
        super(context, 0, boxes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.box_element, parent, false);
        }

        Box currentBox = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        Picasso.get().load(currentBox.getBoxPicture()).into(imageView);
        textView.setText(currentBox.getBoxName());

        return convertView;
    }
}