package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{

    TextView t;
    TextView invalid;
    String s = "";
    String sd = "";
    ArrayList<String> equations = new ArrayList<>();
    int i = 0;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Button one = findViewById(R.id.button1);
        one.setOnClickListener(this);

        Button two = findViewById(R.id.button2);
        two.setOnClickListener(this);

        Button three = findViewById(R.id.button2);
        three.setOnClickListener(this);

         */
// Log.d("MYOUTPUT", "hello"); use it for debugging


        t  = findViewById(R.id.textView);
        invalid = findViewById(R.id.textViewInvalid);

    }


    public void Calc(){

        StringTokenizer tokAdd = new StringTokenizer(s, "+", false);

        ArrayList<String> arr = new ArrayList<String>();

        while(tokAdd.hasMoreTokens()){ //adds each token (separated by addition sign) to String array
            arr.add(tokAdd.nextToken());
        }

        double sum = 0;

        boolean error= false;

        for (int i = 0; i < arr.size(); i++) {

            if (arr.get(i).contains("*") || arr.get(i).contains("/")) {
                if(Product(arr.get(i)) == 0.834791425872){
                    error = true;
                    break;
                }
                else
                    sum += Product(arr.get(i));
            }
            else if(arr.get(i).contains("s") || arr.get(i).contains("c") || arr.get(i).contains("t")){
                arr.set(i, Trig(arr.get(i)) + "");
                sum += Double.parseDouble(arr.get(i) + "");
            }
            else {
                sum += Double.parseDouble(arr.get(i));
            }
        }
        if(error == false){
                //sum = (int)Math.round(sum);
                equations.add(sd);
                s = sum + "";
                sd = sum + "";
                t.setText(sum + "");

        }


    }

    public double Trig(String currentTk){

        StringTokenizer tokTrig = new StringTokenizer(currentTk, "s//c//t", true);

        ArrayList<String> arr3 = new ArrayList<>();

        while (tokTrig.hasMoreTokens()) {
            arr3.add(tokTrig.nextToken());
        }

        double trig = 1;

        for (int i = 0; i < arr3.size(); i++) {
            if (arr3.get(i).equals("s")) {
                trig = Math.sin(Math.toRadians(Double.parseDouble(arr3.get(i+1))));
            }
            else if (arr3.get(i).equals("c")) {
                trig = Math.cos(Math.toRadians(Double.parseDouble(arr3.get(i+1))));

            }
            else if(arr3.get(i).equals("t")) {
                trig = Math.tan(Math.toRadians(Double.parseDouble(arr3.get(i+1))));

            }
        }
        return trig;


    }



    public double Product(String currentTk){ //divides what is in the currentTok

        StringTokenizer tokProd = new StringTokenizer(currentTk, "///*", true);

        ArrayList<String> arr2 = new ArrayList<>();

        int k = 0;

        while (tokProd.hasMoreTokens()) {
            arr2.add(tokProd.nextToken());
            if(arr2.get(k).contains("c")||arr2.get(k).contains("s")||arr2.get(k).contains("t"))
                arr2.set(k, Trig(arr2.get(k))+"");
            k++;
        }
        double product = 1;

        try {

            for (int i = 0; i < arr2.size(); i++) {

                if (arr2.get(i).equals("*")) {
                    System.out.println("Product of mult iteration before mult = " + product);
                    product = Double.parseDouble(arr2.get(i - 1)) * Double.parseDouble(arr2.get(i + 1));
                    arr2.set(i + 1, product + "");
                    System.out.println("Product of mult iteration after mult = " + product);
                } else if (arr2.get(i).equals("/")) {
                    product = Double.parseDouble(arr2.get(i - 1)) / Double.parseDouble(arr2.get(i + 1));
                    arr2.set(i + 1, product + "");
                    System.out.println("Product of divide iteration= " + product);
                }
                /*else if(arr2.get(i).equals("s") || arr2.get(i).equals("c") || arr2.get(i).equals("t")){
                    Trig(arr2.get(i));
                }*/
            }

            if(product == Double.POSITIVE_INFINITY || product == Double.NEGATIVE_INFINITY||Double.isNaN(product)){
                Log.d("ExceptionTime","got to the exception");
                System.out.println("at the exception");
                throw new Exception();
            }
            return product;
        }
        catch(Exception e){
            //System.out.println("The exception is: " + e);
            t.setText("Error");
            return 0.834791425872;
        }


    }

    public boolean valid(boolean trig){
        if(s.length() == 0 && !trig) {
            invalid.setVisibility(View.VISIBLE);
            return false;
        }
        if(trig){
            if((s.length() == 0)||((s.length() > 0 && (s.substring(s.length() - 1).equals("+") || s.substring(s.length() - 1).equals("-") || s.substring(s.length() - 1).equals("/") || s.substring(s.length() - 1).equals("*"))))){
                invalid.setVisibility(View.INVISIBLE);
                return true;
            }
            else {
                invalid.setVisibility(View.VISIBLE);
                return false;
            }
        }

        /*if(trig && ((s.length() == 0)||((s.length() > 0 && (s.substring(s.length() - 1).equals("+") || s.substring(s.length() - 1).equals("-") || s.substring(s.length() - 1).equals("/") || s.substring(s.length() - 1).equals("*")))))){
            invalid.setVisibility(View.VISIBLE);
            return true;
        } */

        else if(s.substring(s.length() - 1).equals("+")|| s.substring(s.length() - 1).equals("-") || s.substring(s.length() - 1).equals("/") || s.substring(s.length() - 1).equals("*") || s.substring(s.length() - 1).equals("s") || s.substring(s.length() - 1).equals("c") || s.substring(s.length() - 1).equals("t")){
            invalid.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            invalid.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    public String previous(){
        i++;
        if(equations.size()>0 && i <= equations.size()){
            return equations.get(equations.size()-i);
        }
        else
            return "";

    }
    /*public String next(){
        j++;
        int k = 0;
        for(int i = 0; i< equations.size(); i++ ){
            if(equations.get(i).equals(sd))
                k = i;
        }
        if(k+1 <= equations.size())
            return equations.get(k+1);

        else
            return "";

    } */




    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                s += "0";
                sd += "0";
                t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button1:
                    s += "1";
                    sd += "1";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);

                break;
            case R.id.button2:
                    s += "2";
                    sd += "2";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button3:
                    s += "3";
                    sd += "3";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button4:
                    s += "4";
                    sd += "4";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button5:
                    s += "5";
                    sd += "5";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button6:
                    s += "6";
                    sd += "6";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button7:
                    s += "7";
                    sd += "7";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button8:
                    s += "8";
                    sd += "8";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.button9:
                    s += "9";
                    sd += "9";
                    t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
             case R.id.buttonEq:
                 if(valid(false)){
                     Calc();
                 }
                break;
            case R.id.buttonPlus:
                if(valid(false)){
                    s += "+";
                    sd += "+";
                    t.setText(sd);
                }
                break;
            case R.id.buttonMinus:
                if(valid(false)){
                    s += "+-"; //for simplicity, I am making subtraction the addition of negative numbers
                    sd += "-";
                    t.setText(sd);
                }
                break;
            case R.id.buttonMult:
                if(valid(false)) {
                    s += "*";
                    sd += "*";
                    t.setText(sd);
                }
                break;
            case R.id.buttonDiv:
                if(valid(false)) {
                    s += "/";
                    sd += "/";
                    t.setText(sd);
                }
                break;
            case R.id.buttonC:
                s = "";
                sd = "";
                t.setText(sd);
                invalid.setVisibility(View.INVISIBLE);
                break;
            case R.id.buttonSin:
                if(valid(true)) {
                    s += "s";
                    sd += "sin";
                    t.setText(sd);
                }
                break;
            case R.id.buttonCos:
                if(valid(true)) {
                    s += "c";
                    sd += "cos";
                    t.setText(sd);
                }
                break;
            case R.id.buttonTan:
                if(valid(true)) {
                    s += "t";
                    sd += "tan";
                    t.setText(sd);
                }
                break;
            case R.id.buttonBackspace:
                s = s.substring(0, s.length() - 1);
                if(sd.length() >= 3 && (sd.substring(sd.length() - 3).equals("sin")|| sd.substring(sd.length() - 3).equals("cos") || sd.substring(sd.length() - 3).equals("tan")))
                    sd = sd.substring(0, sd.length() - 3);
                else{
                    sd = sd.substring(0,sd.length()-1);
                }
                t.setText(sd);
                break;
            case R.id.buttonDec:
                s += ".";
                sd += ".";
                t.setText(sd);
                break;
            case R.id.buttonPrev:
                t.setText(previous());
                break;
            /*case R.id.buttonNext:
                t.setText(next());
                break; */
        }
    }

}