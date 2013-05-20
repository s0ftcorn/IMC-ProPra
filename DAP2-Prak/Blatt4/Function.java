
/**
 * Write a description of class Function here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Function
{   
    private double m = 0;
    private double b = 0;
    
    public Function(int a, int b, int c, int d){
        makeFunc(a,b,c,d);
    }
    
    public Function(Punkt a, Punkt b){
        makeFunc(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    public double getm(){
        return m;
    }
    
    public double getb(){
        return b;
    }
    
    private void makeFunc(int x1, int y1, int x2, int y2){
        if(x1 == x2){
            m = 100000;
        }else if(y1 == y2){
            m = 0;
        }else{
            m = ((double)y2-(double)y1)/((double)x2-(double)x1);
        }
        
        b = (double)y1-(m*(double)x1);
    }
    
    public boolean isRight(Punkt p){
        //and here magic happens
        double func_y = (m*(double)p.getX())+b;
        if(p.getY() < func_y)   //Punkt p liegt links der Funktion
            return false;
        else if(p.getY() > func_y)      //p liegt rechts der Funktion
            return true; 
        else{                   //x-Koordinaten sind gleich
            System.out.println("We have a problem here");
            return false;
        }
    }
    
}
