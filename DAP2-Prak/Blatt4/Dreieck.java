
/**
 * Write a description of class Dreieck here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dreieck
{
    private Punkt A;
    private Punkt B;
    private Punkt C;
    
    public Dreieck(int x1, int y1, int x2, int y2, int x3, int y3){
        A = new Punkt(x1,y1);
        B = new Punkt(x2,y2);
        C = new Punkt(x3,y3);
    }
    
    public Dreieck(Punkt a, Punkt b, Punkt c){
        A = a;
        B = b;
        C = c;
    }
    
    public Punkt getA(){
        return A;
    }
    
    public Punkt getB(){
        return B;
    }
    
    public Punkt getC(){
        return C;
    }
    
    public double euclidDistance(Punkt p1, Punkt p2){
        double x1 = (double)p1.getX();
        double x2 = (double)p1.getY();
        
        double y1 = (double)p2.getX();
        double y2 = (double)p2.getY();
        
        double result = Math.sqrt(((x1-y1)*(x1-y1))+((x2-y2)*(x2-y2)));
        
        return result;
    }
    
    public double umfang(){
	double a = euclidDistance(A, B);
	double b = euclidDistance(B, C);
	double c = euclidDistance(A, C);
	return a+b+c;
    }
    
    public boolean containsPunkte(Punkt[] punkte){
        for(int i = 0; i < punkte.length; i++){
            if(!contains(punkte[i])) return false;
        }
        return true;
    }
    
    public boolean contains(Punkt P){
        boolean tri_side = false;
        boolean des_side = false;
        
        // "Innen" für A und B als gerade und C als "Spitze"
        if(A.getX() == B.getX()){
            // Prüfen der Lage der "Spitze"
            if(C.getX() > A.getX()){
                tri_side = true;
            }
            if(C.getX() < A.getX()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getX() > A.getX()){
                des_side = true;
            }
            if(P.getX() < A.getX()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(A.getX() == B.getX() && P.getX() == A.getX()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else if( ( A.getY() == B.getY() ) ){
            // Prüfen der Lage der "Spitze"
            if(C.getY() > A.getY()){
                tri_side = true;
            }
            if(C.getY() < A.getY()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > A.getY()){
                des_side = true;
            }
            if(P.getY() < A.getY()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(A.getY() == B.getY() && P.getY() == A.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else{
            // Ausrechnen der Funktion und der Funktionswerte an den relevanten Stellen
            double func_m = (double)(A.getY()-B.getY())/(double)(A.getX()-B.getX());
            double func_b = (func_m * (double)A.getX())-(double)A.getY();
            double func_y_p = (func_m*P.getX())+func_b;
            double func_y_c = (func_m*C.getX())+func_b;
            // Prüfen der Lage der "Spitze"
            if(C.getY() > func_y_c){
                tri_side = true;
            }
            if(C.getY() < func_y_c){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > func_y_c){
                des_side = true;
            }
            if(P.getY() < func_y_c){
                des_side = false;
            }
            // Prüfen ob der Punkt auf der Gerade liegt
            if(func_y_p == P.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }
        
        // "Innen" für B und C als gerade und A als "Spitze"
        if(C.getX() == B.getX()){
            // Prüfen der Lage der "Spitze"
            if(A.getX() > C.getX()){
                tri_side = true;
            }
            if(A.getX() < C.getX()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getX() > C.getX()){
                des_side = true;
            }
            if(P.getX() < C.getX()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(C.getX() == B.getX() && P.getX() == C.getX()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else if( ( C.getY() == B.getY() ) ){
            // Prüfen der Lage der "Spitze"
            if(A.getY() > C.getY()){
                tri_side = true;
            }
            if(A.getY() < C.getY()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > C.getY()){
                des_side = true;
            }
            if(P.getY() < C.getY()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(C.getY() == B.getY() && P.getY() == C.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else{
            // Ausrechnen der Funktion und der Funktionswerte an den relevanten Stellen
            double func_m = (double)(C.getY()-B.getY())/(double)(C.getX()-B.getX());
            double func_b = (func_m * (double)C.getX())-(double)C.getY();
            double func_y_p = (func_m*P.getX())+func_b;
            double func_y_a = (func_m*A.getX())+func_b;
            // Prüfen der Lage der "Spitze"
            if(A.getY() > func_y_a){
                tri_side = true;
            }
            if(A.getY() < func_y_a){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > func_y_a){
                des_side = true;
            }
            if(P.getY() < func_y_a){
                des_side = false;
            }
            // Prüfen ob der Punkt auf der Gerade liegt
            if(func_y_p == P.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }
        
        // "Innen" für A und C als gerade und B als "Spitze"
        if(C.getX() == A.getX()){
            // Prüfen der Lage der "Spitze"
            if(B.getX() > C.getX()){
                tri_side = true;
            }
            if(B.getX() < C.getX()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getX() > C.getX()){
                des_side = true;
            }
            if(P.getX() < C.getX()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(C.getX() == A.getX() && P.getX() == C.getX()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else if( ( C.getY() == A.getY() ) ){
            // Prüfen der Lage der "Spitze"
            if(B.getY() > C.getY()){
                tri_side = true;
            }
            if(B.getY() < C.getY()){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > C.getY()){
                des_side = true;
            }
            if(P.getY() < C.getY()){
                des_side = false;
            }
            // Liegen alle Punkte auf einer Geraden
            if(C.getY() == A.getY() && P.getY() == C.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }else{
            // Ausrechnen der Funktion und der Funktionswerte an den relevanten Stellen
            double func_m = (double)(C.getY()-A.getY())/(double)(C.getX()-A.getX());
            double func_b = (func_m * (double)C.getX())-(double)C.getY();
            double func_y_p = (func_m*P.getX())+func_b;
            double func_y_b = (func_m*B.getX())+func_b;
            // Prüfen der Lage der "Spitze"
            if(B.getY() > func_y_b){
                tri_side = true;
            }
            if(B.getY() < func_y_b){
                tri_side = false;
            }
            // Prüfen der Lage des Punktes p
            if(P.getY() > func_y_b){
                des_side = true;
            }
            if(P.getY() < func_y_b){
                des_side = false;
            }
            // Prüfen ob der Punkt auf der Gerade liegt
            if(func_y_p == P.getY()){
                tri_side = true;
                des_side = true;
            }
            
            if(tri_side != des_side){
                return false;
            }
        }
        
        return true;
    }
}
