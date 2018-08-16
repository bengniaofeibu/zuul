package com.jiujiu.util;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Getter
@Setter
public class TestUtil implements Runnable{


    ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    static  int  count = 0;







    @Override
    public void run() {
    }


    public static void main(String[] args) {

        TestUtil testUtil = new TestUtil();

        Object object =new Object();

        final ReentrantLock lock = new ReentrantLock();

        Condition  wait = lock.newCondition();

        Condition  notify = lock.newCondition();

        Thread thread = new Thread() {

           @Override
           public void run() {
               lock.lock();
                   try {
                       count = 100;
                       System.out.println(Thread.currentThread().getName()+"等待");
                       wait.await();
                       System.out.println(Thread.currentThread().getName()+"再次运行");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }finally {
                       lock.unlock();
                   }

           }
       };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {

                    lock.lock();
                    System.out.println(Thread.currentThread().getName()+"运行");
                    System.out.println(Thread.currentThread().getName()+"唤醒");
                    wait.signal();
                }catch (Exception e){

                }finally {
                    lock.unlock();
                }

            }
        };

      thread.start();
        try {
            thread.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("在thread后执行的呢"+count);
        try {

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();


    }
}
