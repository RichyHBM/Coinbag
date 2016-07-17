package uk.co.richyhbm.coinbag.enums;

//Types of donations that are currently supported
public enum DonationType {
    BITCOIN(0, "1234MSEYstWetqTFn5Au"),
    LITECOIN(1, "5678MSEYstWetqTFn5Au"),
    ETHEREUM(2, "9010MSEYstWetqTFn5Au");

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
