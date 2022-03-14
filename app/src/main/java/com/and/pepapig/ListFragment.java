package com.and.pepapig;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.and.pepapig.History.ListAdapter;
import com.and.pepapig.History.ResultList;

public class ListFragment extends Fragment {

    private RecyclerView listView;
    private ListAdapter adapter;
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        listView = v.findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.hasFixedSize();
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter(ResultList.getResults());
        adapter.setOnClickListener(result -> {
            ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("expression", result.getExpression());
            clipboardManager.setPrimaryClip(clipData);
            String message = "No." + adapter.getIndex(result) + " expression : " + getString(R.string.cal_history_toast );
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        });
        listView.setAdapter(adapter);
        return v;
    }
}