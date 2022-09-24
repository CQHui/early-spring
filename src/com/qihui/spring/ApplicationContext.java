package com.qihui.spring;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class ApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;

        //scan
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan annotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = annotation.value();
            path = path.replaceAll("\\.", "/");
            ClassLoader classLoader = this.getClass().getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        String fileName = f.getAbsolutePath();

                        if (fileName.endsWith(".class")) {
                            String className = fileName. substring(
                                    fileName.indexOf("com"), fileName.indexOf(".class"));
                            className = className.replaceAll("/", ".");
                            System.out.println(className);
                            Class<?> aClass = null;
                            try {
                                aClass = classLoader.loadClass(className);
                                if (aClass.isAnnotationPresent(Component.class)) {
                                    Component componentAnnotation  = aClass.getAnnotation(Component.class);
                                    String value = componentAnnotation.value();
                                    //create beanDefinition
                                    BeanDefinition beanDefinition = new BeanDefinition();
                                    beanDefinition.setType(aClass);
                                    if (aClass.isAnnotationPresent(Scope.class)) {
                                        Scope scopeAnnotation = aClass.getAnnotation(Scope.class);
                                        beanDefinition.setScope(scopeAnnotation.value());
                                    }

                                    beanDefinitionMap.put(value, beanDefinition);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}
