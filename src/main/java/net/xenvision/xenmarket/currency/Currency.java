package net.xenvision.xenmarket.currency;

public class Currency {
    private final String id;
    private final String name;
    private final String symbol;
    private final double minRate;
    private final double maxRate;
    private double currentRate;

    public Currency(String id, String name, String symbol, double minRate, double maxRate, double currentRate) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.minRate = minRate;
        this.maxRate = maxRate;
        this.currentRate = currentRate;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getMinRate() { return minRate; }
    public double getMaxRate() { return maxRate; }
    public double getCurrentRate() { return currentRate; }

    public void setCurrentRate(double value) { this.currentRate = value; }
}
