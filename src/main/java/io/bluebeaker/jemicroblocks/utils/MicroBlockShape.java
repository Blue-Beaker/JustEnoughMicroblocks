package io.bluebeaker.jemicroblocks.utils;

public class MicroBlockShape {
    public Shape shape;
    public int size;
    public MicroBlockShape(Shape shape,int size){
        this.shape=shape;
        this.size=size;
    }
    public MicroBlockShape(int meta){
        new MicroBlockShape(Shape.values()[(meta>>8)&3],meta&511);
    }
    public boolean isFullBlock(){
        return size==8;
    }
    public boolean isValid(){
        return size>0 && size<8;
    }
    public int getMeta(){
        return (shape.ordinal()<<8) | Math.max(size & 7,1);
    }
}
