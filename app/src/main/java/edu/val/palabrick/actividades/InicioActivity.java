package edu.val.palabrick.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import edu.val.palabrick.R;


/**
 * En esta Pantalla, se carga la animación inicial del juego (video_inicio)
 */
public class InicioActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private VideoView videoView;


    //defino como constante el LOG, para que en todas las clases, pueda usarlo igual
    public final static String ETIQUETA_LOG = "PALABRICK_APP";

    //TODO PROGRAMAR QUE CUANDO EL VIDEO, CAMBIEMOS DE PANTALLA
        //1 "ESCUCHAR EL FINAL DEL VIDEO" -- definir un listener-evento- esucchar el final del video
        //2 "CAMBIAR DE PANTALLA"

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try { //INTENTA ESTO
            Log.d(InicioActivity.ETIQUETA_LOG, "onCreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inicio);

            //cargar el video NO necesitamos ningún permiso para leer el video
            //por que es un fichero que reside dentro de las carpetas de mi app
            //memoria más privada
            String ruta_video = "android.resource://" + getPackageName() + "/" + R.raw.video_inicio;
            Log.d(InicioActivity.ETIQUETA_LOG, "ruta video = " + ruta_video);
            //URI - string pero con caracteres especiales
            this.videoView = findViewById(R.id.videoView); //inicio la vista
            this.videoView.setOnCompletionListener(this);//video, cuando acabes esta clase te escucha
            this.videoView.setVideoPath(ruta_video);//cargo la ruta
            this.videoView.start();//reproduzco el video "play"
            Log.d(InicioActivity.ETIQUETA_LOG, "play video");

            //getActionBar().hide();//ESTA INSTRUCCIÓN FALLA
            //oculto la barra de forma programática

            /**NOTA SOBRE LA LIBRERÍAS DE SOPORTE
             *
             * SON UNA ESPECIE DE COMPLEMENTO/DE EXTRA
             * PARA QUE EN TELÉFONOS ANTIGUOS (APIS DE ANDROID ANTIGUA)
             * SE MUESTREN CARACTERÍSTICAS MODERNAS
             *
             * POR Ej. UN TELÉFONO ANTIGUO, NO TENDRÍA BARRA DE MENU, ACTION BAR
             * PERO GRACIAS A LA LIBRERÍA DE SOPORTE, SÍ
             *
             * ES COMO UN PARCHE
             */
            getSupportActionBar().hide();
        } catch (Throwable fallo)
        {   //Y SI OCURRRE CUALQUIER FALLO, SE METE POR AQUÍ
            //CAPTURAR LA EXCEPCIÓN
            Log.e(ETIQUETA_LOG, "ha ocurrido un fallo", fallo);
        }
    }

    /**
     * cuando acabe el video, Android, llama a este método para avisarnos - CallBack
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(InicioActivity.ETIQUETA_LOG, "FIN DEL VIDEO");
        cambiarActividad();
        finish();
    }

    // Con esta funcion cambio la actividad a la pantalla del juego
    public void cambiarActividad ()
    {
        Intent intent = new Intent(this, JuegoMainActivityInflada.class);
        startActivity(intent);
    }

    // Con esta funcion sacada del checKbox mediante onclick tambien cambio la actividad
    public void desactivarVideo(View view) {
        cambiarActividad();
        finish();
    }
}