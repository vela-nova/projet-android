package but.devmobile.projet_android.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String itemName;
    private String itemPicture;
    private Double itemRate;

    public Item() {
        this.itemName = null;
        this.itemPicture = null;
        this.itemRate = null;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(String itemPicture) {
        this.itemPicture = itemPicture;
    }

    public Double getItemRate() {
        return itemRate;
    }

    public void setItemRate(Double itemRate) {
        this.itemRate = itemRate;
    }

    protected Item(Parcel in) {
        itemName = in.readString();
        itemPicture = in.readString();
        if (in.readByte() == 0) {
            itemRate = null;
        } else {
            itemRate = in.readDouble();
        }
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemPicture);
        if (itemRate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(itemRate);
        }
    }
}
