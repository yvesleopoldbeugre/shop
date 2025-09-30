package com.asking.api_produit.utils;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pagination {
    private int limit;
    private int page;
    private int previousPage;
    private int nextPage;
    private long totalElements;
    private int totalPages;
    private List<?> data;
}
