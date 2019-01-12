package org.itsallcode.openfasttrace.lambda;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonStructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

class LambdaSystemTest
{
    private static final TestConfig CONFIG = TestConfig.load();
    private JdkRequest request;

    @BeforeEach
    void setUp() throws Exception
    {
        request = new JdkRequest(CONFIG.getBaseUrl());
    }

    @Test
    void test() throws IOException
    {
        final JsonStructure requestBody = Json.createObjectBuilder()
                .add("url", "https://github.com/itsallcode/openfasttrace/archive/develop.zip")
                .build();
        final JsonResponse response = request.uri().path("trace").back() //
                .method(Request.POST).body().set(requestBody).back() //
                .fetch().as(JsonResponse.class);
        System.out.println(response.body());
        assertThat(response.status(), is(200));
    }
}
