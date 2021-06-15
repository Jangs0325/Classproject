package com.swufe.rate;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable {
    private static final String TAG = "MyTask";
    private Handler handler;

    public void setHandler(Handler h) {this.handler = h; }

    @Override
    public void run() {
        Log.i(TAG, "run: ........");
        URL url = null;

       /* for (int i=1;i<3;i++){
            Log.i(TAG,"run: i=" + i);
        }*/


        //线程中完成任务
        List<String> ret = new ArrayList<>();
        try{
            Thread.sleep(5000);

            Document doc = Jsoup.connect("https://it.swufe.edu.cn").get();
            Log.i(TAG, "run: title=" + doc.title());

            //获取时间
            //body > section > div > div > article > p

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG, "run: time = " + publicTime.html());

            Element table = doc.getElementsByTag("table").get(1);
            Elements trs = table.getElementsByTag("tr");
            for(Element tr:trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str = tds.first().text();
                    Log.i(TAG, "run: tds=" + str);

                    String val = tds.get(5).text();
                    Log.i(TAG, "run:rate=" + val);
                    ret.add(str + "-->" + val);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //返回数据给主线程

        Message msg = handler.obtainMessage(9,ret);
        msg.obj = "from message";
        handler.sendMessage(msg);
    }

}