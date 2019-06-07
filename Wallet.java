public class Wallet {
    private int numOfCoins;

    public Wallet(int num){
        numOfCoins = num;
    }
    public void setNumOfCoins(int numOfCoins) {
        this.numOfCoins = numOfCoins;
    }

    public int getNumOfCoins() {
        return numOfCoins;
    }

    public void addCoins(int num){
        numOfCoins += num;
    }
    public void subtractCoins(int num){
        numOfCoins -= num;
    }
}
