package com.and.pepapig;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Home_page extends Fragment {

    private ImageView imageView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_page, container, false);
        imageView = v.findViewById(R.id.home_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.home_dia_title2);
                builder.setIcon(R.drawable.ic_iconmonstr_smiley_11);
                builder.setMessage(R.string.home_dia_body);
                builder.setCancelable(false);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), R.string.home_dia_toast, Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        return v;
    }

}