package uk.edwinek.api;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;

@JsonRpcService("/greeter")
public interface GreeterApi {
    String greet(@JsonRpcParam(value = "name") String name );
}
