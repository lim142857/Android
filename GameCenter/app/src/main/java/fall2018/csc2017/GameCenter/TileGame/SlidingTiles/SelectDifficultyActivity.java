package fall2018.csc2017.GameCenter.TileGame.SlidingTiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.ChooseGameActivity;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.TileGame.StartingActivity;
import fall2018.csc2017.GameCenter.UserManager;

public class SelectDifficultyActivity extends AppCompatActivity {

    /**
     * The instance of UserManager class
     */
    UserManager userManager = UserManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);

        addSizeThreeButtonListener();
        addSizeFourButtonListener();
        addSizeFiveButtonListener();
        addBackButtonListener();
    }

    /**
     * Activate the Size Three Button.
     */
    private void addSizeThreeButtonListener() {
        Button sizeThreeButton = findViewById(R.id.SizeThreeButton);
        sizeThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.setCurrentGameType(0);
                Intent intent = new Intent(SelectDifficultyActivity.this,
                        StartingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Size Four Button.
     */
    private void addSizeFourButtonListener() {
        Button sizeFourButton = findViewById(R.id.SizeFourButton);
        sizeFourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.setCurrentGameType(1);
                Intent intent = new Intent(SelectDifficultyActivity.this,
                        StartingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Size Five Button.
     */
    private void addSizeFiveButtonListener() {
        Button sizeFiveButton = findViewById(R.id.SizeFiveButton);
        sizeFiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.setCurrentGameType(2);
                Intent intent = new Intent(SelectDifficultyActivity.this,
                        StartingActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the Back Button.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDifficultyActivity.this,
                        ChooseGameActivity.class);
                startActivity(intent);
            }
        });
    }

}
