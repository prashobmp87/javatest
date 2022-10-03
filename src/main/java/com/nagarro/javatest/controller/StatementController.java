package com.nagarro.javatest.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;




@Controller
@Slf4j	
public class StatementController {

	    
	
	
	
	@GetMapping("/statement")
    public ModelAndView getStatement() {
	    log.info("B4 leaving StatementController.getStatement");
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
        model.setViewName("statement");
	    log.info("B4 leaving StatementController.getStatement1");
        return model;
    }
	
	
	@GetMapping("/user-statement")
    public ModelAndView getUserStatement() {
	    log.info("B4 leaving StatementController.getStatement");
		ModelAndView model = new ModelAndView();
        model.setViewName("user-statement");
	    log.info("B4 leaving StatementController.getStatement1");
        return model;
    }
	
	/**
     * This method is used to prepare model and view for Error case.
     *
     * @param exception
     * @return model
     */
    private static ModelAndView createErrorResponse(Exception e) {
        ModelAndView model = new ModelAndView();
        model.addObject("message", e.getMessage());
        model.addObject("exception", e);
        model.setViewName("custom_error");
        return model;
    }
	    
    @RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
    @RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("login");
	}
}
