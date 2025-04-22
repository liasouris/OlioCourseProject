package com.example.courseprojectolio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText municipalitySearchText;
    private Button fetchDataButton;
    private RecyclerView recentSearchesRecyclerView;
    private MunicipalityListAdapter adapter;
    private MunicipalitySearchHistory searchHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        municipalitySearchText     = findViewById(R.id.municipalitySearchText);
        fetchDataButton            = findViewById(R.id.fetchDataButton);
        recentSearchesRecyclerView = findViewById(R.id.recentSearchesRecyclerView);

        // 1) Initialize your history storage
        searchHistory = new MunicipalitySearchHistory(this);

        // 2) Create & set up the RecyclerView adapter
        List<String> initialHistory = searchHistory.getAllSearches();
        adapter = new MunicipalityListAdapter(
                initialHistory,
                new MunicipalityListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String municipality) {
                        launchTabActivity(municipality);
                    }
                }
        );
        recentSearchesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        recentSearchesRecyclerView.setAdapter(adapter);

        // 3) Wire up the Fetch Data button
        fetchDataButton.setOnClickListener(v -> {
            String muni = municipalitySearchText.getText()
                    .toString()
                    .trim();
            if (muni.isEmpty()) {
                municipalitySearchText.setError("Enter a municipality");
                return;
            }
            // Save to history and refresh list
            searchHistory.addSearch(muni);
            adapter.updateData(searchHistory.getAllSearches());
            // Launch the tabbed details view
            launchTabActivity(muni);
        });
    }

    /**
     * Helper to start the TabActivity, carrying the selected municipality.
     */
    private void launchTabActivity(String municipality) {
        Intent intent = new Intent(this, TabActivity.class);
        intent.putExtra("municipality", municipality);
        startActivity(intent);
    }
}
