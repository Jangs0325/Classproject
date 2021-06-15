package com.swufe.rate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity {




        private static final String TAG = "RateActivity";
        EditText rmb;
        TextView show;
        float dollarRate = 0.15f;
        float euroRate = 0.12f;
        float wonRate = 172f;
        Handler handler;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rate);
            rmb = (EditText) findViewById(R.id.rmb);
            show = (TextView) findViewById(R.id.showOut);

            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            PreferenceManager.getDefaultSharedPreferences(this);
            dollarRate = sharedPreferences.getFloat("dollar_rate",0.0f);
            euroRate = sharedPreferences.getFloat("euro_rate",0.0f);
            wonRate = sharedPreferences.getFloat("won_rate",wonRate);

            Thread t = new Thread(this::open);
            t.start();

            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == 7) {
                        String str = (String) msg.obj;
                        Log.i(TAG, "handleMessage: get str=" + str);
                        show.setText(str);
                    }
                    super.handleMessage(msg);
                }
            };
        }

        public void click (View btn) {
            Log.d(TAG, "click:");

            float r = 0.0f;
            switch (btn.getId()) {
                case R.id.btn_dollar:
                    r = dollarRate;
                    break;
                case R.id.btn_euro:
                    r = euroRate;
                    break;
                case R.id.btn_won:
                    r = wonRate;
            }

            String str = rmb.getText().toString();
            Log.i(TAG, "click: str=" + str);
            if (str == null || str.length() == 0) {
                Toast.makeText(this, "please input RMB", Toast.LENGTH_SHORT).show();
            } else {
                r = r * Float.parseFloat(str);
                show.setText(String.valueOf(r));
            }
        }
        public void openOnes(View btn) {
            //打开一个页面Activity
            open();
        }

        private void open() {
            Log.i("open", "openOne:");
            Intent config = new Intent(this, ConfigActivity.class);
            //加入传递参数
            config.putExtra("dollarRate", dollarRate);
            config.putExtra("euroRate", euroRate);
            config.putExtra("wonRate", wonRate);

            Log.i(TAG, "Config:dollarRate" + dollarRate);
            Log.i(TAG, "Config:euroRate" + euroRate);
            Log.i(TAG, "Config:wonRate" + wonRate);

            //startActivity(config);
            startActivityForResult(config,5);

        }

        protected void onActivityShow(int requestCode, int resultCode, Intent data){


            if(requestCode==5 && resultCode==2){
                Bundle bundle = data.getExtras();
                dollarRate = bundle.getFloat("key_dollar",0.1f);
                euroRate = bundle.getFloat("key_euro",0.1f);
                wonRate = bundle.getFloat("key_won",0.1f);
                Log.i(TAG,"onActivityResult:dollarRate" + dollarRate);
                Log.i(TAG,"onActivityResult:euroRate" + euroRate);
                Log.i(TAG,"onActivityResult:wonRate" + wonRate);

                SharedPreferences sp = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putFloat("dollar_rate",dollarRate);
                editor.putFloat("euro_rate",euroRate);
                editor.putFloat("won_rate",wonRate);
                editor.apply();
            }
            super.onActivityResult(requestCode,resultCode,data);
        }


        public void run() {

            Log.i(TAG, "run:run()...");


            //线程中完成的任务
            //获取网络数据

            URL url = null;
            try{
//                url = new URL("www.usd-cny.con/bankofchina.htm");
//                HttpURLConnection http = (HttpURLConnection) url.openConnection();
//                InputStream in = http.getInputStream();

//                String html = inputStream2String(in);
//                Log.i(TAG, "run: html=" + html);
                Document doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj").get();
                Log.i(TAG, "run: title=" + doc.title());

                Element publicTime = doc.getElementsByClass("time").first();
                Log.i(TAG, "run: time=" + publicTime.html());
                Element table = doc.getElementsByTag("table").first();
                Elements trs = table.getElementsByTag("tr");

                for(Element tr : trs){
                    Elements tds = tr.getElementsByTag("td");
                    if(tds.size()>0){
                        Log.i(TAG, "run: td="+tds.first().text());
                        Log.i(TAG, "run: rate=" + tds.get(5).text());
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            //返回数据给主线程
            Message msg = handler.obtainMessage(7);
            msg.obj = "from message";
            handler.sendMessage(msg);
        }

        private String inputStream2String(InputStream inputStream)
                throws IOException{
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(inputStream,"gb2312");
            while (true){
                int rsz = in.read(buffer,0,buffer.length);
                if(rsz < 0)
                    break;
                out.append(buffer,0,rsz);

            }
            return out.toString();
        }

    }



