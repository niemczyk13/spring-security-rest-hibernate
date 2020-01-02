package niemiec.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientControllerTest {
	ClientController clientController;
	MockMvc clientControllerMock;
	@Before
	public void start() {
		clientController = new ClientController();
		clientControllerMock = MockMvcBuilders.standaloneSetup(clientController).build();
	}

	@Test
	public void testReservation() throws Exception {
		clientControllerMock.perform(post("/reservation/new")
				.param("name", "Karolina")
				.param("surname", "Niemiec")
				.param("email", "karka.niemiec@gmail.com")
				.param("phoneNumber", "886618194")
				.param("tableId", "1")
				.param("numberOfPeople", "3")
				.param("date", "2020-12-22")
				.param("startHour", "19:00")
				.param("endHour", "20:00"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testReservationWithDateFromThePast() throws Exception {
		clientControllerMock.perform(post("/reservation/new")
				.param("name", "Karolina")
				.param("surname", "Niemiec")
				.param("email", "karka.niemiec@gmail.com")
				.param("phoneNumber", "886618194")
				.param("tableId", "1")
				.param("numberOfPeople", "3")
				.param("date", "2020-01-01")
				.param("startHour", "19:00")
				.param("endHour", "20:00"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testReservationWithBadEmail() throws Exception {
		clientControllerMock.perform(post("/reservation/new")
				.param("name", "Karolina")
				.param("surname", "Niemiec")
				.param("email", "karka.niemiecgmail.com")
				.param("phoneNumber", "886618194")
				.param("tableId", "1")
				.param("numberOfPeople", "3")
				.param("date", "2020-12-22")
				.param("startHour", "19:00")
				.param("endHour", "20:00"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testReservationWithBadPhoneNumber() throws Exception {
		clientControllerMock.perform(post("/reservation/new")
				.param("name", "Karolina")
				.param("surname", "Niemiec")
				.param("email", "karka.niemiec@gmail.com")
				.param("phoneNumber", "88661814")
				.param("tableId", "1")
				.param("numberOfPeople", "3")
				.param("date", "2020-12-22")
				.param("startHour", "19:00")
				.param("endHour", "20:00"))
				.andExpect(status().isBadRequest());
	}

}
