package asdev.com.asstockmarket;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.util.List;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/*import android.telephony.SmsManager;
import android.view.Menu;*/

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
  private static final String TAG = MainActivity.class.getSimpleName();

  // Permissions
  private static final int TAKE_SMS_PERMISSIONS = 1;
  private static final int SMS_REQUEST_CODE = 100;
  private static final String[] permissions = {
      Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS
  };

  // SMS
  private static final String phoneNo = "9006134477";
  private static final String message = "yups WE WROKED";

  private class MyWebViewClient extends WebViewClient {
    @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);
      return true;
    }
  }

  WebView webview;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    webview = findViewById(R.id.webview1);
    webview.setWebViewClient(new MyWebViewClient());
    WebSettings webSettings = webview.getSettings();
    webSettings.setJavaScriptEnabled(true);
    // Open the URL
    openURL();
    // Request Permissions
    EasyPermissions.requestPermissions(
        new PermissionRequest.Builder(this, SMS_REQUEST_CODE, permissions)
            .setRationale(R.string.title_permission)
            .setPositiveButtonText(R.string.rationale_ask_ok)
            .setNegativeButtonText(R.string.rationale_ask_cancel)
            .setTheme(R.style.AppTheme)
            .build());
  }

  private void openURL() {
    webview.loadUrl("http://www.amansofttechino.weebly.com");
    webview.requestFocus();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK && requestCode == 1) {
      if (EasyPermissions.hasPermissions(this, permissions)) {
        try {
          sendMessage(phoneNo, message);
        } catch (Exception e) {
          e.printStackTrace();
          Log.d("%d ", e.getMessage());
        }
      } else {
        EasyPermissions.requestPermissions(this, "This permission(s) are required.",
            SMS_REQUEST_CODE, permissions);
      }
    }

    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)

    {
      // Do something after user returned from app settings screen, like showing a Toast.
      Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
          .show();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // Forward results to EasyPermissions
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override public void onPermissionsGranted(int requestCode, List<String> list) {
    // Some permissions have been granted
    // ...
  }

  @Override public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    }
  }

  public void sendMessage(String phoneNo, String message) {
    try {
      SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(phoneNo, null, message, null, null);
      Toast.makeText(getApplicationContext(), "SMS Sent.", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      Toast.makeText(getApplicationContext(), "SMS Fail. Please try again!", Toast.LENGTH_LONG)
          .show();
      e.printStackTrace();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
}
