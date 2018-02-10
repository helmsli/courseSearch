package com.company.elasticsearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;

import com.company.elasticsearch.controller.rest.ControllerUtils;
import com.company.elasticsearch.domain.CourseSearch;
import com.company.elasticsearch.repositories.CourseSearchRepository;
import com.company.userOrderPlatform.domain.QueryPageRequest;
import com.xinwei.nnl.common.domain.ProcessResult;
@Service("searchEByDefaultService")
public class SearchEByDefaultServiceImpl implements SearchEByDefaultService,InitializingBean  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SearchEByDefaultServiceImpl.class);
	 private String columnFiled[]= {"searchKeys","category","title","courseInfo","teacherName"};
	 @Autowired  
	 private ElasticsearchTemplate elasticsearchTemplate=null; 
	 
	@Autowired
	private CourseSearchRepository courseEByDefaultRepository;

	@Override
	public String saveCourse(CourseSearch course) {
		// TODO Auto-generated method stub
		CourseSearch ret = courseEByDefaultRepository.save(course);
		
		return ret.getCourseId();
	}

	
	protected ProcessResult searchCourseByArray(String searchContentArray[], QueryPageRequest queryPageRequest) {
		  
	//	final String filterSpecialCharsQueryString = null;
		
		int pageNum = queryPageRequest.getPageNum();
		if(pageNum>=1)
		{
			pageNum=pageNum-1;
		}
		//
		BoolQueryBuilder boolQueryBuilder =QueryBuilders.boolQuery();
		QueryBuilder queryBuilder= boolQueryBuilder;
        for(int i=0;i<searchContentArray.length;i++)
        {
        	//boolQueryBuilder.should(QueryBuilders.multiMatchQuery(searchContentArray[i], columnFiled));
        	for(int j=0;j<columnFiled.length;j++)
        	{
        		boolQueryBuilder.should(QueryBuilders.wildcardQuery(columnFiled[j], "*" + searchContentArray[i].trim() + "*"));	
        	}
        	
        }
		if(searchContentArray.length==0)
		{
			queryBuilder = new MatchAllQueryBuilder();
		}
		//
		Pageable pageable = PageRequest.of(pageNum, queryPageRequest.getPageSize());
		FilterFunctionBuilder[] lists = new FilterFunctionBuilder[1];
				
		lists[0]=new FilterFunctionBuilder(queryBuilder,
                ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		
		/*  
		FilterFunctionBuilder[] lists = new FilterFunctionBuilder[5*searchContentArray.length];
		
		  
		for(int i=0;i<searchContentArray.length;i++)
		  {
			 String searchContent = searchContentArray[i];
			 
			
			 
		  lists[5*i] = new FilterFunctionBuilder(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("searchKeys", searchContent)),
                  ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		  lists[5*i+1] = new FilterFunctionBuilder(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("category", searchContent)),
          		ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		  lists[5*i+2] = new FilterFunctionBuilder(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", searchContent)),
	        		ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		  lists[5*i+3] = new FilterFunctionBuilder(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("courseInfo", searchContent)),
				  ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		  lists[5*i+4] = new FilterFunctionBuilder(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("teacherName", searchContent)),
				  ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"));
		  }
		  */
		  // Function Score Query
		
		
        //.mustNot(QueryBuilders.termQuery("message", "nihao"))
        //.should(QueryBuilders.termQuery("gender", "male"));
		  FunctionScoreQueryBuilder functionScoreQueryBuilder = new FunctionScoreQueryBuilder(queryBuilder,lists);
		  /*
	        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
	                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("searchKeys", searchContent)),
	                    ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"))
	                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("category", searchContent)),
	                		ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"))
	        .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title", searchContent)),
	        		ScoreFunctionBuilders.fieldValueFactorFunction("totalRank"))
	        .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("courseInfo", searchContent)),
                    ScoreFunctionBuilders.weightFactorFunction(100))
	        .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("teacherName", searchContent)),
                    ScoreFunctionBuilders.weightFactorFunction(100));
*/
	        // 创建搜索 DSL 查询
	        SearchQuery searchQuery = new NativeSearchQueryBuilder()
	                .withPageable(pageable)
	                .withQuery(functionScoreQueryBuilder).build();

	        //LOGGER.info("\n searchCity(): searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());

	        Page<CourseSearch> searchPageResults = courseEByDefaultRepository.search(searchQuery);
	        ProcessResult result = ControllerUtils.getSuccessResponse(null);
	        result.setResponseInfo(searchPageResults);
	        //return searchPageResults.getContent();
	        return result;
	}
 
	
	@Override
	public ProcessResult searchCourse(String searchContent, QueryPageRequest queryPageRequest) {
		String searchContentArray[] = StringUtils.split(searchContent.trim()," ");
		return this.searchCourseByArray(searchContentArray, queryPageRequest);
	}

	@Override
	public CourseSearch findOne(String id) {
		// TODO Auto-generated method stub
	
		return this.courseEByDefaultRepository.findById(id).get();
		
		
	}
	

	@Override
	public ProcessResult updateTotalRank(String courseId, int totalRank) {
		// TODO Auto-generated method stub
		IndexRequest indexRequest = new IndexRequest();  
	    indexRequest.source("totalRank","150");  
	    
	    UpdateQuery updateQuery = new UpdateQueryBuilder().withId(courseId)  
	            .withClass(CourseSearch.class).withIndexRequest(indexRequest).build();  
	    // when  
	    UpdateResponse updateResponse =  elasticsearchTemplate.update(updateQuery);
	    ProcessResult processResult = ControllerUtils.getSuccessResponse(null);
	    processResult.setResponseInfo(updateResponse);
	    return processResult;
	}

	

	@Override
	public ProcessResult updateParameters(String courseId, Map<String, String> updateMaps) {
		// TODO Auto-generated method stub
		IndexRequest indexRequest = new IndexRequest();  
	    int size = updateMaps.size();
	    Object[] sourceObject = new Object[size*2];
	    int sourceIndex = 0;
	    for (String key : updateMaps.keySet())
	    {
	    	sourceObject[2*sourceIndex] = key;
	    	sourceObject[2*sourceIndex+1] = updateMaps.get(key);
	    	sourceIndex++;
	    }
		indexRequest.source(sourceObject);  
	    
	    UpdateQuery updateQuery = new UpdateQueryBuilder().withId(courseId)  
	            .withClass(CourseSearch.class).withIndexRequest(indexRequest).build();  
	    // when  
	    UpdateResponse updateResponse =  elasticsearchTemplate.update(updateQuery);
	    ProcessResult processResult = ControllerUtils.getErrorResponse(-1, updateResponse.getResult().toString());
	    if(updateResponse.getResult()==Result.UPDATED)
	    {
	    	processResult = ControllerUtils.getSuccessResponse(null);
	    }
	    processResult.setResponseInfo(updateResponse);
	    
	    return processResult;

	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
