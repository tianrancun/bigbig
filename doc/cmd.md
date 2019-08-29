netstat -ano|findstr 8080
  TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       9920
  TCP    172.19.243.184:57876   10.225.228.13:8080     ESTABLISHED     14100
  TCP    172.19.243.184:59981   10.225.228.13:8080     CLOSE_WAIT      7768
  TCP    172.19.243.184:59982   10.225.228.13:8080     CLOSE_WAIT      7768
  TCP    172.19.243.184:59992   10.225.228.13:8080     CLOSE_WAIT      17308
  TCP    172.19.243.184:60063   10.10.228.15:8080      ESTABLISHED     10700
  TCP    172.19.243.184:60066   10.10.228.15:8080      TIME_WAIT       0
  TCP    172.19.243.184:60100   10.10.228.15:8080      ESTABLISHED     3632
  TCP    [::]:8080              [::]:0                 LISTENING       9920

C:\Users>tasklist|findstr 9920
java.exe                      9920 Console                    1    255,792 K

C:\Users>taskkill /f /t /im 9920
SUCCESS: The process with PID 10076 (child process of PID 9920) has been terminated.
SUCCESS: The process with PID 9920 (child process of PID 15660) has been terminated.

/S system 指定要连接到的远程系统。
/U [domain\]user 指定应该在哪个用户上下文执行这个命令。
/P [password] 为提供的用户上下文指定密码。如果忽略，提示输入。
/F 指定要强行终止的进程。
/FI filter 指定筛选进或筛选出查询的的任务。
/PID process id 指定要终止的进程的PID。
/IM image name 指定要终止的进程的映像名称。通配符 '*'可用来指定所有映像名。
/T Tree kill: 终止指定的进程和任何由此启动的子进程。
/? 显示帮助/用法。



2.volatile规则 ：volatile变量的写先于读发生，这保证了volatile变量的可见性
3.锁规则 解锁（unlock）必然发生于加锁之前
4.传递性 A先于B,B先于C,那么A必然先于C
5 线程start（）方法先于它的每一个动作
6.线程的所有操作先于线程的终结（Thread.join()）
7.线程的中断（interrupt）先于被中断的线程代码
8.对象的构造函数的执行、结束先于finalize()方法