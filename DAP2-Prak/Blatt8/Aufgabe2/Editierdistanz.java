package Aufgabe2;

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
            while(arrlist.size() > 1)
            {
                String str1 = arrlist.get(0);
                String str2 = arrlist.get(1);
                arrlist.remove(1);
                arrlist.remove(0);
                System.out.println("String 1: " + str1);
                System.out.println("String 2: " + str2);
                System.out.println("Editierdistanz: " + berechneDistanz(str1, str2)[str1.length()][str2.length()]);
                System.out.println();
            }
        }
        
        if(args.length == 2)
        {
            if(args[1].equals("-o"))
            {
                ArrayList<String> arrlist = dateiEinlesen(args[0]);
                while(arrlist.size() > 1)
                {
                    String str1 = arrlist.get(0);
                    String str2 = arrlist.get(1);
                    arrlist.remove(1);
                    arrlist.remove(0);
                    int[][] zwischen = berechneDistanz(str1, str2);
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
                System.out.println("Editierdistanz: " + berechneDistanz(str1, str2));
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
                ausgabeEditieroperationen(str1.length(), str2.length(), zwischen);
                System.out.println();
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
        ausgabeEditieroperationen(a.length(), b.length(), a, b, arr);
    }
    
    private static void ausgabeEditieroperationen(int i, int j, String a, String b, int[][] arr)
    {
        if(i == 0 && j == 0){
            return;
        }
        if(i != 0 && arr[i][j] == (arr[i-1][j]+1)){
            System.out.println("lösche " + a.charAt(i));
            ausgabeEditieroperationen(i-1, j, a, b, arr);
        }
        else if(j != 0 && arr[i][j] == (arr[i][j-1]+1)){
            System.out.println("füge ein " + b.charAt(j));
            ausgabeEditieroperationen(i, j-1, a, b, arr);
        }
        }
    }
}