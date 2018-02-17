package com.crackerteg.adhellreborn.fragments;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.crackerteg.adhellreborn.R;
import com.crackerteg.adhellreborn.db.AppDatabase;
import com.crackerteg.adhellreborn.db.entity.UserBlockUrl;

import java.util.ArrayList;
import java.util.List;

public class BlockCustomUrlFragment extends LifecycleFragment {

    private List<String> customUrlsToBlock;
    private Context context;
    private AppDatabase appDatabase;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getContext());
        customUrlsToBlock = new ArrayList<>();
        context = this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_url_block, container, false);
        ListView listView = (ListView) view.findViewById(R.id.customUrlsListView);
        appDatabase.userBlockUrlDao()
                .getAll()
                .observe(this, userBlockUrls -> {
                    customUrlsToBlock.clear();
                    if (userBlockUrls != null) {
                        for (UserBlockUrl userBlockUrl : userBlockUrls) {
                            customUrlsToBlock.add(userBlockUrl.url);
                        }
                    }
                    itemsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, customUrlsToBlock);
                    listView.setAdapter(itemsAdapter);
                });

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            AsyncTask.execute(() -> appDatabase.userBlockUrlDao().deleteByUrl(customUrlsToBlock.get(position)));
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(context, "Url removed", Toast.LENGTH_SHORT).show();
        });

        final EditText addBlockedUrlEditText = (EditText) view.findViewById(R.id.addBlockedUrlEditText);
        Button addCustomBlockedUrlButton = (Button) view.findViewById(R.id.addCustomBlockedUrlButton);
        addCustomBlockedUrlButton.setOnClickListener(v -> {
            String urlToAdd = addBlockedUrlEditText.getText().toString().trim().toLowerCase();
            if (!Patterns.WEB_URL.matcher(urlToAdd).matches()) {
                Toast.makeText(context, "Url not valid. Please check", Toast.LENGTH_SHORT).show();
                return;
            }
            AsyncTask.execute(() -> {
                UserBlockUrl userBlockUrl = new UserBlockUrl(urlToAdd);
                appDatabase.userBlockUrlDao().insert(userBlockUrl);
            });
            addBlockedUrlEditText.setText("");
            Toast.makeText(context, "Url has been added", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
