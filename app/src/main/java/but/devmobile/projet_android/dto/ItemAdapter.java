package but.devmobile.projet_android.dto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import but.devmobile.projet_android.R;

public class ItemAdapter extends ArrayAdapter<Item> {


    private Integer layout;
    public ItemAdapter(Context context, List<Item> objects, Integer layout) {
        super(context, 0, objects);
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }

        Item selectedItem = getItem(position);

        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemName = convertView.findViewById(R.id.itemName);
        TextView itemRate;

        Picasso.get().load(selectedItem.getItemPicture()).into(itemImage);
        itemName.setText(selectedItem.getItemName());

        if(layout == R.layout.item_element){
            itemRate = convertView.findViewById(R.id.itemRate);
            itemRate.setText(String.valueOf(selectedItem.getItemRate()));
        }


        return convertView;
    }
}
