package com.qihui.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenqihui
 * @date 9/24/22
 */
public class ApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();


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
                            Class<?> clazz;
                            try {
                                clazz = classLoader.loadClass(className);
                                if (clazz.isAnnotationPresent(Component.class)) {
                                    if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                        beanPostProcessorList.add((BeanPostProcessor) clazz.newInstance());
                                    }

                                    Component componentAnnotation  = clazz.getAnnotation(Component.class);
                                    String beanName = componentAnnotation.value();

                                    if ("".equals(beanName)) {
                                        beanName = Introspector.decapitalize(clazz.getSimpleName());
                                    }
                                    //create beanDefinition
                                    BeanDefinition beanDefinition = new BeanDefinition();
                                    beanDefinition.setType(clazz);
                                    if (clazz.isAnnotationPresent(Scope.class)) {
                                        Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                        beanDefinition.setScope(scopeAnnotation.value());
                                    } else {
                                        beanDefinition.setScope("singleton");
                                    }

                                    beanDefinitionMap.put(beanName, beanDefinition);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


            for (String beanName : beanDefinitionMap.keySet()) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition.getScope().equals("singleton")) {
                    Object bean = getBean(beanName);
                    if (bean == null) {
                        Object o = createBean(beanName, beanDefinition);
                        singletonObjects.put(beanName, o);
                    }
                }
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();

            //DI
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowiredAnnotation  = field.getAnnotation(Autowired.class);
                    field.setAccessible(true);
                    String value = autowiredAnnotation.value();
                    if (!"".equals(value)) {
                        field.set(instance, getBean(value));
                    } else {
                        field.set(instance, getBean(field.getName()));
                    }
                }
            }

            //Aware
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(beanName, instance);
            }

            //Initialize
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(beanName, instance);
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if (beanDefinition == null) {
            throw new NullPointerException();
        }

        String scope = beanDefinition.getScope();
        if (scope.equals("singleton")) {
            return singletonObjects.get(beanName);
        }
        return createBean(beanName, beanDefinition);
    }
}
