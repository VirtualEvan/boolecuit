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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TableLayout ly_table = (TableLayout) findViewById(R.id.ly_table);
        Button bt_a = (Button) this.findViewById( R.id.bt_a);
        Button bt_b = (Button) this.findViewById( R.id.bt_b);
        Button bt_c = (Button) this.findViewById( R.id.bt_c);
        Button bt_d = (Button) this.findViewById( R.id.bt_d);
        Button bt_e = (Button) this.findViewById( R.id.bt_e);
        Button bt_and = (Button) this.findViewById( R.id.bt_and);
        Button bt_or = (Button) this.findViewById( R.id.bt_or);
        Button bt_not = (Button) this.findViewById( R.id.bt_not);
        Button bt_left = (Button) this.findViewById( R.id.bt_left);
        Button bt_right = (Button) this.findViewById( R.id.bt_right);
        Button bt_backspace = (Button) this.findViewById( R.id.bt_backspace);
        Button bt_reset = (Button) this.findViewById( R.id.bt_reset);
        Button bt_evaluar = (Button) this.findViewById( R.id.bt_evaluar);

        final EditText ed_expresion = (EditText) this.findViewById( R.id.ed_expresion);
        comprobar( "aANDbORc" );


        bt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"a" ) ;
            }
        });

        bt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"b" ) ;
            }
        });

        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"c" ) ;
            }
        });

        bt_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"d" ) ;
            }
        });

        bt_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"e" ) ;
            }
        });

        bt_and.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"AND" ) ;
            }
        });

        bt_or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"OR" ) ;
            }
        });

        bt_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"NOT" ) ;
            }
        });

        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+"(" ) ;
            }
        });

        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_expresion.setText( ed_expresion.getText().toString()+")" ) ;
            }
        });

        bt_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ed_expresion.getText().toString();
                int length = ed_expresion.getText().length();

                if (length > 1){
                    String last = text.substring(length-2, length);
                    switch (last) {
                        case "ND" :
                            last = text.substring(0, length-3);
                            break;
                        case "OR" :
                            last = text.substring(0, length-2);
                            break;
                        case "OT" :
                            last = text.substring(0, length-3);
                            break;
                        default:
                            last = text.substring(0, length-1);
                            break;

                    }
                    ed_expresion.setText( last ) ;
                }else{
                    ed_expresion.setText( "" ) ;
                }

            }
        });

        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_expresion.setText( "" ) ;

            }
        });

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
        //Interpreter interpreter = new Interpreter();
        String[] varArray = new String[mapaVariables.size()];
        int count = 0;

        for (String key: mapaVariables.keySet()) {
            varArray[count] = key + " = " + mapaVariables.get(key);
            count++;
        }

        Object[] params = new Object[] { "javaScriptParam" };

        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        Context rhino = Context.enter();

        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            for (String s : varArray) {
                rhino.evaluateString(scope, s, "JavaScript", 1, null).toString();
            }

            String expr = expresion.replaceAll("NOT", "!");
            expr = expr.replaceAll("OR", "|");
            expr = expr.replaceAll("AND", "&");

            String toRet = rhino.evaluateString(scope, expr, "JavaScript", 1, null).toString().replaceAll(".0","");
            toRet = toRet.replaceAll("true","1");
            toRet = toRet.replaceAll("false","0");

            return toRet;


        } finally {
            Context.exit();
        }

    }
}
