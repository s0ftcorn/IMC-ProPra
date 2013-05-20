
public class Job
{
    int duration;
    int deadline;
    
    public Job(int pDuration, int pDeadline)
    {
        duration = pDuration;
        deadline = pDeadline;
    }
    
    public int getDuration()
    {
        return duration;
    }
    
    public int getDeadline()
    {
        return deadline;
    }
}
