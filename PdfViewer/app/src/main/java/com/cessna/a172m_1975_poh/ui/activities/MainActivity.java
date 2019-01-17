package com.cessna.a172m_1975_poh.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cessna.a172m_1975_poh.R;
import com.cessna.a172m_1975_poh.utils.Helpers;
import com.cessna.a172m_1975_poh.utils.SharedPrefferences;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private ProgressBar progressBar;
    private PDFView pdfView;
    private TextView tvTitle;
    private String pdfFileName="book.pdf";
    private SharedPrefferences sharedPrefferences;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        displayFromAsset(Helpers.getAppMode(MainActivity.this),Helpers.getScrollType(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i=new Intent(MainActivity.this,SettingsActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    private void initViews()
    {
        pdfView = findViewById(R.id.pdf_view);
        progressBar=findViewById(R.id.progress_bar);
        tvTitle=findViewById(R.id.tv_page_title);
        sharedPrefferences=new SharedPrefferences(MainActivity.this);
    }

    private void displayFromAsset(String appMode,String appScroll) {

        pdfView.fromAsset(pdfFileName)
                .defaultPage(Helpers.getPageNumber(MainActivity.this))
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(appScroll.equals("horizontal")?null:new DefaultScrollHandle(this))
                .spacing(5)
                .onPageError(this)
                .nightMode(appMode.equals("night")?true:false)
                .pageFitPolicy(FitPolicy.BOTH)

                .swipeHorizontal(appScroll.equals("horizontal")?true:false)
                .pageSnap(appScroll.equals("horizontal")?true:false)
                .autoSpacing(appScroll.equals("horizontal")?true:false)
                .pageFling(appScroll.equals("horizontal")?true:false)

                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        try
        {
            sharedPrefferences.setString(getResources().getString(R.string.page_number), String.valueOf(page+1));
        }
        catch (Exception e)
        {
            Log.e(TAG,e.getMessage());
        }
        tvTitle.setText(String.format("%s %s/%s", "Page", page + 1, pageCount));
    }
    @Override
    public void loadComplete(int nbPages) {
        pdfView.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        System.exit(0);
    }
}
