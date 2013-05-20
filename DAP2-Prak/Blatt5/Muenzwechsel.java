
public class Muenzwechsel
{
    public static void main(String[] args) {
        System.out.println();
        if (args.length != 2) {
            // Falsche Anzahl an Parameter abfangen
            System.out.println("Falsche Anzahl von Parametern");
            syntaxteller();
            return;
        } else {
            // Es wurden genau zwei Parameter eingegeben
            
            // initiate Währung-array
            System.out.print("Ausgewählte Währung: ");
            int[] w;
            
            if (args[0].equals("Euro")) {
                w = new int[] {200, 100, 50, 20, 10, 5, 2, 1};
                System.out.println("Euro");
            } else if (args[0].equals("Alternative")) {
                w = new int[] {200, 100, 50, 20, 10, 5, 4, 2, 1};
                System.out.println("Alternative");
            } else {
                System.out.println("Es wurde eine falsche Währungsart eingegeben");
                syntaxteller();
                return;
            }
            
            // 2. Parameter (die Größe des zu sortierenden Arrays) parsen
            int b;
            try {
                b = Integer.parseInt( args[1] );
                if (b <= 0)
                    b = Integer.parseInt("Exception werfen");
            } catch(NumberFormatException e) {
                System.out.println("Ungültiger Betrag");
                syntaxteller();
                return;
            }
            System.out.println("Geld zum Wechseln: "+b);
            System.out.println("Das Automat gibt folgende Währungen zurück:");
            
            int[] wechselgeld = change(b, w);
            
            // Ausgabe
            for (int i=0; i<w.length; i++) {
                if (wechselgeld[i] != 0) {
                    System.out.print(wechselgeld[i]+" mal "+w[i]+"er ");
                    if (w[i]<5)
                        System.out.println("Münze");
                    else 
                        System.out.println("Schein");
                }
            }
            System.out.println();
        }
    }
    
    public static int[] change(int b, int[] w) {
        // Neues Array ausgeben, wo gezählt wird, wie viele Scheine wir brauchen für den jeweiligen Schein
        int[] result = new int[w.length];
        for (int i=0; i<w.length; i++) {
            // falls b durch eine 
            if (b/w[i] > 0) {
                result[i] = b/w[i];
                b -= w[i]*result[i];
            }
        }
        
        return result;
    }
    
    
    private static void syntaxteller() {
        System.out.println("Richtiger Aufruf: java Muenzwechsel [Euro|Alternative] [143]");
        System.out.println("Erster Parameter steht für die Währung, zweiter - Wert des Wechselgeldes");
        System.out.println();
    }
}
