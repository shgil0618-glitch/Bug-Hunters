package com.thejoa703.dto;


import java.sql.Timestamp;

public class RecipeDto {
	private int recipeId;
	private String title;
	private String category;
	private String description;
	private int cookTime;
	private String difficulty;
	private int servings;
	private String rfile;
	private Timestamp createdAt;

	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public int getCookTime() { return cookTime; }
	public void setCookTime(int cookTime) { this.cookTime = cookTime; }

	public String getDifficulty() { return difficulty; }
	public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

	public int getServings() { return servings; }
	public void setServings(int servings) { this.servings = servings; }

	public String getRfile() { return rfile; }
	public void setRfile(String rfile) { this.rfile = rfile; }

	public Timestamp getCreatedAt() { return createdAt; }
	public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
