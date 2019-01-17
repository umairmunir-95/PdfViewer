package com.cessna.a172m_1975_poh.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cessna.a172m_1975_poh.R;
import com.cessna.a172m_1975_poh.utils.Helpers;
import com.cessna.a172m_1975_poh.utils.SharedPrefferences;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvAppInfo;
    private CheckBox checkBoxAppMode;
    private CheckBox checkBoxAPpScroll;
    private SharedPrefferences sharedPrefferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.settings));
        initViews();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private void initViews()
    {
        tvAppInfo=findViewById(R.id.tv_appinfo);
        checkBoxAppMode=findViewById(R.id.cb_enable_night_mode);
        checkBoxAPpScroll=findViewById(R.id.cb_enable_horizontal_scroll);
        sharedPrefferences=new SharedPrefferences(SettingsActivity.this);

        Helpers.setAppInfo(SettingsActivity.this,tvAppInfo);

        checkBoxAppMode.setChecked(Helpers.getAppMode(SettingsActivity.this).equals("normal")?false:true);
        checkBoxAPpScroll.setChecked(Helpers.getScrollType(SettingsActivity.this).equals("vertical")?false:true);

        checkBoxAppMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked )
                {
                    sharedPrefferences.setString(getResources().getString(R.string.enable_night_mode),"night");
                    Toast.makeText(getApplicationContext(),"Night mode enabled",Toast.LENGTH_LONG).show();
                }
                else
                {
                    sharedPrefferences.setString(getResources().getString(R.string.enable_night_mode),"normal");
                    Toast.makeText(getApplicationContext(),"Night mode disabled",Toast.LENGTH_LONG).show();
                }
            }
        });

        checkBoxAPpScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked )
                {
                    sharedPrefferences.setString(getResources().getString(R.string.enable_horizontal_scroll),"horizontal");
                    Toast.makeText(getApplicationContext(),"Horizontal scroll enabled",Toast.LENGTH_LONG).show();
                }
                else
                {
                    sharedPrefferences.setString(getResources().getString(R.string.enable_horizontal_scroll),"vertical");
                    Toast.makeText(getApplicationContext(),"Horizontal scroll disabled",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
