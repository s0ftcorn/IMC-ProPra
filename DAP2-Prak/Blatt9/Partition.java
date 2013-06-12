import java.util.ArrayList;

public class Partition
{
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            System.out.println("Zu wenig Parameter");
            syntaxteller();
            return;
        }
        
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        for(int i = 0; i < args.length; i++)
        {
            try{arrlist.add(Integer.parseInt(args[i]));}
            catch(NumberFormatException e){
                System.out.println("Bitte nur natürliche Zahlen eingeben");
                syntaxteller();
                return;
            }
            if(arrlist.get(i) < 0){
                System.out.println("Bitte nur positive Zahlen eingeben");
                syntaxteller();
                return;
            }
        }
        
        if(berechnePartition(arrlist)){System.out.println("Eingabe ist partitionierbar");}
        else{System.out.println("Eingabe ist nicht partitionierbar");}
        System.out.println();
    }
    
    private static boolean berechnePartition(ArrayList<Integer> zahlen)
    {
        int summe = 0;
        for(int i = 0; i < zahlen.size(); i++)
        {
            summe += zahlen.get(i);
        }
        if(summe % 2 != 0) return false; //Summe ist ungerade => unteilbar in 2 gleiche Partitionen
        
        boolean[][] G = new boolean[zahlen.size()+1][summe+1];
        for(int i = 0; i <= zahlen.size(); i++)
        {
            // In G[i-1][summe/2] steht ob das Array bis zum Index i partitionierbar ist.
            for(int j = 0; j <= (summe/2); j++)
            {
                // Für G[i-1][j-1] gilt, dass es wahr ist, wenn sich eine teilmenge der Zahlen mit den indizes 1 bis i-1 a die summe j-1 darstellen kann.
                if(j==0){ G[i][j] = true;} // Ist die Summe 0, so ist diese immer partitionierbar
                    else if(i==0){ G[i][j] = false;} // Die Leere Summe ist nicht partitionierbar
                        else if(G[i-1][j]==true || (zahlen.get(i-1)<=j && G[i-1][j-zahlen.get(i-1)]) == true){
                            G[i][j] = true; 
                        }
                        else{G[i][j] = false;
                        }
            }
        }
        
        return G[zahlen.size()][summe/2];
    }
    
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: java Partition (natürliche Zahlen)");
        System.out.println();
    }
}
