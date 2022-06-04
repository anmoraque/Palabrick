package edu.val.palabrick.actividades;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.val.palabrick.R;

public class JuegoMainActivityInflada extends AppCompatActivity {


    //2 constantes (seleccionar con una lista desplegable) - Spinner
    private static final int MAX_INTENTOS = 6;//NUMERO DE FILAS
    private static final int TAMANIO_PALABRAS = 5;//NUMEROS DE CASILLAS

    private LinearLayout espacio_tablero;//parte superior de la pantalla
    private String palabra_adivinar;
    private int intentos_actual;
    private int casilla_actual;
    private String palabra_usuario;

    private List<LinearLayout> lista_filas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_main_inflado);

        dibujarTablero ();
        this.palabra_adivinar = generarPablraAdivnar();
        Log.d(InicioActivity.ETIQUETA_LOG, "PALABRA A ADIVINAR = "+this.palabra_adivinar);
        programarBotones ();
        this.intentos_actual = 0;
        this.casilla_actual = 0;
        this.palabra_usuario = "";
        //this.palabra_usuario = new String("");

    }

    private void programarBotones ()
    {
        //tengo que sacar todos los botones, y programarles el evento
        LinearLayout teclado = findViewById(R.id.espacioTeclado);
        int hijos = teclado.getChildCount();
        Log.d(InicioActivity.ETIQUETA_LOG, "NUM HIJOS ESPACIO TECLADO " + hijos);
        //recorrer las botoneras - las filas de botones
        for (int filabotones=0; filabotones < hijos; filabotones++)
        {
            LinearLayout layoutfilaboton = (LinearLayout) teclado.getChildAt(filabotones);
            int numbotonesfila = layoutfilaboton.getChildCount();
            //voy cogiendo cada botón de la fila y le voy programando el listener "onclick"
            for (int nbotones = 0; nbotones < numbotonesfila; nbotones++)
            {
                Button boton_actual = (Button)layoutfilaboton.getChildAt(nbotones);
                boton_actual.setOnClickListener(this::botonTocado);

            }
        }
    }

    private String generarPablraAdivnar()
    {
        String palabra_adivinar = null;
        //TODO generar una palabra aleatoriamente Arrays Tipados
        //tener el cuenta la longitud de PALABRA
            palabra_adivinar = "VIDAS";

        return palabra_adivinar;

    }

    private void dibujarTablero ()
    {
        //obtener el padre
        this.espacio_tablero = findViewById(R.id.espacioTablero);
        //obtener el inflador "servicio"
        LayoutInflater layoutInflater = getLayoutInflater();

        this.lista_filas = new ArrayList<LinearLayout>(MAX_INTENTOS); //voy a ir guardando en esta lista, la referencia a las filas
        //tengo que inflar tanto fila_palabra como MAX_INTENTOS
        for (int fila=0; fila<MAX_INTENTOS; fila++)
        {
            //inflo
            //añado algun parámetro
            //añado a la caja padre

            // LinearLayout linear_tablero = (LinearLayout) layoutInflater.inflate(R.layout.fila_palabra, espacio_tablero, true);
            LinearLayout fila_layout = (LinearLayout) layoutInflater.inflate(R.layout.fila_palabra, espacio_tablero, false);
            for (int casilla=0; casilla < TAMANIO_PALABRAS; casilla++)
            {
                //layoutInflater.inflate(R.layout.casilla_letra, fila_layout,true);//no necesito meterle mano a la casilla
                //fila_layout.addView(casilla_layout);
                LinearLayout casilla_layout = (LinearLayout) layoutInflater.inflate(R.layout.casilla_letra, fila_layout,false);
                fila_layout.addView(casilla_layout);

            }
            espacio_tablero.addView(fila_layout);
            this.lista_filas.add(fila_layout);
        }
    }

    private void rellenarCasilla (String texto_boton)
    {
        //TODO colocar text_boton en la casilla que toque
        if (casilla_actual< TAMANIO_PALABRAS)
        {
            LinearLayout fila_actual =  this.lista_filas.get(this.intentos_actual);
            //tendré que llevar control de por qué palabra casilla voy
            LinearLayout casilla_linear = (LinearLayout) fila_actual.getChildAt(casilla_actual);
            TextView textView = (TextView) casilla_linear.getChildAt(0);
            textView.setText(texto_boton);
            this.casilla_actual++;//corro a la siguiente casilla
            //actualizo la pabra
            this.palabra_usuario = this.palabra_usuario + texto_boton;
            Log.d(InicioActivity.ETIQUETA_LOG, "PALABRA USUARIO " + this.palabra_usuario);
        } else {
            Log.d(InicioActivity.ETIQUETA_LOG, "PULSACIÓN IGNORADA LA PALABRA TIENE TODAS SUS LETRAS");
        }
    }

    public void cajaTocada(View view) {
        Log.d(InicioActivity.ETIQUETA_LOG, "caja tocada");
    }

    private void borrarCasilla () {
        //TENGO QUE BORRAR PONER VACÍA LA ÚLTIMA CASILLA
        if (casilla_actual>0)
        {
            casilla_actual--;
            LinearLayout fila_actual =  this.lista_filas.get(this.intentos_actual);
            //tendré que llevar control de por qué palabra casilla voy
            LinearLayout casilla_linear = (LinearLayout) fila_actual.getChildAt(casilla_actual);
            TextView textView = (TextView) casilla_linear.getChildAt(0);
            textView.setText("");
            //quitar el último caracter de la palabra_usuario
            this.palabra_usuario = this.palabra_usuario.substring(0, this.palabra_usuario.length()-1);
            Log.d(InicioActivity.ETIQUETA_LOG, "PALABRA USUARIO " + this.palabra_usuario);
        } else {
            Log.d(InicioActivity.ETIQUETA_LOG, "la palabra está vacía, no hay casilla que borrar");
        }

    }

    private boolean esPalabraUsuarioValida (String palabra)
    {
        //?¿?¿ TODO estudiar la viablidad de la validación
        //¿¿ validar contra el diccionario
        //?? validar con una lista
        return true;
    }

    /**
     * tengo que comparar letra a letra entre la palabra del usuario y la de adivinar
     * y por cada letra/posicion
     * DATO. RESULTADO DE LA COMPRACIÓN --> tIPO ENUMERADO
     *  decirme; si conciden la letra - VERDE -0 - COINCIDE
     *          si no coinciden y no está - GRIS -1 NO ESTA
     *          si no coinciden pero está presente - AMARILLO -2 DESORDENADA
     */

    private List<TipoCompracion> comprobarPalabras(String palabra_usuario, String palabra_adivinar)
    {
        //[NO_ESTA, NO_ESTA, NO_ESTA, NO_ESTA, NO_ESTA ] -- PINTAR TODO GRIS
        //[NO_ESTA, DESORDENADA, NO_ESTA, NO_ESTA, NO_ESTA ] -- PINTAR TODO GRIS, menos la segunda EN AMARILLO
        List<TipoCompracion> lista_resultados = null;

            lista_resultados = new ArrayList<TipoCompracion>();
            for (int pos=0; pos<TAMANIO_PALABRAS; pos++)
            {
                TipoCompracion tc = TipoCompracion.comprarPosicion(palabra_usuario, palabra_adivinar, pos);
                lista_resultados.add(tc);
            }

        return lista_resultados;
    }

    private void actualizarFila (List<TipoCompracion> lista_resultados)
    {
     //TODO recorrer la lista_resultados e ir pintando cada casilla de la fila en curso
     LinearLayout fila_palabra_actual = this.lista_filas.get(intentos_actual);
     int color = 0;
     for (int num_casilla = 0; num_casilla<TAMANIO_PALABRAS; num_casilla++)
     {
         LinearLayout casilla = (LinearLayout) fila_palabra_actual.getChildAt(num_casilla);//obtenemos la casilla
         //TextView caja_letra = (TextView) casilla.getChildAt(0);
         TipoCompracion tipoCompracionActual = lista_resultados.get(num_casilla);
         switch (tipoCompracionActual)
         {
             case  NO_ESTA:
                 color = R.color.noesta;
                 break;
             case  COINCIDE:
                 color = R.color.coincide;
                 break;
             case  DESORDENADO:
                 color = R.color.desordenado;
                 break;
         }

         casilla.setBackgroundColor(getResources().getColor(color));
     }
    }

    private void probarPalabra()
    { //TENDRÍA QUE COMPROBAR SI ESTÁ LA PALABRA COMPLETA
        if (casilla_actual==TAMANIO_PALABRAS)
        {
            Log.d(InicioActivity.ETIQUETA_LOG, "La pabra está completa");
            if (esPalabraUsuarioValida(this.palabra_usuario))
            {
                Log.d(InicioActivity.ETIQUETA_LOG, "La pabra del usario no es válida");
                List<TipoCompracion> lista_resultados = comprobarPalabras(palabra_usuario, palabra_adivinar);
                Log.d(InicioActivity.ETIQUETA_LOG, "LISTA RESULTADOS = " + lista_resultados);
                //TODO pintar tanto el tablero (la fila de la palbra actual) como los botones con la lista de resultados
                actualizarFila (lista_resultados);
                if (TipoCompracion.palabraAcertada(lista_resultados)) //== true
                {
                    Log.d(InicioActivity.ETIQUETA_LOG, "HAS GANADO " +this.palabra_adivinar);
                    String mensaje_victoria = getString(R.string.mensaje_victoria);
                    Toast.makeText(this, mensaje_victoria +" "+this.palabra_adivinar, Toast.LENGTH_LONG).show();
                } else {
                    //SI NO HA HACERTADO
                    Log.d(InicioActivity.ETIQUETA_LOG, "NO HA ACERTADO " +this.palabra_adivinar);
                    this.intentos_actual++;//pasar a la siguiente palabra
                    if (intentos_actual<MAX_INTENTOS)
                    {
                        //QUEDAN INTENTOS
                        this.casilla_actual=0;
                        this.palabra_usuario = "";
                    }
                    else {
                        //NO QUEDAN INTENTOS
                        //INFORMAR DERROTA
                        Log.d(InicioActivity.ETIQUETA_LOG, "HAS PERDIDO, ERA " +this.palabra_adivinar);

                        //válido de las dos maneras, para obtener un literal traducido sobre la marcha
                        //on the fly
                        String mensaje_derrota = getString(R.string.mensaje_derrota);
                        //String mensaje_derrota = getResources().getString(R.string.mensaje_derrota);

                        //Toast.makeText(this, "HAS PERDIDO, ERA " + this.palabra_adivnar, Toast.LENGTH_LONG).show();
                        //ponemos i18n
                        Toast.makeText(this, mensaje_derrota + " " +this.palabra_adivinar, Toast.LENGTH_LONG).show();
                        //NAVEGAR A ESTADISTICAS, GESTIONAR FINAL?¿
                    }
                }
            } else {
                //
                Log.d(InicioActivity.ETIQUETA_LOG, "La pabra del usario no es válida");
                String palabra_novalida = getString(R.string.palabra_no_valida);
                Toast.makeText(this, palabra_novalida, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(InicioActivity.ETIQUETA_LOG, "COMPLETE LA PALABRA PARA PROBAR");
            String palabra_incompleta = getString(R.string.palabra_incompleta);
            Toast.makeText(this, palabra_incompleta, Toast.LENGTH_LONG).show();
        }

        //2 comprobar si la palabra es correcta =? / LISTA / DICCIONARIO - OPCIONAL
        //3 comparar con la que hay que adivinar
            //coincida --> has ganado y vas a estadísticas
            //no coincide --> actualizar teclado / tablero con los colores
                //cambiar de fila, ACTUALIZAR CASILLA actual
    }

    public void botonTocado(View view) {

        if (view instanceof Button)
        {
            Log.d(InicioActivity.ETIQUETA_LOG, "LA VISTA TOCADA ES UN BOTÓN");
            switch (view.getId())
            {
                case R.id.boton_borrar:
                    Log.d(InicioActivity.ETIQUETA_LOG, "ha tocado el boton de borrar");
                    borrarCasilla ();
                    break;
                case R.id.boton_enviar:
                    Log.d(InicioActivity.ETIQUETA_LOG, "ha tocado el boton de enviar");
                    probarPalabra();
                    break;
                default://caso general, ha tocado una letra
                    Log.d(InicioActivity.ETIQUETA_LOG, "caso general, ha tocado una letra");
                    Button boton_tocado = (Button) view;//hago el casting
                    String texto_boton = boton_tocado.getText().toString();//para poder accedeer al texto
                    Log.d(InicioActivity.ETIQUETA_LOG, "TEXTO BOTON = "+texto_boton);
                    //TODO EL TEXTO DEL BOTÓN EN LA CASILLA CORRESPONDIENTE
                    rellenarCasilla (texto_boton);
                    break;
            }


        }


    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}