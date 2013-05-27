

public class LCSTest
{
   public static void main(String[] args)
   {
       int x = 0;
       try{x = Integer.parseInt(args[0]);}
       catch(Exception e){}
       
       
       for(int i = 1; i < x; i=i*2)
       {
           String[] s = new String[1];
           s[0] = Integer.toString(i*10);
            LCS.main(s);
        }
   }
}
