package com.fredastone.pandacore.controller;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fredastone.pandacore.constants.AgentUploadType;
import com.fredastone.pandacore.models.AgentModel;
import com.fredastone.pandacore.service.AgentService;


@RestController
@RequestMapping(path="v1/agent")
public class AgentsController {
	
	private AgentService agentService;
	
	@Autowired
	 public AgentsController(AgentService agentService) {
		// TODO Auto-generated constructor stub
		this.agentService = agentService;
	}

    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addAgent(@Valid @RequestBody AgentModel agent) {
    	
    	
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
    public ResponseEntity<?> updateAgentDetails(@PathParam("id") String id,@RequestBody AgentModel agent) {
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
    
    
    @PostMapping(value = "/uploads/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id,@RequestParam("uploadType") AgentUploadType uploadType) {

		agentService.uploadMetaInfo(file, redirectAttributes, id, uploadType);

		return ResponseEntity.ok().build();

	}

	@GetMapping("/media/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id,@RequestParam("uploadtype") AgentUploadType uploadType) {

		final Resource file = agentService.getUploadedMetaInfo(id,uploadType);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
