package uk.edwinek.api;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class GreeterApiImpl implements GreeterApi {

    @Override
    public String greet(String name) {
        return "Hiya, " + name + ".";
    }
}