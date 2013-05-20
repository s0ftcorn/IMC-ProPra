
/**
 * 
 * @author Christian Römer, Eugen Sacharow, Philip Becker
 */
public class KAufgabe3
{
    public static void main(String[] args){
	
	// Abfangen von falscher parametrisierung
	if(args.length == 0){
		System.out.println("Zu wenige Argumente!");
		Syntaxteller();
		return;
	}

	if(args.length > 1){
		System.out.println("Zu viele Argumente!");
		Syntaxteller();
		return;
	}
	// Anfang der Messung des gesamten Prozesses
        long t_start = System.currentTimeMillis();
        float des_time;
        // Parsen der Eingabe 
        try{
            des_time = Float.parseFloat(args[0]) * 1000; // Umrechnung auf millisekunden
            if(des_time <= 0){
                System.out.println("Bitte nur reelle Zahlen größer Null eingeben!");
                return;
            }
        }catch(NumberFormatException e){
            System.out.println("Bitte nur Fließkommazahlen eingeben");
            return;
        }
        System.out.println("Hang on, this could take a while!");
        int init_time = 0;
	// Startwert 500, damit beim ersten Verdoppeln bei 1000 angefangen wird
        int values = 500;
	// Verdopple solange die Länge des Arrays bis mehr Zeit als gewünscht benötigt wird
        while(init_time < (int)des_time){
            values *=2;
            System.gc();
            init_time = (int)measure_smooth(values,5);
        }
        
        System.out.println("Beginne mit binärer Suche bei " + (double)init_time/1000 + "s und " + values + " Werten.");
        // Beginne binäre Suche mit der zu langen Länge und der hälfte, da der
	// gesuchte Wert garantiert dazwischen liegt
        int[] temparr = recBin(values/2,values,(int)des_time);
        System.out.println("Werte: " + temparr[0] + " Zeit: " + (double)temparr[1]/1000 + "s");
        long t_end = System.currentTimeMillis();
        double t_delta = ((double)t_end-(double)t_start)/1000/60; // In Minuten umrechnen
        System.out.println("Das ganze Prozedere hat " + t_delta + "min gedauert.");
    }

    public static boolean isSorted(int[] arr){
        for(int i = 1; i < arr.length; i++){
            if(arr[i-1] > arr[i]) return false;
        }
        return true;
    }

    public static void bubbleSort(int[] arr){
        for(int i = 0; i < arr.length; i++){
        //Invariante: Das Feld arr ist von 0 bis i aufsteigend sortiert
            for(int j = arr.length-1; j > i; j--){
                if(arr[j-1] > arr[j]){
                    int tmp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = tmp;
                }
            }
        }
    }
    // Füllt ein Array absteigend
    public static int[] initiateArray(int length){
        int[] arr = new int[length];
        for(int i = 0; i < length; i++){
            arr[length-i-1] = i;
        }
        return arr;
    }

    // Methode zum Messen einer Sortierung in Abhängigkeit der Eingabegröße
    public static long measure(int length){
        int[] temparr = initiateArray(length);
        long start, ende;
        start = System.currentTimeMillis();
        // System.out.println("Beginne Zeitmessung");
        bubbleSort(temparr);
        ende = System.currentTimeMillis();
        if(isSorted(temparr)){
            return ende - start;
        }else{
            System.out.println("Beim Sortieren ging etwas schief!");
            return -1;
        }
    }
    
    // "Glätten" der Messungen
    public static long measure_smooth(int length, int anz){
        int tmp = 0;
        
        for(int i = 0; i < anz; i++){
	    System.gc();
            tmp += (int)measure(length);
        }
        
        return tmp/anz;
    }
    
    public static int[] recBin(int uS,int oS,long t){
	// Bestimmung der Mitte
        int m = (uS+oS)/2;
        System.out.print("Beginne Aufruf mit unterer Schranke: " + uS + " und mit oberer Schranke ");
        System.out.print(oS);
        System.out.println();
        
        int t_m = (int)measure_smooth(m,5);
        System.out.println("Beim aktuellen Aufruf brauchen wir: " + (double)t_m/1000 + "s bei einer Länge von " + m);
        if(t_m == -1){
		int[] foo = {m, -1};
		return foo;
        }
        
        if(uS+1 == oS || uS == oS+1){
		int[] foo = {m, -1};
		return foo;
        }
        // Die aktuelle Messung sollte auch nach Abzug der Toleranzgrenze immernoch größer sein
        if(t_m-100 > t){
            return recBin(uS,m,t);
        }int[] messungen = new int[anz];
        
        for(int i = 0; i < anz; i++){
            System.gc();
            messungen[i] = (int)measure(length);
        }
        
        // Gleiches für kleiner
        if(t_m+100 < t){
            return recBin(m,oS,t);
        }
        // Rückgabe als Array, da uns zwei Werte interessieren
        int[] arr = {m, (int)t_m};
        return arr;
    }
    
    public static void Syntaxteller(){
	System.out.println("Korrekter Aufruf: java KAufgabe3 [gewünsche Dauer in Sekunden]");
    }
}
