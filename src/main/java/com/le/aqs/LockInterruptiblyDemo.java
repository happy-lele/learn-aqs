package com.le.aqs;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试中断锁&非中断锁
 *
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */

public class LockInterruptiblyDemo {

    private static Object object1 = new Object();
    private static Object object2 = new Object();

    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws Throwable {
        /** 可中断锁 */
//        Thread t1 = new Thread(new ReentrantLockThread(lock1, lock2));
//        Thread t2 = new Thread(new ReentrantLockThread(lock2, lock1));

        /** 不可中断锁 */
        Thread t1 = new Thread(new SynchronizedThread(object1, object2));
        Thread t2 = new Thread(new SynchronizedThread(object2, object1));

        t1.start();
        t2.start();

        //主线程睡眠1秒，避免线程t1直接响应run方法中的睡眠中断
        System.out.println("主线程开始沉睡第1秒！");
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("主线程开始沉睡第2秒！");
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("主线程开始沉睡第3秒！");
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("主线程线程" + t1.getName() + "，在t1上开始执行interrupt()");
        t1.interrupt();
    }

    /**
     * ReentrantLock的lockInterruptibly实现死锁
     */
    static class ReentrantLockThread implements Runnable {
        private ReentrantLock lock1;
        private ReentrantLock lock2;

        public ReentrantLockThread(ReentrantLock lock1, ReentrantLock lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                lock1.lockInterruptibly(); // 获得lock1的可中断锁
                TimeUnit.MILLISECONDS.sleep(100); // 等待lock1和lock2分别被两个线程获取。产生死锁现象
                lock2.lockInterruptibly(); // 获得lock2的可中断锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock1.unlock();
                lock2.unlock();
                System.out.println("线程" + Thread.currentThread().getName() + "，正常结束");
            }
        }
    }

    /**
     * Synchronized实现死锁
     */
    static class SynchronizedThread implements Runnable {
        private Object lock1;
        private Object lock2;

        public SynchronizedThread(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            try {
                synchronized(lock1) { // 获得lock1的不可中断锁
                    TimeUnit.MILLISECONDS.sleep(100); // 等待lock1和lock2分别被两个线程获取。产生死锁现象
                    synchronized(lock2) { // 获得lock2的不可中断锁
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程" + Thread.currentThread().getName() + "正常结束");
            }
        }
    }
}
