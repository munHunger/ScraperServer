package se.mulander.cosmos.folderscraper;

import se.mulander.cosmos.common.ssl.SSLManager;
import se.mulander.cosmos.folderscraper.util.Settings;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Marcus Münger on 2017-09-16.
 */
public class Startup implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent arg0)
    {
        try
        {
            SSLManager.init();
            Settings.init();
        } catch (Exception e)
        {
            System.err.println("Could not initialize SSL");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
    }
}