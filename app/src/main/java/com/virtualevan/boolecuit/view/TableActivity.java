package com.virtualevan.boolecuit.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualevan.boolecuit.R;
import com.virtualevan.boolecuit.core.Logic;
import com.virtualevan.boolecuit.core.SqlIO;

import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;

import java.util.DuplicateFormatFlagsException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TableActivity extends AppCompatActivity {
    private SqlIO sqlDb;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;


    private List<String> expressions = new LinkedList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sqlDb = MainActivity.getDb();
        dbWrite = MainActivity.getWritable();
        dbRead = MainActivity.getReadable();

        TextView lbl_expression_table = (TextView) findViewById( R.id.lbl_expression_table );
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableActivity.this.finish();
            }
        });
        String expression = getIntent().getExtras().getString("expression");

        try {
            lbl_expression_table.setText( expression );

            evaluar( expression );
            expressions = onRead( dbRead, expressions );

            if (expressions.size() >= 5){
                expressions.remove(0);
            }
            expressions.add(expression);

            onWrite( dbWrite, expressions );
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
        for(int i = 0; i<expressions.size(); i++){
            menu.add(0, i, 0, expressions.get(i));
        }
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.menu_table, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        TextView lbl_expression_table = (TextView) findViewById( R.id.lbl_expression_table );
        lbl_expression_table.setText( menuItem.getTitle().toString() );

        this.evaluar( menuItem.getTitle().toString() );

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
        tbrow_head.setBackgroundColor(Color.BLACK);
        for (String key: mapaVariables.keySet()) {
            TextView var_header = new TextView(this);
            var_header.setTextColor(Color.WHITE);
            var_header.setPadding(5, 5, 5, 5);
            var_header.setText( key );
            tbrow_head.addView(var_header);
        }
        TextView result_header = new TextView(this);
        result_header.setText( "Result" );
        result_header.setTextColor(Color.WHITE);
        result_header.setPadding(5, 5, 5, 5);
        tbrow_head.addView(result_header);

        ly_table.addView(tbrow_head);

        for( int i = 0;  i<Math.pow(2,mapaVariables.size()); i++){
            auxBinario = Integer.toBinaryString(i);
            auxFormato = String.format("%0"+mapaVariables.size()+"d", Integer.parseInt(auxBinario));
            valoresBinarios = auxFormato.toCharArray();
            TableRow tbrow_data = new TableRow(this);
            if(i%2 != 0){
                tbrow_data.setBackgroundColor(Color.GRAY);
            }
            else{
                tbrow_data.setBackgroundColor(Color.WHITE);
            }
            for (int j = 0; j < valoresBinarios.length; j++){
                mapaVariables.put(mapaVariables.keySet().toArray()[j].toString(), Character.getNumericValue(valoresBinarios[j]));

                TextView var = new TextView(this);
                var.setPadding(5, 5, 5, 5);
                var.setText( String.valueOf(valoresBinarios[j]) );
                tbrow_data.addView(var);

            }
            TextView result = new TextView(this);
            result.setText( Logic.calc( expresion, mapaVariables ) );
            tbrow_data.addView(result);

            ly_table.addView(tbrow_data);
        }
    }

    public static List<String> onRead( SQLiteDatabase dbRead, List<String> expressions ){
        Cursor expressionsCursor = dbRead.rawQuery( "SELECT expression FROM history", null );

        if ( expressionsCursor.moveToFirst() ) {
            do {
                expressions.add(expressionsCursor.getString( 0 ));
            } while ( expressionsCursor.moveToNext() );
            expressionsCursor.close();
        }
        return expressions;
    }

    public static void onWrite( SQLiteDatabase dbWrite, List<String> expressions ){
        dbWrite.execSQL( "DELETE FROM history" );

        for (int i=0; i<expressions.size(); i++){
            dbWrite.execSQL( "INSERT INTO history(expression) VALUES( ? )",
            new String[]{ expressions.get(i) } );
        }
    }
}
