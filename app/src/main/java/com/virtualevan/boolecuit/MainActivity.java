package com.virtualevan.boolecuit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;

//import javax.script.GenericScriptContext;
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TableLayout ly_table = (TableLayout) findViewById(R.id.ly_table);
        Button bt_evaluar = (Button) this.findViewById( R.id.bt_evaluar);
        final EditText ed_expresion = (EditText) this.findViewById( R.id.ed_expresion);
        comprobar( "aANDbORc" );

        bt_evaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_table.removeAllViews();
                comprobar( ed_expresion.getText().toString() );
            }
        });


    }

    public void comprobar(String expresion){
        Map<String, Integer> mapaVariables = new LinkedHashMap<>();
        char[] valoresBinarios;
        String auxBinario;
        String auxFormato;
        TableLayout ly_table = (TableLayout) findViewById(R.id.ly_table);



        Pattern pattern = Pattern.compile( "([a-z])" );
        Matcher matcher = pattern.matcher( expresion );

        while( matcher.find() ){
            mapaVariables.put(matcher.group(1),0);
        }

        //Table header
        TableRow tbrow_head = new TableRow(this);
        for (String key: mapaVariables.keySet()) {
            TextView var_header = new TextView(this);
            //var.setGravity(Gravity.CENTER);
            var_header.setText( key );
            tbrow_head.addView(var_header);
        }
        TextView result_header = new TextView(this);
        result_header.setGravity(Gravity.CENTER);
        result_header.setText( "Result" );
        tbrow_head.addView(result_header);

        ly_table.addView(tbrow_head);


        for( int i = 0;  i<Math.pow(2,mapaVariables.size()); i++){
            auxBinario = Integer.toBinaryString(i);
            auxFormato = String.format("%0"+mapaVariables.size()+"d", Integer.parseInt(auxBinario));
            valoresBinarios = auxFormato.toCharArray();
            TableRow tbrow_data = new TableRow(this);
            for (int j = 0; j < valoresBinarios.length; j++){
                mapaVariables.put(mapaVariables.keySet().toArray()[j].toString(), Character.getNumericValue(valoresBinarios[j]));

                TextView var = new TextView(this);
                var.setGravity(Gravity.CENTER);
                var.setText( String.valueOf(valoresBinarios[j]) );
                tbrow_data.addView(var);

            }
            TextView result = new TextView(this);
            result.setGravity(Gravity.CENTER);
            result.setText( calc( expresion, mapaVariables ) );
            tbrow_data.addView(result);

            ly_table.addView(tbrow_data);
        }


    }

    public String calc(String expresion, Map<String, Integer> mapaVariables){
        //ScriptEngineManager engineManager = new ScriptEngineManager();
        //ScriptEngine engine = engineManager.getEngineByName("js");
        Interpreter interpreter = new Interpreter();
        String[] varArray = new String[mapaVariables.size()];
        int count = 0;

        System.out.println();
        for (String key: mapaVariables.keySet()) {
            varArray[count] = key + " = " + mapaVariables.get(key);
            count++;
        }

        try{
            for (String s : varArray) {
                interpreter.eval(s);
            }

            String expr = expresion.replaceAll("NOT", "!");
            expr = expr.replaceAll("OR", "|");
            expr = expr.replaceAll("AND", "&");

            return interpreter.eval(expr).toString();


        }
        catch(EvalError e){
            System.out.println(e);
        }

        return null;
    }
}
