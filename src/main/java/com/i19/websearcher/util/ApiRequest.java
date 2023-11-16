//package com.i19.websearcher.util;
//
//public class ApiRequest {
//
//    private String query;
//    private int pageNumber;
//    private int pageSize;
//    private String sort;
//
//    private ApiRequest() {
//
//    }
//
//    public String getQuery() {
//        return query;
//    }
//
//    public int getPageNumber() {
//        return pageNumber;
//    }
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public String getSort() {
//        return sort;
//    }
//
//    public static class Builder {
//
//        private ApiRequest apiRequest;
//
//        public Builder() {
//            apiRequest = new ApiRequest();
//        }
//
//        public Builder withQuery(String query) {
//            apiRequest.query = query;
//            return this;
//        }
//
//        public Builder withPageNumber(int pageNumber) {
//            apiRequest.pageNumber = pageNumber;
//            return this;
//        }
//
//        public Builder withPageSize(int pageSize) {
//            apiRequest.pageSize = pageSize;
//            return this;
//        }
//
//        public Builder withSort(String sort) {
//            apiRequest.sort = sort;
//            return this;
//        }
//
//        public ApiRequest build() {
//            return apiRequest;
//        }
//    }
//}
