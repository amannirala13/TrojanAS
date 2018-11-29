package asdev.com.asstockmarket;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*import android.telephony.SmsManager;
import android.view.Menu;*/
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import permissions.dispatcher.*;
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private int sms_permission_code = 1;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_main);

            webview = (WebView) findViewById(R.id.webview1);
            webview.setWebViewClient(new MyWebViewClient());
            WebSettings webSettings = webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }

     @NeedsPermission(Manifest.permission_group.SMS)
    private void openURL() {
        webview.loadUrl("http://www.amansofttechinfo.weebly.com");
        webview.requestFocus();
    }


      /*  String phoneNo = "9006134477";
        String message = "yups WE WROKED";

        sendMessage(phoneNo,message);
    }

    public void sendMessage(String phoneNo, String message){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS Fail. Please try again!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;*/


}
