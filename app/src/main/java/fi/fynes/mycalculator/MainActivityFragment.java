package fi.fynes.mycalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private String flag = MainActivity.class.getSimpleName();

    ArrayList<CalculationValue> currentValues = new ArrayList<>();
    View rootView;

    Button btnPlus, btnMinus, btnDivide, btnMultiply,
            btnClear, btnDelete, btnEquals, btnBrackets;

    CalculationValue calcObjectMain;
    String displayedValue, newValue, lastOperator;
    TextView input;
    boolean bracketsOpen;

    int currentObjectId;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentValues = new ArrayList<>();
        currentObjectId = 0;
        bracketsOpen = false;
        lastOperator = "=";
         rootView = inflater.inflate(R.layout.fragment_main, container, false);



        btnPlus = (Button) rootView.findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(this);
        btnMinus = (Button) rootView.findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(this);
        btnDivide = (Button) rootView.findViewById(R.id.btn_divide);
        btnDivide.setOnClickListener(this);
        btnMultiply = (Button) rootView.findViewById(R.id.btn_multiply);
        btnMultiply.setOnClickListener(this);

        btnClear = (Button) rootView.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
        btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);
        btnEquals = (Button) rootView.findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(this);
        btnBrackets = (Button) rootView.findViewById(R.id.btn_brackets);
        btnBrackets.setOnClickListener(this);

        calcObjectMain = new CalculationValue();
        currentValues.add(calcObjectMain);
        displayedValue = Integer.toString(0);
        input = (TextView) rootView.findViewById(R.id.input_field);

        input.setText(Integer.toString(0));

        input.setOnClickListener(this);



        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn_plus:
                calculationStarter("+");

                break;
            case R.id.btn_minus:
                calculationStarter("-");

                break;
            case R.id.btn_divide:
                calculationStarter("/");
                break;
            case R.id.btn_multiply:
                calculationStarter("*");
                break;
            case R.id.btn_clear:

                currentValues.clear();
                calcObjectMain = new CalculationValue();
                currentValues.add(calcObjectMain);
                currentObjectId = 0;
                newValue = "";
                displayedValue= "";
                input.setHint(displayedValue);


                break;
            case R.id.btn_delete:

                if(input.getText().toString().contentEquals("")) {
                    newValue ="";
                }else{


                    newValue = input.getText().toString().substring(0, input.getText().length() - 1);
                }
                break;

            case R.id.btn_brackets:



                addBrackets();

                break;

            case R.id.btn_equals:

                newValue = calculationFinisher();
                lastOperator = "=";
                input.setHint(newValue);


                break;

            case R.id.input_field:

                    newValue = "";



                break;
        }

        input.setText(newValue);
    }


    public void displayTextSetter(double value){

        if(value == 0.0){
            newValue = "";
        }
        else{


        if(lastOperator.contentEquals("=")){
            newValue = Double.toString(value);


        }
        else{


            if(input.getText().toString().contentEquals("") || Double.parseDouble(input.getText().toString()) != 0.0 ){


                newValue = input.getText().toString() + Double.toString(value);
            }
        else{
            newValue = Double.toString(value);
        }
        }
        }
    }

    public void addBrackets(){
        if(bracketsOpen){
            displayedValue += input.getText().toString()+ ")";

            currentValues.get(currentObjectId).runCalc(Double.parseDouble(input.getText().toString()));
            lastOperator = ")";

             }
        else{
            displayedValue += "(";

         currentObjectId = currentObjectId+1;
            CalculationValue insideBracket = new CalculationValue();
            currentValues.add(insideBracket);
        }
        bracketsOpen = !bracketsOpen;
        input.setHint(displayedValue);
    }

    public void calculationStarter(String operator) {

        try {
            if (lastOperator.contentEquals(")")) {
                currentValues.get(currentObjectId).setLastSavedAction(operator);
                newValue = "";
                currentObjectId = currentObjectId+1;
                CalculationValue closeBrackets = new CalculationValue();
                currentValues.add(closeBrackets);

            } else {
                lastOperator = operator;


                currentValues.get(currentObjectId).setLastSavedAction(operator);
                Log.d(flag, "OPERATOR SAVED "+currentValues.get(currentObjectId).getLastSavedAction());
                if (newValue.contentEquals("")) {
                    newValue = input.getText().toString();
                }

                currentValues.get(currentObjectId).runCalc(Double.parseDouble(newValue));


                input.setText("");

            }
                if (lastOperator.contentEquals("=")) {
                    displayedValue = Double.toString(currentValues.get(currentObjectId).getLastSavedValue());
                    Log.d(flag, "SAVED VALUE IS " + Double.toString(currentValues.get(currentObjectId).getLastSavedValue()));
                } else if (lastOperator.contentEquals(")")) {
                    displayedValue += operator;
                } else {
                    displayedValue += newValue + " " + operator;

                }



                input.setHint(displayedValue);

                displayTextSetter(0);

            Log.d(flag, "CURRENT OBJECT IS " + Integer.toString(currentObjectId) +" ITS VALUE IS "+Double.toString(currentValues.get(currentObjectId).getLastSavedValue()));
        }
            catch(NullPointerException e){
                Log.d(flag, "WHOOPS! Calculation failed " + e.toString());
            }


        }





    public String calculationFinisher() {



            calculationStarter(currentValues.get(currentObjectId).getLastSavedAction());
        lastOperator = "=";
        double result = 0d;
        String finalValue;
        int lastItemId = currentValues.size() - 1;
        if (currentValues.size() > 1) {

        for(int i=0; i<lastItemId; i++){

            Log.d(flag, "CURRENT OBJECT ID " +Integer.toString(i));
            double secondValue = currentValues.get(i+1).getLastSavedValue();
            double firstValue = currentValues.get(i).getLastSavedValue();
            Log.d(flag, "OBJECT "+ Integer.toString(i)+ " FIRST VALUE is "+ Double.toString(firstValue));
            if(secondValue == 0d){
                secondValue = Double.parseDouble(input.getText().toString());
            }


            Log.d(flag, "OBJECT "+ Integer.toString(i+1)+ " SECOND VALUE is "+ Double.toString(secondValue));

            Log.d(flag, "OPERATION IS " + currentValues.get(i).getLastSavedAction());
            switch(currentValues.get(i).getLastSavedAction()){
                case "+":

                    result = firstValue + secondValue;

                    break;

                case "-":

                    result = firstValue - secondValue;
                    break;

                case "*":

                    result = firstValue * secondValue;
                    break;


                case "/":
                    result = firstValue / secondValue;
                    break;



            }

            currentValues.get(i+1).setLastSavedValue(result);

        }

            finalValue = Double.toString(currentValues.get(lastItemId).getLastSavedValue());
            Log.d(flag, "EQUALS MULTIOBJECT " + finalValue);
            return finalValue;






        }
        else{


            result = currentValues.get(currentObjectId).getLastSavedValue();
            currentValues.get(currentObjectId).setLastSavedValue(result);
            finalValue = Double.toString(result);
            displayTextSetter(result);

            Log.d(flag, "EQUALS SINGLE "+ finalValue);
            return finalValue;
        }


    }








}
