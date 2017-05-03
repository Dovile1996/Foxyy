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

public class LoginActivity extends Activity {
    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mygtukas = (Button) findViewById(R.id.prisijungti);
        Button mygtukas3 = (Button) findViewById(R.id.registruotis);
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        mygtukas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUsernameView.setError(null);
                mPasswordView.setError(null);

                String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();

                Toast.makeText(LoginActivity.this,
                        username+"\n"+password ,Toast.LENGTH_SHORT).show();

                boolean cancel = false;
                View focusView = null;
                if (!isValid(username)) {
                    mUsernameView.setError(getString(R.string.error_invalid_username));
                    focusView = mUsernameView;
                    cancel = true;
                }
                if (!isValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
        mygtukas3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(activityChangeIntent);
            }
        });
    }
    private boolean isValid(String credentials){
        final String CREDENTIALS_PATTERN = "^([0-9a-zA-Z]{3,15})+$";
        Pattern pattern = Pattern.compile(CREDENTIALS_PATTERN);

        Matcher matcher = pattern.matcher(credentials);
        return matcher.matches();
    }

}
