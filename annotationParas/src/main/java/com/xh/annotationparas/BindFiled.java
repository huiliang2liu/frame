package com.xh.annotationparas;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * 2018/7/16 14:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class BindFiled {
    public static final ClassName UNBINDER = ClassName.get("com.xh.annotation", "Bind");
    public static final ClassName CLICK = ClassName.bestGuess("android.view.View.OnClickListener");
    public static final ClassName LONG_CLICK = ClassName.bestGuess("android.view.View.OnLongClickListener");
    public static final ClassName ITEM_CLICK = ClassName.bestGuess("aandroid.widget.AdapterView.OnItemClickListener");
    public static final ClassName ITEM_LONG_CLICK = ClassName.bestGuess("aandroid.widget.AdapterView.OnItemLongClickListener");
    String layout;
    String resourcesPackage;
    String packageName;
    String name;
    String theme;
    String color;
    String clickMethod;
    String itemClickMethod;
    String itemLongClickMethod;
    String longClickMethod;
    List<String> clicks = new ArrayList<>();
    List<String> itemClicks = new ArrayList<>();
    List<String> itemLongClicks = new ArrayList<>();
    List<String> longClicks = new ArrayList<>();
    List<FiledClass> filedClasses = new ArrayList<>();
    List<StringFiled> stringFileds = new ArrayList<>();
    List<StringsFiled> stringsFileds = new ArrayList<>();

    public void addClick(String method, String idName) {
        this.clickMethod = method;
        clicks.add(idName);
    }

    public void addItemClick(String method, String idName) {
        this.itemClickMethod = method;
        itemLongClicks.add(idName);
    }

    public void addItemLongClick(String method, String idName) {
        this.itemLongClickMethod = method;
        itemClicks.add(idName);
    }

    public void addLongClick(String method, String idName) {
        this.longClickMethod = method;
        longClicks.add(idName);
    }

    public void add(String filedName, String idName, String typeName) {
        filedClasses.add(new FiledClass(filedName, idName, typeName));
    }

    public void addSting(String filedName, String idName) {
        stringFileds.add(new StringFiled(filedName, idName));
    }

    public void addStings(String filedName, String idName) {
        stringsFileds.add(new StringsFiled(filedName, idName));
    }

    public JavaFile createJavaFile() {
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(name + "$" + "XhAnnotatoin")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        typeSpecBuilder.addSuperinterface(UNBINDER);
        if (clickMethod != null && !clickMethod.isEmpty() && clicks.size() > 0) {
            typeSpecBuilder.addSuperinterface(CLICK);
            typeSpecBuilder.addMethod(createClick());

        }
        if (longClickMethod != null && !longClickMethod.isEmpty() && longClicks.size() > 0) {
            typeSpecBuilder.addSuperinterface(LONG_CLICK);
            typeSpecBuilder.addMethod(createLongClick());
        }
        if (itemClickMethod != null && !itemClickMethod.isEmpty() && itemClicks.size() > 0) {
            typeSpecBuilder.addSuperinterface(ITEM_CLICK);
            typeSpecBuilder.addMethod(createItemClick());
        }
        if (itemLongClickMethod != null && !itemLongClickMethod.isEmpty() && itemLongClicks.size() > 0) {
            typeSpecBuilder.addSuperinterface(ITEM_LONG_CLICK);
            typeSpecBuilder.addMethod(createItemLongClick());
        }
        typeSpecBuilder.addField(ClassName.get(packageName, name), "target", Modifier.PRIVATE);
        typeSpecBuilder.addField(ClassName.get(String.class), "packageName", Modifier.PRIVATE);
        typeSpecBuilder.addField(ClassName.get(String.class), "color", Modifier.PRIVATE);
        typeSpecBuilder.addField(TypeName.INT, "theme", Modifier.PRIVATE);
//        typeSpecBuilder.addField(ClassName.get("android.view", "View"), "layout", Modifier.PRIVATE);
        typeSpecBuilder.addField(TypeName.INT, "layoutId", Modifier.PRIVATE);
        typeSpecBuilder.addMethod(createUnBind());
        typeSpecBuilder.addMethod(createConstructor());
        typeSpecBuilder.addMethod(createBind());
        typeSpecBuilder.addMethod(createPackage());
        typeSpecBuilder.addMethod(createColor());
        typeSpecBuilder.addMethod(createLayoutId());
        typeSpecBuilder.addMethod(createTheme());
        JavaFile.Builder javaBuilder = JavaFile.builder(packageName, typeSpecBuilder.build());
        return javaBuilder.build();
    }

    public MethodSpec createConstructor() {
        MethodSpec.Builder main = MethodSpec.constructorBuilder();
        main.addParameter(TypeName.OBJECT, "target1");
        main.addParameter(ClassName.get("android.content", "Context"), "context");
        main.addStatement("this.target=(" + packageName + "." + name + ")target1");
        if (resourcesPackage == null || resourcesPackage.isEmpty()) {
            main.addStatement("this.packageName=context.getPackageName()");
        } else {
            main.addStatement("this.packageName=\"" + resourcesPackage + "\"");
        }
        if (layout != null && !layout.isEmpty())
            main.addStatement("this.layoutId= com.xh.annotation.Util.layout(\"" + layout + "\",this.packageName,context)");
        if (color != null && !color.isEmpty())
            main.addStatement("this.color= \"" + color + "\"");
        if (theme != null && !theme.isEmpty())
            main.addStatement("this.theme= com.xh.annotation.Util.style(\"" + theme + "\",this.packageName,context)");
//        main.addStatement("this.layout= com.xh.annotation.Util.layoutView(layoutId,context);");
//        main.addStatement("init()");


        return main.build();
    }

    public MethodSpec createUnBind() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("unBind")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class);
        for (FiledClass filedClass : filedClasses) {
            main.addStatement("target." + filedClass.filedName + "=null");
        }
        for (StringFiled filedClass : stringFileds) {
            main.addStatement("target." + filedClass.filedName + "=null");
        }
        for (StringsFiled filedClass : stringsFileds) {
            main.addStatement("target." + filedClass.filedName + "=null");
        }
        main.addStatement("target=null");
        return main.build();
    }

    public MethodSpec createClick() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("onClick")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("android.view", "View"), "view")
                .returns(void.class);
        main.addStatement("target." + clickMethod + "(view)");
        return main.build();
    }

    public MethodSpec createLongClick() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("onLongClick")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("android.view", "View"), "view")
                .returns(boolean.class);
        main.addStatement("return target." + longClickMethod + "(view)");
        return main.build();
    }

    public MethodSpec createItemClick() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("onItemClick")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("android.widget", "AdapterView<?>"), "parent")
                .addParameter(ClassName.get("android.view", "View"), "view")
                .addParameter(TypeName.INT, "position")
                .addParameter(TypeName.LONG, "id")
                .returns(Void.class);
        main.addStatement("target." + itemClickMethod + "(parent,view,position,id)");
        return main.build();
    }

    public MethodSpec createItemLongClick() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("onItemLongClick")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get("android.widget", "AdapterView<?>"), "parent")
                .addParameter(ClassName.get("android.view", "View"), "view")
                .addParameter(TypeName.INT, "position")
                .addParameter(TypeName.LONG, "id")
                .returns(boolean.class);
        main.addStatement("return target." + itemLongClickMethod + "(parent,view,position,id)");
        return main.build();
    }

    public MethodSpec createBind() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.bestGuess("android.view.View"), "layout")
                .returns(void.class);
        if (resourcesPackage == null)
            resourcesPackage = "null";
        for (FiledClass filedClass : filedClasses) {
            main.addStatement("target." + filedClass.filedName + "=(" + filedClass.typeName + ")com.xh.annotation.Util.findView(\"" + filedClass.idName + "\",\"" + resourcesPackage + "\",layout)");
        }
        for (StringFiled filedClass : stringFileds) {
            main.addStatement("target." + filedClass.filedName + "=com.xh.annotation.Util.string(\"" + filedClass.idName + "\",\"" + resourcesPackage + "\",layout)");
        }
        for (StringsFiled filedClass : stringsFileds) {
            main.addStatement("target." + filedClass.filedName + "=com.xh.annotation.Util.strings(\"" + filedClass.idName + "\",\"" + resourcesPackage + "\",layout)");
        }
        if (clickMethod != null && !clickMethod.isEmpty() && clicks.size() > 0) {
            FiledClass filedClass = null;
            for (String idName : clicks) {
                for (FiledClass filedClass1 : filedClasses) {
                    if (idName.equals(filedClass1.idName)) {
                        filedClass = filedClass1;
                        break;
                    }
                }
                if (filedClass != null) {
                    main.addStatement("target." + filedClass.filedName + ".setOnClickListener(this)");
                } else {
                    main.addStatement("com.xh.annotation.Util.findView(\"" + idName + "\",\"" + resourcesPackage + "\",layout).setOnClickListener(this)");
                }
                filedClass = null;
            }

        }
        if (longClickMethod != null && !longClickMethod.isEmpty() && longClicks.size() > 0) {
            FiledClass filedClass = null;
            for (String idName : longClicks) {
                for (FiledClass filedClass1 : filedClasses) {
                    if (idName.equals(filedClass1.idName)) {
                        filedClass = filedClass1;
                        break;
                    }
                }
                if (filedClass != null) {
                    main.addStatement("target." + filedClass.filedName + ".setOnLongClickListener(this)");
                } else {
                    main.addStatement("com.xh.annotation.Util.findView(\"" + idName + "\",\"" + resourcesPackage + "\",layout).setOnLongClickListener(this)");
                }
                filedClass = null;
            }

        }
        if (itemClickMethod != null && !itemClickMethod.isEmpty() && itemClicks.size() > 0) {
            FiledClass filedClass = null;
            for (String idName : itemClicks) {
                for (FiledClass filedClass1 : filedClasses) {
                    if (idName.equals(filedClass1.idName)) {
                        filedClass = filedClass1;
                        break;
                    }
                }
                if (filedClass != null) {
                    main.addStatement("target." + filedClass.filedName + ".setOnItemClickListener(this)");
                } else {
                    main.addStatement("com.xh.annotation.Util.findView(\"" + idName + "\",\"" + resourcesPackage + "\",layout).setOnItemClickListener(this)");
                }
                filedClass = null;
            }

        }
        if (itemLongClickMethod != null && !itemLongClickMethod.isEmpty() && itemLongClicks.size() > 0) {
            FiledClass filedClass = null;
            for (String idName : itemLongClicks) {
                for (FiledClass filedClass1 : filedClasses) {
                    if (idName.equals(filedClass1.idName)) {
                        filedClass = filedClass1;
                        break;
                    }
                }
                if (filedClass != null) {
                    main.addStatement("target." + filedClass.filedName + ".setOnItemLongClickListener(this)");
                } else {
                    main.addStatement("com.xh.annotation.Util.findView(\"" + idName + "\",\"" + resourcesPackage + "\",layout).setOnItemLongClickListener(this)");
                }
                filedClass = null;
            }

        }
        return main.build();
    }

    public MethodSpec createPackage() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("packageName")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(String.class);
        main.addStatement("return packageName");
        return main.build();
    }

    public MethodSpec createColor() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("color")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(String.class);
        main.addStatement("return color");
        return main.build();
    }

    public MethodSpec createTheme() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("theme")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class);
        main.addStatement("return theme");
        return main.build();
    }

    public MethodSpec createLayout() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("layout")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClassName.bestGuess("android.view.View"));
        main.addStatement("return layout");
        return main.build();
    }

    public MethodSpec createLayoutId() {
        MethodSpec.Builder main = MethodSpec.methodBuilder("layout")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class);
        main.addStatement("return layoutId");
        return main.build();
    }


    public class FiledClass {
        String filedName;
        String idName;
        String typeName;
        boolean click = false;

        FiledClass(String filedName, String idName, String typeName) {
            this.filedName = filedName;
            this.idName = idName;
            this.typeName = typeName;
            System.out.println(filedName + "  " + idName);
        }
    }

    public class StringFiled {
        String idName;
        String filedName;

        StringFiled(String filedName, String idName) {
            this.idName = idName;
            this.filedName = filedName;
        }

    }

    public class StringsFiled {
        String idName;
        String filedName;

        StringsFiled(String filedName, String idName) {
            this.idName = idName;
            this.filedName = filedName;
        }

    }
}
