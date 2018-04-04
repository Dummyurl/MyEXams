package com.saxxis.myexamspace.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.saxxis.myexamspace.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineActivity extends AppCompatActivity {

    @BindView(R.id.linktext)
    WebView mWebView;

    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ButterKnife.bind(this);

        Bundle extras=getIntent().getExtras();
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
//                if (progress == 100) {
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                }
            }
        });

        if (extras!=null){
            String linkrtext=extras.getString("linktext");
            mWebView.getSettings().setJavaScriptEnabled(true);

            if(linkrtext.endsWith(".pdf")){
                mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+linkrtext);
                progressBar.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
                mWebView.loadUrl(linkrtext);
            }
        }
    }
}
