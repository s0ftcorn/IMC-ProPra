import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;



/**
 * Beschreiben Sie hier die Klasse Breitensuche.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Breitensuche
{
    public static void main(String[] args)
    {
        if(args.length != 2)
        {
            System.out.println("Falsche Anzahl Parameter");
            return;
        }
        
        Graph g = Graph.fromFile(args[0]);
        int c;
        
        try{
            c = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Nur nicht-negative Ganzzahlen eingeben");
            return;
        }
        
        ArrayList<Edge> arrlist = bfs(g, c);
        
        for(int i = 0; i < arrlist.size(); i++){
            Edge currEdge = arrlist.get(i);
            System.out.println("Knoten: " + currEdge.getDest().getID() + "; Weglaenge von Start: " + currEdge.getCost());
            
            //kürzeste Wege Ausgeben
            Node start = g.getNode(c);
            ArrayList<Node> weg = kuerzesterWeg(arrlist, start, currEdge.getDest());
            String wegString = "";
            for(int j = 0; j < weg.size(); j++){
                wegString += (weg.get(j).getID() + " <= ");
            }
            System.out.println(wegString.substring(0, wegString.length() - 4));
            System.out.println();
        }
        
        
    }
    
    public static ArrayList<Node> kuerzesterWeg(ArrayList<Edge> arrlist, Node start, Node ziel){
        ArrayList<Node> ergebniss = new ArrayList<Node>();
        
        ergebniss.add(ziel);
        
        Node currNode = ziel;
        
        while(currNode != start)
        {
            for(int i = 0; i < arrlist.size(); i++){
                if(arrlist.get(i).getDest().getID() == currNode.getID()){
                    currNode = arrlist.get(i).getSource();
                    ergebniss.add(currNode);
                    break;
                }
            }
        }
        
        
        return ergebniss;
    }

    public static ArrayList<Edge> bfs (Graph g, int s) {
        // bfs initialisieren
        // queue
        Queue<Node> queue = new LinkedList<Node>();
        
        ArrayList<Edge> ergebnisse = new ArrayList<Edge>(); //initialisiere Liste von Rückgaben
        
        // für d[u] und pi[u] arrays initialisieren, für die Farben - arrayLists
        ArrayList<Node> nodes = g.getNodes();
        int numberOfNodes = nodes.size();
        
        double[] d = new double[numberOfNodes];
        Node[] pi = new Node[numberOfNodes];
        ArrayList<Node> whiteNodes = new ArrayList<Node>();
        ArrayList<Node> grayNodes = new ArrayList<Node>();
        
        // brauchen wir das?
        ArrayList<Node> blackNodes = new ArrayList<Node>();
        
        for (int i=0; i<numberOfNodes; i++) {
            // unendlich für die d-Werte initialisieren
            d[i] = Double.POSITIVE_INFINITY;
            
            // alle Knoten zur whiteNodes hinzufügen
            whiteNodes.add(nodes.get(i));
        }
        
        // den Startknoten initialisieren
        d[s] = 0;
        grayNodes.add( whiteNodes.remove(s) );
        
        // s-knoten in die queue hinzufügen
        queue.offer( nodes.get(s) );
        
        Node u, v;
        int uID, vID;
        while (!queue.isEmpty()) {
            u = queue.peek();   
            uID = u.getID();
            //adjazenzliste bekommen
            ArrayList<Edge> adjazente = u.getAdjacentList();
            for (int i=0; i<adjazente.size(); i++) {    //gehe alle von u erreichbaren Knoten durch
                v = adjazente.get(i).getDest();
                vID = v.getID();
                if (whiteNodes.contains( v )) {     //wenn betrachteter Knoten weiß => setze auch grau und füge zu Queue hinzu
                    if (whiteNodes.remove(v))
                        grayNodes.add(v);
                    d[vID] = d[uID]+1;
                    pi[vID] = u;
                    
                    ergebnisse.add(new Edge(u, v, d[vID]));
                    
                    queue.offer(v);
                }
            }
            
            u = queue.poll();   //setze u auf schwarz (abgearbeitet)
            uID = u.getID();
            if (grayNodes.remove(u))
                blackNodes.add(u);
        }
        
        return ergebnisse;
        
    }
}
