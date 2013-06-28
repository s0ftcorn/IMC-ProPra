import java.lang.Math;

public class GraphTest
{
    public static void main(String[] args){
        
        //Test mit Beispiel aus Vorlesung (Vorlesung19.pdf, Folie 30 ca)
        Graph g = new Graph(true);
        Node s, a, b, c, d;
        g.addNode(0);
        s = g.getNode(0);
        g.addNode(1);
        a = g.getNode(1);
        g.addNode(2);
        b = g.getNode(2);
        g.addNode(3);
        c = g.getNode(3);
        g.addNode(4);
        d = g.getNode(4);
        
        g.addEdge(s, a, 2);
        g.addEdge(s, c, 5);
        g.addEdge(a, b, 4);
        g.addEdge(b, c, 1);
        g.addEdge(c, a, -4);
        g.addEdge(c, b, 6);
        g.addEdge(c, d, 2);
        g.addEdge(d, b, 8);
        
        double[][] test = bellmanford(g, 0);
    }
    
    public static double[] sssp(Graph g, int source){
        return null;
    }
    
    private static double[][] bellmanford(Graph g, int source){
        
        int nodecount = g.getNodes().size();
        double[][] arr = new double[nodecount][nodecount];
        for(int i = 0; i < nodecount; i++){
            arr[0][i] = Double.POSITIVE_INFINITY;
        }
        arr[0][source] = 0;
        
        Edge lastEdge;
        for(int i = 0; i < nodecount; i++){
            lastEdge = null;
            for(int j = 1; j < nodecount; j++){
                if(lastEdge != null){
                    arr[i][j] = Math.min(arr[i-1][j], arr[i-1][j]+ lastEdge.getCost());
                }
                else{
                    arr[i][j] = arr[i-1][j];
                }
            }
        }
        
        
        return arr;
    }
}
