package com.unimate.unimate;

import com.unimate.unimate.entity.Account;

public class AccountContext {

    private static final ThreadLocal<Account> accountThreadLocal = new ThreadLocal<>();

    public static Account getAccount() {
        return accountThreadLocal.get();
    }

    public static void setAccount(Account account) {
        accountThreadLocal.set(account);
    }

    public static void clear() {
        accountThreadLocal.remove();
    }
}
