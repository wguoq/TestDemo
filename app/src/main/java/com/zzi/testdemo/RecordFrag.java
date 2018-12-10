package com.zzi.testdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordFrag} interface
 * to handle interaction events.
 * Use the {@link RecordFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public RecordFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFrag newInstance(String param1, String param2) {
        RecordFrag fragment = new RecordFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] titles = new String[]{"A","B","C","D","E","F","G","H"};
        View v = inflater.inflate(R.layout.fragment_record, container, false);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tablayout_id);
        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar_id);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());

        for(String t:titles){
            RecordContactFrag recordContactFrag = new RecordContactFrag();
            recordContactFrag.setTitile(t);
            adapter.AddFragment(recordContactFrag,t);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String tag) {
        if (mListener != null) {
            mListener.onFragmentInteraction(tag);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
