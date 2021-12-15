package Controller;

import View.Renderable;

public abstract class Engine<RenderObject> implements Runnable {
    private volatile boolean running;
    private final double secondsPerFrame;
    private final Renderable<RenderObject> renderable;
    private final Updatable<RenderObject> updatable;
    private int actualFPS;

    public Engine(Renderable<RenderObject> renderable, Updatable<RenderObject> updatable, int FPS){
        this.renderable = renderable;
        this.updatable = updatable;
        this.secondsPerFrame = 1.0/FPS;
    }

    public void start(){
        Thread thread = new Thread(this); // Demands that this is Runnable
        thread.start();
    }

    public void stop(){
        running = false;
    }

    protected abstract void stopped();

    @Override
    public void run(){

        running = true;
        double lastTime = System.nanoTime() / 1000000000.0; // 1 = 1 second
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;

        while (running){
            boolean render = false;
            double firstTime = System.nanoTime() / 1000000000.0;
            double passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= secondsPerFrame){
                unprocessedTime -= secondsPerFrame;
                render = true;
                //update
                updatable.update(secondsPerFrame);//In seconds
                 if (stopCondition()) running = false;

                if (frameTime >= 1){
                    frameTime = 0;
                    actualFPS = frames;
                    System.out.println("FPS: "+actualFPS);
                    frames = 0;
                }
            }

            if (render){
                //render game
                renderable.render(updatable.getRenderObject());
                frames++;

            } else { //Nothing to do, so wait a bit before running through the running loop again.
                //Decreases CPU-load by around 85%
                try {
                    Thread.sleep(0,500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game ended");
        stopped();
    }

    protected abstract boolean stopCondition();

    public int getCurrentFPS(){
        return actualFPS;
    }
}