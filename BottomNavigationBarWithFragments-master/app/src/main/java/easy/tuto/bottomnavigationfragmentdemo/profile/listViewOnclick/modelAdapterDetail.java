package easy.tuto.bottomnavigationfragmentdemo.profile.listViewOnclick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class modelAdapterDetail extends ArrayAdapter<modelProfileDetail> {

    public modelAdapterDetail(Context context, List<modelProfileDetail> models) {
        super(context, 0, models);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items_details_profile, parent, false);
        }

        modelProfileDetail currentModel = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.selectedItemImageDetail);
        TextView textView = convertView.findViewById(R.id.selectedItemTextView1);
        TextView textViewDetail = convertView.findViewById(R.id.selectedItemTextView2);

        imageView.setImageResource(currentModel.getImageResId());
        textView.setText(currentModel.getText());
        textViewDetail.setText(currentModel.getTextViewText());

        return convertView;
    }
}