package com.lapiragua.applapiragua.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lapiragua.applapiragua.LugarPatrimonialActivity;
import com.lapiragua.applapiragua.R;
import com.lapiragua.applapiragua.viewQRActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQR#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQR extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Button btn_AbrirScanner;
    private Button btn_Buscar;
    private EditText et_QR;
    private IntentIntegrator intentIntegrator;

    private View view;

    public FragmentQR() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQR.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQR newInstance(String param1, String param2) {
        FragmentQR fragment = new FragmentQR();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_q_r, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();

    }
    private void init(){

        btn_AbrirScanner = view.findViewById(R.id.AbrirScanner);
        btn_Buscar= view.findViewById(R.id.button_buscar);
        et_QR = view.findViewById(R.id.editTextQR);
    }
    private void event(){

        btn_AbrirScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewQRUI();
                /*
                intentIntegrator = new IntentIntegrator(
                        CodigoQR.this);
                intentIntegrator.setPrompt("Coloca el código QR en el interior del rectángulo del viso para escanear ");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

                 */
            }
        });
        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeQR;
                codeQR = et_QR.getText().toString();
                codeQR = codeQR.toUpperCase();
                Intent intent = new Intent(getContext(), LugarPatrimonialActivity.class);
                intent.putExtra("codeQR", codeQR);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // llamar a la informacion

        IntentResult result = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );

        if (result.getContents()!=null) {
            //obtener la información en un String
            String dato = result.getContents();
            et_QR.setText(dato);
        }
        /*
        Uri link = Uri.parse(dato);
        Intent i = new Intent(Intent.ACTION_VIEW,link);
        startActivity(i);
        */

    }


    private void showHomeUI(){
        Intent intent = new Intent(getContext(), HomeAppActivity.class);
        startActivity(intent);
    }
    private void showViewQRUI(){
        Intent intent = new Intent(getContext(), viewQRActivity.class);
        startActivity(intent);
    }
}