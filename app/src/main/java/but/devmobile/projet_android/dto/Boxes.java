package but.devmobile.projet_android.dto;

import java.util.ArrayList;

import but.devmobile.projet_android.R;
import but.devmobile.projet_android.utils.BoxesClientCallback;
import but.devmobile.projet_android.utils.HttpClient;

public class Boxes{
    private ArrayList<Box> boxes=new ArrayList<>();

    public Boxes(){

    }
    public Box getBox(Integer i){
        return boxes.get(i);
    }
    public Integer getSize(){
        return boxes.size();
    }
    public ArrayList<Box> getBoxes(){
        return boxes;
    }
}


