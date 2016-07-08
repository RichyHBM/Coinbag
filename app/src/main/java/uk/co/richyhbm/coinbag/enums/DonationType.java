package uk.co.richyhbm.coinbag.enums;


public enum DonationType {
    BITCOIN(0),
    LITECOIN(1),
    ETHEREUM(2);

    private int value;
    DonationType(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public String getAddress() {
        switch (this) {
            case BITCOIN:
                return "1234MSEYstWetqTFn5Au";
            case LITECOIN:
                return "5678MSEYstWetqTFn5Au";
            case ETHEREUM:
                return "9010MSEYstWetqTFn5Au";
        }
        throw new IllegalStateException("DonationType unknown");
    }

    @Override
    public String toString() {
        switch (this) {
            case BITCOIN:
                return "Bitcoin";
            case LITECOIN:
                return "Litecoin";
            case ETHEREUM:
                return "Ethereum";
            default:
                return "Unknown";
        }
    }
}
