package com.example.autoassignee.config.key.generator;

import com.example.autoassignee.service.WeightByNotValues;
import com.sun.istack.NotNull;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class PercentWeightKeyGenerator implements KeyGenerator {
    private static final String DELIMITER = "_";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder builder = new StringBuilder();
        for(Object param : params) {
            if (param instanceof WeightByNotValues) {
                builder.append(((WeightByNotValues) param).getCacheKey());
            } else {
                builder.append(param.hashCode());
            }
            builder.append(DELIMITER);
        }

        return builder.toString();
    }
}
