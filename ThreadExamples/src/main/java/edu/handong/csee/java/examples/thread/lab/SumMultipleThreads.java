package edu.handong.csee.java.examples.thread.lab;

import java.util.ArrayList;

public class SumMultipleThreads implements Runnable{

	long from, to, totalSum;

	public static void main(String[] args) {

		long to = 10000000;
		ArrayList<SumMultipleThreads> sumRunners = new ArrayList<SumMultipleThreads>();

		/* Let a thread compute a sub-sum.
		 * sumRunners.add(new SumMultipleThreads(1,1000000));
		 * sumRunners.add(new SumMultipleThreads(1000001,2000000));
		 * sumRunners.add(new SumMultipleThreads(2000001,3000000));
		 * sumRunners.add(new SumMultipleThreads(3000001,4000000));
		 * sumRunners.add(new SumMultipleThreads(4000001,5000000));
		 * sumRunners.add(new SumMultipleThreads(5000001,6000000));
		 * sumRunners.add(new SumMultipleThreads(6000001,7000000));
		 * sumRunners.add(new SumMultipleThreads(7000001,8000000));
		 * sumRunners.add(new SumMultipleThreads(8000001,9000000));
		 * sumRunners.add(new SumMultipleThreads(9000001,10000000));*/
		for(long i=0; i<to/1000000; i++) {
			sumRunners.add(new SumMultipleThreads((i*1000000)+1, (i+1)*1000000));
		}

		ArrayList<Thread> threadsForSubSum = new ArrayList<Thread>();

		for(SumMultipleThreads runner:sumRunners) {
			Thread thread = new Thread(runner);
			thread.start();
			threadsForSubSum.add(thread);
		}

		long grandTotal = 0;
		for(SumMultipleThreads runner:sumRunners) {
			grandTotal += runner.totalSum;
		}

		System.out.println("Grand Total = " + grandTotal);
	}

	@Override
	public void run() {
		totalSum = getTotalSumFromTo();
		System.out.println("Computed from " + from + " to " + to);
	}

	public SumMultipleThreads(long from, long to) {
		this.from = from;
		this.to= to;
	}

	public long getTotalSumFromTo() {
		long total = 0;
		for(long i=from;i<=to;i++)
			total+=i;

		return total;
	}
}
