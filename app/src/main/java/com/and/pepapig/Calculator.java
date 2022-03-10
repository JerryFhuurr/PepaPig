package com.and.pepapig;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.and.pepapig.databinding.ActivityCalculatorBinding;

import java.util.Locale;

public class Calculator extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCalculatorBinding binding;

    private TextView tv;
    private TextView rv;
    private CharSequence result = "";
    private int sign_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calculator);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setImageDrawable(getDrawable(R.drawable.ic_iconmonstr_home_7));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Calculator.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calculator);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void buttonsPressed(View view) {
        tv = findViewById(R.id.stepView);
        switch (view.getId()) {
            case R.id.btn_c:
                result = "";
                tv.setText(result);
                sign_time = 0;
                break;
            case R.id.btn_0:
                result += "0";
                tv.setText(result);
                break;
            case R.id.btn_1:
                result += "1";
                tv.setText(result);
                break;
            case R.id.btn_2:
                result += "2";
                tv.setText(result);
                break;
            case R.id.btn_3:
                result += "3";
                tv.setText(result);
                break;
            case R.id.btn_4:
                result += "4";
                tv.setText(result);
                break;
            case R.id.btn_5:
                result += "5";
                tv.setText(result);
                break;
            case R.id.btn_6:
                result += "6";
                tv.setText(result);
                break;
            case R.id.btn_7:
                result += "7";
                tv.setText(result);
                break;
            case R.id.btn_8:
                result += "8";
                tv.setText(result);
                break;
            case R.id.btn_9:
                result += "9";
                tv.setText(result);
                break;
            case R.id.btn_plus:
                if (sign_time == 0)
                    result += " + ";
                tv.setText(result);
                sign_time++;
                break;
            case R.id.btn_minus:
                if (sign_time == 0)
                    result += " - ";
                tv.setText(result);
                sign_time++;
                break;
            default:
                result = "0";
                tv.setText(result);
                sign_time = 0;
        }
    }

    public void calculate(View view) {
        rv = findViewById(R.id.resultView);
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '+' || result.charAt(i) == '-') {
                String a = "";
                String b = "";
                int r = 0;
                for (int j = 0; j < i - 1; j++) {
                    a += result.charAt(j);
                }

                for (int k = i + 2; k < result.length(); k++) {
                    b += result.charAt(k);
                }
                int ai = Integer.parseInt(a);

                String final_express = "";

                try {
                    int bi = Integer.parseInt(b);
                    if (result.charAt(i) == '+') {
                        r = ai + bi;
                        String final_result = String.valueOf(r);
                        rv.setText(final_result);
                        final_express = result + " = " +  final_result; // 可作为历史记录保存
                        Log.i("result", final_express);
                        sign_time = 0;
                        break;
                    } else if (result.charAt(i) == '-') {
                        r = ai - bi;
                        String final_result = String.valueOf(r);
                        rv.setText(final_result);
                        final_express = result + " = " +  final_result;
                        Log.i("result", final_express);
                        sign_time = 0;
                        break;
                    }
                } catch (NumberFormatException e) {
                    rv.setText("ERROR");
                    String error = getString(R.string.calculator_error_toast);
                    Toast.makeText(Calculator.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void calculator_change_language(View v){
        String system_language = Locale.getDefault().getLanguage();  // 获取当前语言

        // 用if判断，是中文就切换成英文，反之亦然

    }
}