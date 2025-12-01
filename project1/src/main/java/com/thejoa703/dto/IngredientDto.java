package com.thejoa703.dto;

public class IngredientDto {
	private int ingredientId;
	private int recipeId;
	private String ingredientName;
	private double quantity;
	private String unit;
	private double kcal;
	private double carbs;
	private double protein;
	private double fat;
	private String allergens;

	public int getIngredientId() { return ingredientId; }
	public void setIngredientId(int ingredientId) { this.ingredientId = ingredientId; }

	public int getRecipeId() { return recipeId; }
	public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

	public String getIngredientName() { return ingredientName; }
	public void setIngredientName(String ingredientName) { this.ingredientName = ingredientName; }

	public double getQuantity() { return quantity; }
	public void setQuantity(double quantity) { this.quantity = quantity; }

	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }

	public double getKcal() { return kcal; }
	public void setKcal(double kcal) { this.kcal = kcal; }

	public double getCarbs() { return carbs; }
	public void setCarbs(double carbs) { this.carbs = carbs; }

	public double getProtein() { return protein; }
	public void setProtein(double protein) { this.protein = protein; }

	public double getFat() { return fat; }
	public void setFat(double fat) { this.fat = fat; }

	public String getAllergens() { return allergens; }
	public void setAllergens(String allergens) { this.allergens = allergens; }
}
