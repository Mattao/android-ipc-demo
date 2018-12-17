package com.matao.server;

import android.os.IInterface;

/**
 * Define services that the RemoveServer provides.
 */
public interface ICompute extends IInterface {

    String DESCRIPTOR = "com.matao.server.ICompute";

    int add(int a, int b);
}
