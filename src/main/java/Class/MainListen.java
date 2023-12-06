package Class;

import Main.sendMail;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MainListen implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            sendMail.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
