package com.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class Main {
  public static void main(String[] args) {
    try {
      String packageName="com.javassist.Hello";

      //执行了loadClass,下面的toClass就会报错
      //想实现loadClass之后 下面的toClass也生效，
      //不是用子类代替.
      Main.class.getClassLoader().loadClass(packageName);

      ClassPool cp = ClassPool.getDefault();
      CtClass cc = cp.get(packageName);
      CtMethod m = cc.getDeclaredMethod("sayHello");
      m.addLocalVariable("begin",CtClass.longType);
      m.addLocalVariable("end",CtClass.longType);
      m.insertBefore("begin=System.currentTimeMillis();");
      m.insertAfter("end=System.currentTimeMillis();");
      m.insertAfter("System.out.println(\"类："+cc.getSimpleName()+"方法："+m.getName()+",耗时:\"+(end-begin)+\"毫秒\");\n");
      cc.toClass();

      Hello hello=new Hello();
      hello.sayHello();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
