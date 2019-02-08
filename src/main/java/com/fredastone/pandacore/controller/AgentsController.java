package com.fredastone.pandacore.controller;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.models.RestAgent;


@RestController
@RequestMapping(path="v1/agents")
public class AgentsController {

    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addAgent(@Valid @RequestBody RestAgent agent) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get/mobile/{mobileNumber}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentByMobileNumber(@Valid @PathParam("mobileNumber")String  mobileNumber) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get/id/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentById(@Valid @PathParam("mobileNumber")String  id) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get/email/{email}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentByEmail(@Valid @PathParam("mobileNumber")String  mobileNumber) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get",params = { "active", "direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllAgentsByActive(@RequestParam("active")boolean isEnabled,
    		@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="get/terminated",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllTerminatedAGents(@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateAgentDetails(@PathParam("id") String id,@RequestBody RestAgent agent) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="update/activate/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> enableAgent(@PathParam("id") String id) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="update/dactivate/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> disableAgent(@PathParam("id") String id) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="update/terminate/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> terminateAgentContract(@PathParam("id") String id) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
}
