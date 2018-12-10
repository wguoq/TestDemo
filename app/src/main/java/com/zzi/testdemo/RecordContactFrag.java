package com.zzi.testdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecordContactFrag} interface
 * to handle interaction events.
 * Use the {@link RecordContactFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordContactFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //activity的回调接口
    private OnFragmentInteractionListener mListener;

    TextView ttt;
    Button btn_OK,btn_CUT,btn_NG;
    ConstraintLayout btn_layout;
    String mTitile;
    int previous = 0,current = 0,next = 0;
    List<RecordRvData> lstData;
    RecyclerViewAdapter adapter;

    //把对应tablayout的titile传进来
    public void setTitile(String titile){
        mTitile = titile;
    }

    public RecordContactFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordContactFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordContactFrag newInstance(String param1, String param2) {
        RecordContactFrag fragment = new RecordContactFrag();
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
        View v;
        v = inflater.inflate(R.layout.fragment_record_contact, container, false);

        lstData = new ArrayList<>();
        int card = 1;

        final int color = ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.colorPrimary);

        for(int i = 0;i<99;i++){
            lstData.add(new RecordRvData(" card "+card, "clip "+i, color, R.drawable.ic_launcher_background));
        }
        lstData.get(current).setBackground(ContextCompat.getColor(getContext(),R.color.colorAccent));
        RecyclerView recyclerview = v.findViewById(R.id.record_contact_recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        //每个item高度一样用这个可以提高性能
        recyclerview.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(getContext(), lstData);
        recyclerview.setAdapter(adapter);

        btn_layout = (ConstraintLayout) v.findViewById(R.id.layout_bottom);
        ttt = (TextView) v.findViewById(R.id.text777);
        btn_OK = (Button) v.findViewById(R.id.button_ok);
        btn_CUT = (Button) v.findViewById(R.id.button_cut);
        btn_NG = (Button) v.findViewById(R.id.button_ng);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyItemChanged(current,"click_OK");
                btn_layout.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.btn_OK));

            }
        });

        btn_CUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyItemChanged(current,"click_CUT");
                btn_layout.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.btn_CUT));
            }
        });

        btn_NG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyItemChanged(current,"click_NG");
                btn_layout.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.btn_NG));
            }
        });


        return v;
    }


    @SuppressLint("SetTextI18n")
    private void updata(int position){
        ttt.setText("position= "+ position +"  "+ mTitile);
    }

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

    //RecyclerViewAdapter
    class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        private Context mContext;
        private List<RecordRvData> mData;



        RecyclerViewAdapter(Context mContext, List<RecordRvData> mData) {
            this.mContext = mContext;
            this.mData = mData;

        }

        @NonNull
        @Override
        //载入item view布局文件
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(mContext).inflate(R.layout.fragment_record_item,parent,false);
            MyViewHolder vHolder = new MyViewHolder(v);

            return vHolder;
        }


        //给元素赋值
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.tv_name.setText(mData.get(position).getName());
            holder.tv_phone.setText(mData.get(position).getPhone());
            holder.img.setImageResource(mData.get(position).getPhoto());
            holder.linear.setBackgroundColor(mData.get(position).getBackground());
        }

        //刷新指定的item
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (!payloads.isEmpty()){

                if (payloads.get(0) == "click") {
                    mData.get(position).setBackground(ContextCompat.getColor(getContext(),R.color.colorAccent));
                    holder.linear.setBackgroundColor(mData.get(position).getBackground());
                    //取消上一个选中状态
                    mData.get(previous).setBackground(ContextCompat.getColor(getContext(),R.color.colorPrimary));

                }else if(payloads.get(0) == "unclick"){
                    mData.get(position).setBackground(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                    holder.linear.setBackgroundColor(mData.get(position).getBackground());
                }else if(payloads.get(0) == "click_OK"){
                    holder.tv_name.setText("click_OK");
                }else if(payloads.get(0) == "click_CUT"){
                    holder.tv_name.setText("click_CUT");
                }else if(payloads.get(0) == "click_NG"){
                    holder.tv_name.setText("click_NG");
                }

            }else {
                //使用默认的onBindViewHolder
                onBindViewHolder(holder, position);
            }
        }

        //决定item数量
        @Override
        public int getItemCount() {
            return  mData.size();
        }

        //获取布局元素setOnClickListener
        class MyViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout record_item,linear;
            private TextView tv_name,tv_phone;
            private ImageView img;

            MyViewHolder(View itemView) {
                super(itemView);
                record_item = (LinearLayout)itemView.findViewById(R.id.record_item_id);
                linear = (LinearLayout) itemView.findViewById(R.id.linear1);
                tv_name = (TextView) itemView.findViewById(R.id.name_contact);
                tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
                img = (ImageView) itemView.findViewById(R.id.img_contact);

                record_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        previous = current;
                        current = position;
                        next = current + 1;
                        Log.d("AAAAAAAAAAAAAAAAA", String.valueOf(previous));
                        Log.d("BBBBBBBBBBBBBBBBBB", String.valueOf(current));
                        if (previous != current){
                            //如果item不在当前显示中好像不会执行notifyItemChanged(previous,"unclick");
                            notifyItemChanged(previous,"unclick");
                            notifyItemChanged(current,"click");
                            updata(current);
                        }
                    }
                });

            }
        }
    }



}
