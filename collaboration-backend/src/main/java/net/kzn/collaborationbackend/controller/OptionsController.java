package net.kzn.collaborationbackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OptionsController {

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handle() {
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
	
}
