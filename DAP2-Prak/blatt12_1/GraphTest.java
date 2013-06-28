import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse GraphTest.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GraphTest
{
    private static double[] sssp(Graph g, int source) {
        // get NodesListe (damit wir wissen, wie viel Knoten gibt es insgesamt)
        ArrayList<Node> nodes = g.getNodes();
        int numberOfNodes = nodes.size();
        
        // SSSP erstellen und die 1. Zeile Initialisieren
        double[][] SSSP = new double[numberOfNodes][numberOfNodes];
        for (int j=0; j<numberOfNodes; j++) {
            SSSP[0][j] = -1;
        }
        SSSP[0][source] = 0;
        
        // weitere Zeilen ausfüllen
        double tempMin, tempCost;
        for (int i=1; i<numberOfNodes; i++) {
            for (int j=0; j<numberOfNodes; j++) {
                // den kleinsten Weg zum Knoten j finden
                tempMin = SSSP[i-1][j];
                
                // die SSSP Matrix in der Zeile drüben durchgehen
                for (int from=0; from<numberOfNodes; from++) {
                    // wir merken uns die Kosten der Kante from -> j
                    tempCost = g.getCost(from, j);
                    
                    // falls die Costen > 0 sind (also die Kante from -> j existiert)
                    // und SSSP[i-1][from] > -1 ist (d.h. wir haben schon einen Wert),
                    // berechnen wir den tempMin. tempMin nimmt dann den kleinsten Wert
                    //      von allen möglichen `from` Knoten
                    if (tempCost > 0 && SSSP[i-1][from] > -1) {
                        
                        // falls unser tempMin noch nicht defeniert ist -> überschreiben,
                        // sonst das Minimum von aktuellem und gefundenem tempMin finden
                        if (tempMin == -1) 
                            tempMin = SSSP[i-1][from]+tempCost;
                        else
                            tempMin = Math.min(tempMin, SSSP[i-1][from]+tempCost);
                    }
                }
                
                // gefundenen tempMin in der Tabelle speichern 
                SSSP[i][j] = tempMin;
            }
        }
        
        
        /*
        // tabelle zum testen ausgeben 
        for (int i=0; i<numberOfNodes; i++) {
            System.out.println();
            for (int j=0; j<numberOfNodes; j++) {
                System.out.print(SSSP[i][j]+" ");
            }
        }
        */
        
        // die letzte Zeile zurückgeben, da wir die kürzeste Wege vom Startknoten source zu jeweiligen Knoten
        // in dieser Zeile haben
        return SSSP[numberOfNodes-1];
    }
    
    
    public static void main(String[] args) {
        System.out.println();
        if (args.length == 2) {
            // wir versuchen einen Graph aus der Datei einzulesen
            Graph g = Graph.fromFile(args[0]);
            
            if (g == null) {
                System.out.println("Der Graph konnte nicht aus der Datei \""+args[0]+"\" eingelesen werden");
                return;
            }
            
            // zweiten Parameter versuchen zu parsen
            int source;
            try {
                source = Integer.parseInt( args[1] );
                if (source < 0)
                    Integer.parseInt("Exception werfen");
            } catch (NumberFormatException e) {
                System.out.println("Als Startknoten werden nur Natürliche Zahlen akzeptiert");
                return;
            }
            
            // get ArrayList der Knoten, damit wir die Anzahl der Knoten ausrechnen können
            ArrayList<Node> nodeList = g.getNodes();
            int nodeAnzahl = nodeList.size();
            if (source >= nodeAnzahl) {
                System.out.println("Der eingegebener Index der Startknoten existiert nicht!");
                System.out.println("Der Graph in der eingegebener Datei beinhaltet "+nodeAnzahl+" Knoten");
                return;
            }
            
            double[] allDistance = sssp(g, source);
            
            System.out.println("Pfadkosten :");
            for (int i=0; i<allDistance.length; i++) {
                String kosten = (allDistance[i] == -1) ? "- nicht erreichbar" : "= "+allDistance[i];
                System.out.println( source+" -> "+i+" "+kosten );
            }
            
        } else {
            System.out.println("Falsche Anzahl an Parametern");
            syntaxteller();
        }
        System.out.println();
    }
    
    private static void syntaxteller() {
        System.out.println("Richtiger Aufruf: java GraphTest DATEI SOURCE");
        System.out.println("DATEI - Pfad zur datei");
        System.out.println("SOURCE - Index des Startknotens");
    }
}
