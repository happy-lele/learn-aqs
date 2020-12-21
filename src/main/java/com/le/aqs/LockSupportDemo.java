package com.le.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类
 * LockSupport是一个非常方便实用的线程阻塞工具，它可以在线程内任意位置让线程阻塞。
 * 和Object.wait()方法相比，它不需要先获得某个对象的锁，也不会抛出InterruptedException异常。
 *
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */
public class LockSupportDemo {

    public static void main(String[] args) throws Throwable {
        Thread thread1 = new Thread(new RunningDemo());
        Thread thread2 = new Thread(new RunningDemo());
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("主线程sleep2秒完毕 ");
        thread2.start();
        /** unpark方法，将thread1的一个许可变为［可用］状态*/
        LockSupport.unpark(thread1);
        System.out.println("LockSupport.unpark(thread1) 执行完毕 ");
        /** unpark方法，将thread2的一个许可变为［可用］状态*/
        LockSupport.unpark(thread2);
        System.out.println("LockSupport.unpark(thread2) 执行完毕 ");
    }
}

class RunningDemo implements Runnable {

    private final static Object object = new Object();

    @Override
    public void run() {
        synchronized(object) {
            System.out.println(Thread.currentThread().getName() + "开始运行！");
            /**
             * park()可以阻塞当前线程
             * 每一个线程有一个许可，该许可默认为[不可用]。
             * 如果该许可是[可用]状态，那么park()方法会立即返回，消费这个许可，将该许可消变更为[不可用]状态，流程代码可以继续执行。
             */
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "运行结束！");
        }
    }
}