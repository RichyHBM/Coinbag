package uk.co.richyhbm.coinbag.enums;

//Types of donations that are currently supported
public enum DonationType {
    BITCOIN(0, "TEST_Bitcoin"),
    LITECOIN(1, "TEST_Litecoin"),
    ETHEREUM(2, "TEST_Ethereum"),
    DASH(3, "TEST_Dash");

    //Use a value to assure donation types id's don't change, store the address to use with the enum
    private int value;
    private String address;
    DonationType(int i, String address) {
        value = i;
        this.address = address;
    }

    public int getValue() {
        return value;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        String name = this.name().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
