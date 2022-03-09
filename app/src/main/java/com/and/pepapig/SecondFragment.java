package com.and.pepapig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.and.pepapig.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private EditText plain;
    private EditText multi;
    private Button send_plain;
    private Button send_email;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        plain = getActivity().findViewById(R.id.edit_text_cal2);
        multi = getActivity().findViewById(R.id.edit_text_email);
        send_plain = getActivity().findViewById(R.id.cal2_send_btn);
        send_email = getActivity().findViewById(R.id.cal2_send_email_btn);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        send_plain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                result.putString("bundleKey", plain.getText().toString());
                getParentFragmentManager().setFragmentResult("requestKey", result);
                Toast.makeText(getActivity(), getText(R.string.toast_sent), Toast.LENGTH_SHORT).show();

                plain.setText("");
            }
        });

        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_share = new Intent(Intent.ACTION_SEND);
                intent_share.setType("text/plain");
                intent_share.putExtra(Intent.EXTRA_EMAIL, new String[]{"1063128177@qq.com"});
                intent_share.putExtra(Intent.EXTRA_SUBJECT, "A message to you");
                intent_share.putExtra(Intent.EXTRA_TEXT, multi.getText());
                startActivity(intent_share);

                multi.setText("");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}