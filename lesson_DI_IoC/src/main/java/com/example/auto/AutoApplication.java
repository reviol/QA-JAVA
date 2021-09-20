package com.example.auto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoApplication.class, args);

// Strong and Week Dependency.......
//		Car carForFamily = new Car();
//		System.out.println(carForFamily.readyForSale());
//
//		Car carForYoung = new Car();
//		System.out.println(carForYoung.readyForSale());

// Dependency Injection for Constructor.......
//		Car carForFamily = new Car(new ClassicConfiguration());
//		System.out.println(carForFamily.readyForSale());
//
//		Car carForYoung = new Car(new SportConfiguration());
//		System.out.println(carForYoung.readyForSale());
//
//		Car carForGirl = new Car(new SuperSportConfiguration());
//		System.out.println(carForGirl.readyForSale());

// Spring Beans Injection.......
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		Car carForFamilyNew = context.getBean("classicCar", Car.class);
		Car carForYoungNew = context.getBean("sportCar", Car.class);
		Car carForGirl = context.getBean("superSportCar", Car.class);

		System.out.println(carForFamilyNew.readyForSale());
		System.out.println(carForYoungNew.readyForSale());
		System.out.println(carForGirl.readyForSale());
	}
}
