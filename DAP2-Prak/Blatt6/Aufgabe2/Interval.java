
public class Interval
{
    int start;
    int end;
    
    public Interval(int pstart, int pend)
    {
        start = pstart;
        end = pend;
    }
    
    public int getStart()
    {
        return start;
    }
    
    public int getEnd()
    {
        return end;
    }
    
    public String toString()
    {
        return "Intervall Start: " + start + ", Ende: " + end;
    }
    
    public Job toJob()
    {
        return new Job(start, end);
    }

}
