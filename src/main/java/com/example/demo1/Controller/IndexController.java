package com.example.demo1.Controller;

import com.example.demo1.domain.Message;
import com.example.demo1.domain.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {

    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public ModelAndView indexView(){
        return new ModelAndView("index");}

    @RequestMapping(value = "index",method = RequestMethod.POST)
    public ModelAndView indexView(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @RequestParam("question") String question,
                                  @RequestParam("message") String message){
        Message message1 = new Message(name,email,question,message);
        messageRepository.insert(message1);
        System.out.println(message1.toString());


        return new ModelAndView("index");}

}
