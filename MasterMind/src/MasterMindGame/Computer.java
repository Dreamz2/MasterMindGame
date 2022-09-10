package src.MasterMindGame;

public class Computer {
    private int[] digits;
    private int size;

    Computer(int size) {
        this.size = size;
        digits = getRandom(size);
    }

    private int[] getRandom(final int length) {
        int[] list = new int[length];
        int num;

        for(int i=0; i<length; i++){
            int x = 0;
            num = (int)(Math.random()*10);
            while(x<i){
                if(list[x]==num){
                    num = (int)(Math.random()*10);
                    x=0;
                }
                else
                    x++;
            }
            list[i] = num;
        }
        
        return list;
    }

    public int get(int index) {
        return digits[index];
    }

    public int getSize() {
        return size;
    }

}
