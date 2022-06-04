package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import edu.val.palabrick.R;

public class AjustesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Integer[] opciones_longitud_palabra = {3, 4, 5};
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        //SPINNER O LISTA DESPLEGABLE
        this.spinner = findViewById(R.id.spinner_longitud);
        //Adapter --> actúa como proveedor de datos del spinner
        //yo el adapter, se lo asgino al spinner
        //cuando el spinner se vaya a representar, usa al adapter
        //y le dice: DAME LOS DATOS!
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, opciones_longitud_palabra);
        //le tenemos que dar una apariencia para cuando esté desplegado el spinner
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinner.setAdapter(arrayAdapter);//le digo al spinner, éste es tu adapter

        //al spinner, le tengo que programar su listener
        this.spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(InicioActivity.ETIQUETA_LOG, "OPCION SELECCIONADA DEL DESPLAGABLE");
        //cómo puedo hacer para obtener el texto si view la opción seleecionada
        TextView opcion_seleccionada = (TextView) view;
        //opcion_seleccionada.getText();
        Log.d(InicioActivity.ETIQUETA_LOG, "OPCION seleccionada = " + opcion_seleccionada.getText().toString() );


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(InicioActivity.ETIQUETA_LOG, "OPCION DESAPARECIDA DEL DESPLAGABLE onNothingSelected");

    }
}