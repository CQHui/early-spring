package com.qihui.spring;

import java.io.File;
import java.net.URL;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class ApplicationContext {
    private Class configClass;

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
                                if (aClass.isAnnotationPresent(ComponentScan.class)) {
                                    //bean name
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
