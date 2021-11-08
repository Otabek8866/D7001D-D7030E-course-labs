package gui_client;

public class Loop {

    int iter;

    public Loop(int num) {
        this.iter = num;
    }

    public void run() {
        for(int i=1; i <= iter; i++){
            System.out.println(i);
        }
        System.out.println("I finished the loop");
    }
}
