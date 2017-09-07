package com.example.demo1.Controller;

import com.example.demo1.domain.MessageRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    Gson gson = new Gson();

    @Autowired
    MessageRepository messageRepository;

    /**
     * 显示所有留言
     * @return
     */
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public String getAllMessage(){
        String allMessage = gson.toJson(messageRepository.queryAll());

//      return messageRepository.queryAll().get(0).toString();
        return allMessage;
    }
}
