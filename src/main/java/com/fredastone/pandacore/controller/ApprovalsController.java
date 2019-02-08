package com.fredastone.pandacore.controller;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.Approvals;

@RestController
@RequestMapping(path = "v1/approvals")
public class ApprovalsController {

	@RequestMapping(path = "add", method = RequestMethod.POST)
	public ResponseEntity<?> addApproval(@Valid @RequestBody Approvals agent) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "get", method = RequestMethod.GET)
	public ResponseEntity<?> getAllApprovals(@Valid Approvals agent) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getApprovalById(@Valid @PathParam("id") String id) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "get/type/{type}", method = RequestMethod.GET)
	public ResponseEntity<?> getApprovalByType(@Valid @PathParam("type") String type) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "get/{status}", method = RequestMethod.GET)
	public ResponseEntity<?> getyStatus() {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}
	
	@RequestMapping(path = "get/types", method = RequestMethod.GET)
	public ResponseEntity<?> getAllApprovalTypes() {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "updatee/{id}/{status}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateApprovalStatus(@Valid @PathParam("id") String id,
			@PathParam("status") String status) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

}
