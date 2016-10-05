package uk.co.richyhbm.coinbag.records;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;
import uk.co.richyhbm.coinbag.exchanges.Exchange;
import uk.co.richyhbm.coinbag.utils.WalletBalanceValue;

//Wallet class, uses SugarORM to store in sqlite db
public class Wallet extends SugarRecord {
    //Store the address and type of crypto currency of the wallet
    @Unique
    String address;
    CryptoCurrencies cryptoType;

    @Ignore
    String balance = "Unknown";
    @Ignore
    String value = "Unknown";

    public Wallet() {
        updateValueBalance();
    }

    public Wallet(String address, CryptoCurrencies type){
        this.address = address;
        this.cryptoType = type;
        updateValueBalance();
    }

    public CryptoCurrencies getType() {
        return cryptoType;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public String getValue() {
        return value;
    }

    public void updateValueBalance() {
        if(address == null || address.trim().isEmpty())
            return;

        value = "Fetching";
        balance = "Fetching";

        Single<WalletBalanceValue> balanceSubscription = Single.create(new Single.OnSubscribe<WalletBalanceValue>() {
            @Override
            public void call(SingleSubscriber singleSubscriber) {
                DecimalFormat df2 = new DecimalFormat("#,###.##");
                DecimalFormat df5 = new DecimalFormat("#,###.#####");
                df2.setRoundingMode(RoundingMode.HALF_DOWN);
                df5.setRoundingMode(RoundingMode.HALF_DOWN);

                WalletBalanceValue result = new WalletBalanceValue();
                CryptoCurrencies cryptoType = getType();
                double balance = 0;

                try {
                    if (Balance.balanceFetchers.containsKey(cryptoType)) {
                        double blc = Balance.balanceFetchers.get(cryptoType).getBalanceForAddress(getAddress());
                        if (blc >= 0) {
                            balance = blc;
                            result.balance = df5.format(balance) + " " + cryptoType.getDenomination();
                        } else result.balance = "Error";
                    } else result.balance = "Error";

                    if (Exchange.exchangeFetchers.containsKey(cryptoType) && !result.balance.equalsIgnoreCase("Error")) {
                        double usd = Exchange.exchangeFetchers.get(cryptoType).getExchangeForCurrency(cryptoType);
                        if (usd >= 0) {
                            double totalValue = (balance * usd);
                            result.value = df2.format(totalValue) + " USD";
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    result = new WalletBalanceValue("Error", "Error");
                }

                singleSubscriber.onSuccess(result);
            }
        });
        balanceSubscription.subscribeOn(Schedulers.newThread());
        balanceSubscription.observeOn(AndroidSchedulers.mainThread());
        balanceSubscription.subscribe(new Action1<WalletBalanceValue>() {
            @Override
            public void call(WalletBalanceValue wbv) {
                value = wbv.value;
                balance = wbv.balance;
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                value = "Error";
                balance = "Error";
            }
        });
    }
}
