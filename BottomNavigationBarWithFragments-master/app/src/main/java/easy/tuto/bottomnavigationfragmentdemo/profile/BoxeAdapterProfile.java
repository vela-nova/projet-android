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

public class BoxeAdapterProfile extends ArrayAdapter<Box> {

    public BoxeAdapterProfile(Context context, List<Box> Box) {
        super(context, 0, Box);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Box currentModel = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        if (currentModel != null) {
            String imageFileName = currentModel.getImageResId();
            int drawableResourceId = getContext().getResources().getIdentifier(
                    imageFileName, "drawable", getContext().getPackageName()
            );
            imageView.setImageResource(drawableResourceId);

            textView.setText(currentModel.getText());
        }

        return convertView;
    }
}
