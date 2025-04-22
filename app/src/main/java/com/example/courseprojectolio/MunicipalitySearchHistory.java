package com.example.courseprojectolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MunicipalitySearchHistory {
    private static final String PREFS = "search_prefs";
    private static final String KEY_HISTORY = "history";

    private final SharedPreferences prefs;

    public MunicipalitySearchHistory(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public List<String> getAllSearches() {
        String csv = prefs.getString(KEY_HISTORY, "");
        if (csv.isEmpty()) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(csv.split(",")));
    }

    public void addSearch(String municipality) {
        List<String> list = getAllSearches();
        list.remove(municipality);
        list.add(0, municipality);
        if (list.size() > 5) list = list.subList(0, 5);

        String csv = TextUtils.join(",", list);
        prefs.edit().putString(KEY_HISTORY, csv).apply();
    }
}
