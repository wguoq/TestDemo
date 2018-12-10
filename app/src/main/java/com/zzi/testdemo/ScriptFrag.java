package com.zzi.testdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zzi.testdemo.PermisionUtils.verifyStoragePermissions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScriptFrag} interface
 * to handle interaction events.
 * Use the {@link ScriptFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScriptFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private static final String PREFS_NAME = "def_PrefsFile";

    final String root = Environment.getExternalStorageDirectory() + "/" + "download/" + "TestDemo/";

    TextView textView1,textView2,textView3;
    EditText editTextS1,editTextS2;
    Button searchBtn,photoBtn;
    Spinner spinner;
    String scriptName;
    String encode;

    public ScriptFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScriptFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ScriptFrag newInstance(String param1, String param2) {
        ScriptFrag fragment = new ScriptFrag();
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
    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_script, container, false);


        textView2 = v.findViewById(R.id.textView2);
        //设置滚动
        textView2.setMovementMethod(ScrollingMovementMethod.getInstance());

        textView3 = v.findViewById(R.id.textView3);
        editTextS1 = v.findViewById(R.id.textInputLayout_top).findViewById(R.id.inputText1);
        editTextS2 = v.findViewById(R.id.textInputLayout_top).findViewById(R.id.inputText2);
        searchBtn = v.findViewById(R.id.button_search);
        photoBtn = v.findViewById(R.id.button_photo);
        //读取默认值
        SharedPreferences defPF = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, 0);
        editTextS1.setText(defPF.getString("editTextS1", ""));
        editTextS2.setText(defPF.getString("editTextS2", ""));
        encode = defPF.getString("encode","UTF8");

        //限制输入长度和内容
        final EditText et[] = {editTextS1, editTextS2};
        for (final EditText t : et) {
            t.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
            t.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String regEx = "[^a-zA-Z0-9\\-_@#.+]";  //字母数字指定符号
                    String text = t.getText().toString();
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(text);
                    String str = m.replaceAll("").trim();    //删掉不匹配的字符
                    if (!text.equals(str)) {
                        t.setText(str);  //设置EditText的字符
                        t.setSelection(str.length()); //重写光标所在位置
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        //获取Script文件夹里面的子文件夹名
        File path = new File(root+"Script/");
        File[] files = path.listFiles();
        List<String> fileNames = new ArrayList<String>();
        if (files != null){
            for (File f : files){
                if (f.isDirectory()){
                    fileNames.add(f.getName());
                }
            }
        }

        //下拉框选择Script文件夹里面的子文件夹名
        fileNames.add("test");
        spinner = (Spinner) v.findViewById(R.id.scriptFag_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_item,fileNames);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scriptName = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //查询按钮
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String j = editTextS1.getText().toString();
                String c = editTextS2.getText().toString();

                editTextS1.setError(null);
                editTextS2.setError(null);
                boolean cancel = false;
                View focusView = null;
                if (TextUtils.isEmpty(j)) {
                    editTextS1.setError("?");
                    focusView = editTextS1;
                    cancel = true;
                } else if (TextUtils.isEmpty(c)) {
                    editTextS2.setError("?");
                    focusView = editTextS2;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                }else {
                    // TODO
                    String filePath,fileName;
                    filePath = root + "Script/" + scriptName + "/";
                    fileName = scriptName + j + "-" + c + ".txt";
                    File file = new File(filePath, fileName);
                    StringBuilder txt = new StringBuilder();
                    try {
                        //GBK UTF8
                        InputStreamReader read = new InputStreamReader(new FileInputStream(file),encode);
                        BufferedReader br = new BufferedReader(read);
                        String line;
                        while ((line = br.readLine()) != null) {
                            txt.append(line);
                            txt.append('\n');
                        }
                        br.close();
                    }
                    catch (IOException e) {
                        Log.d(e.toString(), "IOException: ");
                    }

                    //String txt = FileUtils.getString(filePath,fileName);
                    textView2.setText("scriptName= "+ scriptName +"\n" + "filePath= "+ filePath + "\n" + "fileName=" + fileName + "\n" +"txt= " + "\n"+ txt);
                }

                //保存默认值
                SharedPreferences defPF = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = defPF.edit();
                editor.putString("editTextS1",j);
                editor.putString("editTextS2",c);
                editor.apply();

            }
        });

        //拍照按钮
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String j = editTextS1.getText().toString();
                String c = editTextS2.getText().toString();

                editTextS1.setError(null);
                editTextS2.setError(null);
                boolean cancel = false;
                View focusView = null;
                if (TextUtils.isEmpty(j)) {
                    editTextS1.setError("?");
                    focusView = editTextS1;
                    cancel = true;
                } else if (TextUtils.isEmpty(c)) {
                    editTextS2.setError("?");
                    focusView = editTextS2;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String t = getCurrentTimeStamp();
                    String fileName = j + "_" + c + "_" + t + ".jpg";
                    String imagePath = root + "JX/" + j + "/" + c;
                    File imageDir = new File(imagePath);
                    boolean x = true;
                    if (!imageDir.exists()) {
                        x = imageDir.mkdirs();
                    }
                    if (x) {
                        textView3.setText("保存路径：" + "download/" + "TestDemo/"+ "JX/" + j + "/" + c );
                        openCameraIntent(imagePath,fileName);
                    }
                    //保存默认值
                    SharedPreferences defPF = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = defPF.edit();
                    editor.putString("editTextS1",j);
                    editor.putString("editTextS2",c);
                    editor.apply();
                }
            }
        });

        return v;
    }


    //获取当前时间
    String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());//指定地点为本地Locale.getDefault()
        Date now = new Date();
        return sdf.format(now);
    }

    //调用系统相机
    private void openCameraIntent(String filePath,String fileName) {
        File f = new File(filePath,fileName);
        Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),"com.zzi.testdemo.fileprovider",f);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivity(intent);
        //startActivityForResult(pictureIntent, REQUEST_CAMERA);
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
}
