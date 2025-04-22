package com.example.courseprojectolio;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TabActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        String municipality = getIntent().getStringExtra("municipality");

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabPagerAdapter adapter = new TabPagerAdapter(this, municipality);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText("Municipality"); break;
                        case 1: tab.setText("Weather");      break;
                        case 2: tab.setText("Home");         break;
                    }
                }
        ).attach();
    }
}
