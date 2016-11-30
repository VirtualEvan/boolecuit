package com.virtualevan.boolecuit.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.virtualevan.boolecuit.R;
import com.virtualevan.boolecuit.core.SqlIO;


public class MainActivity extends AppCompatActivity {
    private SqlIO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SqlIO( this.getApplicationContext() );

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

                    Intent tableActivityIntent = new Intent( MainActivity.this, TableActivity.class );
                    tableActivityIntent.putExtra("expression", ed_expresion.getText().toString());

                    startActivity( tableActivityIntent );

            }
        });


    }

    public SqlIO getDb(){
        return db;
    }

}
