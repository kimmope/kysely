package com.eduix.spring.demo;

//import java.sql.Date;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Timer;
//import com.eduix.spring.demo.domain.YearlyStatisticsUpdater;
//
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//


@SpringBootApplication
public class OmaProjektiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmaProjektiRestApplication.class, args);
		
//		// UPDATING THE YEARLY STATISTICS TO provinces-TABLE
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//	    Date date = dateFormatter.parse(year+"-12-31");
//		Timer timer = new Timer();
//
//		timer.schedule(new YearlyStatisticsUpdater(),date);		
	}
}
