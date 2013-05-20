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
        boolean lateness;
        String pfad;

        //Beginn Parameterüberprüfung
        if(args.length < 1 || args.length > 2){
            System.out.println("Falsche Anzahl Parameter");
            syntaxteller();
            return;
        }
        else if(args.length == 1)
        {
            lateness = false;
            pfad = args[0];
        }
        else{
            if(args[0].equals("Lateness")){
                lateness = true;
            }
            else if(args[0].equals("Interval")){
                lateness = false;
            }
            else{
                System.out.println("Falsche Eingabe bei Methode");
                syntaxteller();
                return;
            }
            pfad = args[1];
        }
        //Ende Parameterüberprüfung

        //Die Datei Einlesen, falls die Rückgabe null ist ist ein Fehler aufgetreten
        
        Interval[] arr = dateiEinlesen(pfad);
        if(arr == null) return;
        
        //Anfang Ausgabe
        System.out.println();
        System.out.println("Bearbeite Datei: " + pfad + ".");
        System.out.println();
        System.out.println("Es wurden " + arr.length + " Zeilen mit folgenden Inhalten gelesen:");

        //Die eingelesenen Werte unsortiert ausgeben
        ausgabe(arr);

        //Sortieren und Werte sortiert ausgeben
        mergeSort(arr);    
        System.out.println("Sortiert: ");
        ausgabe(arr);

        //Ausgabe abhängig von Lateness oder Intervallscheduling
        if(!lateness)
        {
            System.out.println("Berechnetes Intervallscheduling");
            ArrayList<Interval> x = intervalScheduling(arr);
            arr = new Interval[x.size()];
            arr = x.toArray(arr);
            ausgabe(arr);
            
        }
        else{
            System.out.println("Berechnetes Latenessscheduling");
            Job[] jobarr = convertArray(arr);
            int[] intarr = latenessScheduling(jobarr);
            ausgabe(intarr);
            System.out.println();
            System.out.println("Maximale Verzögerung: " + maxDelay(intarr, jobarr));
        }
        System.out.println("----------------------------------------------------------");
    }

    /**
     * Gibt das übergebene Array formatiert aus
     */
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
    
    /**
     * Ausgabe-Methode für den Lateness-Algo
     */
    private static void ausgabe(int[] arr)
    {
        String a = "[";

        for(int i = 0; i < arr.length; i++)
        {
            a = a + arr[i] + ", ";
        }

        a = a.substring(0, a.length()-2);
        a = a + "]";

        System.out.println(a);
        System.out.println();
    }
    
    

    /**
     * Liest die Datei im übergebenen Pfad ein und gibt ein Array mit den gefundenen Intervallen aus
     */
    private static Interval[] dateiEinlesen(String pfad)
    {
        RandomAccessFile file;

        //Versuche die Datei einzulesen, es können Fehler auftreten die abgefangen werden
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
        //Die Schleife geht die eingelesene Datei Zeile für Zeile durch und führt 
        while(!endOfFileReached)
        {
            StringTokenizer st = null;

            //Versuche die nächste Zeile einzulesen
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

            //solange noch Tokens vorhanden sind lese das nächste Inverall und füge es zur Liste hinzu
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
    
    /**
     * Berechnet die maximale Verzögerung des übergebenen Jobarrays mit dem übergebenen Schedule
     */
    private static int maxDelay(int[] schedule, Job[] jobarr)
    {
        int ausgabe = 0;
        for(int i = 0; i < schedule.length; i++)
        {
            int a = schedule[i] +jobarr[i].getDuration() - jobarr[i].getDeadline();
            if(a > ausgabe) ausgabe = a;
        }
        return ausgabe;
    }

    /**
     * Konvertiert ein Interval-Array zu einem Job-Array
     */
    private static Job[] convertArray(Interval[] arr)
    {
        Job[] ausgabe = new Job[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            ausgabe[i] = arr[i].toJob();
        }
        return ausgabe;
    }
    
    /**
     * Lateness-Scheduling-Algorithmus aus der Vorlesung
     */
    private static int[] latenessScheduling(Job[] arr)
    {
        int n = arr.length;
        int[] ausgabe = new int[n];
        int z = 0;
        for(int i = 0; i < n; i++)
        {
            ausgabe[i] = z;
            z = z + arr[i].getDuration();
        }
        return ausgabe;
    }
    
    /**
     * Wendet Intervalscheduling auf das übergebene Array aus
     */
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

    /**
     * Hilfsmethode zur Ausgabe des korrekten Syntax
     */
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: Anwendung [Interval|Lateness] (Pfad zur Datei)");
        System.out.println();
    }

    //-----------------------------------------------------------------------------------------------------------
    //Ab hier: Mergesort
    //-----------------------------------------------------------------------------------------------------------
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
