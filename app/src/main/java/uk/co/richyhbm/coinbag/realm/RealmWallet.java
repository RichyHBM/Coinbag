package uk.co.richyhbm.coinbag.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmWallet extends RealmObject {
    @PrimaryKey
    public Integer walletId;

    public String walletNickName;

    public String walletAddress;

    public String walletType;
}