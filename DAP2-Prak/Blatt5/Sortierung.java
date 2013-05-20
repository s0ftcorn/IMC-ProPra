public class Sortierung{

    public static void main(String[] args){

        // Bei zu wenig Argumenten die korrekte Syntax und die zur Verfügung stehenden Optionen ausgeben
        if(args.length == 0){
            System.out.println("Zu wenig Argumente!");
            syntaxteller();
            return;
        }

        // Selbiges bei zu vielen Argumenten
        if(args.length > 3){
            System.out.println("Zu viele Argumente!");
            syntaxteller();
            return;
        }

        // Initialisierung von Hilfsvariablen
        int arrLen;
        int[] array = {1};
        long msecs = 0;

        // Parameter parsen
        try{
            arrLen = (int)Integer.parseInt(args[0]);
            if(arrLen <= 0){
                int temp = (int)Integer.parseInt("Das hier ist kein Integer :)");
            }
        }catch(NumberFormatException e){
            System.out.println("Bitte nur natürliche Zahlen größer Null eingeben!");
            syntaxteller();
            return;
        }

        if(args.length == 1){ // Arbeiten mit quicksort
            System.out.println("Sortiere mit QuickSort");
            array = initiateArr(arrLen, "rand");
            long tStart = 0;
            long tEnd = 0;

            // Beginn der Messung
            tStart = System.currentTimeMillis();
            quickSort(array);

            // Ende der Messung
            tEnd = System.currentTimeMillis();
            msecs = tEnd - tStart;
        }

        if(args.length == 2){ 
            array = initiateArr(arrLen, "rand");
            long tStart = 0;
            long tEnd = 0;

            if(args[1].equals("insert") ){
                tStart = System.currentTimeMillis();
                insertionSort(array);
            }else if(args[1].equals("merge")){
                tStart = System.currentTimeMillis();
                mergeSort(array);
            }else if(args[1].equals("quick")){
		tStart = System.currentTimeMillis();
		quickSort(array);
            }else{
                System.out.println("Fehlerhafter Algorithmusname!");
                syntaxteller();
                return;
            }

            // Ende der Messung
            tEnd = System.currentTimeMillis();
            msecs = tEnd - tStart;
        }

        if(args.length == 3){
            array = initiateArr(arrLen, args[2]);

            long tStart = 0;
            long tEnd = 0;

            if(args[1].equals("insert") ){
                tStart = System.currentTimeMillis();
                insertionSort(array);
            }else if(args[1].equals("merge")){
                tStart = System.currentTimeMillis();
                mergeSort(array);
            }else if(args[1].equals("quick")){
		tStart = System.currentTimeMillis();
		quickSort(array);
            }else{
                System.out.println("Fehlerhafter Algorithmusname!");
                syntaxteller();
                return;
            }

            // Ende der Messung
            tEnd = System.currentTimeMillis();
            msecs = tEnd - tStart;
        }

        if(isSorted(array)){
            System.out.println("Feld ist sortiert!");
        }else{
            System.out.println("Feld ist NICHT sortiert!");
        }

        if(array != null){
            if(arrLen <= 100){
                String ausgabe = "";

                for(int i = 0; i < array.length; i++){
                    ausgabe += ", " + array[i];
                }
                System.out.println("Werte des Arrays: " + ausgabe.substring(2));
            }
        }else{
            syntaxteller();
            return;
        }
        System.out.println("Gemesse Zeit: " + msecs + "ms");

    }
    
     /**
     * Hilfsmethode für quickSort
     * @param array Das zu sortierende Feld
     */
    public static void quickSort(int[] array){
	if(array == null || array.length == 0)
	{
	
	}else{
        quickSort(array,0, array.length-1);
        }
    }

    public static void quickSort(int[] A, int l, int r) {
        if(l < r){
            int pivot = A[(l+r)/2];
            int i = l;
            int j = r;
            int tmp;
            
            while (i<=j) {
                while (A[i] < pivot)
                    i++;
                while (A[j] > pivot)
                    j--;
                if (i<=j) {
                    tmp = A[i];
                    A[i] = A[j];
                    A[j] = tmp;
                    i++;
                    j--;
                }
            }
            quickSort(A, l, j);
            quickSort(A, i, r);
        }
    }
    
    // Fehler in den Folien mit ">" anstatt von ">=", bei ">" 'vergisst' insertionSort bei aufsteigender
    // Sortierung das erste Element im Array einzusortieren
    public static void insertionSort(int[] array){
        int key, j;
        for(int i = 1; i < array.length;i++){ // Algorithmus terminiert bei i > länge des Arrays, woraus mithilfe der invariante folgt, dass
            key = array[i];                   // der linke (bei terminierung das ganze array) sortiert ist.
            j = i-1;
            while(j >= 0 && array[j] > key) // Init.: j=2 -> Einelementige linke Hälfte ist immer sortiert
            {
                array[j+1] = array[j];
                j = j-1;
            }
            array[j+1] = key;// Invariante: A[0...i-1] ist sortiert, sprich der linke Teil des Arrays ist immer sortiert
            assert isSorted(array,0,i):"Assert Fehler insertSort bei " + i + ": nicht sortiert";
        }
        assert isSorted(array):"Assert Fehler insertSort ende: nicht sortiert";
    }

    /**
     * Hilfsmethode für mergeSort
     * @param array Das zu sortierende Feld
     */
    public static void mergeSort(int[] array){
        mergeSort(array,0, array.length-1);
    }

    /**
     * Sortiert das übergebene Feld nach dem Mergesort-Verfahren
     * @param arr   Das (Teil-)Feld, welches sortiert werden soll
     * @param p     Index der linken Grenze
     * @param r     Index der rechten Grenze
     */
    public static void mergeSort(int[] arr, int p, int r) {
        // Invariante: 
        if(p < r){
            int q = (p+r)/2;
            mergeSort(arr, p, q);
            mergeSort(arr,q+1,r);
            merge(arr,p,q,r);
        }
        assert isSorted(arr):"Assert Fehler mergeSort Intervall: " + p + " bis " + r + ": nicht sortiert";
    }

    /**
     * Sortiert 2 Teilfelder zu einem kombinierten, sortierten Feld
     * @param intArr    Feld, auf dem gearbeitet wird
     * @param p         Index der linken Grenze
     * @param q         Index der Mitte (rechte Grenze vom linken Teilfeld)
     * @param r         Index der rechten Grenze
     */
    private static void merge(int[] intArr, int p, int q, int r){
        // Die Länge des Hilfsarrays bestimmen und selbiges anlegen
        // Die da wir nur von p bis r mergen müssen, ist die Länge des hilfsarrays r-p+1
        int harrLength = r-p+1;
        int[] harr = new int[harrLength];

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
            // Invariante: Das Array ist von p bis k aufsteigend sortiert
            if(harr[i]<harr[j])
                intArr[k++] = harr[i++];
            else
                intArr[k++] = harr[j++];
        }
        
        
        
        // Falls der Algorithmus dem rechten Teil fertig ist, aber nicht mit dem Linken
        // Die rechte hälfte ist schon sortiert und entfällt somit
        while (i<= q-p) {
            // Invariante: Das Array ist von p bis k aufsteigend sortiert
            intArr[k++] = harr[i++];
        }

        assert isSorted(intArr,p,r):"Assert Fehler merge Intervall: " + p + " bis " + r +": nicht sortiert";
    }

    // Hilfsmethode zum Füllen eines Arrays mit der Länge len und einer bestimmten Füllmethode
    public static int[] initiateArr(int len, String method){
        int[] arr = new int[len];
        if(method.equals("rand")){
            java.util.Random zufallsgenerator = new java.util.Random();
            for(int i = 0; i < len; i++){
                arr[i] = zufallsgenerator.nextInt(1000);
            }
        }else if(method.equals("auf")){
            for(int i = 0; i < len; i++){
                arr[i] = i; 
            }
        }else if(method.equals("ab")){
            for(int i = 0; i < len; i++){
                arr[i] = len-i-1;
            }
        }else{
            System.out.println("Fehler beim initialisieren des Arrays!");
            return null;
        }

        return arr;
    }

    public static boolean isSorted(int[] arr){
        if(arr != null){
            return isSorted(arr, 0, arr.length-1);
        }else{
            return false;
        }
    }

    // Selbe funktion wie bei isSorted, aber wir überprüfen das Array nur in den angegebenen Grenzen
    public static boolean isSorted(int[] arr, int anfang, int ende){
        for(int i = anfang+1; i <= ende; i++){
            // Invariante: Das Feld ist von arr[0] bis arr[i-1] sortiert
            if(arr[i-1] > arr[i]) return false;
        }
        // Das Feld ist innerhalb der gegebenen Grenzen sortiert
        return true;
    }

    public static void syntaxteller(){
        System.out.println("Korrekter aufruf: java Sortierung länge [algorithmus] [füllart]");
        System.out.println("Füllart optional; Füllarten: auf, ab und rand");
        System.out.println("Defaultfüllart: rand");
        System.out.println("Algorithmus optional; Algorithmen 'merge' für mergeSort und 'insert' für insertionSort und 'quick' für quickSort");
        System.out.println("Defaultalgorithmus: mergeSort");
    }
}