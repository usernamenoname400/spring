package org.example;

import org.apache.log4j.Logger;
import org.example.app.config.AppContextConfig;
import org.example.web.config.WebContextConfig;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {
  Logger logger = Logger.getLogger(this.getClass());

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    logger.info("load app-config");
    //XmlWebApplicationContext appContext = new XmlWebApplicationContext();
    //appContext.setConfigLocation("classpath:app-config.xml");
    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(AppContextConfig.class);
    servletContext.addListener(new ContextLoaderListener(appContext));

    logger.info("load web-config");
    //XmlWebApplicationContext webContext = new XmlWebApplicationContext();
    //webContext.setConfigLocation("classpath:web-config.xml");
    AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
    webContext.register(WebContextConfig.class);

    DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
    logger.info("dispatcher is ready");

    ServletRegistration.Dynamic servlet = servletContext.addServlet("h2-console", new WebServlet());
    servlet.setLoadOnStartup(2);
    servlet.addMapping("/console/*");
    logger.info("db console is ready");
  }
}
