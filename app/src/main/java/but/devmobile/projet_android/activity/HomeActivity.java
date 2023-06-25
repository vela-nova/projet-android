package but.devmobile.projet_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import but.devmobile.projet_android.R;
import but.devmobile.projet_android.dto.Box;
import but.devmobile.projet_android.dto.BoxAdapter;
import but.devmobile.projet_android.dto.ItemAdapter;
import but.devmobile.projet_android.utils.BoxesClientCallback;
import but.devmobile.projet_android.utils.HttpClient;

public class HomeActivity extends AppCompatActivity implements BoxesClientCallback {

    private List<Box> allBoxes;
    private List<Box> currentBoxes;
    private ListView boxList;
    private SearchView searchView;
    private BoxAdapter boxAdapter;
    BottomNavigationView bottomNavigationView;


    protected void getBoxes(){
        HttpClient httpClient = new HttpClient();
        httpClient.getBoxData(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currUser == null){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        setContentView(R.layout.activity_home);

        boxList = findViewById(R.id.list_boxes);
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        searchView = findViewById(R.id.search_view);

        bottomNavigationView.getMenu().findItem(R.id.settings).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);

        getBoxes();


        boxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, HomeActivityDetail.class);
                intent.putExtra("selectedBoxId", boxAdapter.getItem(position).getBoxId());
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.settings:
                        intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    public void searchBoxes(String text){
        currentBoxes.clear();
        for (Box box:allBoxes){
            if (box.getBoxName().toLowerCase().contains(text.toLowerCase())){
                currentBoxes.add(box);
            }
        }
        boxAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(ArrayList<Box> result) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    allBoxes = result;
                    currentBoxes = new ArrayList<>(result);
                    boxAdapter = new BoxAdapter(HomeActivity.this, currentBoxes);
                    boxList.setAdapter(boxAdapter);


                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String text) {
                            searchBoxes(text);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String text) {
                            searchBoxes(text);
                            return true;
                        }
                    });
                }
                finally {
                    System.out.println("HomeActivity : Fin du thread qui crée la liste des boites");
                }
            }
        });

    }

    @Override
    public void onFailure(Exception e) {

    }
}
