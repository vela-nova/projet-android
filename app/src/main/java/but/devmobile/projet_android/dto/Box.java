package but.devmobile.projet_android.dto;

import java.util.ArrayList;
import java.util.Random;

public class Box {
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
        this.items = new ArrayList<Item>();
    }

    public Item openBox(){
        Random random = new Random();
        Integer roll = random.nextInt(100)+1;
        System.out.print(roll);
        Double cumulativeRate = 0.0;
        for(Item item:items){
            cumulativeRate +=item.getItemRate()*100;
            System.out.print(cumulativeRate);
            if (cumulativeRate>=roll){
                System.out.print("ding ding ding we have a winner");
                return item;
            }
        }
        return null;
    }
    public void addItem(Item item){
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
}
