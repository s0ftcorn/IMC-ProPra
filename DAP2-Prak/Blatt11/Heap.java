
public class Heap
{
 
    int limit;
    int heapSize;
    int[] arr;
    
    public Heap(int n)
    {
        if(n < 0){
            System.out.println("Negative limit");
            return;
        }
        else limit = n;
        
        heapSize = 0;
        arr = new int[limit+1];
    }
    
    public int left(int i)
    {
        return i*2;
    }
    
    public int right(int i)
    {
        return i*2+1;
    }
    
    public int parent(int i)
    {
        return i/2;
    }
    
    public void heapify(int i)
    {
        int l = left(i);
        int r = right(i);
        int largest;
        if( l<= heapSize && arr[l]>arr[i]){
            largest = l;
        }
        else{
            largest = i;
        }
        if(r<= heapSize && arr[r] > arr[largest]){
            largest = r;
        }
        if(largest!=i){
            int zwischen = arr[i];
            arr[i] = arr[largest];
            arr[largest] = zwischen;
            heapify(largest);
        }
        
    }
    
    public void insert(int key)
    {
        if(heapSize == limit)
        {
            System.out.println("Heap limit reached");
            return;
        }
        else{
            heapSize++;
            int i = heapSize;
            while(i>1 && arr[parent(i)] < key){
                arr[i] = arr[parent(i)];
                i = parent(i);
            }
            arr[i] = key;
        }
    }
    
    public int extractMax()
    {
        if(heapSize < 1){
            System.out.println("No element");
            return 0;
        }
        else{
            int max = arr[1];
            arr[1] = arr[heapSize];
            heapSize--;
            heapify(1);
            return max;
        }
    }
    
    public void printHeap()
    {
        int levellimit = 1;
        int count = 0;
        for(int i = 1; i <= heapSize; i++){
            System.out.print(arr[i] + " ");
            count++;
            if(count == levellimit){
                levellimit *= 2;
                count = 0;
                System.out.println();
            }
        }
        System.out.println();
    }
}
