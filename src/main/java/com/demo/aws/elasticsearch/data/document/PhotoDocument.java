package com.demo.aws.elasticsearch.data.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * PhotoDocument annotated with @{@link Document} where the @{@link Document#createIndex()} false prevents the spring data to
 * create and index during application boot
 */
@Data
@Document(indexName = "dev_photos", type = "_doc", createIndex = false)
public class PhotoDocument {
    @Id
    private String id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
