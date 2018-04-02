package com.company.elasticsearch.domain;

import com.company.userOrderPlatform.domain.QueryPageRequest;

public class SearchRequest extends QueryPageRequest {
	/**
	 * 用;分割多个分类，用空格在一个分类内主分类和子分类；
	 */
	/**
	 * 客户端应用类型  app/web
	 */
	private String appType;
	private String category;
	private String keyword;
	private String fitPeople;
	private String difficultyLevel;
	
	public static final int PRICE_NotFREE=0;
	public static final int PRICE_FREE=1;
	public static final int PRICE_All=2;
	
	private int isFree;
	/**
	 * 按照，分割
	 */
	private int priceStart=-1;
	
	private int priceEnd=-1;
	
	public SearchRequest()
	{
		this.setPageNum(1);
		this.setPageSize(100);
	}
	
    public int getIsFree() {
		return isFree;
	}
	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}
	/**
     * 0 --综合 1-好评 2 人气  3 价格
     */
	private int  orderBy;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getFitPeople() {
		return fitPeople;
	}
	public void setFitPeople(String fitPeople) {
		this.fitPeople = fitPeople;
	}
	public String getDifficultyLevel() {
		return difficultyLevel;
	}
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}
	
	public int getPriceStart() {
		return priceStart;
	}
	public void setPriceStart(int priceStart) {
		this.priceStart = priceStart;
	}
	public int getPriceEnd() {
		return priceEnd;
	}
	public void setPriceEnd(int priceEnd) {
		this.priceEnd = priceEnd;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
}
