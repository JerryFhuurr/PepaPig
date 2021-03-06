package com.and.pepapig;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class About extends Fragment {

    private TextView git;
    private FloatingActionButton fab;
    private TextView versionText;

    public About() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        versionText = v.findViewById(R.id.version_textView);
        PackageManager manager = getContext().getPackageManager();
        String version = "Version ";
        try{
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            version += String.valueOf(info.versionName);
            versionText.setText(version);
        } catch (PackageManager.NameNotFoundException e){
            Log.e("version error", e.getMessage());
        }

        git = v.findViewById(R.id.git_link);
        fab = v.findViewById(R.id.fab);

        fabOnClick(fab, v);

        git.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("git_link", git.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getActivity(), R.string.about_git_toast, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return v;
    }

    private void fabOnClick(FloatingActionButton button, View v) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(v.findViewById(R.id.about_container), R.string.about_snack1, Snackbar.LENGTH_LONG)
                        .setAction(R.string.about_snack2, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), R.string.about_snack_toast, Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }
}