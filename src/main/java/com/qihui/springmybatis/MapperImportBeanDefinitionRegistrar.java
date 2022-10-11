package com.qihui.springmybatis;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MapperImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName());
        String path = (String) annotationAttributes.get("value");

        MapperScanner mapperScanner = new MapperScanner(registry);
        mapperScanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

        mapperScanner.scan(path);
    }
}
