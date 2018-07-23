package com.xh.annotationparas;

import com.xh.annotation.BindArray;
import com.xh.annotation.BindClick;
import com.xh.annotation.BindItemClick;
import com.xh.annotation.BindItemLongClick;
import com.xh.annotation.BindLayout;
import com.xh.annotation.BindLongClick;
import com.xh.annotation.BindPackage;
import com.xh.annotation.BindString;
import com.xh.annotation.BindView;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class AnnotationParas extends AbstractProcessor {
    Filer filer;
    Elements elementUtils;
    List<Class<? extends Annotation>> annotations;
    Map<String, BindFiled> filedMap;

    {
        annotations = Arrays.asList(BindArray.class, BindClick.class, BindItemClick.class, BindItemLongClick.class, BindLongClick.class, BindString.class, BindView.class, BindPackage.class, BindLayout.class);
        filedMap = new HashMap<>();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
        super.init(processingEnvironment);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        println("process");
        parasArray(roundEnvironment);
        parasClick(roundEnvironment);
        parasItemClick(roundEnvironment);
        parasItemLongClick(roundEnvironment);
        parasLongClick(roundEnvironment);
        parasPackage(roundEnvironment);
        parasString(roundEnvironment);
        parasView(roundEnvironment);
        parasLayout(roundEnvironment);

        Collection<BindFiled> set1 = filedMap.values();
        for (BindFiled bindFiled : set1)
            try {
                bindFiled.createJavaFile().writeTo(filer);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        parasArray(roundEnvironment);
        return true;
    }

    private void println(Object msg) {
        System.err.println(msg);
    }

    private void parasArray(RoundEnvironment roundEnvironment) {
        println("parasArray");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindArray.class)) {
            try {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled bindFiled = element2BindFiled(typeElement);
                BindArray bindView = element.getAnnotation(BindArray.class);
                bindFiled.addStings(element.getSimpleName().toString(), bindView.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasClick(RoundEnvironment roundEnvironment) {
        println("parasClick");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindClick.class)) {
            try {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled filed = element2BindFiled(typeElement);
                BindClick bindView = element.getAnnotation(BindClick.class);
                for (String name : bindView.names()) {
                    filed.addClick(executableElement.getSimpleName().toString(), name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasItemClick(RoundEnvironment roundEnvironment) {
        println("parasItemClick");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindItemClick.class)) {
            try {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled filed = element2BindFiled(typeElement);
                BindItemClick bindView = element.getAnnotation(BindItemClick.class);
                for (String name : bindView.names()) {
                    filed.addItemClick(executableElement.getSimpleName().toString(), name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasItemLongClick(RoundEnvironment roundEnvironment) {
        println("parasItemLongClick");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindItemLongClick.class)) {
            try {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled filed = element2BindFiled(typeElement);
                BindItemLongClick bindView = element.getAnnotation(BindItemLongClick.class);
                for (String name : bindView.names()) {
                    filed.addItemLongClick(executableElement.getSimpleName().toString(), name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void parasLongClick(RoundEnvironment roundEnvironment) {
        println("parasLongClick");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindLongClick.class)) {
            try {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled filed = element2BindFiled(typeElement);
                BindLongClick bindView = element.getAnnotation(BindLongClick.class);
                for (String name : bindView.names()) {
                    filed.addLongClick(executableElement.getSimpleName().toString(), name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasPackage(RoundEnvironment roundEnvironment) {
        println("parasPackage");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindPackage.class)) {
            try {
                TypeElement typeElement = (TypeElement) element;
                BindFiled bindFiled = element2BindFiled(typeElement);
                BindPackage bindView = element.getAnnotation(BindPackage.class);
                bindFiled.resourcesPackage = bindView.packageName();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasLayout(RoundEnvironment roundEnvironment) {
        println("parasPackage");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindLayout.class)) {
            try {
                TypeElement typeElement = (TypeElement) element;
                BindFiled bindFiled = element2BindFiled(typeElement);
                BindLayout bindView = element.getAnnotation(BindLayout.class);
                bindFiled.layout = bindView.layout();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasString(RoundEnvironment roundEnvironment) {
        println("parasString");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindString.class)) {
            try {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled bindFiled = element2BindFiled(typeElement);
                BindString bindView = element.getAnnotation(BindString.class);
                bindFiled.addSting(element.getSimpleName().toString(), bindView.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parasView(RoundEnvironment roundEnvironment) {
        println("parasView");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            try {
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                BindFiled bindFiled = element2BindFiled(typeElement);
                BindView bindView = element.getAnnotation(BindView.class);
                bindFiled.add(element.getSimpleName().toString(), bindView.name(), element.asType().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private BindFiled element2BindFiled(TypeElement element) {
        String className = element.getQualifiedName().toString();
        BindFiled bindFiled = filedMap.get(className);
        if (bindFiled == null) {
            bindFiled = new BindFiled();
            bindFiled.packageName = packageName(element);
            bindFiled.name = element.getSimpleName().toString();
            filedMap.put(className, bindFiled);
        }
        return bindFiled;
    }

    /**
     * 2018/7/16 10:10
     * annotation：获取包名
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    private String packageName(TypeElement element) {
        return elementUtils.getPackageOf(element).getQualifiedName().toString();
    }
}
