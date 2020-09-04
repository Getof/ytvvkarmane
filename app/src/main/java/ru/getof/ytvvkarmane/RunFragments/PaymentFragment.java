package ru.getof.ytvvkarmane.RunFragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.getof.ytvvkarmane.R;
import ru.getof.ytvvkarmane.Utils.PaymentWebViewClient;

public class PaymentFragment extends Fragment {

    private WebView webViewPay;
    private String runId, urlRun;
    private ProgressBar loaderUrl;
    private String currentUrl;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_payment_run, container, false);

        loaderUrl = view.findViewById(R.id.loader_url);

        Bundle bundle = getArguments();
        if (bundle != null) {
            runId = bundle.getString("idRun");
            urlRun = bundle.getString("urlRun");
        }

        webViewPay = view.findViewById(R.id.webViewPay);
        //webViewPay.setWebViewClient(new PaymentWebViewClient());
        webViewPay.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loaderUrl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loaderUrl.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                    currentUrl = request.getUrl().toString();
                    if (currentUrl.equals("http://xn--80aeb5cm2e.xn--p1ai/")){
                        Toast.makeText(getActivity(),currentUrl, Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                currentUrl = url;
                if (currentUrl.equals("http://xn--80aeb5cm2e.xn--p1ai/")){
                    Toast.makeText(getActivity(),currentUrl, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        webViewPay.loadUrl(urlRun);
        webViewPay.getSettings().setJavaScriptEnabled(true);
        webViewPay.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);



        return view;
    }
}
