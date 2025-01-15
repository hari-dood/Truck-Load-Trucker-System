package com.hariSolution.logger;

import com.hariSolution.model.LogInfo;
import com.hariSolution.repository.LogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class LogOncePerReqFilter extends OncePerRequestFilter {

    @Autowired
    private LogRepository logRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Wrap the request and response
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // Proceed with the filter chain
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Extract request and response data
        String requestBody = getValueAsString(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
        String responseBody = getValueAsString(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

        // Log the request and response asynchronously
        logRequestAndResponse(
                requestBody,
                responseBody,
                requestWrapper.getRequestURI(),
                requestWrapper.getMethod()
        );

        // Copy the response back to the original response
        responseWrapper.copyBodyToResponse();
    }

    private String getValueAsString(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding Exception", e);
            return "[UNREADABLE]";
        }
    }

    @Async
    private void logRequestAndResponse(String request, String response, String uri, String httpMethod) {
        LogInfo logInfo = new LogInfo();
        logInfo.setRequest(request);
        logInfo.setResponse(response);
        logInfo.setUri(uri);
        logInfo.setHttpMethod(httpMethod);

        logRepository.save(logInfo);
        log.info("Logged Request and Response for URI: {}", uri);
    }
}
