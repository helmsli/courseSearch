package com.company.elasticsearch.domain;

import com.company.userOrderPlatform.domain.QueryPageRequest;

public class SearchRequest extends QueryPageRequest {
	/**
	 * 用;分割多个分类，用空格在一个分类内主分类和子分类；
	 */
	private String category;
	private String keyword;
	private String fitPeople;
	private String difficultyLevel;
	/**
	 * 按照，分割
	 */
	private String priceRange;
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
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	
}
