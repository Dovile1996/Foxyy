package tk.folrikas.bitinelis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends Activity {

    private static final String REGISTER_URL = "Adresiukas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button mygtukas4 = (Button) findViewById(R.id.register_now);

        final EditText mUsernameView = (EditText) findViewById(R.id.new_user);
        final EditText mPasswordNew= (EditText) findViewById(R.id.new_password);
        final EditText mPasswordRepeat = (EditText) findViewById(R.id.new_password_repeat);
        final EditText mNewEmail = (EditText) findViewById(R.id.new_email);



        mygtukas4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUsernameView.setError(null);
                mPasswordNew.setError(null);
                mPasswordRepeat.setError(null);
                mNewEmail.setError(null);

                String username = mUsernameView.getText().toString();
                String password = mPasswordNew.getText().toString();
                String passwordRepeat = mPasswordRepeat.getText().toString();
                String email = mNewEmail.getText().toString();



                boolean cancel = false;
                View focusView = null;
                if (!isValid(username)) {
                    mUsernameView.setError(getString(R.string.error_invalid_username));
                    focusView = mUsernameView;
                    cancel = true;
                }
            if (!isValid(password)) {
                mPasswordNew.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordNew;
                cancel = true;
            }
            if (!passwordRepeat.equals(password)) {
                mPasswordNew.setError(getString(R.string.error_email_match));
                focusView = mPasswordNew;
                cancel = true;
            }
            if (!isValidEmail(email)) {
                mNewEmail.setError(getString(R.string.error_invalid_email));
                focusView = mNewEmail;
                cancel = true;
            }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    Register REG = new Register(username, password, email);
                        /*Toast.makeText(RegisterActivity.this,

                                "objekte: " +
                                "\nVartotojo vardas: " + REG.getUsername()+
                                "\nSlaptazodis: " + REG.getPassword()+
                                "\npasto adresas: " + REG.getEmail(),
                                Toast.LENGTH_SHORT).show();*/
                    registerUser(REG.getUsername(),REG.getPassword(),REG.getEmail());
                    Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    private boolean isValid(String credentials){
        final String CREDENTIALS_PATTERN = "^([0-9a-zA-Z]{3,15})+$";
        Pattern pattern = Pattern.compile(CREDENTIALS_PATTERN);

        Matcher matcher = pattern.matcher(credentials);
        return matcher.matches();

    }
    private boolean isValidEmail(String credentials){
        final String CREDENTIALS_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+";
        Pattern pattern = Pattern.compile(CREDENTIALS_PATTERN);

        Matcher matcher = pattern.matcher(credentials);
        return matcher.matches();
    }
    private void registerUser(String username, String password, String email) {
        String urlSuffix = "?username="+username+"&password="+password+"&email="+email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Prašome palaukti",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    // byethost naudoja antibot sistema, todel reikia kiekvienam rankutėmis suvesti cookie turinį,
                    // kuris pas kiekviena bus skirtingas. kaip tai padaryti zemiau nuoroda
                    // http://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string
                    con.setRequestProperty("Cookie", "__test=7a4d917e220fbf9a55cab3046bd1a3b7; expires=2038 m. sausio 1 d., penktadienis 01:55:55; path=/");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }

}
