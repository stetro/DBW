package de.stetro.matin.dbw;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("classpath*:/META-INF/applicationContext.xml");


    }

    public static ApplicationContext getContext() {
        return context;
    }
}
