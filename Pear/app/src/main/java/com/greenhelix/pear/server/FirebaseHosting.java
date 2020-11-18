package com.greenhelix.pear.server;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.greenhelix.pear.R;

public class FirebaseHosting extends AppCompatActivity {

    private WebView addressApiView;
    private WebSettings webViewSettings;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_api_web);

        restartWebView();
        handler = new Handler();

    }//onCreate END
    
    public void restartWebView(){
        addressApiView = findViewById(R.id.wv_adr_api);
        addressApiView.setWebChromeClient(new WebChromeClient());
        addressApiView.addJavascriptInterface(new AndroidBridge(), "Pear");

        /*웹뷰세팅부분*/
        webViewSettings = addressApiView.getSettings();
        webViewSettings.setJavaScriptEnabled(true); //js허용
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true); //js에서 window.open허용
        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setUseWideViewPort(true);
        webViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webViewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webViewSettings.setDomStorageEnabled(true);


        /*웹뷰 주소 불러오는 부분*/

        addressApiView.loadUrl("https://pear-57581.firebaseapp.com/jusoPopup.html");
    }// webview 연결 및 옵션 설정 END

    private class AndroidBridge{
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String addr1 = arg1;
                    String addr2 = arg2;
                    String addr3 = arg3;

                    restartWebView();
                }
            });
        }
    }// webview에서 데이터 가져오기 END

}//FirebaseHosting END

