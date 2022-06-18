package com.mycomp.cab.model.trip;


import com.mycomp.cab.model.cab.Cab;
import com.mycomp.cab.model.city.City;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private Tripstatus tripStatus;
    private Long tripRequestId;

    @ManyToOne
    private Cab cabAssigned;
    @ManyToOne
    private City city;
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
}
