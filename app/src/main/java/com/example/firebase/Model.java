package com.example.firebase;

public class Model {
    String foodId,label,category,image,categoryLabel;

    public Model(String foodId, String label, String category, String image, String categoryLabel) {
        this.foodId = foodId;
        this.label = label;
        this.category = category;
        this.image = image;
        this.categoryLabel = categoryLabel;
    }

    public Model(String foodId, String label, String category, String image) {
        this.foodId = foodId;
        this.label = label;
        this.category = category;
        this.image = image;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }
}
