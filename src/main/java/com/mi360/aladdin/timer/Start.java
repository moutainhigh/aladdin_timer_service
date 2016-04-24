package com.mi360.aladdin.timer;

import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	private static Logger logger = Logger.getLogger("System.out");
	
	public static void main(String[] args) throws Exception{
		
		initialize();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext-timer.xml" , "classpath*:/applicationContext.xml" });
		context.start();
		System.out.println("定时器微服务启动");
		while(true){
			Thread.sleep(1000);
		}
	}
	
	private static void initialize() {
		System.setOut(new PrintStream(System.out) {
			@Override
			public void print(final String string) {
				logger.debug(string);
			}

			@Override
			public void print(boolean b) {
				logger.debug(Boolean.valueOf(b));
			}

			@Override
			public void print(char c) {
				logger.debug(Character.valueOf(c));
			}

			@Override
			public void print(int i) {
				logger.debug(String.valueOf(i));
			}

			@Override
			public void print(long l) {
				logger.debug(String.valueOf(l));
			}

			@Override
			public void print(float f) {
				logger.debug(String.valueOf(f));
			}

			@Override
			public void print(double d) {
				logger.debug(String.valueOf(d));
			}

			@Override
			public void print(char[] x) {
				logger.debug(x == null ? null : new String(x));
			}

			@Override
			public void print(Object obj) {
				logger.debug(obj);
			}
		});
		System.setErr(new PrintStream(System.out) {
			@Override
			public void print(final String string) {
				logger.error(string);
			}

			@Override
			public void print(boolean b) {
				logger.error(Boolean.valueOf(b));
			}

			@Override
			public void print(char c) {
				logger.error(Character.valueOf(c));
			}

			@Override
			public void print(int i) {
				logger.error(String.valueOf(i));
			}

			@Override
			public void print(long l) {
				logger.error(String.valueOf(l));
			}

			@Override
			public void print(float f) {
				logger.error(String.valueOf(f));
			}

			@Override
			public void print(double d) {
				logger.error(String.valueOf(d));
			}

			@Override
			public void print(char[] x) {
				logger.error(x == null ? null : new String(x));
			}

			@Override
			public void print(Object obj) {
				logger.error(obj);
			}
		});
	}
}
