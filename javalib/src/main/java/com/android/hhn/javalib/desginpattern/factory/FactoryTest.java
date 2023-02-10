package com.android.hhn.javalib.desginpattern.factory;

/**
 * Author: haonan.he ;<p/>
 * Date: 2/10/23,4:41 PM ;<p/>
 * Description: 工厂模式示例;<p/>
 * Other: ;
 */
class FactoryTest {
    public static void main(String[] args) {
        // 工厂方法
        ShapeFactory1 shapeFactory = new ShapeFactory1();
        //获取 Circle 的对象，并调用它的 draw 方法
        Shape shape1 = shapeFactory.getShape("CIRCLE");

        //抽象工厂
        AbstractFactory absFactory = FactoryProducer.getFactory("SHAPE");
        //获取形状为 Circle 的对象
        Shape circle = absFactory.getShape("CIRCLE");
        circle.draw();
        Color red = absFactory.getColor("RED");
        red.draw();
    }
}

//创建一个接口
interface Shape {
    void draw();
}

//创建实现接口的实体类。
class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

// 工厂方法模式
class ShapeFactory1 {
    //使用 getShape 方法获取形状类型的对象
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}

// 抽象工厂模式

// 新增加一个产品族 Color 接口
interface Color {
    void draw();
}

// 抽象工厂接口
abstract class AbstractFactory {
    public abstract Shape getShape(String shapeType);

    public abstract Color getColor(String colorType);
}

//创建一个工厂创造器/生成器类，通过传递形状或颜色信息来获取工厂。
class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("SHAPE")) {
            return new ShapeFactory();
        } else if (choice.equalsIgnoreCase("COLOR")) {
            return new ColorFactory();
        }
        return null;
    }
}

class ShapeFactory extends AbstractFactory {

    @Override
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}

class Red implements Color {

    @Override
    public void draw() {
        System.out.println("Inside Red::fill() method.");
    }
}

class Green implements Color {

    @Override
    public void draw() {
        System.out.println("Inside Green::fill() method.");
    }
}

class Blue implements Color {

    @Override
    public void draw() {
        System.out.println("Inside Blue::fill() method.");
    }
}

class ColorFactory extends AbstractFactory {

    @Override
    public Shape getShape(String shapeType) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase("RED")) {
            return new Red();
        } else if (color.equalsIgnoreCase("GREEN")) {
            return new Green();
        } else if (color.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }
        return null;
    }
}


