package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.models.AgentModel;
import com.fredastone.pandacore.service.AgentService;


@RestController
@RequestMapping(path="v1/agentmeta")
public class AgentsController {
	
	private AgentService agentService;
	
	@Autowired
	 public AgentsController(AgentService agentService) {
		this.agentService = agentService;
	}

    @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addAgentMeta(@Valid @RequestBody AgentMeta agent) {    	
        return ResponseEntity.ok(agentService.addAgentMeta(agent));
    }
    
    @RequestMapping(path="get",params = { "active", "direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getAllAgentsByActive(@RequestParam("isactive")String isactive,
    		@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
    	
    	
    	Pageable sortedByCreatedon = PageRequest.of(page, size, Sort.by("createdon").descending());
    	
    	Page<AgentMeta> meta;
    	switch(isactive) {
    		case "ACTIVE":
    			meta = agentService.getAllAgentsByActive(sortedByCreatedon, true);
    			
    			break;
    		case "NOT_ACTIVE":
    			meta = agentService.getAllAgentsByActive(sortedByCreatedon, false);
    			System.out.println("check");
    			break;
    		default:
    			meta = agentService.findAllAgents(sortedByCreatedon);
    	}
    	
    	if(meta == null) {
			return ResponseEntity.noContent().build();			
		}
    	
        return ResponseEntity.ok(meta);
    }
    
    @RequestMapping(path="get/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentById(@Valid @PathVariable("id")String  id) {
        return ResponseEntity.ok(agentService.getAgentByUserId(id));
    }
    
    @RequestMapping(path="get/mobile/{mobileNumber}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentByMobileNumber(@Valid @PathParam("mobileNumber")String  mobileNumber) {
    	return ResponseEntity.ok(agentService.getAgentByPhoneNumber(mobileNumber));
    }
    
    @RequestMapping(path="get/email/{email}",method = RequestMethod.GET)
    public ResponseEntity<?> getAgentByEmail(@Valid @PathParam("email")String  email) {
        return ResponseEntity.ok(agentService.getAgentByEmail(email));
    }
    
    @RequestMapping(path="update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateAgentDetails(@PathParam("id") String id,@RequestBody AgentModel agent) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    /*
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
    */
    //--- This is going to be deprecated
    
    @PostMapping(value = "/uploads/{id}")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, @PathVariable("id") String id,
			@RequestParam("uploadType") AgentUploadType uploadType) {

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
	
	 @PostMapping(value = "/replace/{filename}")
	 public ResponseEntity<?> replaceFile(@RequestParam("file") MultipartFile file, @PathVariable("filename") String filename){
		 agentService.replaceFile(filename, file);
		 return ResponseEntity.ok().build();
	 }
    
    //--- This is going to be deprecated
   /* 
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
	*/
}

