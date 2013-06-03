import java.lang.Math;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.io.IOException;


public class Editierdistanz
{
    public static void main(String[] args)
    {
        if(args.length == 0 || args.length > 2)
        {
            System.out.println("Falsche Anzahl an Parametern!");
            System.out.println("Syntax: Editierdistanz (Pfad zu Datei) oder Editierdistanz (Wort 1) (Wort 2)");
            return;
        }
        
        if(args.length == 1)
        {
            ArrayList<String> arrlist = dateiEinlesen(args[0]);
            while(arrlist.size > 1)
            {
                String str1 = arrlist.get(0);
                String str2 = arrlist.get(1);
                arrlist.remove(1);
                arrlist.remove(0);
                System.out.println("String 1: " + str1);
                System.out.println("String 2: " + str2);
                System.out.println("Editierdistanz: " + berechneDistanz(str1, str2));
            }
        }
    }
    
    private static int berechneDistanz(String a, String b)
    {
        int laengeA = a.length();
        int laengeB = b.length();
        int[][] arr = new int[laengeA+1][laengeB+1];
        arr[0][0] = 0;
        for(int i = 1; i <= laengeA; i++){
            arr[i][0] = i;
        }
        for(int j = 1; j <= laengeB; j++){
            arr[0][j] = j;
        }
        for(int i = 1; i <= laengeA; i++){
            for(int j = 1; j <= laengeB; j++){
                int stringsGleich = ((a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1);
                int zwischen = Math.min(arr[i-1][j]+1,
                                        arr[i][j-1]+1);
                arr[i][j] = Math.min(zwischen, arr[i-1][j-1]+stringsGleich);
            }
        }
        return arr[laengeA][laengeB];
    }
    
    private static ArrayList<String> dateiEinlesen(String pfad)
    {
        RandomAccessFile file;
        try{
            file = new RandomAccessFile(pfad, "r");
        }
        catch(FileNotFoundException e){
            System.out.println("Fehler beim Einlesen der Datei: Datei nicht gefunden, Pfad korrigieren");
            return null;
        }
        catch(SecurityException e){
            System.out.println("Fehler beim Einlesen der Datei: Keine Berechtigung");
            return null;
        }
        
        ArrayList<String> arrlist = new ArrayList<String>();
        
        boolean endOfFileReached = false;
        while(!endOfFileReached){
            String z;
            try{
                z = file.readLine();
            }
            catch(IOException e){
                System.out.println("Fehler beim Einlesen der Datei");
                System.out.println(e);
                return null;
            }
            if(z != null)
                arrlist.add(z);
            else
                endOfFileReached = true;
        }
        
        return arrlist;
    }
}