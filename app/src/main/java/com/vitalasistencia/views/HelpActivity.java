package com.vitalasistencia.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.vitalasistencia.R;
import com.vitalasistencia.interfaces.IHelp;
import com.vitalasistencia.presenters.PHelp;

public class HelpActivity extends AppCompatActivity implements IHelp.View {

    private IHelp.Presenter presenter;
    private Context myContext;
    private String fromActivity;
    private WebView mWebview;

    String TAG = "Vital_Asistencia/HelpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        setTheme(R.style.Theme_VitalAsistencia_Help);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        myContext = this;
        presenter = new PHelp(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.Help);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Pressing Back Button");
                    onBackPressed();
                }
            });
        } else {
            Log.d(TAG, "Error loading toolbar");
        }
        fromActivity = getIntent().getStringExtra("activity");
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mWebview = findViewById(R.id.webview_help);

        if (networkInfo != null && networkInfo.isConnected()) {
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.setWebViewClient(new WebViewClient() {
                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(HelpActivity.this, description, Toast.LENGTH_SHORT).show();
                }
                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
            });
            if (fromActivity != null) {
                switch (fromActivity){
                    case "list":
                        getSupportActionBar().setTitle(getResources().getString(R.string.Help)+" "+getResources().getString(R.string.List));
                        mWebview.loadUrl("https://varo95.github.io/Vital-Asistencia/activity/list.html");
                        break;
                    case "search":
                        getSupportActionBar().setTitle(getResources().getString(R.string.Help)+" "+getResources().getString(R.string.title_activity_search));
                        mWebview.loadUrl("https://varo95.github.io/Vital-Asistencia/activity/search.html");
                        break;
                    case "form":
                        getSupportActionBar().setTitle(getResources().getString(R.string.Help)+" "+getResources().getString(R.string.title_activity_form));
                        mWebview.loadUrl("https://varo95.github.io/Vital-Asistencia/activity/form.html");
                        break;
                }
            } else {
                getSupportActionBar().setTitle(R.string.Help);
                mWebview.loadUrl("https://varo95.github.io/Vital-Asistencia/index.html");
            }
        }else{
            presenter.connectionError();
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Starting onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Starting onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Starting onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "Starting onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Starting onDestroy");
        super.onDestroy();
    }

    @Override
    public void showConnectionError(){
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.connectionError), Toast.LENGTH_SHORT).show();
    }
}