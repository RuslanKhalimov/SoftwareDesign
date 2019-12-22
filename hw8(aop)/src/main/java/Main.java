import application.ApplicationExample;
import profiler.Profiler;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ApplicationExample application = new ApplicationExample();

        application.a();
        application.b();
        application.c();

        Profiler.printStatistics();
    }

}
