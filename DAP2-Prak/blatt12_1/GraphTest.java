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
    
    private static double[][] apsp(Graph g) {
        // die KnotenListe bekommen, damit wir die Anzahl ausrechnen können
        ArrayList<Node> nodes = g.getNodes();
        int n = nodes.size();
        
        double[][] apspD = new double[n][n];
        
        for (int k=0; k<n; k++) {
            if (k == 0) {
                // falls k==0, wir initialisieren die Tabelle (D^0)
                for (int i=0; i<n; i++) {
                    for (int j=0; j<n; j++) {
                        if (i!=j)
                            apspD[i][j] = g.getCost(i,j);
                    }
                }
            } else {
                // sonst finden wir immer den kleinsten Wert, und somit bilden D^k
                for (int i=0; i<n; i++) {
                    for (int j=0; j<n; j++) {
                        // ein bisschen black-magic, damit wir tatsächlich 
                        // richtige eingaben in die Tabelle einfügen
                        if (apspD[i][k] > -1 && apspD[k][j] > -1) {
                            if (apspD[i][j] == -1) 
                                apspD[i][j] = apspD[i][k] + apspD[k][j];
                            else
                                apspD[i][j] = Math.min(apspD[i][j], apspD[i][k] + apspD[k][j]);
                        }
                    }
                }
                
            }
        }
        
        return apspD;
    }
    
    private static double[][] apspBellmandFord(Graph g) {
        // die KnotenListe bekommen, damit wir die Anzahl ausrechnen können
        ArrayList<Node> nodes = g.getNodes();
        int n = nodes.size();
        
        double[][] apspT = new double[n][n];
        for (int i=0; i<n; i++) {
            System.arraycopy(sssp(g, i), 0, apspT[i], 0, n);
        }
        
        return apspT;
    }
    
    
    public static void main(String[] args) {
        System.out.println();
        if (args.length > 2) {
            System.out.println("Falsche Anzahl an Parametern");
            syntaxteller();
        } else {
            // wir versuchen einen Graph aus der Datei einzulesen
            Graph g = Graph.fromFile(args[0]);
            
            if (g == null) {
                System.out.println("Der Graph konnte nicht aus der Datei \""+args[0]+"\" eingelesen werden");
                return;
            }
            
            if (args.length == 2) {
                // SSSP PROBLEM
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
                
                if (allDistance.length <= 20) {
                    System.out.println("Pfadkosten zum jeweiligem Punkt :");
                    for (int i=0; i<allDistance.length; i++) {
                        String kosten = (allDistance[i] == -1) ? "- nicht erreichbar" : "= "+allDistance[i];
                        System.out.println( source+" -> "+i+" "+kosten );
                    }
                    System.out.println();
                }
                
                // den längten Weg finden
                double maxPath = -1;
                int maxNode = 0;
                for (int i=0; i<allDistance.length; i++) {
                    if (maxPath < allDistance[i]) {
                        maxPath = allDistance[i];
                        maxNode = i;
                    }
                }
                
                System.out.println("Der längste Weg ist "+source+" -> "+maxNode+" = "+maxPath);
            
            } else {
                //APSP PROBLEM
                
                
                // Zeitmessung der APSP mit Floyd Warshall
                long t_startFW = System.currentTimeMillis();
                double[][] apspFW = apsp(g);
                long t_endFW = System.currentTimeMillis();
                double t_deltaFW = ((double)t_endFW-(double)t_startFW);
                
                
                // Zeitmessung der APSP mit Bellmand Ford
                long t_startBF = System.currentTimeMillis();
                double[][] apspBF = apspBellmandFord(g);
                long t_endBF = System.currentTimeMillis();
                double t_deltaBF = ((double)t_endBF-(double)t_startBF);
                
                
                
                // eine VERDAMMT SCHÖNE AUSGABE der APSP Tabelle!!!!
                if (apspFW.length <= 10) {
                    String tempS;
                    System.out.print("         | ");
                    for (int i=0; i<apspFW.length; i++) {
                        System.out.print(" nach "+i+"  | ");
                    }
                    System.out.println();
                    for (int i=0; i<apspFW.length; i++) {
                        System.out.print("---------|");
                        for (int m=0; m<apspFW.length; m++) {
                            System.out.print("----------|");
                        }
                        System.out.println();
                        System.out.print("Von "+i+"    | ");
                        for (int j=0; j<apspFW.length; j++) {
                            if (apspFW[i][j] == -1) {
                                tempS = "notReach";
                            } else {
                                tempS = String.valueOf(apspFW[i][j]);
                                for (int s=tempS.length(); s<8; s++) {
                                    tempS +=" ";
                                }
                            }
                            tempS += " | ";
                            System.out.print(tempS);
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                
                // am weitesten entfernten knoten-paar suchen
                double maxPath = -1;
                int from = 0;
                int to = 0;
                for (int i=0; i<apspFW.length; i++) {
                    for (int j=0; j<apspFW.length; j++) {
                        if (maxPath < apspFW[i][j]) {
                            from = i;
                            to = j;
                            maxPath = apspFW[i][j];
                        }
                    }
                }
                
                System.out.println("Am weitesten entfernte Knotenpaar: "+from+" -> "+to+" = "+maxPath);
                System.out.println("Die Zeitmessungen: Floyd Warshall - "+(int)t_deltaFW+"ms | Bellmand Ford - "+(int)t_deltaBF+"ms");
            }
        }
        System.out.println();
    }
    
    private static void syntaxteller() {
        System.out.println("Richtiger Aufruf: java GraphTest DATEI [SOURCE]");
        System.out.println("DATEI - Pfad zur datei");
        System.out.println("SOURCE - Index des Startknotens");
        System.out.println("Falls SOURCE eingegeben wurde, wird SSSP Alghorithmus durchgeführt, sonst APSP");
    }
}
