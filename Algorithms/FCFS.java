package Algorithms;

import java.util.*;

public class FCFS {
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter no of process: ");
        int n = sc.nextInt();
        int pid[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        int wt[] = new int[n];
        float avgWT=0,avgTAT=0;

        for(int i = 0; i < n; i++) {
            System.out.println("enter process " + (i+1) + " arrival time: ");
            at[i] = sc.nextInt();
            System.out.println("enter process " + (i+1) + " brust time: ");
            bt[i] = sc.nextInt();
            pid[i] = i+1;
        }

//sorting according to arrival times
        for(int i = 0 ; i <n; i++) {
            for(int  j=0;  j < n-(i+1) ; j++) {
                if( at[j] > at[j+1] ) {
                    swap(at,j,j+1);
                    swap(bt,j,j+1);
                    swap(pid,j,j+1);
                }
            }
        }
// finding completion times
        ct[0] = at[0] + bt[0];
        for(int  i = 1 ; i < n; i++) {
            if( at[i] > ct[i-1]) {
                ct[i] = at[i] + bt[i];
            }
            else {
                ct[i] = ct[i - 1] + bt[i];
            }
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
            avgWT += wt[i];
            avgTAT += tat[i];
        }

        System.out.println("\npid  arrival  burst  complete turn waiting");
        for(int  i = 0 ; i< n;  i++) {
            System.out.println(pid[i] + "  \t " + at[i] + "\t\t" + bt[i] + "\t\t" + ct[i] + "\t\t" + tat[i] + "\t\t"  + wt[i] ) ;
        }
        sc.close();
        System.out.println("\naverage waiting time: "+ (avgWT/n));
        System.out.println("average turnaround time:"+(avgTAT/n));
    }

    static void swap(int nums[], int start, int end){
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }
}

