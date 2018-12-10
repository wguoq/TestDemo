package com.zzi.testdemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ActivityRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        String[] titles = new String[]{"A","B","C","D","E","F","G","H"};

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for(String t:titles){
            RecordContactFrag recordContactFrag = new RecordContactFrag();
            recordContactFrag.setTitile(t);
            adapter.AddFragment(recordContactFrag,t);
        }

        viewPager.setAdapter(adapter);
        //同步标签页
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_name_a);
        //tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_name_b);
    }
}
