package com.mycomp.cab.model.cab;

import com.mycomp.cab.model.city.City;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Cab implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private City city;
    @Enumerated
    private CabState state;
    @Column(unique = true)
    private String cabNumber;
    private Long registerRequestId;
    private Long updateRequestId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date idleStateStartTime;
}
