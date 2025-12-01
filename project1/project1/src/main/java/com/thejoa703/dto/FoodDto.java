package com.company.jeongmin.Dao_Dto;

import java.util.Date;

public class FoodDto {
	private int foodId;
    private String name;
    private int categoryId;
    private String categoryName;
    private double kcal;
    private double protein;
    private double carb;
    private double fat;
    private String recipe;
    private String imageUrl;
    private Date regDate;
    
	public int getFoodId() {return foodId;}
	public void setFoodId(int foodId) {this.foodId = foodId;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getCategoryId() {return categoryId;}
	public void setCategoryId(int categoryId) {this.categoryId = categoryId;}
	public String getCategoryName() {return categoryName;}
	public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
	public double getKcal() {return kcal;}
	public void setKcal(double kcal) {this.kcal = kcal;}
	public double getProtein() {return protein;}
	public void setProtein(double protein) {this.protein = protein;}
	public double getCarb() {return carb;}
	public void setCarb(double carb) {this.carb = carb;}
	public double getFat() {return fat;}
	public void setFat(double fat) {this.fat = fat;}
	public String getRecipe() {return recipe;}
	public void setRecipe(String recipe) {this.recipe = recipe;}
	public String getImageUrl() {return imageUrl;}
	public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
	public Date getRegDate() {return regDate;}
	public void setRegDate(Date regDate) {this.regDate = regDate;}
	
	public FoodDto(int foodId, String name, int categoryId, String categoryName, double kcal, double protein, double carb,
			double fat, String recipe, String imageUrl, Date regDate) {
		super(); this.foodId = foodId; this.name = name; this.categoryId = categoryId; this.categoryName = categoryName; this.kcal = kcal;
		this.protein = protein; this.carb = carb; this.fat = fat; this.recipe = recipe; this.imageUrl = imageUrl; this.regDate = regDate;}
	
	public FoodDto() {super();}
	
 
	public FoodDto(int foodId, String name, int categoryId, double kcal, double protein, double carb, double fat,
			String recipe, String imageUrl, Date regDate) {
		super();
		this.foodId = foodId;
		this.name = name;
		this.categoryId = categoryId;
		this.kcal = kcal;
		this.protein = protein;
		this.carb = carb;
		this.fat = fat;
		this.recipe = recipe;
		this.imageUrl = imageUrl;
		this.regDate = regDate;
	}
	@Override public String toString() {
		return "Model [foodId=" + foodId + ", name=" + name + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", kcal=" + kcal + ", protein=" + protein + ", carb=" + carb + ", fat=" + fat
				+ ", recipe=" + recipe + ", imageUrl=" + imageUrl + ", regDate=" + regDate + ", getFoodId()="
				+ getFoodId() + ", getName()=" + getName() + ", getCategoryId()=" + getCategoryId()
				+ ", getCategoryName()=" + getCategoryName() + ", getKcal()=" + getKcal() + ", getProtein()="
				+ getProtein() + ", getCarb()=" + getCarb() + ", getFat()=" + getFat() + ", getRecipe()=" + getRecipe()
				+ ", getImageUrl()=" + getImageUrl() + ", getRegDate()=" + getRegDate() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

		
	}
    
