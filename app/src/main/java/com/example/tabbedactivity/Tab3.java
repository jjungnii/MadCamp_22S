package com.example.tabbedactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab3#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Tab3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String txt_fname = null;
    public String str = null;
    public String img_fname = null;
    public CalendarView calendarView;
    public Button edit_Btn, edit_img_Btn, del_img_Btn, save_Btn;
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;
    public ImageView selectedImg;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Tab3() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
        calendarView = rootView.findViewById(R.id.calendarView);
        diaryTextView = rootView.findViewById(R.id.diaryTextView);
        save_Btn = rootView.findViewById(R.id.save_Btn);
        edit_img_Btn = rootView.findViewById(R.id.edit_img_Btn);
        del_img_Btn = rootView.findViewById(R.id.del_img_Btn);
        edit_Btn = rootView.findViewById(R.id.edit_Btn);
        textView2 = rootView.findViewById(R.id.textView2);
        textView3 = rootView.findViewById(R.id.textView3);
        contextEditText = rootView.findViewById(R.id.contextEditText);
        selectedImg = rootView.findViewById(R.id.selectedImg);
        contextEditText.setPrivateImeOptions( "defaultInputmode=korean;" );

        textView2.setMovementMethod(new ScrollingMovementMethod());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                diaryTextView.setVisibility(View.VISIBLE);
                edit_img_Btn.setVisibility(View.VISIBLE);
                del_img_Btn.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                edit_Btn.setVisibility(View.INVISIBLE);
                selectedImg.setVisibility(View.VISIBLE);
                diaryTextView.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                contextEditText.setText("");
                img_fname = "" + year + "-" + (month + 1) + "" + "-" + dayOfMonth + ".png";
                checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveDiary(txt_fname);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                save_Btn.setVisibility(View.INVISIBLE);
                edit_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
                selectedImg.setVisibility(View.VISIBLE);
            }
        });

        edit_img_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditImage.class);
                intent.putExtra("img_filename", img_fname);
                startActivityForResult(intent, 200);

            }
        });

        del_img_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File storage = getActivity().getCacheDir();
                    File file = new File(storage, img_fname);
                    file.delete();
                    selectedImg.setImageResource(R.drawable.icon_img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileInputStream fis_img;
        BufferedInputStream buf;

        File storage = getActivity().getCacheDir();
        File f = new File(storage, img_fname);

        try{
            if(!f.exists()) {
                selectedImg.setImageResource(R.drawable.icon_img);
            }
            else{
                fis_img = new FileInputStream(f);
                buf = new BufferedInputStream(fis_img);
                Bitmap tmp = BitmapFactory.decodeStream(buf);
                selectedImg.setImageBitmap(tmp);
                fis_img.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkDay(int cYear, int cMonth, int cDay)
    {
        txt_fname = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";

        FileInputStream fis_txt, fis_img;
        BufferedInputStream buf;

        File storage = getActivity().getCacheDir();
        File f = new File(storage, img_fname);

        try{
            if(!f.exists()) {
                selectedImg.setImageResource(R.drawable.icon_img);
            }
            else{
                fis_img = new FileInputStream(f);
                buf = new BufferedInputStream(fis_img);
                Bitmap tmp = BitmapFactory.decodeStream(buf);
                selectedImg.setImageBitmap(tmp);
                fis_img.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            File storage_txt = getActivity().getCacheDir();
            File file_txt = new File(storage_txt, txt_fname);

            if(!file_txt.exists()) {
                textView2.setText("");
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);

                save_Btn.setVisibility(View.VISIBLE);
                edit_Btn.setVisibility(View.INVISIBLE);
                selectedImg.setVisibility(View.VISIBLE);
            }
            else{
                fis_txt = new FileInputStream(file_txt);

                byte[] fileData = new byte[fis_txt.available()];
                fis_txt.read(fileData);
                fis_txt.close();

                str = new String(fileData);

                if(str.length() == 0){
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    edit_Btn.setVisibility(View.INVISIBLE);
                }
                else{
                    contextEditText.setVisibility(View.INVISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    textView2.setText(str);

                    save_Btn.setVisibility(View.INVISIBLE);
                    edit_Btn.setVisibility(View.VISIBLE);
                    selectedImg.setVisibility(View.VISIBLE);
                }
            }


            edit_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);
                    save_Btn.setVisibility(View.VISIBLE);
                    edit_Btn.setVisibility(View.INVISIBLE);
                    selectedImg.setVisibility(View.VISIBLE);
                    textView2.setText(contextEditText.getText());
                }

            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (textView2.getText() == null)
        {
            textView2.setVisibility(View.INVISIBLE);
            diaryTextView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.VISIBLE);
            edit_Btn.setVisibility(View.INVISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
            selectedImg.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String txt_fname)
    {
        try
        {
            File storage_txt = getActivity().getCacheDir();
            File file_txt = new File(storage_txt, txt_fname);
            file_txt.delete();
            contextEditText.setText("");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            File storage = getActivity().getCacheDir();
            File file = new File(storage, img_fname);
            file.delete();
            selectedImg.setImageResource(R.drawable.icon_img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String txt_fname)
    {
        FileOutputStream fos_txt;
        File storage_txt = getActivity().getCacheDir();
        File file_txt = new File(storage_txt, txt_fname);

        try
        {
            fos_txt = new FileOutputStream(file_txt);
            String content = contextEditText.getText().toString();
            fos_txt.write((content).getBytes());
            fos_txt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}