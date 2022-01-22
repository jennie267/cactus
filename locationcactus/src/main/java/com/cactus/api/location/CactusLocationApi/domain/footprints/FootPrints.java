package com.cactus.api.location.CactusLocationApi.domain.footprints;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
public class FootPrints {
    @Id
    @Column(name = "foot_prints_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long footPrintsId;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "seq")
    private Long seq;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    public FootPrints() {
		// TODO Auto-generated constructor stub
	}
    
    @Builder
    public FootPrints(LocalDateTime insertTime, Long seq, Long userId, Double latitude, Double longitude) {
        this.insertTime = insertTime;
        this.seq = seq;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    
}
