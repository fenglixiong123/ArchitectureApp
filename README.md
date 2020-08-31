# JavaMultiThread
### Java多线程教程笔记

[Jvm虚拟机知识点讲解](./jvm-learn/jvm.md)

---

### 线程名
Thread+递增的数字
如果不传入名字会调用nextThreadNum()的同步方法，对静态变量threadInitNumber++

### 线程组
如果构造线程对象时候，没有传入ThreadGroup,会默认获取父线程的ThreadGroup作为自己的，此时子线程和父线程会在同一个ThreadGroup中

### ThreadSize
构建线程的时候传入这个值，代表着该线程占用的stack大小<br>
如果没有指定这个值，则默认为0,0代表会忽略这个参数，该参数会被JNI函数去使用<br>
注意：该参数在一些平台有效，一些无效

### 守护线程：DaemonThread
将线程设置为守护线程，则会随着创建自己的线程的结束而结束<br>
t.setDaemon(true);<br>
注意：守护线程是随着创建自己的父线程一起结束<br>
**线程组设置为守护线程**：<br>
当线程组中最后一个线程结束时候，整个线程组销毁<br>

### 线程优先级
1-10 10为最大，普通优先级为5<br>
尽量优先最大优先级的线程执行，但是不保证<br>

### Thread Join
等待其他线程执行完<br>
join:当前线程需要等我这个线程结束再执行下面的任务

### ThreadInterrupt
线程中断<br/>
 -interrupted()<br/>
 -静态方法，获得线程中断标识后会将中断标识清除 <br/>
 -isInterrupted() <br/>
 -只会获得线程是否中断，不会清除中断标识 <br/>

  真正优雅的停止线程：先设置中断标志，在程序中检测中断标志，并主动停止任务

### Synchronized 
同步方法:使用的this锁<br/>
同步方法块:使用的对象锁<br/>
this锁:<br/>
对于对象锁（this），如果是同一个实例，就会按顺序访问，但是如果是不同实例，就可以同时访问。<br/>
class锁:<br/>
静态锁，锁的是类的字节码信息等同于synchronized (ThreadSynchronizedStatic.class)
只要采用类锁，就会拦截所有线程，只能让一个线程访问。<br/>

### Wait Notify

* wait：等待锁资源，需要被其他线程唤醒（或者自己设置超时时间）
* notify：唤醒一个等待这个锁的线程
* notifyAll：唤醒所有等待这个锁的线程

**sleep和wait的区别？**

1. sleep是Thread的方法，wait是Object的方法
2. sleep不会释放锁，wait会释放锁并加入对象等待队列
3. sleep不依赖锁，但是wait必须在synchronized里面
4. sleep不需要被唤醒，但是wait需要被其他线程唤醒













































































  
