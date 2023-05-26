package easy.tuto.bottomnavigationfragmentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {
    Activity context;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //logoutButton = view.findViewById(R.id.btnLogout);

        /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onStart(){
        super.onStart();
        Button btn = (Button) context.findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
    }

}