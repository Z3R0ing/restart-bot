package ru.z3r0ing.restartbot.services;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

public interface HostsCheckerService {

    /**
     * Checks all hosts from host list (./src/main/resources/host_list.properties)
     * @return map with result. Key is name, value is status.
     * @throws UnknownHostException if no IP address for a some host could be found.
     * @throws IOException if a network error occurs or an error occurred when reading from the input stream
     */
    Map<String, Boolean> getHostsStatus() throws UnknownHostException, IOException;

}
