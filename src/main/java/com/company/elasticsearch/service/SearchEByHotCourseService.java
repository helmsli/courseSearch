package com.company.elasticsearch.service;

import java.util.Map;

import com.company.elasticsearch.domain.HotCourseRecommend;
import com.company.userOrderPlatform.domain.QueryPageRequest;
import com.xinwei.nnl.common.domain.ProcessResult;

public interface SearchEByHotCourseService {
	String saveCourse(HotCourseRecommend course);
	/**
     * 根据关键词，function score query 权重分分页查询
     *
     * @param pageNumber
     * @param pageSize
     * @param searchContent
     * @return
     */
	ProcessResult searchCourse(String searchContent,QueryPageRequest queryPageRequest);

	
	HotCourseRecommend findOne(String courseId);
	
	ProcessResult updateTotalRank(String courseId,int totalRank);
	
	ProcessResult updateParameters(String courseId,Map<String,String> updateMaps);
	ProcessResult plusParameters(String courseId,Map<String,String> updateMaps);

}
