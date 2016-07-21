package eder.padilla.personal.works.redhabitat20.fragments.fragmentsPreguntas;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;

import eder.padilla.personal.works.redhabitat20.activitys.CalendarActivity;
import eder.padilla.personal.works.redhabitat20.activitys.MainActivity;
import eder.padilla.personal.works.redhabitat20.fragments.dialogs.DialogoFinalEncuesta;
import eder.padilla.personal.works.redhabitat20.fragments.dialogs.Firma_Electronica;
import eder.padilla.personal.works.redhabitat20.util.Constants;
import eder.padilla.personal.works.redhabitat20.util.Util;
import eder.padilla.personal.works.redhabitat20.modelos.Encuesta;
import eder.padilla.personal.works.redhabitat20.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Eder on 15/03/2016.
 */
public class Fg_Encuesta_Pregunta_Nueve extends Fragment implements View.OnClickListener {
    private Button btnTerminarEncuesta;
    private EditText etComentarioFinal;
    private TextView firma_digital;
    Calendar c = Calendar.getInstance();
    Realm realm;
    RealmConfiguration config;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encuesta_pregunta_nueve, container, false);
        objectInitialization(view);
        setListeneres();
         return view;
    }


    public static Fg_Encuesta_Pregunta_Nueve newInstance() {
        Fg_Encuesta_Pregunta_Nueve nueve_de_nueve = new Fg_Encuesta_Pregunta_Nueve();
        return nueve_de_nueve;
    }
    public void objectInitialization(View view) {
        etComentarioFinal = (EditText) view.findViewById(R.id.comentariofinalNueve);
        btnTerminarEncuesta = (Button) view.findViewById(R.id.final1);
        firma_digital=(TextView)view.findViewById(R.id.firmadigital);
        config= new RealmConfiguration.Builder(getContext()).build();
        realm = Realm.getInstance(config);
    }
    public void setListeneres(){
        firma_digital.setOnClickListener(this);
        btnTerminarEncuesta.setOnClickListener(this);
    }
    public void signatureRequest(){
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        Firma_Electronica editNameDialog = new Firma_Electronica();
        editNameDialog.show(fm, "fragment_firma_cliente");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.final1:
                String f=c.get(Calendar.DAY_OF_YEAR)+" / "+c.get(Calendar.MONTH)+" / "+c.get(Calendar.YEAR);
                  /**Casteamos nuestro objeto y asignamos valor al campo del modelo y llamamos a
                    * la proxima pagina del viewpager*/
                ((MainActivity) getActivity()).encuesta.setComentarioFinal(etComentarioFinal.getText().toString());
                ((MainActivity) getActivity()).encuesta.setFecha(f);
                createEncuestaRespondida(((MainActivity) getActivity()).encuesta);
                Encuesta a = ((MainActivity)getActivity()).encuesta;
                System.out.println("El objeto es " + Util.toStringEncuesta(a));
                dialogoFinal();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.RESULT_OF_END_QUIZ,"finalizada");
                Log.i("ponemos en el extra",""+returnIntent.getExtras());
                getActivity().setResult(Activity.RESULT_OK,returnIntent);
                getActivity().finish();
                break;
            case R.id.firmadigital:
                signatureRequest();
                break;
        }
    }
    public void dialogoFinal() {
        FragmentManager fm2 = getActivity().getFragmentManager();
        DialogoFinalEncuesta editNameDialog = new DialogoFinalEncuesta();
        editNameDialog.show(fm2,"Hi");
        editNameDialog.dismiss();
    }
    public void createEncuestaRespondida(Encuesta encuesta) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(encuesta);
        realm.commitTransaction();
    }
}
