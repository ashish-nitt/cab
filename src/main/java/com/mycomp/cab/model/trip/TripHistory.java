package com.mycomp.cab.model.trip;


import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.city.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long tripId;
    @ManyToOne
    private Cab tripCab;
    @ManyToOne
    private City tripCity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tripStartTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tripEndTime;
}
