package Controller;

import Model.Direction;

public class DirectionController {

    public static Direction getLeftDirection(Direction direction){
        switch (direction){
            case NORTH -> {return Direction.WEST;}
            case EAST -> {return Direction.NORTH;}
            case SOUTH -> {return Direction.EAST;}
            case WEST -> {return Direction.SOUTH;}
        }
        return null;
    }

    public static Direction getRightDirection(Direction direction){
        switch (direction){
            case NORTH -> {return Direction.EAST;}
            case EAST -> {return Direction.SOUTH;}
            case SOUTH -> {return Direction.WEST;}
            case WEST -> {return Direction.NORTH;}
        }
        return null;
    }

    public static boolean isOpposite(Direction direction1, Direction direction2){
        switch (direction1){
            case NORTH -> {if(direction2 == Direction.SOUTH) return true;}
            case EAST -> {if(direction2 == Direction.WEST) return true;}
            case SOUTH -> {if(direction2 == Direction.NORTH) return true;}
            case WEST -> {if(direction2 == Direction.EAST) return true;}
        }
        return false;
    }

    public static Direction getOpposite(Direction direction){
        switch (direction){
            case NORTH -> {return Direction.SOUTH;}
            case EAST -> {return Direction.WEST;}
            case SOUTH -> {return Direction.NORTH;}
            case WEST -> {return Direction.EAST;}
            default -> {return null;}
        }
    }
}