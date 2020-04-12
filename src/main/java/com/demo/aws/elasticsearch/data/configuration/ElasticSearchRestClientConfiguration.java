package com.demo.aws.elasticsearch.data.configuration;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.demo.aws.elasticsearch.data.repository")
public class ElasticSearchRestClientConfiguration extends AbstractElasticsearchConfiguration {

    @Value("${aws.es.endpoint}")
    private String endpoint = null;

    @Value("${aws.es.region}")
    private String region = null;

    @Autowired
    private AWSCredentialsProvider credentialsProvider = null;

    /**
     * SpringDataElasticSearch data provides us the flexibility to implement our custom {@link RestHighLevelClient} instance by implementing the abstract method {@link AbstractElasticsearchConfiguration#elasticsearchClient()},
     *
     * @return RestHighLevelClient. AWS ElasticService Https rest calls have to be signed with AWS credentials, hence an interceptor {@link HttpRequestInterceptor} is required to sign every
     * API calls with credentials. The signing is happening through the below snippet
     * <code>
     * signer.sign(signableRequest, awsCredentialsProvider.getCredentials());
     * </code>
     */
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        AWS4Signer signer = new AWS4Signer();
        String serviceName = "es";
        signer.setServiceName(serviceName);
        signer.setRegionName(region);
        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
        return new RestHighLevelClient(RestClient.builder(HttpHost.create(endpoint)).setHttpClientConfigCallback(e -> e.addInterceptorLast(interceptor)));
    }
}