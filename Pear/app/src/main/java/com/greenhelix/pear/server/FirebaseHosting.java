package com.greenhelix.pear.server;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.greenhelix.pear.R;

public class FirebaseHosting extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    private WebView addressApiView;

    private class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")//이이분이 있어야 SetAddress가 활성화 된다.
        public void setAddress(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_api_web);

        addressApiView = findViewById(R.id.wv_adr_api);
        addressApiView.getSettings().setJavaScriptEnabled(true);
        addressApiView.addJavascriptInterface(new MyJavaScriptInterface(),"Pear");

        addressApiView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                addressApiView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });
        addressApiView.loadUrl("https://pear-57581.web.app/api/address/apiKakao.html");
        //https://www.juso.go.kr/addrlink/addrMobileLinkUrlSearch.do

//        restartWebView();
//        handler = new Handler();

    }//onCreate END



//    public void restartWebView(){
//        addressApiView = findViewById(R.id.wv_adr_api);
//        addressApiView.setWebChromeClient(new WebChromeClient());
//
//        /*웹뷰세팅부분*/
//        webViewSettings = addressApiView.getSettings();
//        webViewSettings.setJavaScriptEnabled(true); //js허용
//        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true); //js에서 window.open허용
//        webViewSettings.setLoadWithOverviewMode(true);
//        webViewSettings.setUseWideViewPort(true);
//        webViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        webViewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webViewSettings.setDomStorageEnabled(true);
//
//
//        /*웹뷰 주소 불러오는 부분*/
//        addressApiView.addJavascriptInterface(new AndroidBridge(), "Pear");
//        addressApiView.loadUrl("https://pear-57581.firebaseapp.com/Sample.html");
//    }// webview 연결 및 옵션 설정 END

//    private class AndroidBridge{
//        @JavascriptInterface
//
//        public void setAddress(final String arg1, final String arg2, final String arg3){
////            handler.post(new Runnable() {
////                @Override
////                public void run() {
////                    Log.d(LOG_TAG, "** web에서 가져온 문자값 **\n"+arg1+"\n"+arg2+"\n"+arg3);
////                    restartWebView();
////                }
////            });
//            Bundle extra = new Bundle();
//            Intent intent = new Intent();
//            extra.putString("data", data);
//            intent.putExtras(extra);
//            setResult(RESULT_OK, intent);
//            finish();
//        }//setAddress END
//
//    }// AndroidBridge class END


}//FirebaseHosting END
