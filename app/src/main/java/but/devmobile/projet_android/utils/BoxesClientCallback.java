package but.devmobile.projet_android.utils;

import java.util.ArrayList;

import but.devmobile.projet_android.dto.Box;

public interface BoxesClientCallback {
    void onSuccess(ArrayList<Box> result);
    void onFailure(Exception e);
}
