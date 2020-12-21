package com.le.aqs;

import java.util.concurrent.TimeUnit;

/**
 * Synchronized修饰的对象的4种方式
 *
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        /** case1：无Synchronized，乱序输出 */
        //        NoneSyncDemo noneSyncDemo = new NoneSyncDemo();
        //        Thread thread1 = new Thread(noneSyncDemo);
        //        Thread thread2 = new Thread(noneSyncDemo);

        /** case2：synchronized修饰代码块, 对象锁 */
        // 加锁有效
        //        SyncBlockDemo syncBlockDemo = new SyncBlockDemo();
        //        Thread thread1 = new Thread(syncBlockDemo);
        //        Thread thread2 = new Thread(syncBlockDemo);

        // 加锁无效
        //        SyncBlockDemo syncBlockDemo1 = new SyncBlockDemo();
        //        SyncBlockDemo syncBlockDemo2 = new SyncBlockDemo();
        //        Thread thread1 = new Thread(syncBlockDemo1);
        //        Thread thread2 = new Thread(syncBlockDemo2);

        /** case3：synchronized修饰普通方法，对象锁 */
        // 加锁有效
        //        SyncMethodDemo syncMethodDemo = new SyncMethodDemo();
        //        Thread thread1 = new Thread(syncMethodDemo);
        //        Thread thread2 = new Thread(syncMethodDemo);

        // 加锁无效
        //                SyncMethodDemo syncMethodDemo1 = new SyncMethodDemo();
        //                SyncMethodDemo syncMethodDemo2 = new SyncMethodDemo();
        //                Thread thread1 = new Thread(syncMethodDemo1);
        //                Thread thread2 = new Thread(syncMethodDemo2);

        /** case4：synchronized修饰静态方法，类锁 */
        // 加锁有效
        //        SyncStaticMethodDemo syncStaticMethodDemo = new SyncStaticMethodDemo();
        //        Thread thread1 = new Thread(syncStaticMethodDemo);
        //        Thread thread2 = new Thread(syncStaticMethodDemo);

        // 加锁有效
//        SyncStaticMethodDemo syncStaticMethodDemo1 = new SyncStaticMethodDemo();
//        SyncStaticMethodDemo syncStaticMethodDemo2 = new SyncStaticMethodDemo();
//        Thread thread1 = new Thread(syncStaticMethodDemo1);
//        Thread thread2 = new Thread(syncStaticMethodDemo2);

        /** case5：synchronized修饰类，类锁 */
        // 加锁有效
        //        SyncClassDemo syncClassDemo = new SyncClassDemo();
        //        Thread thread1 = new Thread(syncClassDemo);
        //        Thread thread2 = new Thread(syncClassDemo);

        // 加锁有效
        SyncClassDemo syncClassDemo1 = new SyncClassDemo();
        SyncClassDemo syncClassDemo2 = new SyncClassDemo();
        Thread thread1 = new Thread(syncClassDemo1);
        Thread thread2 = new Thread(syncClassDemo2);

        thread1.start();
        thread2.start();
    }
}

/**
 * [case1：无Synchronized]
 */
class NoneSyncDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * [case2：synchronized修饰代码块]
 * 一个线程访问一个对象中的synchronized(this)同步代码块时，其他试图访问该对象的线程将被阻塞。
 **/
class SyncBlockDemo implements Runnable {
    @Override
    public void run() {
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * [case3：synchronized修饰方法]
 * 一个线程访问一个对象中的synchronized修饰的方法时，其他试图访问该对象的线程将被阻塞。
 **/
class SyncMethodDemo implements Runnable {
    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * [case4：synchronized修饰静态方法]
 * 修饰一个静态的方法，类锁，其作用的对象是这个类的所有对象；
 **/
class SyncStaticMethodDemo implements Runnable {
    @Override
    public void run() {
        method();
    }

    public synchronized static void method() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * [case5：synchronized修饰类]
 * 修饰一个类，类锁，其作用的范围是synchronized后面括号括起来的部分，作用的对象是这个类的所有对象；
 **/
class SyncClassDemo implements Runnable {
    @Override
    public void run() {
        synchronized(SyncClassDemo.class) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}