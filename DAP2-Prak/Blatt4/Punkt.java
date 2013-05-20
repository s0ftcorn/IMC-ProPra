public class Punkt
{
    private int X, Y;

    public Punkt(int x, int y){
        X = x;
        Y = y;
    }

    public Punkt(){
        X = 0;
        Y = 0;
    }
    
    public boolean equals(Punkt p)
    {
        if(X == p.getX() && Y == p.getY())
            return true;
        else
            return false;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }
    
    public String toString(){
	return "(" + X + ":" + Y + ")";
    }
}
