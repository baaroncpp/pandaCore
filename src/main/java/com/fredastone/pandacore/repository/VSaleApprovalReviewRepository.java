package com.fredastone.pandacore.repository;

import java.util.List;

import com.fredastone.pandacore.entity.VSaleApprovalReview;

public interface VSaleApprovalReviewRepository {
	
	List<VSaleApprovalReview> getAllSaleApprovalReview(String agentid,String itemid);

}
