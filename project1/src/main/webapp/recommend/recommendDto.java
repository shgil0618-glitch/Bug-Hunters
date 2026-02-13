package dto;

import java.util.Date;

public class RecommendDTO {
    private int recId;          // 추천 고유번호
    private String userId;      // 사용자 ID
    private int foodId;         // 음식 ID
    private String type;        // 추천 유형
    private String feedback;    // 피드백
    private Date createdAt;     // 생성일

    //  기본 생성자
    public RecommendDTO() {}

    //  전체 필드 생성자
    public RecommendDTO(int recId, String userId, int foodId, String type, String feedback, Date createdAt) {
        this.recId = recId;
        this.userId = userId;
        this.foodId = foodId;
        this.type = type;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }

    //  Getter & Setter
    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    //  toString() 오버라이드
    @Override
    public String toString() {
        return "RecommendDTO{" +
                "recId=" + recId +
                ", userId='" + userId + '\'' +
                ", foodId=" + foodId +
                ", type='" + type + '\'' +
                ", feedback='" + feedback + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
