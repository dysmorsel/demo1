package com.example.demo1;

import com.example.demo1.domain.Message;
import com.example.demo1.domain.MessageRepository;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

	@Autowired
	private MessageRepository messageRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void insert(){
		Message message1 = new Message("fl","13@12","2345","1212");
		messageRepository.insert(message1);

	}

	@Test
	public void query(){
		Gson gson = new Gson();
		String allMessage = gson.toJson(messageRepository.queryAll());
		System.out.println(allMessage);
		System.out.println(messageRepository.queryAll().toString());
	}

}
