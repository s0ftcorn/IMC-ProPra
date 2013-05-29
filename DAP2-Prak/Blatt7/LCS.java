import java.util.Random;

public class LCS
{
    public static void main(String[] args)
    {
        int n = 0;
        //Beginn Êinlesen der Kommandozeile
        if(args.length != 1)
        {
            System.out.println("Ungültige Anzahl an Parametern");
            syntaxteller();
            return;
        }
        try{
            n = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e){
            System.out.println("Bitte nur ganze positive Zahlen eingeben");
            syntaxteller();
            return;
        }
        if(n < 0){
            System.out.println("Bitte nur ganze positive Zahlen eingeben");
            syntaxteller();
            return;
        }
        //Ende Einlesen der Kommandozeile
        
        String x = randStr(n, new Random());
        String y = randStr(n, new Random());
        
        long tStart = System.currentTimeMillis();
        
        int[][] arr = LCSlaenge(x, y);
        
        long tEnd = System.currentTimeMillis();
        float tDiff = tEnd-tStart;
        
        System.out.println("Lauf mit Stringlänge " + n + ": " + (tDiff/1000) + " Sekunden");
        System.out.println("Länge der längsten gemeinsamen Teilsequenz: " + arr[n][n]);
    }
    
    public static int[][] LCSlaenge(String x, String y)
    {
        int m = x.length();
        int n = y.length();
        int[][] c = new int[m+1][n+1];
        
        for(int i = 0; i<m+1; i++)
            c[i][0] = 0;
        for(int j = 0; j<n+1; j++)
            c[0][j] = 0;
        for(int i = 1; i<m+1; i++)
        {
            //Invariante: In Zelle [i-1][n] steht die Länge des längsten gemeinsamen Teilstring von x[1 bis (i-1)] und y
            for(int j = 1; j<n+1; j++)
            {
                //Invariante: In Zelle [i][j-1] steht die Länge des längsten gemeinsamen Teilstring von x[1 bis i] und y[1 bis (j-1)]
                laengenberechnung(x, y, c, i, j);
            }
        }
        
        return c;
    }

    private static void laengenberechnung(String x, String y, int[][] c, int i, int j)
    {
        if(x.charAt(i-1) == y.charAt(j-1))
            c[i][j] = (c[i-1][j-1]+1);
        else
        {
            if(c[i-1][j] >= c[i][j-1])  c[i][j] = c[i-1][j];
            else                        c[i][j] = c[i][j-1];
            
        }
    }
    
    /**
     * Erzeugt einen zufälligen String mit der Länge n
     */
    private static String randStr(int n, Random r) {
        String alphabet ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder res = new StringBuilder(n);
        while (--n>=0)
            res.append(alphabet.charAt(r.nextInt(alphabet.length())));
        return res.toString();
    }  
    
    /**
     * Gibt den korrekten Syntax aus
     */
    private static void syntaxteller()
    {
        System.out.println("Korrekter Syntax: LCS (Länge der Zufallsfolgen)");
        System.out.println();
    }
}