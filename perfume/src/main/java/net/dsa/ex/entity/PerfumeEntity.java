package net.dsa.ex.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "perfume")
public class PerfumeEntity {
        @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer no;

       @Column(length = 30, nullable = false)
       private String name;

       @Column(length = 10)
       private String gender;

       @Column(nullable = false)
       private Integer age;

       @Column(length = 50)
       private String favoriteScent;

       @Column(length = 50)
       private String favoriteBrand;

       @Column(length = 50)
       private String usageFrequency;

       @Column(length = 50)
       private String purchaseBudget;

       @Column(length = 200)
       private String comments;
       
       @CreatedDate
       @Column(name = "completion_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
       private LocalDateTime completionTime;     
}
