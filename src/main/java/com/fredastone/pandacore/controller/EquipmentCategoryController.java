package com.fredastone.pandacore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.EquipCategory;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.repository.EquipementCategoryRepository;
import com.fredastone.pandacore.util.ServiceUtils;

@RestController
@RequestMapping("v1/equipmentcategory")
public class EquipmentCategoryController {
	
	private EquipementCategoryRepository categoryRepo;
	
	@Autowired
	public EquipmentCategoryController(EquipementCategoryRepository categoryRepo) {
		// TODO Auto-generated constructor stub
		this.categoryRepo = categoryRepo;
	}
	
	@Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
   @RequestMapping(path="add",method = RequestMethod.POST)
    public ResponseEntity<?> addNewCategory(@RequestBody EquipCategory category) {
	
		Optional<EquipCategory> equipCategory = categoryRepo.findByName(category.getName());
		
		if(equipCategory.isPresent()) {
			throw new RuntimeException("EquipmentCategory of name "+category.getName()+" already exists");
		}
		
	   	category.setId(ServiceUtils.getUUID());
	   	categoryRepo.save(category);
	   	
	   	return  ResponseEntity.ok(category);
    }
   

   @Secured({"ROLE_MANAGER,ROLE_MARKETING,ROLE_FINANCE"})
   @RequestMapping(path="update",method = RequestMethod.PUT)
   public ResponseEntity<?> updateCategory(@RequestBody EquipCategory category) {
	   
	   if(category.getId().isEmpty()) {
		   throw new RuntimeException("Expected a category Id to work with");
	   }
	   Optional<EquipCategory> cat = categoryRepo.findById(category.getId());
	   	
	   if(!cat.isPresent())
		   throw new ItemNotFoundException(category.getId()); 
	   	
	   cat.get().setIsactive(category.getIsactive());
	   cat.get().setName(category.getName());
	   cat.get().setDescription(category.getDescription());
	  
	   categoryRepo.save(cat.get());
	   	
	   return  ResponseEntity.ok(cat.get());
   }

    @RequestMapping(path="get",method = RequestMethod.GET)
    public Iterable<EquipCategory> getAllEquipment() {
    	return categoryRepo.findAll();
    }

    
    @RequestMapping(path="get/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getEquipmentById(@PathVariable("id") String id) {
    	
    	Optional<EquipCategory> category = categoryRepo.findById(id);
    	
    	if(!category.isPresent()) {
    		throw new RuntimeException("Category of ID "+id+" does not exist");
    	}
        
    	return ResponseEntity.ok(categoryRepo.findById(id));
    }
    
}
