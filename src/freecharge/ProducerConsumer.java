package freecharge;

import java.util.LinkedList;
import java.util.List;

public class ProducerConsumer{

    public static void main(String[] args) {

        final PC pc = new PC();

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                int i = 20;

                while(i >= 0){
                    pc.produce(i);
                    i--;

                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                int i = 20;

                while(i >= 0){
                    pc.consume();
                    i--;

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread1.start();
        thread2.start();

    }
}


class PC {

    private List<Integer> list = new LinkedList<Integer>();
    private final int size = 10;

    public void consume(){
        synchronized (this){
            if(list.size() == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Consuming " + list.get(0));
            list.remove(0);
            notify();
        }
        System.out.println("SOMETHING");
    }

    public void produce(int i){
        synchronized (this){

            if(list.size() == size){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Producing " + i);
            list.add(i);
            notify();
        }
    }

}