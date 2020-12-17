package com.le.aqs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @Author happy_le
 * @date 2020/12/14 下午1:25
 */
public class SemaphoreBoundedList {

    public static void main(String[] args) {
        //4、容器边界限制
        final BoundedList ba = new BoundedList(5);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                try {
                    ba.add("John");
                    ba.add("Martin");
                    ba.add("Adam");
                    ba.add("Prince");
                    ba.add("Tod");
                    System.out.println("Available Permits : " + ba.getSemaphore().availablePermits());
                    ba.add("Tony");
                    System.out.println("Final list: " + ba.getArrayList());
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Before removing elements: "+ ba.getArrayList());
                    Thread.sleep(5000);
                    ba.remove("Martin");
                    ba.remove("Adam");
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
}

class BoundedList<T> {
    private final Semaphore semaphore;
    private List arrayList;

    public BoundedList(int limit) {
        this.semaphore = new Semaphore(limit);
        this.arrayList = Collections.synchronizedList(new ArrayList<>());
    }

    public boolean add(T t) throws InterruptedException {
        boolean added = false;
        semaphore.acquire();
        try {
            added = arrayList.add(t);
            return added;
        } finally {
            if (!added) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T t) {
        boolean wasRemoved = arrayList.remove(t);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }

    public void remove(int index) {
        arrayList.remove(index);
        semaphore.release();
    }

    public List getArrayList() {
        return arrayList;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}