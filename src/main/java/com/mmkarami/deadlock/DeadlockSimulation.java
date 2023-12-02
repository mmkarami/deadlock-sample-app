package com.mmkarami.deadlock;

public class DeadlockSimulation {

	private static String firstResource = null;

	private static final Object FIRST_RESOURCE_LOCK = new Object();

	private static String secondResource = null;

	private static final Object SECOND_RESOURCE_LOCK = new Object();

	public static void main(String[] args) {

		Thread firstThread = new Thread(new Runnable() {

			public void run() {
				try {
					getFirstResource();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		Thread secondThread = new Thread(new Runnable() {

			public void run() {
				try {
					getSecondResource();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		firstThread.start();
		secondThread.start();
	}

	private static String getFirstResource() throws InterruptedException {
		System.out.println("Waiting for first resource By: " + Thread.currentThread().getName());
		synchronized (FIRST_RESOURCE_LOCK) {
			System.out.println("First resource is locked By: " + Thread.currentThread().getName());
			if (firstResource == null)
				firstResource = "This resource is ok.";
			Thread.sleep(650);
			getSecondResource();
		}
		System.out.println("First resource is unlocked By: " + Thread.currentThread().getName());
		return firstResource;
	}

	private static String getSecondResource() throws InterruptedException {
		System.out.println("Waiting for second resource By: " + Thread.currentThread().getName());
		synchronized (SECOND_RESOURCE_LOCK) {
			System.out.println("Second resource is locked By: " + Thread.currentThread().getName());
			if (secondResource == null)
				secondResource = "This resource is ok.";
			Thread.sleep(750);
			getFirstResource();
		}
		System.out.println("Second resource is unlocked By: " + Thread.currentThread().getName());
		return secondResource;
	}
}