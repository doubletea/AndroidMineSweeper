package bmj.project.minesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity
{
    // These variables implement the application function of the game
    LinearLayout            base;
    GridView                mineGrid;
    Button                  resetButton;
    CheckBox                flagBox;
    
    public static TextView  counter;
    public static MineView  myMineView;

    MineCountDialogFragment resetWindow;

    @Override
    // Equivalent of Main Method
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base = (LinearLayout) findViewById(R.id.container);
        counter = (TextView) findViewById(R.id.textView1);

        resetWindow = new MineCountDialogFragment();

        myMineView = new MineView(this);
        myMineView.setOnTouchListener(myMineView);
        base.addView(myMineView);
        
        resetGame(null);   
    }

    public void resetGame(View v)
    {
        resetWindow.show(getFragmentManager(), "tag");
    }

    public void flagged(View v)
    {
        MineView.flagChecked = !MineView.flagChecked;
        return;
    }
}
