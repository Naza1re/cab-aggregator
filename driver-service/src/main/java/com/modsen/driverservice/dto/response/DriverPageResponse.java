package com.modsen.driverservice.dto.response;

import lombok.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DriverPageResponse {
    private List<DriverResponse> driverList;
    private long totalElements;
    private int totalPages;
}
