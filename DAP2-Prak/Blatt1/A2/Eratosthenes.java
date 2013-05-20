
public class Eratosthenes
{
    public static void main(String[] args)
    {
        boolean[] arr;	//Array, auf welchem gearbeitet wird. Der Index entspricht jeweils der Zahl
        int len = 0;	//Gewünschte Zahl, bis zu der nach Primzahlen gesucht wird
        boolean output = false;		//Gibt an, ob eine Ausgabe der Primzahlen gewünscht wird
        
        //Anfang Befehlsinterpretierung
        
        if(args.length == 0){
            System.out.println("Zu wenige Parameter");
	    System.out.println("Gültige Syntax: java Eratosthenes Anzahl [-o]");
            return;
        }
        
        if(args.length > 0){   //es gibt min. einen Parameter
            
            try{
                len = (int)Integer.parseInt(args[0]);
            }
            catch(NumberFormatException e){
                System.out.println("Bitte eine ganze Zahl eingeben");
                return;
            }
            
            if(len < 0){
                System.out.println("Bitte eine positive Zahl eingeben");
            }
        
        }
        
        if(args.length > 1){    //es gibt min. einen zweiten Parameter
            
            if(args[1].equals("-o")){    //ist zweiter Parameter -o ?
                output = true;
            }
            else{
                System.out.println("Zweiter Parameter unbekannt, wird ignoriert");
            }
            
        }
        
        if(args.length > 2){    //Es gibt weitere Parameter
            System.out.println("Zu viele Parameter");
	    System.out.println("Gültige Syntax: java Eratosthenes Anzahl [-o]");
	    return;
        }
        
        //Ende Befehlsinterpretierung
        
        arr = erato(len); //Berechne Primzahlen
        
        int counter = 0;        //Zähler für gefundene Primzahlen
        String results = new String();  //Ergebnisstring gefundener Primzahlen
        
        for(int i = 2; i < arr.length; i++){ 
            if(arr[i] == false){
                counter++;          //zähle gefundene Primzahlen
                if(output){         
                    results = results + ", " + i;
                }
            }
        }
        
        System.out.println("Gefundene Primzahlen: " + counter);
        
        if(output){
            System.out.println(results.substring(2));
        }
        
    }
    
    /**
     * Berechnet mit Sieb des Eratosthenes
     */
    public static boolean[] erato(int len)
    {
        boolean[] arr = new boolean[len];
        
        for(int i = 2; i < arr.length; i++){    //Feld mit false vorinitialisieren
            arr[i] = false;
        }
        
        for(int i = 2; i < arr.length/2+1; i++){    //Gehe Feld bis zur Hälfte durch
            if(!arr[i]){    //ist i = Primzahl, dann streiche alle Vielfachen von i
                
                int j = 2;
                while(i*j < arr.length){
                    arr[i*j] = true;
                    j++;
                }
                
            }
        }
        
        return arr;
    }
    
    
}
