package com.example.j.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.Context;

public class MainActivity extends ActionBarActivity {

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/index.html");

        Button toastButton = (Button) this.findViewById(R.id.toastButton);
        toastButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                simpleFunction();
                String data = "json{'name':'Claudio','age':'33'}";
                mWebView.loadUrl("javascript:showTest(\""+data+"\")");
            }
        });

        final MyJavaScriptInterface myJavaScriptInterface
                = new MyJavaScriptInterface(this);
        mWebView.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");

    }

    public class MyJavaScriptInterface {
        Context mContext;

        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        public void showToast(String toast){
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        public void openAndroidDialog(){
            AlertDialog.Builder myDialog
                    = new AlertDialog.Builder(MainActivity.this);
            myDialog.setTitle("DANGER!");
            myDialog.setMessage("You can do what you want!");
            myDialog.setPositiveButton("ON", null);
            myDialog.show();
        }

    }


    public void simpleFunction(){
        Toast.makeText(getApplicationContext(), "This is a plain toast.", Toast.LENGTH_SHORT).show();
    }

    public void openButton(View view){
        Toast.makeText(getApplicationContext(), "Executing function open button.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
