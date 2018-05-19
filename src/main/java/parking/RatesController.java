package parking;

import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class RatesController {
	
	private ListOfRates input;

	@PostMapping("/setrates")
	public List<Rate> rates(@RequestBody ListOfRates input){
		
		this.input = input;
		
		return this.input.getRatesList();
	    
	}
	
	@GetMapping("/getrates")
	public List<Rate> rates(){
		
		return input.getRatesList();
	    
	}
	
	@RequestMapping("/getcharge")
    public String chargedate(@RequestParam(value="startDate") String startDateString,
    		@RequestParam(value="endDate") String endDateString) throws ParseException {
    	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DateFormat dfYmd = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dayOnly = new SimpleDateFormat("EEE");
		DateFormat hourAndMin = new SimpleDateFormat("HHmm");
		String noParking = "unavailable";
		
		Date startDate = df.parse(startDateString);
		Date endDate = df.parse(endDateString);
		
		String startYmd = dfYmd.format(startDate);
		String endYmd = dfYmd.format(endDate);
		
		if (!startYmd.equals(endYmd)) {
			String noOvernightParking = "Overnight parking is not allowed.";
			return noOvernightParking;
		}
		
		String startDay = dayOnly.format(startDate).toLowerCase();
		String endDay = dayOnly.format(endDate).toLowerCase();
		
		String startHour = hourAndMin.format(startDate);
		String endHour = hourAndMin.format(endDate);
		
		for (Rate rate : input.getRatesList()) {
			if (rate.getDays().contains(startDay)) {
				if (Integer.parseInt(rate.getTimes().substring(0, 4)) < Integer.parseInt(startHour) && Integer.parseInt(rate.getTimes().substring(5)) > Integer.parseInt(endHour)) {
					String price = String.valueOf(rate.getPrice());
					return price;
				}
			}
		}
		return noParking;
		
    }
	
}
