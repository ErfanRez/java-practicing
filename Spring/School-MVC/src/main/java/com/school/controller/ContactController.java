package com.school.controller;

import com.school.model.Contact;
import com.school.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ContactController {
//    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(){
        return "contact";
    }

//    @PostMapping(value = "/saveMsg")
//    public ModelAndView saveMessage(@RequestParam(name = "name") String name, @RequestParam String mobileNum, @RequestParam String email, @RequestParam String subject, @RequestParam String message){
//        logger.info("Name" + name);
//        logger.info("Mobile Number" + mobileNum);
//        logger.info("Email Address" + email);
//        logger.info("Subject" + subject);
//        logger.info("Message" + message);
//
//        return new ModelAndView("redirect:/contact");
//    }

    @PostMapping(value = "/saveMsg")
    public ModelAndView saveMessage(Contact contact){
        contactService.saveMessageDetails(contact);
        return new ModelAndView("redirect:/contact");
    }
}
