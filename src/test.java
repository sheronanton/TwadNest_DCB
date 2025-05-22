import java.text.SimpleDateFormat;
import java.util.*;


public class test {
	public static void main(String[] args) {
	    List<List<String>> weekdates = getNumberOfWeeks(2013, Calendar.JULY);
	    for(List<String> weekDatesLoop:weekdates){
	        System.out.println("Start day: "+weekDatesLoop.get(0).toString());
	        System.out.println("End day: "+weekDatesLoop.get(1).toString());
	    }
	  }
	public static List<List<String>> getNumberOfWeeks(int year, int month) {
	        System.out.println("Month Id: "+month);
	        month = month-1;
	        System.out.println("Month Id: " + month);
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	        List<List<String>> weekdates = new ArrayList<List<String>>();

	        List<String> dates = new ArrayList<String>();
	        Calendar c = Calendar.getInstance();
	        c.set(Calendar.YEAR, year);
	        c.set(Calendar.MONTH, month);
	        c.set(Calendar.DAY_OF_MONTH, 1);
	        dates.add(format.format(c.getTime()));
	        //int numOfWeeksInMonth = 1;
	        while (c.get(Calendar.MONTH) == month) {
	          //System.out.println(c.get(Calendar.DAY_OF_WEEK) );

	          if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
	            dates.add(format.format(c.getTime()));
	            weekdates.add(dates);
	          }
	          else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
	            dates = new ArrayList<String>();
	            dates.add(format.format(c.getTime()));
	            //numOfWeeksInMonth++;
	          }
	          c.add(Calendar.DAY_OF_MONTH, 1);
	        }
	        if(dates.size() < 2){
	          c.add(Calendar.DAY_OF_MONTH, -1);
	          dates.add(format.format(c.getTime()));
	          weekdates.add(dates);
	        }
	        System.out.println(weekdates);
	        return weekdates;
	      }

}
