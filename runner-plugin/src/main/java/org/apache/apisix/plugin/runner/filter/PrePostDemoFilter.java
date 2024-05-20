package org.apache.apisix.plugin.runner.filter;

import com.google.gson.Gson;
import org.apache.apisix.plugin.runner.HttpRequest;
import org.apache.apisix.plugin.runner.HttpResponse;
import org.apache.apisix.plugin.runner.PostRequest;
import org.apache.apisix.plugin.runner.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrePostDemoFilter implements PluginFilter{
    private final Logger logger = LoggerFactory.getLogger(PrePostDemoFilter.class);
    @Override
    public String name() {
        return "PrePostDemoFilter";
    }

    @Override
    public void filter(HttpRequest request, HttpResponse response, PluginFilterChain chain) {
        logger.warn("PrePostDemoFilter is running");
        logger.warn("PrePostDemoFilter list request object:" + request);
        logger.warn("PrePostDemoFilter list request body data:" + request.getBody());
        String body = request.getBody();
        Gson gson = new Gson();
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap = gson.fromJson(body, bodyMap.getClass());
        bodyMap.put("plugin","PrePostDemoFilter");
        request.setBody(gson.toJson(bodyMap));
        chain.filter(request, response);
    }

    @Override
    public void postFilter(PostRequest request, PostResponse response, PluginFilterChain chain) {
        PluginFilter.super.postFilter(request, response, chain);
    }

    @Override
    public List<String> requiredVars() {
        return PluginFilter.super.requiredVars();
    }

    @Override
    public Boolean requiredBody() {
        return true;
    }

    @Override
    public Boolean requiredRespBody() {
        return PluginFilter.super.requiredRespBody();
    }
}
