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
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    /**
     * User's input of username.
     */
    private String name;

    /**
     * User's input of password.
     */
    private String password;
    /**
     * User's input of confirm password.
     */
    private String confirmPassword;

    /**
     * To get the User's input and set the value to name.
     */
    private EditText nameInput;
    /**
     * To get the User's input and set the value to password .
     */
    private EditText passwordInput;
    /**
     * To get the User's input and set the value to confirmPassword.
     */
    private EditText confirmInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.UserNameText);
        passwordInput = findViewById(R.id.PasswordText);
        confirmInput = findViewById(R.id.ConfirmPasswordText);
        addSignUpButtonListener();
    }

    /**
     * Activate the Sign Up Button.
     */
    private void addSignUpButtonListener() {
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                password = passwordInput.getText().toString();
                confirmPassword = confirmInput.getText().toString();
                checkSignUpState();
            }
        });
    }

    /**
     * Check is the sign up progress is valid
     */
    private void checkSignUpState() {
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "Password can't be empty!",
                    Toast.LENGTH_SHORT).show();
        } else {
            checkState();
        }
    }

    /**
     * Check if the user name and password are proper
     */
    private void checkState() {
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "User name can't be empty!",
                    Toast.LENGTH_SHORT).show();
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Password doesn't match!",
                    Toast.LENGTH_SHORT).show();
        } else if (!signUp(name, password)) {
            Toast.makeText(getApplicationContext(), "User name exists.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(SignUpActivity.this,
                    ChooseGameActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Sign up method to construct a new user or return false if the user already exist.
     *
     * @param name     the name that sign up
     * @param password that sign up
     * @return the opposite of whether the user already exist,
     */
    @SuppressWarnings("unchecked")
    private boolean signUp(String name, String password) {
        boolean valid;
        UserManager userManager = UserManager.getInstance();
        Map<String, User> userList = getStringUserMap();
        valid = userManager.signUp(name, password, userList);

        // save the new updated userList to file
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput("save_file_temp.ser", MODE_PRIVATE));

            outputStream.writeObject(userManager.getUserList());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return valid;
    }

    /**
     * the help method for signUp which will download the user list from the file
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
