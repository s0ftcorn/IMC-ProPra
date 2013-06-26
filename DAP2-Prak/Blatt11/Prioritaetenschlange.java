
public class Prioritaetenschlange
{
    public static void main(String args[])
    {
        //Beginn Kommandozeileninterpreatation
        if(args.length != 2)
        {
            System.out.println("Falsche Anzahl an Parametern");
            syntaxteller();
            return;
        }
        
        int n,k;
        
        try{
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException e){
            System.out.println("Bitte nur ganze, positive Zahlen eingeben");
            return;
        }
        if(n < 0 || k < 0){     //negative Zahlen wurden eingegeben
            System.out.println("Bitte nur ganze, positive Zahlen eingeben");
            return;
        }
        //Ende Kommandozeileninterpretation
        
        java.util.Random numberGenerator = new java.util.Random(); //Zufallsgenreator
        Heap heap = new Heap(n+k);  //Limit ist n+k, da theoretisch nachträglich noch k Jobs eingefügt werden könnten
        for(int i = 0; i < n; i++){     //n zufällige Jobs mit zufälligen Prioritäten in Heap ablegen
            heap.insert(numberGenerator.nextInt(101));
        }
        heap.printHeap();
        
        for(int i = 0; i < k; i++){
            int x = numberGenerator.nextInt(4); //Fallunterscheidung mit 25% Wahrscheinlichkeiten
            if(x==0){   //25% Wahrscheinlichkeit: Füge neues Element ein und stelle Heap-Struktur her
                int z = numberGenerator.nextInt(101);
                heap.insert(z);
                heap.heapify(1);
                System.out.println("Füge Job mit Prioritaet " + z + " ein.");
                heap.printHeap();
            }
            else{   //75% Wahrscheinlichkeit: Entferne ein Element und gib es aus (Struktur wird wiederhergestellt)
                int z = heap.extractMax();
                System.out.println("Job mit Prioritaet " + z + " fertig.");
                heap.printHeap();
            }
            System.out.println();
        }
        
    }
    
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: Prioritaetenschlange (Anzahl Jobs) (Anzahl Aktionen)");
        System.out.println();
    }
}
