package com.prinum.sdkclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prinum.kmmsdksample.sdk.PublicApi;
import com.prinum.kmmsdksample.sdk.SdkData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        PublicApi api = new PublicApi(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView = findViewById(R.id.textView);
        findViewById(R.id.bt_open_sdk).setOnClickListener(view -> {
            api.launchSdkScreen();
        });
        findViewById(R.id.bt_get_data_from_sdk).setOnClickListener(view -> {
            getDataFromSdkSync(api, textView);
        });

        findViewById(R.id.bt_get_data_from_sdk_callback).setOnClickListener(view -> {
            getDataFromSdkWithClientUtils(api, textView);
        });
    }

    private void getDataFromSdkSync(PublicApi api, TextView textView) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            SdkData data = api.getSdkDataSync("Sync");
            handler.post(() -> textView.setText("Version form: " + data.getId()));
        });
    }

    private void getDataFromSdkWithClientUtils(PublicApi api, TextView textView) {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            SdkData data = new SdkJavaExecutor(api).getSdkDataWithCoroutinesExecutor("ClientUtils");
            handler.post(() -> textView.setText("Version form: " + data.getId()));
        });
    }
}