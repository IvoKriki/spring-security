package com.ivokriki.spring.security.controller.dto;

import java.util.List;

public record FeedDto(List<FeedItemDto> feedItens, int page, int pageSize, int totalPages, int totalElements) {
}
