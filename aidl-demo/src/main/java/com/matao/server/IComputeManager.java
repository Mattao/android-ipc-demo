package com.matao.server;

import android.os.IInterface;

/**
 * Define services that the RemoveServer provides.
 */
public interface IComputeManager extends IInterface {

    String DESCRIPTOR = "com.matao.server.IComputeManager";

    int add(int a, int b);
}
