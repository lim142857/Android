package fall2018.csc2017.GameCenter.Hanoi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.ChooseGameActivity;
import fall2018.csc2017.GameCenter.R;

@SuppressLint("Registered")
public class StartActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.hanoi_home);

        addHanoiScoreButtonListener();
        addBackButtonListener();
    }

    /**
     * Activate the ChooseLevelActivity
     *
     * @param v Button new game
     */
    public void game(View v) {
        Intent i = new Intent(this, ChooseLevelActivity.class);
        startActivity(i);
    }

    /**
     * Activate the InstructActivity
     *
     * @param v Button instruct
     */
    public void instruct(View v) {
        Intent i = new Intent(this, InstructActivity.class);
        startActivity(i);
    }

    /**
     * Add a button HanoiScore
     */
    public void addHanoiScoreButtonListener() {
        Button hanoiButton = findViewById(R.id.HanoiScore);
        hanoiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,
                        HanoiScore.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Active the back button.
     */
    public void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this,
                        ChooseGameActivity.class);
                startActivity(intent);
            }
        });
    }
}