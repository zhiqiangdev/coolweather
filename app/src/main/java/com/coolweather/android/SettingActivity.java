package com.coolweather.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.coolweather.android.service.AutoUpdateService;

public class SettingActivity extends AppCompatActivity {
    private ImageView backImg;
    private Switch autoUpdateSwitch;
    private FrameLayout lastLine;
    private RelativeLayout updateInterval;
    private AlertDialog.Builder builder;
    private TextView intervalTimeText;
    public static int checkedItem;
    public static int realCheckedItem;
    public static boolean checkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backImg = (ImageView) findViewById(R.id.back_img);
        autoUpdateSwitch = (Switch) findViewById(R.id.auto_update_switch);
        lastLine = (FrameLayout) findViewById(R.id.last_line);
        updateInterval = (RelativeLayout) findViewById(R.id.update_interval_layout);
        intervalTimeText = (TextView) findViewById(R.id.interval_time_text);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        realCheckedItem = prefs.getInt("realCheckedItem", 0);
        checkedItem = prefs.getInt("checkedItem", 0);
        checkSwitch = prefs.getBoolean("checkSwitch", false);

        builder = new AlertDialog.Builder(this);

        if(checkSwitch){
            autoUpdateSwitch.setChecked(true);
            updateInterval.setVisibility(View.VISIBLE);
            lastLine.setVisibility(View.VISIBLE);
        }else {
            autoUpdateSwitch.setChecked(false);
            updateInterval.setVisibility(View.INVISIBLE);
            lastLine.setVisibility(View.INVISIBLE);
        }

        switch (realCheckedItem){
            case 0:
                intervalTimeText.setText("1小时"+" >");
                break;
            case 1:
                intervalTimeText.setText("2小时"+" >");
                break;
            case 2:
                intervalTimeText.setText("6小时"+" >");
                break;
            case 3:
                intervalTimeText.setText("8小时"+" >");
                break;
            case 4:
                intervalTimeText.setText("12小时"+" >");
                break;
            case 5:
                intervalTimeText.setText("24小时"+" >");
                break;
            default:
                break;
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        autoUpdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkSwitch = true;
                    updateInterval.setVisibility(View.VISIBLE);
                    lastLine.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(SettingActivity.this, AutoUpdateService.class);
                    startService(intent);
                }else {
                    checkSwitch = false;
                    updateInterval.setVisibility(View.INVISIBLE);
                    lastLine.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(SettingActivity.this, AutoUpdateService.class);
                    stopService(intent);
                }
            }
        });

        intervalTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("更新间隔");
                builder.setCancelable(false);
                final String[] time = {"1小时","2小时","6小时","8小时","12小时","24小时"};
                builder.setSingleChoiceItems(time, realCheckedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                checkedItem = 0;
                                break;
                            case 1:
                                checkedItem = 1;
                                break;
                            case 2:
                                checkedItem = 2;
                                break;
                            case 3:
                                checkedItem = 3;
                                break;
                            case 4:
                                checkedItem = 4;
                                break;
                            case 5:
                                checkedItem = 5 ;
                                break;
                            default:
                                break;
                        }
                    }
                });

                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realCheckedItem = checkedItem;
                        switch (checkedItem){
                            case 0:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 1;
                                break;
                            case 1:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 2;
                                break;
                            case 2:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 6;
                                break;
                            case 3:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 8;
                                break;
                            case 4:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 12;
                                break;
                            case 5:
                                intervalTimeText.setText(time[checkedItem]+" >");
                                AutoUpdateService.updateIntervalHours = 24;
                                break;
                            default:
                                break;
                        }
                        Intent intent = new Intent(SettingActivity.this, AutoUpdateService.class);
                        startService(intent);
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putInt("realCheckedItem", realCheckedItem);
        editor.putInt("checkedItem", checkedItem);
        editor.putBoolean("checkSwitch", checkSwitch);
        editor.apply();
    }
}
