package com.virtualevan.boolecuit.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualevan.boolecuit.R;
import com.virtualevan.boolecuit.core.Logic;

import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;

import java.util.DuplicateFormatFlagsException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableActivity.this.finish();
            }
        });
        String expression = getIntent().getExtras().getString("expression");

        try {
            evaluar( expression );

        }
        catch ( DuplicateFormatFlagsException | EvaluatorException | EcmaError ex) {
            if ( expression.equals("") ) {
                Toast.makeText( this, "Introduzca una expresión", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText( this, "Expresión inválida", Toast.LENGTH_SHORT).show();
            }
            this.finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 0, 0, "Opcion1");
        menu.add(0, 1, 0, "Opcion2");
        menu.add(0, 2, 0, "Opcion3");
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.menu_table, menu );
        return true;
    }

    public void evaluar(String expresion) {
        Map<String, Integer> mapaVariables = new LinkedHashMap<>();
        char[] valoresBinarios;
        String auxBinario;
        String auxFormato;
        TableLayout ly_table = (TableLayout) findViewById(R.id.ly_table);
        ly_table.removeAllViews();



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
            result.setText( Logic.calc( expresion, mapaVariables ) );
            tbrow_data.addView(result);

            ly_table.addView(tbrow_data);
        }


    }
}
