package Q1.solutions;

import java.util.Collections;
import java.util.List;

public class HttpRequestBuilder {

    /**
     * We have our builder-local values here.
     * We also need to give each of these fields a default value.
     */
    private final String url;
    private final HttpRequest.Method method;

    private String body = "";
    private List<String> params = Collections.emptyList();
    private List<String> headers = Collections.emptyList();

    /**
     * private constructor forces us to use the factory method.
     * This definitely isn't necessary for builder classes. ```new HttpRequestBuilder()``` is perfectly idiomatic.
     */
    private HttpRequestBuilder(String url, HttpRequest.Method method) {
        this.url = url;
        this.method = method;
    }

    /**
     * A factory method for the httpRequest builder.
     * We include the necessary parameters here. You can't have a http request without a url/method
     */
    public HttpRequestBuilder httpRequest(String url, HttpRequest.Method method) {
        return new HttpRequestBuilder(url, method);
    }

    public HttpRequestBuilder params(List<String> params) {
        this.params = params;
        return this;
    }

    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpRequestBuilder headers(List<String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * This is how we get the object at the end of the build process.
     */
    public HttpRequest build() {
        return new HttpRequest(
                url,
                method,
                params,
                headers,
                body
        );
    }
}
