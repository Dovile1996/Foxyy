package tk.folrikas.bitinelis;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class NaujaPreke extends Activity {
    private Spinner TipasSelect, ind_uzsakymas, medaus_rusis, ipakavimo_rusis;
    private Button pateikti;
    private EditText kiekis, kaina, turis;
    private static final String REGISTER_URL = "http://gulp.byethost12.com/mobilus/new_post.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauja_preke);
        //buttonas
        pateikti();
    }

    public void pateikti() {
        TipasSelect = (Spinner) findViewById(R.id.TipasSelect);
        ind_uzsakymas = (Spinner) findViewById(R.id.ind_uzsakymai);
        medaus_rusis = (Spinner) findViewById(R.id.medaus_rusis);
        ipakavimo_rusis = (Spinner) findViewById(R.id.ipakavimo_rusis);
        kiekis = (EditText) findViewById(R.id.kiekis);
        kaina = (EditText) findViewById(R.id.kaina);
        turis = (EditText) findViewById(R.id.turis);
        pateikti = (Button) findViewById(R.id.pateikti);

        //final Spinner Tipas = (Spinner) findViewById(R.id.TipasSelect);
        List<String> list = new ArrayList<String>();
        list.add("Medus");
        list.add("Pikis");
        list.add("Bičių duonelė");
        list.add("Žiedadulkės");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(NaujaPreke.this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TipasSelect.setAdapter(dataAdapter);

        //final Spinner ind_uzsakymas = (Spinner) findViewById(R.id.ind_uzsakymai);
        List<String> list2 = new ArrayList<String>();
        list2.add("Priimami");
        list2.add("Nepriimami");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(NaujaPreke.this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ind_uzsakymas.setAdapter(dataAdapter2);

        //final Spinner medaus_rusiss = (Spinner) findViewById(R.id.medaus_rusis);
        List<String> list3 = new ArrayList<String>();
        list3.add("pavasarinis");
        list3.add("miško");
        list3.add("kreminis");
        list3.add("grikių");
        list3.add("aviečių");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(NaujaPreke.this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medaus_rusis.setAdapter(dataAdapter3);

        //final Spinner ipakavim_rusiss = (Spinner) findViewById(R.id.ipakavimo_rusis);
        List<String> list4 = new ArrayList<String>();
        list4.add("dovaninis");
        list4.add("paprastas");
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(NaujaPreke.this,
                android.R.layout.simple_spinner_item, list4);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ipakavimo_rusis.setAdapter(dataAdapter4);



        pateikti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kiekis1 = kiekis.getText().toString();
                String kaina1 = kaina.getText().toString();
                String turis1 = turis.getText().toString();

                kiekis.setError(null);
                kaina.setError(null);
                turis.setError(null);

                boolean show = true;
                if (TextUtils.isEmpty(kiekis1)){
                    kiekis.setError("privalomas laukelis");
                    show = false;
                }
                if (TextUtils.isEmpty(kaina1)){
                    kaina.setError("privalomas laukelis");
                    show = false;
                }
                if (TextUtils.isEmpty(turis1)){
                    turis.setError("privalomas laukelis");
                    show = false;
                }
                if (show){
                    double kaina = Double.parseDouble(kaina1);
                    double turis = Double.parseDouble(turis1);
                    double kiekis = Double.parseDouble(kiekis1);
                    //public Produktas(String tipas, double kiekis, String uzsakymai, double kaina, String rusis, double turis, String ipakavimas)
                    Produktas irasas = new Produktas(String.valueOf(TipasSelect.getSelectedItem()),kiekis,String.valueOf(ind_uzsakymas.getSelectedItem()),kaina,String.valueOf(medaus_rusis.getSelectedItem()),turis,String.valueOf(ipakavimo_rusis.getSelectedItem()));
                    Toast.makeText(NaujaPreke.this,
                            "objekte: " +
                                    "\nTipas: " + irasas.getTipas()+
                                    "\nKiekis: " + irasas.getKiekis()+
                                    "\nUzsakymai: " + irasas.getUzsakymai()+
                                    "\nkaina: " + irasas.getKaina()+
                                    "\nRūšis: " + irasas.getRusis()+
                                    "\nTūris: " + irasas.getTuris()+
                                    "\nĮpakavimo tipas: " + irasas.getIpakavimas(),
                            Toast.LENGTH_SHORT).show();
                    postToDatabase(irasas);
                }
            }
        });
    }
    private void postToDatabase (final Produktas cl){
        class NewEntry extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NaujaPreke.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                // Pirmas string raktas, antras string reiksme
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("tipas",params[0]);
                data.put("kiekis",params[1]);
                data.put("uzsakymai",params[2]);
                data.put("kaina",params[3]);
                data.put("rusis",params[4]);
                data.put("turis",params[5]);
                data.put("ipakavimas",params[6]);


                String result = database.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        NewEntry ru = new NewEntry();
        /*Produktas(String tipas, double kiekis, String uzsakymai, double kaina, String rusis, double turis, String ipakavimas)*/
    String stringKiekis = Double.toString(cl.getKiekis());
    String stringKaina = Double.toString(cl.getKaina());
    String stringTuris =  Double.toString(cl.getTuris());
        ru.execute(cl.getTipas(),stringKiekis,cl.getUzsakymai(),stringKaina,cl.getRusis(),stringTuris,cl.getIpakavimas());
    }

}

