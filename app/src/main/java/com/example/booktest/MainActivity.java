package com.example.booktest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private String filename = "test";
    private String filename2 = "test2";
    public float[] mValue = {0.1f, 0.0f, 0.0f};
    private DecimalFormat df = new DecimalFormat("#,##0.00000 m/s²");

    private File dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","onCreate execute");
        Button btn = (Button)findViewById(R.id.jump_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                saveFile(mValue);

                addlogFile(filename2,mValue,System.currentTimeMillis(),true);
            }
        });

//        setDir();
        updataFile(filename,mValue,false);
//        float[] mValue2= new float[]{1.1f, 2.2f, 3.3f};
//        saveFile(mValue2);
//        String con = null;
//        try {
//            con = this.read(filename);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.i("M",con);
    }
    //那么平时要是有测试的代码写在哪？onCreate中吗？
    //可换行写入（文件中）
    public boolean saveFile(float[] value)
    {
        String[] heads={"x=","y=","z="};
        try {
            FileOutputStream out = openFileOutput(filename,MODE_PRIVATE);
            for(int i=0;i<3;i++)
            {
                String content =heads[i]+this.df.format((double)-value[i]);
                out.write(content.getBytes());
                out.write("\r\n".getBytes());   //换行
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("M","已写入");
        return true;
    }
    //可换行读出（字符串显示）
    public String read(String filename) throws IOException{
        FileInputStream fis=openFileInput(filename);
        byte[] buff =new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        while((len = fis.read(buff))>0){
            sb.append(new String(buff,0,len));
        }
        fis.close();
        return sb.toString();
    }

    //设置目录
//    public boolean setDir() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            System.out.println("ok");
//        }else {
//            ActivityCompat.requestPermissions(this,new String[] {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            },1);
//        }
////        this.dir = new File(Environment.getExternalStorageDirectory() + File.separator+"/Virmeters");
////        if (!dir.exists()) {
////            dir.mkdirs();
////        }
//
////        String dirPath = Environment.getExternalStorageDirectory().getPath()+"/Virmeters";
////        File dir = new File(dirPath);
////        if (!dir.exists()){
////            //创建目录
////            dir.mkdirs();
////        }
//        File rootF = Environment.getExternalStorageDirectory();
//        File valueF = new File(rootF.getAbsolutePath() +"/Virmeters");
//        if (!valueF.exists()) {
//            valueF.mkdirs();
//        }
//        this.dir = valueF;
//        Log.i("File","路径是"+dir);
//
//        return true;
//    }
    public boolean updataFile(String filename,float[] value,boolean append)
    {
        String[] heads={"x=","y=","z="};    //还没掉位置


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            System.out.println("ok");
        }else {
            ActivityCompat.requestPermissions(this,new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

//        File file = new File(this.dir,File.separator+filename+".txt");
//        File rootF = Environment.getExternalStorageDirectory();
//        File valueF = new File(rootF.getPath() +"/Virmeters");
//        String name = "Virmeters";
//        String path = "";
//        File rootFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/Virmeters/");
//        if (rootFile != null) {
//            if (!rootFile.exists()) {
//                boolean m = rootFile.mkdirs();
//                Log.i("File","rootFile:"+rootFile);
//                Log.i("File","结果是"+m);
//            }
//            path = rootFile.getAbsolutePath();
//
//        }
        String sdPath = getExternalFilesDir(null)+"/Virmeters/" ;
        File file1 = new File(sdPath);
        Log.e("wy", "绝对文件路径: " + file1.getAbsoluteFile());
        Log.e("wy", "文件名: " + file1.getName());
        if (!file1.exists()) {
            file1.mkdirs();
            Log.e("wy", "创建文件夹,路径：" + file1.getPath());
        }

        File file = new File(file1.getAbsoluteFile(),File.separator+filename+".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file,append);
            for(int i=0;i<3;i++)
            {
                String content =heads[i]+this.df.format((double)-value[i]);
                writer.write(content);
                writer.write("\r\n");   //换行
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("File","不追加写入");
        return true;
    }

    public boolean addlogFile(String filename,float[] value,long j,boolean append)
    {
        String[] heads={"x=","y=","z="};
        File file = new File(this.dir,File.separator+filename2+".txt");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file,append);
            String division="##########################################"+"\n";
            String time = "时间为"+new SimpleDateFormat("yyyy年MM月dd日hh时mm分ss秒SSS").format(Long.valueOf(j));
            writer.write(division);
            writer.write(time);
            writer.write("\r\n");
            for(int i=0;i<3;i++)
            {
                String content =heads[i]+this.df.format((double)-value[i]);
                writer.write(content);
                writer.write("\r\n");   //换行
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("File","追加写入");
        return true;
    }
}