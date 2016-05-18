package bmj.project.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MineLogic
{
    public static int       Rows                = 10;
    public static int       Columns             = 10;
    public static int       mineCount = 15;

    public static int[][]   fieldValues         = new int[Rows][Columns];
    static int[]            minesIndices;

    static Random           rng                 = new Random();

    static ArrayList<int[]> discoveredLocations = new ArrayList<int[]>();

    public static boolean   gameOver            = false;

    public static void generateMines()
    {
        minesIndices = new int[mineCount];
        // All squares generated with coordinates and initial value of 0

        for (int i = 0; i < Rows; i++)
        {
            for (int j = 0; j < Columns; j++)
            {
                fieldValues[i][j] = 0;
            }

        }

        // Make "MineCount" random integers between 0 and 100 and set the
        // board's values of those indices to 9 (mines)

        for (int i = 0; i < mineCount; i++)
        {
            boolean repeat = false;

            do
            {

                repeat = false;
                int temp = rng.nextInt(MineLogic.Rows*MineLogic.Columns);
                minesIndices[i] = temp;

                for (int j = 0; j < i; j++)
                {

                    if (minesIndices[j] == minesIndices[i])
                    {
                        repeat = true;
                    }

                }

            } while (repeat);
        }

        // sets mines on the board
        for (int i = 0; i < mineCount; i++)
        {
            int tempx = minesIndices[i] % MineLogic.Rows;
            int tempy = minesIndices[i] / MineLogic.Columns;

            fieldValues[tempx][tempy] = 9;

        }

        // set the other numbers on the board

        for (int i = 0; i < MineLogic.Rows; i++)
        {
            for (int j = 0; j < MineLogic.Columns; j++)
            {
                if (fieldValues[i][j] != 9)
                {

                    if (i - 1 >= 0 && j - 1 >= 0
                            && fieldValues[i - 1][j - 1] == 9)
                        fieldValues[i][j]++;
                    if (j - 1 >= 0 && fieldValues[i][j - 1] == 9)
                        fieldValues[i][j]++;
                    if (i + 1 < MineLogic.Rows && j - 1 >= 0
                            && fieldValues[i + 1][j - 1] == 9)
                        fieldValues[i][j]++;
                    if (i - 1 >= 0 && fieldValues[i - 1][j] == 9)
                        fieldValues[i][j]++;
                    if (i + 1 < MineLogic.Rows && fieldValues[i + 1][j] == 9)
                        fieldValues[i][j]++;
                    if (i - 1 >= 0 && j + 1 < MineLogic.Columns
                            && fieldValues[i - 1][j + 1] == 9)
                        fieldValues[i][j]++;
                    if (j + 1 < MineLogic.Columns && fieldValues[i][j + 1] == 9)
                        fieldValues[i][j]++;
                    if (i + 1 < MineLogic.Rows && j + 1 < MineLogic.Columns
                            && fieldValues[i + 1][j + 1] == 9)
                        fieldValues[i][j]++;

                }

            }

        }

    }

    public static boolean discovered(int x, int y)
    {
        int[] temp = { x, y };

        for (int[] z : discoveredLocations)
        {
            if (Arrays.equals(temp, z))
            {
                return true;
            }
        }

        return false;
    }

    public static void extendzeros(int x, int y)
    {

        int[] currentLocation = { x, y };
        discoveredLocations.add(currentLocation);

        MineView.mineCells[x][y].getPaint().setColor(0xffFFFFFF);

        if (fieldValues[x][y] == 0)
        {
            // Orthogonal
            if (x + 1 < MineLogic.Rows && !discovered(x + 1, y))
            {
                extendzeros(x + 1, y);
            }
            if (x - 1 >= 0 && !discovered(x - 1, y))
            {
                extendzeros(x - 1, y);
            }
            if (y + 1 < MineLogic.Columns && !discovered(x, y + 1))
            {
                extendzeros(x, y + 1);
            }
            if (y - 1 >= 0 && !discovered(x, y - 1))
            {
                extendzeros(x, y - 1);
            }

            // Diagonal
            if (x + 1 < MineLogic.Rows && y + 1 < MineLogic.Columns && !discovered(x + 1, y + 1))
            {
                extendzeros(x + 1, y + 1);
            }
            if (x + 1 < MineLogic.Rows && y - 1 >= 0 && !discovered(x + 1, y - 1))
            {
                extendzeros(x + 1, y - 1);
            }
            if (x - 1 >= 0 && y + 1 < MineLogic.Columns && !discovered(x - 1, y + 1))
            {
                extendzeros(x - 1, y + 1);
            }
            if (x - 1 >= 0 && y - 1 >= 0 && !discovered(x - 1, y - 1))
            {
                extendzeros(x - 1, y - 1);
            }
        }
    }
}
