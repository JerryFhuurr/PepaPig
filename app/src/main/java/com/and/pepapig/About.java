package com.and.pepapig;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class About extends Fragment {

    TextView my_email;
    TextView git;
    FloatingActionButton fab;

    public About() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        my_email = v.findViewById(R.id.my_email);
        git = v.findViewById(R.id.git_link);
        fab = v.findViewById(R.id.fab);

        fabOnClick(fab, v);

        my_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.about_send_email, Toast.LENGTH_LONG).show();
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                intent_share.setType("text/plain");
                intent_share.putExtra(Intent.EXTRA_EMAIL, new String[]{"1063128177@qq.com"});
                intent_share.putExtra(Intent.EXTRA_SUBJECT, "This is a test email to you");
                intent_share.putExtra(Intent.EXTRA_TEXT, "Hello world");
                startActivity(intent_share);
            }
        });
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