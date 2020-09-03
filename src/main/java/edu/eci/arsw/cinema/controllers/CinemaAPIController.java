/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.services.CinemaServices;
/**
 *
 * @author ceseg
 */
@RestController
@RequestMapping(value = "/cinema")
public class CinemaAPIController {
	
	@Autowired
    CinemaServices service = null;
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> manejadorGetRecursoXX(){
		Set<Cinema> cinemas = null;
	    try {
	        //obtener datos que se enviarán a través del API
	    	cinemas = service.getAllCinemas();
	    } catch (Exception ex) {
	        Logger.getLogger(CinemaAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error 404",HttpStatus.NOT_FOUND);
	    } 
        return new ResponseEntity<>(cinemas,HttpStatus.ACCEPTED);
	}
    
}
