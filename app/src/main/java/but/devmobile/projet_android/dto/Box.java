package but.devmobile.projet_android.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

import but.devmobile.projet_android.dto.Item;

public class Box implements Parcelable {
    private Integer boxId;
    private String boxInstanceReference;
    private String boxName;
    private String boxPicture;
    private ArrayList<Item> items;

    public Box() {
        this.boxId = null;
        this.boxInstanceReference = null;
        this.boxName = null;
        this.boxPicture = null;
        this.items = new ArrayList<>();
    }

    public Item openBox() {
        Random random = new Random();
        Integer roll = random.nextInt(100) + 1;
        System.out.print(roll);
        Double cumulativeRate = 0.0;
        for (Item item : items) {
            cumulativeRate += item.getItemRate() * 100;
            System.out.print(cumulativeRate);
            if (cumulativeRate >= roll) {
                System.out.print("ding ding ding we have a winner");
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getBoxPicture() {
        return boxPicture;
    }

    public void setBoxPicture(String boxPicture) {
        this.boxPicture = boxPicture;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }

    public String getBoxInstanceReference() {
        return boxInstanceReference;
    }

    public void setBoxInstanceReference(String boxInstanceReference) {
        this.boxInstanceReference = boxInstanceReference;
    }

    public static final Creator<Box> CREATOR = new Creator<Box>() {
        @Override
        public Box createFromParcel(Parcel in) {
            return new Box(in);
        }

        @Override
        public Box[] newArray(int size) {
            return new Box[size];
        }
    };

    protected Box(Parcel in) {
        boxId = in.readInt();
        boxInstanceReference = in.readString();
        boxName = in.readString();
        boxPicture = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(boxId);
        dest.writeString(boxInstanceReference);
        dest.writeString(boxName);
        dest.writeString(boxPicture);
        dest.writeTypedList(items);
    }
}
