package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    /**
     * User name that the user inputs.
     */
    private String name;

    /**
     * Password that the user inputs.
     */
    private String password;

    /**
     * To catch the input of name.
     */
    private EditText nameInput;

    /**
     * To catch the input of password.
     */
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        nameInput = findViewById(R.id.UserName);
        passwordInput = findViewById(R.id.PasswordText);

        addRegisterButtonListener();
        addSignInButtonListener();
    }

    /**
     * Activate Register Button
     */
    private void addRegisterButtonListener() {
        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,
                        SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate SignIn Button
     */
    private void addSignInButtonListener() {
        Button signInButton = findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (!signIn(name, password)) {
                    Toast.makeText(getApplicationContext(), "User doesn't exist or password " +
                            "doesn't match", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignInActivity.this,
                            ChooseGameActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Sign in method to find a user from the user file or return false if the user does not exist or the password is wrong.
     *
     * @param name     the name that sign in
     * @param password the password that sign in
     * @return whether the name exist or the password is right,
     */
    @SuppressWarnings("unchecked")
    private boolean signIn(String name, String password) {
        Map<String, User> userList = getStringUserMap();
        UserManager userManager = UserManager.getInstance();

        return userManager.signIn(name, password, userList);
    }

    /**
     * the help method of signIn method that will get the user list from the file which we used to save documents
     *
     * @return the user list that has been saved in the file.
     */
    @SuppressWarnings("unchecked")
    private Map<String, User> getStringUserMap() {
        Map<String, User> userList = new HashMap<>();
        try {
            InputStream inputStream = this.openFileInput("save_file_temp.ser");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userList = (Map<String, User>) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return userList;
    }
}

