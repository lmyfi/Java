韩顺平Java,线程的学习

线程的创建方式：
方式一：Thread01.java,通过继承Thread，重写run方法，创建对象，使用ObjectName.start()方法启动线程
方式二：Thread02.java,实现Runnable接口，同样重写run方法，创建Runnable实现接口对象，将该对象传入到新建的线程中：new Thread(ObjectName).start()