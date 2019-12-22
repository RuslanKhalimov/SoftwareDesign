package application;

public class ApplicationExample {
    public void a() throws InterruptedException {
        b();
        Thread.sleep(100);
    }

    public void b() throws InterruptedException {
        c();
        Thread.sleep(200);
    }

    public void c() throws InterruptedException {
        Thread.sleep(300);
    }

}
