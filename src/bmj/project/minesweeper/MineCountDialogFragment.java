package bmj.project.minesweeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MineCountDialogFragment extends DialogFragment {

    private final int[] choices = { 5, 10, 15, 20, 25 };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Number of Mines")
                .setSingleChoiceItems(R.array.Mine_Count_Choices, 2,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which)
                            {
                                MineLogic.mineCount = choices[which];
                            }
                        })
                .setPositiveButton("Start!",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Main.myMineView.resetBoard();
                            }
                        });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}