package com.and.pepapig;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
        listView.setAdapter(adapter);
        return v;
    }
}