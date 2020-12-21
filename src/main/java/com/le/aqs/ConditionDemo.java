package com.le.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author happy_le
 * @date 2020/12/14 下午11:05
 */
public class ConditionDemo {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Throwable {
        new Thread(() -> {
            try {
                lock.lock();
                System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", "
                        + "子线程开始等待！");
                condition.await(); // 释放当前锁，进入等待中；其中，调用await，一定要先获得锁。
                System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", "
                        + "子线程继续执行！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程睡了第1秒钟！");
        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程睡了第2秒钟！");
        lock.lock(); // 调用signal之前，一定要先获得锁，否则会报IllegalMonitorStateException异常
        condition.signal();
        System.out.println(
                System.currentTimeMillis() / 1000 + " " + Thread.currentThread().getName() + ", 主线程调用了signal！");

        lock.unlock();
    }
}

