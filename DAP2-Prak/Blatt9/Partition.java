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
                System.out.println("Bitte nur ganze Zahlen eingeben");
                syntaxteller();
                return;
            }
        }
        
        if(berechnePartition(arrlist)){System.out.println("Eingabe ist partitionierbar");}
        else{System.out.println("Eingabe ist nicht partitionierbar");}
        System.out.println();
    }
    
    private static boolean berechnePartition(ArrayList<Integer> z)
    {
        int W = 0;
        for(int i = 0; i < z.size(); i++)
        {
            W += z.get(i);
        }
        if(W % 2 != 0) return false; //Summe ist ungerade => unteilbar in 2 gleiche Partitionen
        
        boolean[][] G = new boolean[z.size()+1][W+1];
        for(int i = 0; i <= z.size(); i++)
        {
            for(int j = 0; j <= (W/2); j++)
            {
                if(j==0){ G[i][j] = true;}
                    else if(i==0){ G[i][j] = false;}
                        else if(G[i-1][j]==true || (z.get(i-1)<=j && G[i-1][j-z.get(i-1)]) == true){
                            G[i][j] = true;
                        }
                            else{G[i][j] = false;}
            }
        }
        
        return G[z.size()][W/2];
    }
    
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: java Partition (Ganze Zahlen)");
        System.out.println();
    }
}
