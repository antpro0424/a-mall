package com.chuwa.DTO;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Data
public class Paginated {
    public static final Integer DEFAULT_LIMIT = 5;

    @Min(1)
    @Max(1000)
    private Integer limit;
    private String pagingState;

    public Optional<String> getPagingState() {
        return Optional.ofNullable(pagingState);
    }

    public Integer getLimit() {
        return limit != null ? limit : DEFAULT_LIMIT;
    }
}
