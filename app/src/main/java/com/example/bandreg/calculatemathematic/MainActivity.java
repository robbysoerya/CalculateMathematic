package com.example.bandreg.calculatemathematic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText valueP, valueI, valueN, valuePV;
    Button btnProcess;
    TextView txtResult;
    RadioButton a, b;
    Spinner spinnerAnuitas;
    String replaceable,replaceable2,currentP,currentI,currentPV,currentN;
    String result = "";
    double valuep, valuei, valuepv, valuend, formula;
    int valuen;

    DecimalFormat formatDecimal = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatIDR = new DecimalFormatSymbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaration
        valueP = findViewById(R.id.edtP);
        valueI = findViewById(R.id.edtI);
        valueN = findViewById(R.id.edtN);
        valuePV = findViewById(R.id.edtPV);
        a = findViewById(R.id.rdA);
        b = findViewById(R.id.rdB);
        spinnerAnuitas = findViewById(R.id.spinnerAnuitas);

        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!a.isChecked()) {
                    valuePV.setHint("Masukkan Nilai FV");
                } else {
                    valuePV.setHint("Masukkan Nilai PV");
                }
            }
        });

        //Output Result
        txtResult = findViewById(R.id.txtResult);

        btnProcess = findViewById(R.id.btnProcess);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int anuitas = spinnerAnuitas.getSelectedItemPosition();
                replaceable = valueP.getText().toString().trim();
                replaceable2 = valuePV.getText().toString().trim();
                currentP = replaceable;
                currentI = valueI.getText().toString().trim();
                currentPV = replaceable2;
                currentN = valueN.getText().toString().trim();
                result = "";

                if (anuitas == 0) {
                    if (a.isChecked()) {

                        try {
                            setResultCurrentValue();
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, "Cek kembali inputan ", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        try {
                            setResultValue();
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, "Cek kembali inputan", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    if (a.isChecked()) {

                        try {
                            setResultDPCurrentValue();
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, "Cek kembali inputan", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        try {
                            setResultDPValue();
                        } catch (NumberFormatException e) {
                            Toast.makeText(MainActivity.this, "Cek kembali inputan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void setResultDPValue() {

        if (currentPV.equals("") || currentPV.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            double percentageN = valuen;
            formula = ((valuep * ((Math.pow((1 + percentageI), percentageN) - 1) / percentageI)) * (1+percentageI));
            result = parseCurrency(formula);
        } else if (currentN.equals("") || currentN.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            double percentageI = (valuei / 100);
            Double formula2 = (Math.log(1 + ((valuepv * percentageI) / valuep))) / Math.log(1 + percentageI);
            result = String.valueOf(Math.ceil(formula2) + " Kali");
        } else if (currentP.equals("") || currentP.equals("0")) {
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            formula = valuepv / (((Math.pow((1 + percentageI), valuen) - 1) / percentageI) * (1 + percentageI));
            result = parseCurrency(formula);
        }

        txtResult.setText(result);

    }

    public void setResultDPCurrentValue() {

        if (currentPV.equals("") || currentPV.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            double percentageN = valuen;
            formula = (valuep * (1 - Math.pow((1 + percentageI), -(percentageN - 1))) / percentageI) + 1;
            result = parseCurrency(formula);
        } else if (currentN.equals("") || currentN.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            double percentageI = (valuei / 100);
            Double formula2 = (-Math.log(1 - ((valuepv * percentageI) / valuep))) / Math.log(1 + percentageI);
            result = String.valueOf(Math.ceil(formula2) + " Kali");
        } else if (currentP.equals("") || currentP.equals("0")) {
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            valuend = parseDouble(currentN);
            double percentageI = (valuei / 100);
            formula = valuepv / (((1 - Math.pow((1 + percentageI), (-valuend + 1))) / percentageI) + 1);
            result = parseCurrency(formula);
        }

        txtResult.setText(result);

    }

    public void setResultValue() {

        if (currentPV.equals("") || currentPV.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            formula = (valuep * Math.pow((1 + percentageI), valuen) - 1) / percentageI;
            result = parseCurrency(formula);
        } else if (currentN.equals("") || currentN.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            double percentageI = (valuei / 100);
            Double formula2 = (Math.log(1 + ((valuepv * percentageI) / valuep))) / Math.log(1 + percentageI);
            result = String.valueOf(Math.round(formula2) + " Kali");
        } else if (currentP.equals("") || currentP.equals("0")) {
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            valuend = parseDouble(currentN);
            double percentageI = (valuei / 100);
            double percentageN = (valuend);
            formula = valuepv / (Math.pow((1 + percentageI), percentageN) - 1) / percentageI;
            result = parseCurrency(formula);
        }

        txtResult.setText(result);

    }

    public void setResultCurrentValue() {

        if (currentPV.equals("") || currentPV.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            formula = (valuep * (1 - Math.pow((1 + percentageI), -valuen))) / percentageI;
            result = parseCurrency(formula);
        } else if (currentN.equals("") || currentN.equals("0")) {
            valuep = parseDouble(currentP);
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            double percentageI = (valuei / 100);
            Double formula2 = (-Math.log(1 - ((valuepv * percentageI) / valuep))) / Math.log(1 + percentageI);
            result = String.valueOf(formula2.intValue()) + " Bulan";
        } else if (currentP.equals("") || currentP.equals("0")) {
            valuei = parseDouble(currentI);
            valuepv = parseDouble(currentPV);
            valuen = Integer.parseInt(currentN);
            double percentageI = (valuei / 100);
            double percentageN = (valuen);
            formula = (valuepv * percentageI) / (1 - Math.pow((1 + percentageI), -percentageN));
            result = parseCurrency(formula);
        }

        txtResult.setText(result);

    }

    public String parseCurrency(double value) {
        formatIDR.setCurrencySymbol("Rp");
        formatIDR.setMonetaryDecimalSeparator(',');
        formatIDR.setGroupingSeparator('.');
        formatDecimal.setMaximumFractionDigits(0);
        formatDecimal.setDecimalFormatSymbols(formatIDR);
        return formatDecimal.format(value);
    }

    public double parseDouble(String value) {
        return Double.parseDouble(value);
    }

    }
