package com.demo.aws.elasticsearch.data.model;

import lombok.Data;

@Data
public class PhotoDto {
    private String id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
