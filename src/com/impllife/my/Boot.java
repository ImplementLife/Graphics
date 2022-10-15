package com.impllife.my;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Boot {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Boot.class);
        DrawService drawService = context.getBean(DrawService.class);
        drawService.start();
    }
}
