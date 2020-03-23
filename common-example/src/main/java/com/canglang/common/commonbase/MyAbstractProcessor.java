package com.canglang.common.commonbase;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author leitao.
 * @category
 * @time: 2019/11/21 0021-11:21
 * @version: 1.0
 * @description:
 **/
//指定该注解处理器可以解决的类型，需要完整的包名+类命
@SupportedAnnotationTypes("com.canglang.common.commonbase.CustomAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyAbstractProcessor extends AbstractProcessor {

    public MyAbstractProcessor(){
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //创建动态代码，实际上就是创建一个String, 写入到文件里
        //然后文件会被解释为.class文件

        StringBuilder builder = new StringBuilder()
                .append("package com.canglang.common.commonbase;\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage() {\n")
                .append("\t\treturn \"");

        //获取所有被CustomAnnotation修饰的代码元素
        for (Element element : roundEnv.getElementsAnnotatedWith(CustomAnnotation.class)) {
            String objectType = element.getSimpleName().toString();
            builder.append(objectType).append(" exists!\\n");
        }

        builder.append("\";\n")
                .append("\t}\n")
                .append("}\n");

        //将String写入并生成.class文件
        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(
                    "com.zhangjian.annotationprocessor.generated.GeneratedClass");

            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //
        }
        return false;
    }
}