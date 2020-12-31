package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {
  private final Logger logger = Logger.getLogger(this.getClass());

  public String provideId(Book book) {
    return this.hashCode() + "_" + book.hashCode();
  }

  private void initIdProvider() {
    logger.info("PROVIDER INIT (" + this.getClass().getName() + ")");
  }

  private void destroyIdProvider() {
    logger.info("PROVIDER DESTROY (" + this.getClass().getName() + ")");
  }

  private void defaultInit() {
    logger.info("DEFAULT INIT (" + this.getClass().getName() + ")");
  }

  private void defaultDestroy() {
    logger.info("DEFAULT DESTROY (" + this.getClass().getName() + ")");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    logger.info("AFTER PROPERTIES SET");
  }

  @Override
  public void destroy() throws Exception {
    logger.info("DISPOSE ID PROVIDER");
  }

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    logger.info("POST PROCESS BEFORE INIT of bean \"" + beanName + "\"");
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    logger.info("POST PROCESS AFTER INIT of bean \"" + beanName + "\"");
    return bean;
  }

  @PostConstruct
  public void postConstructIdProvider() {
    logger.info("POST CONSTRUCT");
  }

  @PreDestroy
  public void preDestroyIdProvider(){
    logger.info("PRE DESTROY");
  }
}
