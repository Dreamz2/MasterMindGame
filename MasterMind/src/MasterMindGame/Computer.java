package src.MasterMindGame;

public class Computer {
    private int[] digits;
    private int size; // Length of digits being guessed
    private int sum;

    Computer(int size) {
        this.size = size;
        sum = 0;
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
            sum += num;
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

    public int getSum() {
        return sum;
    }

    public int[] getCompList() {
        return digits;
    }

}
