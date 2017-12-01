package Auora.util;


public class HintIcon {

    private int coordX;
    private int coordY;
    private int height;
    private int distanceFromFloor;
    private int targetType;
    private int targetIndex;
    private int arrowType;
    private int modelId;
    private int index;


    public HintIcon() {
        this.setIndex(7);
    }

    public HintIcon(int targetType, int modelId, int index) {
        this.setTargetType(targetType);
        this.setModelId(modelId);
        this.setIndex(index);
    }

    public HintIcon(int targetIndex, int targetType, int arrowType, int modelId, int index) {
        this.setTargetType(targetType);
        this.setTargetIndex(targetIndex);
        this.setArrowType(arrowType);
        this.setModelId(modelId);
        this.setIndex(index);
    }

    public HintIcon(int coordX, int coordY, int height, int distanceFromFloor, int targetType, int arrowType, int modelId, int index) {
        this.setCoordX(coordX);
        this.setCoordY(coordY);
        this.setHeight(height);
        this.setDistanceFromFloor(distanceFromFloor);
        this.setTargetType(targetType);
        this.setArrowType(arrowType);
        this.setModelId(modelId);
        this.setIndex(index);
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public int getArrowType() {
        return arrowType;
    }

    public void setArrowType(int arrowType) {
        this.arrowType = arrowType;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int modelPart) {
        this.index = modelPart;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDistanceFromFloor() {
        return distanceFromFloor;
    }

    public void setDistanceFromFloor(int distanceFromFloor) {
        this.distanceFromFloor = distanceFromFloor;
    }
}
