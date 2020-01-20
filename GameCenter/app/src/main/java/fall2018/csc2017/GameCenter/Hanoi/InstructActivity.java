package fall2018.csc2017.GameCenter.Hanoi;

/*
Adapted from:
https://github.com/ashokgujju/Towers-of-Hanoi/blob/master/src/com/as/tohanoi/Instruct.java
*/

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import fall2018.csc2017.GameCenter.R;

@SuppressLint("Registered")
public class InstructActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_hanoi_instruction);
    }
}