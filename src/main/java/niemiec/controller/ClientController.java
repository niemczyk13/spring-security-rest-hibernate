package niemiec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ClientController {
	@Autowired
	ObjectMapper mapper;
	
	@GetMapping("/")
	public String startPage() throws JsonProcessingException {
		return mapper.writeValueAsString("Hej");
	}
}
