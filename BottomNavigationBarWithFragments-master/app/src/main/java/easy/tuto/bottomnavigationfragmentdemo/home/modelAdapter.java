package easy.tuto.bottomnavigationfragmentdemo.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class modelAdapter extends ArrayAdapter<model> {

    public modelAdapter(Context context, List<model> models) {
        super(context, 0, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        model currentModel = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        imageView.setImageResource(currentModel.getImageResId());
        textView.setText(currentModel.getText());

        return convertView;
    }
}