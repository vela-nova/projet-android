package easy.tuto.bottomnavigationfragmentdemo.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.R;
public class modelAdapterProfile extends ArrayAdapter<modelProfile> {

    public modelAdapterProfile(Context context, List<modelProfile> modelProfiles) {
        super(context, 0, modelProfiles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        modelProfile currentModel = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        if (currentModel != null) {
            int imageResId = currentModel.getImageResId();
            if (imageResId != 0) {
                imageView.setImageResource(imageResId);
            }
            textView.setText(currentModel.getText());
        }

        return convertView;
    }
}
