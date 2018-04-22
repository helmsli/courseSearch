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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
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
import com.company.elasticsearch.domain.SearchRequest;
import com.company.elasticsearch.repositories.CourseSearchRepository;
import com.company.userOrderPlatform.domain.QueryPageRequest;
import com.xinwei.nnl.common.domain.ProcessResult;
@Service("searchEByDefaultService")
public class SearchEByDefaultServiceImpl implements SearchEByDefaultService,InitializingBean  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SearchEByDefaultServiceImpl.class);
	 private String columnFiled[]= {"searchKeys","category","title","courseInfo","teacherName"};
	 private Logger logger = LoggerFactory.getLogger(getClass());
		
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

	
	protected ProcessResult searchCourseByArray(String searchContentArray[], SearchRequest queryPageRequest,String queryColumnFiled[]) {
		  
	//	final String filterSpecialCharsQueryString = null;
		
		int pageNum = queryPageRequest.getPageNum();
		if(pageNum>=1)
		{
			pageNum=pageNum-1;
		}
		//构造查询条件
		BoolQueryBuilder boolQueryBuilder =QueryBuilders.boolQuery();
		QueryBuilder queryBuilder= boolQueryBuilder;
		
        boolean isMatchQuery = false;
		
		if(searchContentArray.length>0)
		{
			BoolQueryBuilder contentQueryBuilder =QueryBuilders.boolQuery();
			for(int i=0;i<searchContentArray.length;i++)
	        {
	        	//boolQueryBuilder.should(QueryBuilders.multiMatchQuery(searchContentArray[i], columnFiled));
	        	for(int j=0;j<queryColumnFiled.length;j++)
	        	{
	        		logger.debug("query content:" + queryColumnFiled[j] + ":" + "*" + searchContentArray[i].trim() + "*");
	        		contentQueryBuilder.should(QueryBuilders.wildcardQuery(queryColumnFiled[j], "*" + searchContentArray[i].trim() + "*"));	
	        		isMatchQuery = true;
	        	}
	        		
	        	
	        }
			boolQueryBuilder.must(contentQueryBuilder);
		}
		//
		
		if(!StringUtils.isEmpty(queryPageRequest.getCategory()))
        {
			logger.debug("category query:" + queryPageRequest.getCategory());
    		boolQueryBuilder.must(QueryBuilders.wildcardQuery("category", "*" + queryPageRequest.getCategory() + "*"));	
    		isMatchQuery = true;
        }
		if(queryPageRequest.getIsFree()==queryPageRequest.PRICE_FREE)
		{
			logger.debug("query by free");
			boolQueryBuilder.must(QueryBuilders.rangeQuery("realPrice").gte(0).lte(0));
			isMatchQuery = true;
		}
		else if(queryPageRequest.getIsFree()==queryPageRequest.PRICE_NotFREE)
		{
			logger.debug("query by Nofree");
			boolQueryBuilder.must(QueryBuilders.rangeQuery("realPrice").gt(0));
			isMatchQuery = true;
		}
		
		if(queryPageRequest.getPriceStart()>=0&& queryPageRequest.getPriceStart()<=queryPageRequest.getPriceEnd())
        {
        	logger.debug("query by price:" + queryPageRequest.getPriceStart() + ":" + queryPageRequest.getPriceEnd());
        	boolQueryBuilder.must(QueryBuilders.rangeQuery("realPrice").gte(queryPageRequest.getPriceStart()).lte(queryPageRequest.getPriceEnd()));
        	isMatchQuery = true;
        } 
        if(!isMatchQuery)
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
	public ProcessResult searchCourse(String searchContent, SearchRequest queryPageRequest) {
		String searchContentArray[] = StringUtils.split(searchContent.trim()," ");
		return this.searchCourseByArray(searchContentArray, queryPageRequest,this.columnFiled);
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

	/**
	 * 将原有的properties + 偏移量 = new value
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws ProcessResultException
	 */
	public static String getNewValue(Object object,String propertyName,String offset) throws ProcessResultException
	{
		 BeanWrapper beanWrapper =PropertyAccessorFactory.forBeanPropertyAccess(object);
    	 Object objectValue = beanWrapper.getPropertyValue(propertyName);
    	if(objectValue == null)
    	{
    		
    		throw new ProcessResultException(ControllerUtils.getErrorResponse(-1, "parameter " + propertyName + " value is null"));
    	}
    	Integer intObject = (Integer)objectValue;
    	int newValue = intObject.intValue() + Integer.parseInt(offset);
    	return String.valueOf(newValue);
    	
	}
	

	@Override
	public ProcessResult plusParameters(String courseId, Map<String, String> updateMaps) {
		// TODO Auto-generated method stub
		       CourseSearch courseSearch =this.courseEByDefaultRepository.findById(courseId).get();
			   IndexRequest indexRequest = new IndexRequest();  
			    int size = updateMaps.size();
			    Object[] sourceObject = new Object[size*2];
			    int sourceIndex = 0;
			    for (String key : updateMaps.keySet())
			    {
			    	sourceObject[2*sourceIndex] = key;
			    	String newValue;
					try {
						newValue = getNewValue(courseSearch,key,updateMaps.get(key));
					} catch (ProcessResultException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return e.getProcessResult();
					}
			    	sourceObject[2*sourceIndex+1] = newValue;
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

}
