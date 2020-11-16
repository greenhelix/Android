//package com.greenhelix.pear.directOrder;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//import com.greenhelix.pear.R;
//
//public class AddressFragment extends DialogFragment{
//    private WebView webView;
//    private Handler handler;
//    private OnAddressFragmentInteractionListener mListener;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.find_address_api, container, false);
//        webView = v.findViewById(R.id.wv_adr_api);
//        init_webView();
//        handler = new Handler();
//        return v;
//    }
//
//    private void init_webView() {
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDefaultTextEncodingName("UTF-8");
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.addJavascriptInterface(new AndroidBridge(), "pear");
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if(view.getProgress() == 100){
//                    webView.postDelayed(()->webView.scrollTo(0,0),300)
//                }
//            }
//        });
//        webView.loadUrl("");
//    }
//
//    private interface OnAddressFragmentInteractionListener {
//        void onAddressFragmentInteraction(AddressModel model);
//    }
//}