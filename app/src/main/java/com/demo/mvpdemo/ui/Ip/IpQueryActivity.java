package com.demo.mvpdemo.ui.Ip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.mvpdemo.R;

import rx.Subscription;

public class IpQueryActivity extends AppCompatActivity implements View.OnClickListener, IpContract.View {
    private EditText etIp;
    private EditText etAppKey;
    private EditText etSign;
    private Button btnQuery;
    private TextView tvStatus;

    private IpContract.Presenter presenter;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        btnQuery = (Button) findViewById(R.id.query);
        etIp = (EditText) findViewById(R.id.ip);
        etAppKey = (EditText) findViewById(R.id.appkey);
        etSign = (EditText) findViewById(R.id.sign);
        tvStatus = (TextView) findViewById(R.id.status);

        btnQuery.setOnClickListener(this);

        presenter = new IpPresenter(this);
    }

    @Override
    public void onClick(View v) {
        tvStatus.setText("");
        subscription = presenter.queryIp();
    }

    @Override
    public String getIP() {
        return etIp.getText().toString().trim();
    }

    @Override
    public String getAppKey() {
        return etAppKey.getText().toString().trim();
    }

    @Override
    public String getSign() {
        return etSign.getText().toString().trim();
    }

    @Override
    public void setResult(String result) {
        tvStatus.setText(result);
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
