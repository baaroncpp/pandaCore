package com.fredastone.pandacore.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.County;
import com.fredastone.pandacore.entity.District;
import com.fredastone.pandacore.entity.Parish;
import com.fredastone.pandacore.entity.Subcounty;
import com.fredastone.pandacore.entity.Village;
import com.fredastone.pandacore.repository.CountyRepository;
import com.fredastone.pandacore.repository.DistrictRepository;
import com.fredastone.pandacore.repository.ParishRepository;
import com.fredastone.pandacore.repository.RegionRepository;
import com.fredastone.pandacore.repository.SubCountyRepository;
import com.fredastone.pandacore.repository.VillageRepository;

@RestController
@RequestMapping("v1/region")
public class RegionController {

	private static final String APPROVE_ACTION = "approve";
	private static final String REJECT_ACTION = "reject";
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private CountyRepository countyRepository;
	
	@Autowired 
	private SubCountyRepository subCountyRepository;
	
	@Autowired 
	private ParishRepository parishRepository;

    @RequestMapping(path="district/add",params= {"region","name"},method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<District> addNewDistrict(
    		@Valid @NotNull
    		@RequestParam("region") int region, 
    		@Valid
    		@NotNull
    		@RequestParam("name") String name) {
    	
    	District d = new District();
    	d.setName(name);
    	d.setRegionid(region);
    	d.setReview(Boolean.TRUE);
    
    	
    	d = districtRepository.save(d);
    	
    	
        return ResponseEntity.ok(d);
    }
    
    @RequestMapping(path="county/add",params= {"district","name"},method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addNewCounty(
    		@Valid @NotNull
    		@RequestParam("district") int district, 
    		@Valid
    		@NotNull
    		@RequestParam("name") String name) {
    	
    	County d = new County();
    	d.setName(name);
    	d.setDistrictid(district);
 	
    	d = countyRepository.save(d);
    	
    	
        return ResponseEntity.ok(d);
    }
    
    
    @RequestMapping(path="subcounty/add",params= {"county","name"},method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addNewSubCounty(
    		@Valid @NotNull
    		@RequestParam("county") int county, 
    		@Valid
    		@NotNull
    		@RequestParam("name") String name) {
    	
    	Subcounty d = new Subcounty();
    	
    	d.setName(name);
    	d.setCountyid(county);
    	
    	d = subCountyRepository.save(d);
    	
        return ResponseEntity.ok(d);
    }
    
    @RequestMapping(path="parish/add",params= {"subcounty","name"},method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addNewParish(
    		@Valid @NotNull
    		@RequestParam("subcounty") int subcounty, 
    		@Valid
    		@NotNull
    		@RequestParam("name") String name) {
    
    	Parish p = new Parish();
    	p.setName(name);
    	p.setSubcountyid(subcounty);
    	
    	p = parishRepository.save(p);
	
        return ResponseEntity.ok(p);
    }
    
    
    @RequestMapping(path="village/add",params= {"parish","name"},method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> addNewVillage(
    		@Valid @NotNull
    		@RequestParam("parish") int parish, 
    		@Valid
    		@NotNull
    		@RequestParam("name") String name) {
   
    	
    	Village v = new Village();
    	v.setName(name);
    	v.setParishid(parish);

    	v = villageRepository.save(v);
    	 	
        return ResponseEntity.ok(v);
    }
    
    
    @RequestMapping(path="district/add/review",params= {"id","action"},method = RequestMethod.PUT)
    public ResponseEntity<?> addDistrictForReview(
    		@Valid @NotNull
    		 @RequestParam("id") int id,@Valid @NotNull @RequestParam("action") String action
    		) {
    	
    	Optional<District> d = districtRepository.findById(id);
    	if(!d.isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
    	
    	switch (action) {
		case RegionController.APPROVE_ACTION:
			d.get().setReview(Boolean.FALSE);
			break;
			
		case RegionController.REJECT_ACTION:
			//Not sure how to handle this yet

		default:
			break;
		}
    	final District k = districtRepository.save(d.get());
    	
        return ResponseEntity.accepted().body(k);
    }
    
	
    @RequestMapping(path="get",method = RequestMethod.GET)
    public ResponseEntity<?> getAllREgions() {
        return ResponseEntity.ok(regionRepository.findAll());
    }
    
    
}
