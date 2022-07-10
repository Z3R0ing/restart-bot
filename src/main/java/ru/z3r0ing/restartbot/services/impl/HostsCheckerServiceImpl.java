package ru.z3r0ing.restartbot.services.impl;

import org.springframework.stereotype.Service;
import ru.z3r0ing.restartbot.services.HostsCheckerService;
import ru.z3r0ing.restartbot.utils.WebUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service(HostsCheckerService.NAME)
public class HostsCheckerServiceImpl implements HostsCheckerService {

    @Override
    public Map<String, Boolean> getHostsStatus() throws UnknownHostException, IOException {
        Properties hosts = new Properties();
        // load a properties file
        InputStream input = new FileInputStream("./src/main/resources/host_list.properties");
        hosts.load(input);

        // result map
        Map<String, Boolean> result = new HashMap<>();

        // for every host in prop file
        for (Map.Entry<Object, Object> entry : hosts.entrySet()) {
            String name = entry.getKey().toString();
            String host = entry.getValue().toString();
            // check host
            boolean isReachable = WebUtils.isHostReachable(host);
            // put result in map with name
            result.put(name, isReachable);
        }

        return result;
    }

}
