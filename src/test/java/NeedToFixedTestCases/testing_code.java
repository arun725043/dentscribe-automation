package NeedToFixedTestCases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class testing_code 
{
	public static void main(String[] args) throws ParseException {
	     DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
	     String formattedDate = df.format(new Date());
	     
	     System.out.println("Formated Date:" + formattedDate);
	     
	     String[] seperateDate = formattedDate.split("-");
	     System.out.println(seperateDate[0]);
	     System.out.println(seperateDate[1]);
	     System.out.println(seperateDate[2]);


	}
	
//	public static void main(String args[]) 
//	{	      
////	      String monthYear = getCurrentMonth() + " " + String.valueOf(getCurrentYear());
////	      System.out.println("Current Month and Year is - " + monthYear.compareToIgnoreCase(monthYear));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		String formatted = df.format(new Date());
//		System.out.println(formatted);
//	      
//	   // Get an instance of LocalTime
//	        // from date
//        LocalDate currentDate = LocalDate.parse(formatted);
// 
//        // Get day from date
//        int day = currentDate.getDayOfMonth();
// 
//        // Get month from date
//        Month month = currentDate.getMonth();
// 
//        // Get year from date
//        int year = currentDate.getYear();
// 
//        // Print the day, month, and year
//        System.out.println("Day: " + day);
//        System.out.println("Month: " + month);
//        System.out.println("Year: " + year);
//	        
//	}
	
	public static Month getCurrentMonth()
	{
		LocalDate currentdate = LocalDate.now();
		//Getting the current month
	    Month currentMonth = currentdate.getMonth();
		return currentMonth;
		
	}
	
	public static int getCurrentYear()
	{
		LocalDate currentdate = LocalDate.now();
		//getting the current year
	    int currentYear = currentdate.getYear();
		return currentYear;
		
	}

}
