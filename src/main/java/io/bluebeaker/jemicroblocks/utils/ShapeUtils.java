package io.bluebeaker.jemicroblocks.utils;

public class ShapeUtils {

    public static Shape getShape(int meta){
        return Shape.values()[(meta>>8)&3];
    }

    public static int getSize(int meta){
        return meta&511;
    }

    public static int getMeta(Shape shape,int size){
        return (shape.ordinal()<<8) | Math.max(size & 7,1);
    }
}
