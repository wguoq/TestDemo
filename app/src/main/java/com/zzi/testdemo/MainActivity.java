package com.zzi.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;

import static com.zzi.testdemo.PermisionUtils.verifyStoragePermissions;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    static final String PREFS_NAME = "def_PrefsFile";
    final String root = Environment.getExternalStorageDirectory() + "/download" + "/TestDemo/";

    DashboardFrag dashboardFrag;
    SettingFrag settingFrag;
    ScriptFrag scriptFrag;
    RecordFrag recordFrag;

    FragmentManager fragmentManager = getSupportFragmentManager();
    String fragTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //申请读写权限
        verifyStoragePermissions(this);

        dashboardFrag = new DashboardFrag();
        settingFrag = new SettingFrag();
        scriptFrag = new ScriptFrag();
        recordFrag = new RecordFrag();

        //Dashboard instantiate
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,dashboardFrag);
        fragmentTransaction.commit();
        fragTag = "dashboardFrag";

        //创建文件夹
        File dir;
        dir = new File(root + "Script/");
        boolean x = true;
        if (!dir.exists()) {
            x = dir.mkdirs();
        }
        if (!x){
            verifyStoragePermissions(this);
        }

        dir = new File(root + "JX/");
        boolean y = true;
        if (!dir.exists()) {
            y = dir.mkdirs();
        }
        if (!y){
            verifyStoragePermissions(this);
        }

    }
    // 返回键的处理
    public void onBackPressed() {
        //如果当前页面不是主页面就退回主页面
        if(!fragTag.equals("dashboardFrag")){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,dashboardFrag);
            fragmentTransaction.commit();
            fragTag = "dashboardFrag";
            }else {
            super.onBackPressed();
        }
    }

    //Frag回调接口实现
    @Override
    public void onFragmentInteraction(String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (tag) {
            case "DashboardFragTag1":
                Toast.makeText(this, "1111111111", Toast.LENGTH_SHORT).show();
                break;
            case "DashboardFragTag2":
                Toast.makeText(this, "2222222222", Toast.LENGTH_SHORT).show();
                break;
            case "DashboardFragTag3":
                fragTag = "scriptFrag";
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, scriptFrag);
                fragmentTransaction.commit();
                break;
            case "DashboardFragTag4":
                //fragTag = "recordFrag";
                //fragmentTransaction.replace(R.id.container, recordFrag);
                //fragmentTransaction.commit();
                Intent intent = new Intent(this, ActivityRecord.class);
                startActivity(intent);
                break;
            case "DashboardFragTag5":
                fragTag = "settingFrag";
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container, settingFrag);
                fragmentTransaction.commit();
                break;
        }
    }
}
