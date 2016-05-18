package bmj.project.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MineView extends View implements View.OnTouchListener
{

    public static ShapeDrawable[][] mineCells;

    private final int               leftBorder   = 35;
    private final int               topBorder    = 20;
    private final int               cellWidth    = 40;
    private final int               cellHeight   = 40;

    private Paint                   paint;
    private Context                 main;

    public static boolean[][]       fieldFlagged = new boolean[MineLogic.Rows][MineLogic.Columns];
    public static boolean           flagChecked  = false;
    public static int               flagCount;

    public MineView(Context context)
    {
        super(context);
        main = context;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        mineCells = new ShapeDrawable[MineLogic.Rows][MineLogic.Columns];

        resetBoard();
    }

    protected void onDraw(Canvas canvas)
    {

        for (int i = 0; i < MineLogic.Rows; i++)
        {
            for (int j = 0; j < MineLogic.Columns; j++)
            {
                mineCells[i][j].draw(canvas);

                if (fieldFlagged[i][j])
                {
                    paint.setColor(Color.BLACK);
                    canvas.drawText("F", 3 * leftBorder / 2 + i * cellWidth, 5
                            * topBorder / 2 + j * cellHeight, paint);
                }
                else if (MineLogic.discovered(i, j))
                {
                    switch (MineLogic.fieldValues[i][j])
                    {
                        case 0:
                            paint.setColor(Color.LTGRAY);
                            break;
                        case 1:
                            paint.setColor(Color.BLUE);
                            break;
                        case 2:
                            paint.setColor(Color.GREEN);
                            break;
                        case 3:
                            paint.setColor(Color.RED);
                            break;
                        default:
                            paint.setColor(Color.BLACK);
                            break;
                    }

                    canvas.drawText("" + MineLogic.fieldValues[i][j], 3
                            * leftBorder / 2 + i * cellWidth, 5 * topBorder / 2
                            + j * cellHeight, paint);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (MineLogic.gameOver || event.getX() < leftBorder
                || event.getX() > leftBorder + cellWidth * MineLogic.Columns
                || event.getY() < topBorder
                || event.getY() > topBorder + cellHeight * MineLogic.Rows) return false;

        int x = ((int) event.getX() - leftBorder) / cellWidth;
        int y = ((int) event.getY() - topBorder) / cellHeight;
        int[] temp = { x, y };

        if (flagChecked)
        {
            fieldFlagged[x][y] = !fieldFlagged[x][y];
            if (fieldFlagged[x][y]){
               flagCount++;
            } else {
                flagCount--;
            }
            
            Main.counter.setText("Left: " + (MineLogic.mineCount - flagCount));
        }
        else if (!fieldFlagged[x][y])
        {
            mineCells[x][y].getPaint().setColor(0xffFFFFFF); // White

            if (MineLogic.fieldValues[x][y] == 9)
            {
                Toast.makeText(main, "GAME OVER", Toast.LENGTH_LONG).show();
                MineLogic.gameOver = true;

            }
            else if (MineLogic.fieldValues[x][y] == 0
                    && !MineLogic.discovered(x, y))
            {
                MineLogic.extendzeros(x, y);
            }

            if (!MineLogic.discovered(x, y))
            {
                MineLogic.discoveredLocations.add(temp);
            }
        }

        //Check Win Condition
        if (MineLogic.discoveredLocations.size() >= MineLogic.Rows
                * MineLogic.Columns - MineLogic.mineCount)
        {
            Toast.makeText(main, "You Win!", Toast.LENGTH_LONG).show();
            MineLogic.gameOver = true;
        }

        invalidate();

        // Toast.makeText(main, "X:" + event.getX() + " Y:" + event.getY(),
        // Toast.LENGTH_SHORT).show();

        return false;
    }

    public void resetBoard()
    {
        for (int i = 0; i < MineLogic.Rows; i++)
        {
            for (int j = 0; j < MineLogic.Columns; j++)
            {
                mineCells[i][j] = new ShapeDrawable(new OvalShape());
                mineCells[i][j].getPaint().setColor(0xff74AC23);
                mineCells[i][j].setBounds(leftBorder + i * cellWidth, topBorder
                        + j * cellHeight, leftBorder + i * cellWidth
                        + cellWidth, topBorder + j * cellHeight + cellHeight);
            }
        }
        
        flagCount = 0;
        fieldFlagged = new boolean[MineLogic.Rows][MineLogic.Columns];
        
        Main.counter.setText("Left: " + MineLogic.mineCount);
        
        MineLogic.generateMines();
        MineLogic.discoveredLocations.clear();
        MineLogic.gameOver = false;
        
        invalidate();
    }

}
