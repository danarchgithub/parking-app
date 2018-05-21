package parking;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatesControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void setRateShouldReturnRate() throws Exception {
    	String testJson = "{\"rates\":[{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2100\",\"price\":1500},{\"days\":\"fri,sat,sun\",\"times\":\"0900-2100\",\"price\":2000},{\"days\":\"wed\",\"times\":\"0600-1800\",\"price\":1750},{\"days\":\"mon,wed,sat\",\"times\":\"0100-0500\",\"price\":1000},{\"days\":\"sun,tues\",\"times\":\"0100-0700\",\"price\":925}]}";
    	
    	this.mockMvc.perform(post("/setrates").contentType(MediaType.APPLICATION_JSON).content(testJson))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].days").value("mon,tues,thurs"))
		.andExpect(jsonPath("$.[1].days").value("fri,sat,sun"))
		.andExpect(jsonPath("$.[2].days").value("wed"))
		.andExpect(jsonPath("$.[3].days").value("mon,wed,sat"))
		.andExpect(jsonPath("$.[4].days").value("sun,tues"))
		.andExpect(jsonPath("$.[0].times").value("0900-2100"))
		.andExpect(jsonPath("$.[1].times").value("0900-2100"))
		.andExpect(jsonPath("$.[2].times").value("0600-1800"))
		.andExpect(jsonPath("$.[3].times").value("0100-0500"))
		.andExpect(jsonPath("$.[4].times").value("0100-0700"))
		.andExpect(jsonPath("$.[0].price").value("1500"))
		.andExpect(jsonPath("$.[1].price").value("2000"))
		.andExpect(jsonPath("$.[2].price").value("1750"))
		.andExpect(jsonPath("$.[3].price").value("1000"))
		.andExpect(jsonPath("$.[4].price").value("925"));
    	
    }
    
    @Test
    public void setRateandgetChargeShouldReturnCorrectRate() throws Exception {
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-15T10:00:00Z").param("endDate","2018-05-15T20:00:00Z"))
    		.andDo(print())
    		.andExpect(status().isOk())
    		.andExpect(content().string("1500"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-18T10:00:00Z").param("endDate","2018-05-18T20:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("2000"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-16T07:00:00Z").param("endDate","2018-05-16T17:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("1750"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-19T02:00:00Z").param("endDate","2018-05-19T04:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("1000"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-20T02:00:00Z").param("endDate","2018-05-20T06:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("925"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-20T02:00:00Z").param("endDate","2018-05-20T07:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("unavailable"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-20T01:00:00Z").param("endDate","2018-05-20T06:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("unavailable"));
    	
    	this.mockMvc.perform(get("/price").param("startDate","2018-05-20T00:00:00Z").param("endDate","2018-05-20T08:00:00Z"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("unavailable"));
    	
    }
    
}
