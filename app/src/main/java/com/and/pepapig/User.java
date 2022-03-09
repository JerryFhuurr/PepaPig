package com.and.pepapig;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class User extends Fragment {

    private ImageButton button;

    public User() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, null);


        button = v.findViewById(R.id.user_icon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.dialog_title);
                builder.setIcon(R.drawable.ic_iconmonstr_smiley_11);
                builder.setMessage(R.string.dialog_msg);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), R.string.toast_dialog, Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        return v;
    }

}