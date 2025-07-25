package net.dsa.ex.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.ex.entity.PerfumeEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerfumeDTO {
    private Integer no;
    private String name;
    private String gender;
    private Integer age;
    private String favoriteScent;
    private String favoriteBrand;
    private String usageFrequency;
    private String purchaseBudget;
    private String comments;
    private LocalDateTime completionTime;
    
    public PerfumeEntity toEntity() {
       PerfumeEntity perfume = new PerfumeEntity();
        perfume.setNo(this.no);
        perfume.setName(this.name);
        perfume.setGender(this.gender);
        perfume.setAge(this.age);
        perfume.setFavoriteScent(this.favoriteScent);
        perfume.setFavoriteBrand(this.favoriteBrand);
        perfume.setUsageFrequency(this.usageFrequency);
        perfume.setPurchaseBudget(this.purchaseBudget);
        perfume.setComments(this.comments);
        perfume.setCompletionTime(this.completionTime);
        return perfume;
    }

    
    public static PerfumeDTO fromEntity(PerfumeEntity perfume) {
        if (perfume == null) {
            return null;
        }
        PerfumeDTO dto = new PerfumeDTO();
        dto.setNo(perfume.getNo());
        dto.setName(perfume.getName());
        dto.setGender(perfume.getGender());
        dto.setAge(perfume.getAge());
        dto.setFavoriteScent(perfume.getFavoriteScent());
        dto.setFavoriteBrand(perfume.getFavoriteBrand());
        dto.setUsageFrequency(perfume.getUsageFrequency());
        dto.setPurchaseBudget(perfume.getPurchaseBudget());
        dto.setComments(perfume.getComments());
        dto.setCompletionTime(perfume.getCompletionTime());
        return dto;
    }
}
