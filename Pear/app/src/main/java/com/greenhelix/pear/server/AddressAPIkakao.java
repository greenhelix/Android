package com.greenhelix.pear.server;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.pear.R;

public class AddressAPIkakao extends AppCompatActivity {

    private WebView addressApiView;

    class MyJavaScriptInterface{
        @JavascriptInterface
        public void processDATA(String data){
            Bundle dataBundle = new Bundle();
            Intent dataIntent = new Intent();
            dataBundle.putString("data", data);
            dataIntent.putExtras(dataBundle);
            setResult(RESULT_OK, dataIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_api_web);

        addressApiView = findViewById(R.id.wv_adr_api);
        addressApiView.getSettings().setJavaScriptEnabled(true);
        addressApiView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        addressApiView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                addressApiView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
//        addressApiView.loadUrl("http://192.168.0.5:3000/addressAPI.html");
        addressApiView.loadUrl("https://pear-57581.web.app/addressAPI");
    }
}
