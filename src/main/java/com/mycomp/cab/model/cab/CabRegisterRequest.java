package com.mycomp.cab.model.cab;

import com.mycomp.cab.model.RequestStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CabRegisterRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long cabId;
    private RequestStatus status;
    private Long cityId;
    private String cabNumber;
}
