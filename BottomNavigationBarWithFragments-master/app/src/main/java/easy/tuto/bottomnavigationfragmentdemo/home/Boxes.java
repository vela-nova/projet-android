package easy.tuto.bottomnavigationfragmentdemo.home;

import java.util.ArrayList;

import easy.tuto.bottomnavigationfragmentdemo.R;

public class Boxes {
    private ArrayList<Box> boxes=new ArrayList<>();

    public Boxes(){
        boxes.add(new Box(R.drawable.nounours1, "Boîte 1"));
        boxes.add(new Box(R.drawable.nounours2, "Boîte 2"));
        boxes.add(new Box(R.drawable.nounours3, "Boîte 3"));
        boxes.add(new Box(R.drawable.nounours4, "Boîte 4"));
        boxes.add(new Box(R.drawable.nounours5, "Boîte 5"));
    }

    public ArrayList<Box> getBoxes(){
        return boxes;
    }
}


class Box {
    private int imageResId;
    private String text;

    public Box(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
