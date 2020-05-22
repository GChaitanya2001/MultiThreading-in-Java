import java.io.*;
import java.util.*;
import java.lang.*;

class sort
{
	int[] arr=new int[50];
	
	//bubble sort functions
	synchronized void bSort(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1])
                {
                    // swap temp and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }
	
	
	//quick sort functions
	synchronized int partition(int arr[], int low, int high) 
    { 
        int pivot = arr[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            // If current element is smaller than the pivot 
            if (arr[j] < pivot) 
            { 
                i++; 
  
                // swap arr[i] and arr[j] 
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        int temp = arr[i+1]; 
        arr[i+1] = arr[high]; 
        arr[high] = temp; 
  
        return i+1; 
    } 
	synchronized void qsort(int arr[], int low, int high) 
    { 
        if (low < high) 
        { 
            /* pi is partitioning index, arr[pi] is  
              now at right place */
            int pi = partition(arr, low, high); 
  
            /* Recursively sort elements before 
            partition and after partition */
            qsort(arr, low, pi-1); 
            qsort(arr, pi+1, high); 
        } 
		
    } 
	
	
	
	//merge sort functions
	synchronized void merge(int arr[], int l, int m, int r) 
    { 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp arrays */
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
  
  
        /* Merge the temp arrays */
  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] <= R[j]) 
            { 
                arr[k] = L[i]; 
                i++; 
            } 
            else
            { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
  
    // Main function that sorts arr[l..r] using 
    // merge() 
    synchronized void msort(int arr[], int l, int r) 
    { 
        if (l < r) 
        { 
            // Find the middle point 
            int m = (l+r)/2; 
  
            // Sort first and second halves 
            msort(arr, l, m); 
            msort(arr , m+1, r); 
  
            // Merge the sorted halves 
            merge(arr, l, m, r); 
        } 
    } 
	
	synchronized void mprintArray(int arr[])
    {
        int n = arr.length;
		System.out.println("\n");
		System.out.println("Thread t3 implementing merge sort.....");
        for (int i=0; i<n; ++i)
		{ System.out.print(arr[i] + " "); }
        System.out.println();
    }
	
	synchronized void qprintArray(int arr[])
    {
        int n = arr.length;
		System.out.println("\n");
		System.out.println("Thread t2 implementing quick sort.....");
        for (int i=0; i<n; ++i)
		{ System.out.print(arr[i] + " "); }
        System.out.println();
    }
	
	synchronized void bprintArray(int arr[])
    {
        int n = arr.length;
		System.out.println("\n");
		System.out.println("Thread t1 implementing bubble sort.....");	
        for (int i=0; i<n; ++i)
		{ System.out.print(arr[i] + " "); }
        System.out.println();
    }
  
}


//Thread implementing merge sort
class mergesort extends Thread
{
  sort s;
  mergesort(sort s){
	 this.s=s; 
  }
	public void run()
	{
		int[] arr=new int[50];
		arr=s.arr;
		s.msort(arr,0,49);
        s.mprintArray(arr);	
	}
}

//Thread implementing bubblesort
class bubblesort extends Thread
{ 
  sort s;
  bubblesort(sort s){
	 this.s=s; 
  }
    public void run()
    {
		int[] arr=new int[50];
		arr=s.arr;
		s.bSort(arr);
        s.bprintArray(arr);		
	}
	
}	

//Thread implementing quicksort
class quicksort extends Thread
{	
  sort s;
  quicksort(sort s){
	 this.s=s; 
  }
	public void run()
	{
		int[] arr=new int[50];
		arr=s.arr;
		s.qsort(arr,0,49);
        s.qprintArray(arr);
	}
}


public class sorting
{
	public static void main(String[] args)
	{
		int i;
		Random rand=new Random();
		int[] arr=new int[50];
		for(i=0;i<50;i++)
		{
			arr[i]=rand.nextInt(100);
		}
		System.out.println("An Array with 50 numbers between 0 - 100 is generated......");
		System.out.println("Array Values:");
		for(i=0;i<50;i++)
		{
			System.out.print(arr[i]+" ");
		}
		sort s=new sort();
		s.arr=arr;
		
		//Thread implementing Bubble sort on arr;
		bubblesort t1=new bubblesort(s);
		t1.setPriority(Thread.MIN_PRIORITY); 
		t1.start();
		
		//Thread implementing Quick sort on arr;
		quicksort t2=new quicksort(s);
		t2.setPriority(Thread.MAX_PRIORITY); 
		t2.start();
		
		//Thread implementing Merge sort on arr;
		mergesort t3=new mergesort(s);
		t3.setPriority(Thread.NORM_PRIORITY); 
		t3.start();
	}
}