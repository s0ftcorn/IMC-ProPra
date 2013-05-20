
public class Euclid
{
    public static void main(String[] args)
    {
        if(args.length < 2  || args.length > 2) //gibt es genügend Parameter?
        {
            System.out.println("Ungültige Anzahl an Parametern");
            System.out.println("Syntax: java Euclid a b");
            return;
        }
        
        int a, b;   //Speicher für Parameter deklarieren
        
        try{
        a = (int)Integer.parseInt(args[0]); //erster Parameter
        b = (int)Integer.parseInt(args[1]); //zweiter Parameter
        }
        catch(NumberFormatException e)      //es wurde ein nicht-ganzzahliges Parameter übergeben
        {
            System.out.println("Nur ganzzahlige Parameter erlaubt");
            return;
        }

        
        if(a <= 0 || b <= 0)    //sind die Parameter positiv und != 0?
        {
            System.out.println("Parameter dürfen nicht 0 oder negativ sein");
            return;
        }
        

        
        System.out.println("Größter gemeinsamer Teiler von " + a + " und " + b + " = " + ggT(a, b));
        
    }
    
    public static int ggT(int a, int b)     //Umsetzung euklidischer Algorithmus
    {
        if(b == 0)
        {
            return a;
        }
        else{
            return ggT(b, a%b);
        }
    }
}
