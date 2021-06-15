package com.swufe.rate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    /*private static final String TAG ="ConfigActivity";
    float dollarEditor;
    float euroEditor;
    float wonEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        //接收数据
        Intent conf = getIntent();
        float dollar = conf.getFloatExtra("dollar_rate_key",0.0f);
        float euro = conf.getFloatExtra("euro_rate_key",0.0f);
        float won = conf.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreate:dollar=" + dollar);
        Log.i(TAG,"onCreate:euro=" + euro);
        Log.i(TAG,"onCreate:won=" + won);

        //获取控件
        dollarEditor = findViewById(R.id.edit_dollor);
        euroEditor = findViewById(R.id.edit_euro);
        wonEditor = findViewById(R.id.edit_won);

        //将汇率值放入控件
        dollarEditor.setText(String.valueOf(dollar));
        euroEditor.setText(String.valueOf(euro));
        wonEditor.setText(String.valueOf(won));

    }

    public <Indent> void save(View save){
        //获得输入的数据
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuro = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());


        //将输入的新汇率带回计算页面
        Indent intent = (Indent) getIntent();
        Bundle bd1 = new Bundle();
        bd1.putFloat("key_dollar",newDollar);
        bd1.putFloat("key_euro",newEuro);
        bd1.putFloat("key_won",newWon);
        ((Intent) intent).putExtras(bd1);
        setResult(2, (Intent) intent);//设置resultCode及带回的数据
        //返回到调用页面
        finish();
    }
}
     */
    private static final String TAG = "ConfigActivity";
    EditText dollarEditor, euroEditor,wonEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent conf = getIntent();
        float dollar = conf.getFloatExtra("dollar_rate_key",0.0f);
        float euro = conf.getFloatExtra("euro_rate_key",0.0f);
        float won = conf.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG, "onCreate: dollar=" + dollar);
        Log.i(TAG, "onCreate: euro=" + euro);
        Log.i(TAG, "onCreate: won=" + won);

        dollarEditor = findViewById(R.id.edit_dollar);
        euroEditor = findViewById(R.id.edit_euro);
        wonEditor = findViewById(R.id.edit_won);

        dollarEditor.setText(String.valueOf(dollar));
        euroEditor.setText(String.valueOf(euro));
        wonEditor.setText(String.valueOf(won));
    }
    public void save(View btn){
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuro = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }

}