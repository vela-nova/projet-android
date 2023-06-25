package but.devmobile.projet_android.dto;

public class Item {
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
}
