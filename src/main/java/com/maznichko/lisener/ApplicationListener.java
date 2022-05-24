package com.maznichko.lisener;

import com.maznichko.services.common.GetMasters;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
public class ApplicationListener implements ServletContextListener{

    private static final Logger log = Logger.getLogger(GetMasters.class);

    public ApplicationListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Application was start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("application has completed");
    }

}
