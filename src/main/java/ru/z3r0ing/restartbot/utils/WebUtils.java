package ru.z3r0ing.restartbot.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WebUtils {

    private WebUtils() {}

    /**
     * Test whether that host is reachable
     * @param host the specified host (host name or textual representation of an IP address)
     * @return a boolean indicating if the host is reachable
     * @throws UnknownHostException if no IP address for the host could be found.
     * @throws IOException if a network error occurs
     */
    public static boolean isHostReachable(String host) throws UnknownHostException, IOException {
        InetAddress address = InetAddress.getByName(host);
        return address.isReachable(10000);
    }

}
