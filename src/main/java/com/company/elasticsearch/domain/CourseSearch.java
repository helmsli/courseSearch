package com.company.elasticsearch.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.StringUtils;

@Document(indexName = "courseindex", type = "courseSearch", shards = 1, replicas = 0, refreshInterval = "-1")
public class CourseSearch  extends CourseSearchBase{
		
	
}
