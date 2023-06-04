package easy.tuto.bottomnavigationfragmentdemo.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import easy.tuto.bottomnavigationfragmentdemo.LoginActivity;
import easy.tuto.bottomnavigationfragmentdemo.R;
import easy.tuto.bottomnavigationfragmentdemo.profile.ProfileActivity;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "Benjamin " + getClass().getSimpleName();
    private ListView listView;
    private BoxeAdapter adapter;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = findViewById(R.id.listView);
        List<Box> itemList = new ArrayList<>();
        Boxes boxes= new Boxes();
        bottomNavigationView  = findViewById(R.id.bottom_navigation);


        // Initialize Firebase Auth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Créer l'adaptateur et l'associer à la ListView
        adapter = new BoxeAdapter(this, boxes.getBoxes());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, HomeActivityDetail.class);
                intent.putExtra("selectedIndex", position);
                String name="boite"+(position+1);
                System.out.println("name"+name);
                intent.putExtra("selectedItemName", name);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(false);
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
}
