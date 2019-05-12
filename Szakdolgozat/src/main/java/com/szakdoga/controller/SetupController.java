package com.szakdoga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.szakdoga.services.implementations.DbPollService;
import com.szakdoga.services.interfaces.ITestDataCreator;

@RequestMapping("/setup")
@RestController()
public class SetupController {

	@Autowired
	private ITestDataCreator testDataCreator;
	@Autowired
	private DbPollService poll;
	
	private boolean once = true;
	
	@GetMapping("/poll")
	public void startPoll()
	{
		if(once)
		{
			poll.start(5);
			System.out.println("Poll has started");
			once=false;
		}
	}
	
	@GetMapping("testdata")
	public void setupDb()
	{
		testDataCreator.createAttributes();
	}	
}
