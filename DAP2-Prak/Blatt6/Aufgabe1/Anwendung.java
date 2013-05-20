import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.SecurityException;

public class Anwendung
{
    public static void main(String[] args)
    {
        if(args.length != 1){
            System.out.println("Falsche Anzahl Parameter");
            syntaxteller();
            return;
        }
        
        Interval[] arr = dateiEinlesen(args[0]);
        if(arr == null) return;
        
        System.out.println();
        System.out.println("Bearbeite Datei: " + args[0] + ".");
        System.out.println();
        System.out.println("Es wurden " + arr.length + " Intervalle mit folgenden Inhalten gelesen:");
        
        ausgabe(arr);
        
        mergeSort(arr);    
        System.out.println("Sortiert: ");
        ausgabe(arr);
        
        System.out.println("Berechnetes Intervallscheduling");
        
        ArrayList<Interval> x = intervalScheduling(arr);
        arr = new Interval[x.size()];
        arr = x.toArray(arr);
        ausgabe(arr);
        System.out.println("----------------------------------------------------------");
        
    }
    
    private static void ausgabe(Interval[] arr)
    {        
        String a = "[";
        
        for(int i = 0; i < arr.length; i++)
        {
            a = a + "[" + arr[i].getStart() + ", " + arr[i].getEnd() + "], ";
        }
        
        a = a.substring(0, a.length()-2);
        a = a + "]";
        
        System.out.println(a);
        System.out.println();
        
        
    }
    
    private static Interval[] dateiEinlesen(String pfad)
    {
        RandomAccessFile file;
        
        try{
            file = new RandomAccessFile(pfad, "r");
        }
        catch(FileNotFoundException e){
            System.out.println("Keine Datei gefunden, Pfad korrigieren");
            return null;
        }
        catch(SecurityException e){
            System.out.println("Keine Berechtigung für Zugriff auf Datei");
            return null;
        }
        
        
        ArrayList<Interval> arrlist = new ArrayList<Interval>();
        
        boolean endOfFileReached = false;
        
        while(!endOfFileReached)
        {
            StringTokenizer st = null;
            
            try{
                 st = new StringTokenizer(file.readLine(), ",");
            }
            catch(IOException e)
            {
                //wenn file.readLine() eine IOException wirft ist etwas schief gelaufen
                System.out.println("Ein-/Ausgabeausnahme beim Einlesen der Datei");
                return null;
            }
            catch(NullPointerException e)
            {
                //wenn readLine() eine NullPointerException wirft ist das Ende der Datei erreicht
                endOfFileReached = true;
                continue; //Wenn die Datei zuende ist Rest der Schleife überspringen
            }

            
            while(st.hasMoreTokens())
            {
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                Interval ivall = new Interval(start, end);
                arrlist.add(ivall);
            }
        }
        
        if(arrlist.isEmpty()) System.out.println("Die Eingabedatei war leer oder etwas blödes ist passiert");
        
        Interval[] ausgabe = new Interval[arrlist.size()];
        ausgabe = arrlist.toArray(ausgabe);
        return ausgabe;

        
    }
    
    private static ArrayList<Interval> intervalScheduling(Interval[] array)
    {
        int n = array.length;
        ArrayList<Interval> arrlist = new ArrayList<Interval>();
        arrlist.add(array[0]);
        int j = 0;
        for(int i = 1; i < n; i++)
        {
            if(array[i].getStart() >= array[j].getEnd()){
                arrlist.add(array[i]);
                j = i;
            }
        }
        
        return arrlist;
    }
    
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: Anwendung (Pfad zur Datei)");
        System.out.println();
    }
    
    private static void mergeSort(Interval[] array){
        mergeSort(array,0, array.length-1);
    }
    
    private static void mergeSort(Interval[] arr, int p, int r) {
        if(p < r){
            int q = (p+r)/2;
            mergeSort(arr, p, q);
            mergeSort(arr,q+1,r);
            merge(arr,p,q,r);
        }
    }

    private static void merge(Interval[] intArr, int p, int q, int r){
        // Die Länge des Hilfsarrays bestimmen und selbiges anlegen
        // Die da wir nur von p bis r mergen müssen, ist die Länge des hilfsarrays r-p+1
        int harrLength = r-p+1;
        Interval[] harr = new Interval[harrLength];

        // Hilfsarray füllen, anfangend bei der Anfangsposition p
        for (int i = 0; i< harrLength; i++) {
            harr[i] = intArr[p+i];
        }

        // Laufvariablen bestimmen
        int i=0;
        int k=p;
        // Bestimmung der rechten hälfte des Arrays
        int j=q-p+1;

        // Den jeweils kleinsten Wert von links oder rechts ins zu sortierende Array
        while(i<= q-p && j < harrLength) {
            if(harr[i].getEnd()<harr[j].getEnd())
                intArr[k++] = harr[i++];
            else
                intArr[k++] = harr[j++];
        }

        // Falls der Algorithmus dem rechten Teil fertig ist, aber nicht mit dem Linken
        // Die rechte hälfte ist schon sortiert und entfällt somit
        while (i<= q-p) {
            intArr[k++] = harr[i++];
        }
    }
}
