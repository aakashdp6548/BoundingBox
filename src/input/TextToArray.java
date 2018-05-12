package input;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextToArray
{
    public static double[][] readFile(File file, int rows, int columns) throws FileNotFoundException
    {
        Scanner sc = new Scanner(file);
        double numbers[][] = new double[rows][columns];
       
        for (int row = 0; row < rows; row++) {
        	String line = sc.nextLine();
        	Scanner sc2 = new Scanner(line);
        	sc2.useDelimiter(";");
        	for (int col = 0; col < columns; col++) {
        		numbers[row][col]=sc2.nextInt();
        	} sc2.close();
        } sc.close();
        return numbers;
    }
}