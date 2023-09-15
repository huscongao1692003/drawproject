package com.drawproject.dev.controller;

import com.drawproject.dev.model.Contact;
import com.drawproject.dev.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<String> saveMsg(@Valid @RequestBody Contact contact, Errors errors){
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }else {
            contactService.saveMessageDetails(contact);
            return new ResponseEntity<>("Save Contact Successful", HttpStatus.OK);
        }
    }
    @GetMapping("/displayMessages")
    public List<Contact> displayMessages(){
       List<Contact> contactMsgs= contactService.findMsgsWithOpenStatus();
       return contactMsgs;
    }
    @PostMapping("/closeMsg")
    public ResponseEntity<String> closeMsg(@RequestParam int id){
            contactService.updateMsgStatus(id);
            return new ResponseEntity<>("Close Contact Successful", HttpStatus.OK);
    }


}
