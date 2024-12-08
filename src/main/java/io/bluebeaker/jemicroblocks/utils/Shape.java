package io.bluebeaker.jemicroblocks.utils;

public enum Shape {
    FACE,
    HOLLOW,
    POINT,
    EDGE;

    public Shape shapeUp(){
        if(this==POINT){
            return EDGE;
        }else{
            return FACE;
        }
    }
    public Shape shapeDown(){
        if(this==FACE || this==HOLLOW){
            return EDGE;
        }else{
            return POINT;
        }
    }
    public boolean canShapeDown(){
        return this!=POINT;
    }
    public boolean canShapeUp(){
        return this!=FACE && this!=HOLLOW;
    }

}
