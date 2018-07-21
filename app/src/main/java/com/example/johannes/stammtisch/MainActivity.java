package com.example.johannes.stammtisch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    List <CheckBox> essensauswahl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         datenLaden();

    }

    public void datenLaden(){
       MysqlConnector msc = new MysqlConnector();
        try {
            String test = msc.execute("userdaten.php").get();

            Log.e("Ergebnis in Main",test);

            Spinner sp = findViewById(R.id.spinner);

            List<String> tage = new ArrayList<String>();
            tage.add("Montag");
            tage.add("Dienstag");
            tage.add("Mittwoch");
            tage.add("Donnerstag");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tage);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(dataAdapter);
            sp.setSelection(0);


            List<String> essen = new ArrayList<String>();

            essen.add("Currywurst");
            essen.add("Spätzle");
            essen.add("Leberkas und Pommes");

            TableLayout tl = findViewById(R.id.tableLayout);

            essensauswahl = new ArrayList<CheckBox>();


            for (int i=0;i<essen.size();i++){
                TableRow tr = new TableRow(this);

                CheckBox cb = new CheckBox(this);
                essensauswahl.add(cb);
                TextView tv = new TextView(this);
                tv.setWidth(20);
                cb.setText(essen.get(i));
                tr.addView(tv);
                tr.addView(cb);
                tl.addView(tr);
            }

            Button bt =  new Button(this);
            bt.setText("Bestätigen");


            bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    auswahlAnzeigen();
                }
            });
            TableRow tr = new TableRow(this);
            tr.addView(bt);
            tl.addView(tr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void auswahlAnzeigen(){
    Spinner sp = findViewById(R.id.spinner);


        String ausgewaehlt = "";

        for(int i=0;i<essensauswahl.size();i++){
            if(essensauswahl.get(i).isChecked()){
                ausgewaehlt=ausgewaehlt+essensauswahl.get(i).getText()+";";

            }

        }

        if(ausgewaehlt.length()==0){
            Toast.makeText(this.getBaseContext(),"Fehler: Es muss mindestens ein Essen ausgewählt werden",
                    Toast.LENGTH_SHORT).show();
        }else{
            //Hier restlicher Code
            Toast.makeText(this.getBaseContext(),"Ausgewählt:"+sp.getSelectedItem()+"\nEssen: "+ausgewaehlt,
                    Toast.LENGTH_SHORT).show();
        }




    }
}
