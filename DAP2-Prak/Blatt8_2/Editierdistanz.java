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
        if(args.length == 0 || args.length > 3)
        {
            System.out.println("Falsche Anzahl an Parametern!");
            syntaxteller();
            return;
        }
        
        if(args.length == 1)
        {
            ArrayList<String> arrlist = dateiEinlesen(args[0]);
            if(arrlist == null){    //Datei nicht gefunden
                return;
            }
            while(arrlist.size() > 1)
            {
                String str1 = arrlist.get(0);
                String str2 = arrlist.get(1);
                arrlist.remove(1);
                arrlist.remove(0);
                System.out.println("String 1: " + str1);
                System.out.println("String 2: " + str2);
                int[][] zwischen = berechneDistanz(str1, str2);
                System.out.println("Editierdistanz: " + zwischen[str1.length()][str2.length()]);
                System.out.println();
            }
        }
        
        if(args.length == 2)
        {
        // Abfangen der MÃ¶glichkeit, dass wir den -o Parameter kriegen
            if(args[1].equals("-o"))
            {
                ArrayList<String> arrlist = dateiEinlesen(args[0]);
                if(arrlist == null){    //Datei nicht gefunden
                    return;
                }
                while(arrlist.size() > 1)
                {
                    String str1 = arrlist.get(0);
                    String str2 = arrlist.get(1);
                    arrlist.remove(1);
                    arrlist.remove(0);
                    int[][] zwischen = berechneDistanz(str1, str2);
                    // "Formatierte" Ausgabe
                    System.out.println("Loesung fuer '" + str1 + "' --> '" + str2 + "' mit Gesamtkosten " + zwischen[str1.length()][str2.length()] + ":");
                    System.out.println("===================================================");
                    ausgabeEditieroperationen(str1, str2, zwischen);
                    System.out.println();
                }
            }
            
            else{
                String str1 = args[0];
                String str2 = args[1];
            
                System.out.println("String 1: " + str1);
                System.out.println("String 2: " + str2);
                int[][] zwischen = berechneDistanz(str1, str2);
                System.out.println("Editierdistanz: " + zwischen[str1.length()][str2.length()]);
                System.out.println();
            }
        }
        
        if(args.length == 3)
        {
            if(args[2].equals("-o"))
            {
                String str1 = args[0];
                String str2 = args[1];
                int[][] zwischen = berechneDistanz(str1, str2);
                System.out.println("Loesung fuer '" + str1 + "' --> '" + str2 + "' mit Gesamtkosten " + zwischen[str1.length()][str2.length()] + ":");
                System.out.println("===================================================");
                ausgabeEditieroperationen(str1, str2, zwischen);
                System.out.println();
                for(int i = 0; i < zwischen.length; i++){
            for(int j = 0; j < zwischen[i].length; j++){
            System.out.print(zwischen[i][j] + ", ");
            }
            System.out.println();
                }
            }
            else{
                System.out.println("Falsche Anzahl an Parametern");
                syntaxteller();
            }
        }
    }
    
    private static int[][] berechneDistanz(String a, String b)
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
        return arr;
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
    
    private static void syntaxteller()
    {
        System.out.println("Syntax: Editierdistanz (Pfad zu Datei) [-o] oder Editierdistanz (Wort 1) (Wort 2) [-o]");
        System.out.println();
    }
    
    private static void ausgabeEditieroperationen(String a, String b, int[][] arr)
    {
        System.out.println(ausgabeEditieroperationen(a.length(), b.length(), a, b, arr, b ));
    }
    
    // Und hier Black Magic
    private static String ausgabeEditieroperationen(int i, int j, String a, String b, int[][] arr, String temp)
    {
        if(i == 0 || j == 0){
            return "";
        } else {
            if(arr[i][j] == (arr[i-1][j]+1)){
                String ret = "\nKosten 1: Lšsche " + a.charAt(i-1)+" an der Position "+j+" --> "+temp;
                temp = temp.substring(0,j)+a.charAt(i-1)+temp.substring(j, temp.length());
                return ausgabeEditieroperationen(i-1, j, a, b, arr, temp) + ret;
            }
            else if(arr[i][j] == (arr[i][j-1]+1)){
                String ret = "\nKosten 1: FŸge " + b.charAt(j-1)+" an der Position "+j+" ein --> "+temp;
                temp = temp.substring(0,j-1) + temp.substring(j, temp.length());
                return ausgabeEditieroperationen(i, j-1, a, b, arr, temp) +ret;
            }
            else{
                if (a.charAt(i-1) == b.charAt(j-1)) {
                    String ret = "\nKosten 0: Ersetze " + a.charAt(i-1) + " durch " + b.charAt(j-1)+" an der Position "+j+" --> "+temp;
                    return ausgabeEditieroperationen(i-1, j-1, a, b, arr, temp)+ret;
                } else {
                    String ret = "\nKosten 1: Ersetze " + a.charAt(i-1) + " durch " + b.charAt(j-1)+" an der Position "+j+" --> "+temp;
                    temp = temp.substring(0,j-1) + a.charAt(j-1) + temp.substring(j, temp.length());
                    return ausgabeEditieroperationen(i-1, j-1, a, b, arr, temp)+ret;
                }
            }
        }
    }
}