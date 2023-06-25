package but.devmobile.projet_android.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import but.devmobile.projet_android.dto.Box;
import but.devmobile.projet_android.dto.Boxes;
import but.devmobile.projet_android.dto.Item;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
    private OkHttpClient client;
    private final String boxesUrl = "http://androidapi.aminhe.live/";

    public HttpClient() {
        this.client = new OkHttpClient();
    }

    public void getBoxData(final BoxesClientCallback callback){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(boxesUrl)
                            .build();


                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        ArrayList<Box> boxes = new ArrayList<>();

                        String responseBody = response.body().string();
                        JSONObject responseJson = new JSONObject(responseBody);
                        JSONArray boxesJson = responseJson.getJSONArray("boxes");


                        for(int i=0; i<boxesJson.length();i++){
                            Box box = new Box();
                            JSONObject boxJson = boxesJson.getJSONObject(i);
                            box.setBoxId(boxJson.getInt("boxId"));
                            box.setBoxName(boxJson.getString("boxName"));
                            box.setBoxPicture(boxJson.getString("boxPicture"));
                            JSONArray itemsJson = boxJson.getJSONArray("items");

                            for (int y=0; y<itemsJson.length();y++){
                                Item item = new Item();

                                JSONObject itemJson = itemsJson.getJSONObject(y);
                                item.setItemName(itemJson.getString("itemName"));
                                item.setItemPicture(itemJson.getString("itemPicture"));
                                item.setItemRate(itemJson.getDouble("itemRate"));

                                box.addItem(item);
                            }
                            boxes.add(box);
                        }

                        callback.onSuccess(boxes);

                    } else {
                        callback.onFailure(new Exception(response.message()));
                    }
                } catch (Exception e) {
                    callback.onFailure(e);
                }
                finally {
                    System.out.println("THREAD END");
                }
            }
        });

        thread.start();
    }
}
