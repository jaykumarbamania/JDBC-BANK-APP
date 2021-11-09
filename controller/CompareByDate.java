package bankApp.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import bankApp.model.Payment;


public class CompareByDate implements Comparator<Payment> {

	 public int compare(Payment p1, Payment p2) {
		 Date date1 = new Date();
		 Date date2= new Date();
		try {
			date1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(p1.getCreatedAt());
			date2 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(p2.getCreatedAt());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      long time1 = date1.getTime();
	      long time2 = date2.getTime();
	      return (time1<time2 ? -1 : (time1==time2 ? 0 : 1));
	  }

}