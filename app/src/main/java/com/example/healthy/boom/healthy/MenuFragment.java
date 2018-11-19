package com.example.healthy.boom.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthy.boom.healthy.Post.PostFragment;
import com.example.healthy.boom.healthy.Sleep.SleepFormFragment;
import com.example.healthy.boom.healthy.Sleep.SleepHistoryFragment;
import com.example.healthy.boom.healthy.weight.WeightFromFragment;
import com.example.healthy.boom.healthy.weight.WeightHistoryFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private List<String> _menu;
    private FirebaseAuth firebaseAuth;

    public MenuFragment() {
        this._menu = new ArrayList<>();
        this.firebaseAuth = FirebaseAuth.getInstance();

        _menu.add("BMI");
        _menu.add("Add Weight");
        _menu.add("Weight History");
        _menu.add("Add Sleep");
        _menu.add("Sleep History");
        _menu.add("Post");
        _menu.add("Logout");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showMenu();
    }

    public void showMenu() {

        /**
         * First: create List and ArrayAdapter
         * Second: findViewById(ListView)
         * Third: just combine between List and ArrayAdapter
         * Fourth: setAdapter for ListView => ListView.setAdapter
         */
        // Define Adapter with ArrayList
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                _menu
        );

        // Define ListView
        ListView menuList = getView().findViewById(R.id.menu_list);

        //ListView set Adapter
        menuList.setAdapter(menuAdapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("MENU", "Click on menu: " + _menu.get(position));

                if (position == 0) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                } else if (position == 1) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFromFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                } else if (position == 2){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightHistoryFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                } else if (position == 3) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepFormFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                } else if (position == 4) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepHistoryFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                } else if (position == 5) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new PostFragment())
                            .addToBackStack(null)
                            .commit()
                    ;
                }
                else {
                    firebaseAuth.signOut();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .commit()
                    ;
                }
            }
        });
    }

}