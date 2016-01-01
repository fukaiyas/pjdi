package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.ListeningConnector;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class ListeningConnectorWrapperBuilder extends WrapperBuilder<ListeningConnectorWrapperBuilder>{

    protected OptionalInt listeningPort = OptionalInt.empty();
    protected OptionalInt timeout = OptionalInt.empty();
    protected Optional<String> localAdress = Optional.empty();

    public ListeningConnectorWrapperBuilder(){
        this(Bootstrap.virtualMachineManager());
    }

    public ListeningConnectorWrapperBuilder(VirtualMachineManager vmm){
        super(vmm);
    }

    public ListeningConnectorWrapperBuilder port(int port){
        listeningPort = OptionalInt.of(port);
        return this;
    }

    public ListeningConnectorWrapperBuilder timeout(int time){
        timeout = OptionalInt.of(time);
        return this;
    }

    public ListeningConnectorWrapperBuilder localAddress(String address){
        localAdress = Optional.of(address);
        return this;
    }

    public VirtualMachineWrapper accept(){

        ListeningConnector connector = manager.listeningConnectors().stream()
                .filter(lc -> lc.name().equals("com.sun.jdi.SocketListen"))
                .findAny()
                .orElseThrow(() -> new PjdiException("Can't find ListeningConnector."));

        Map<String, Connector.Argument> map = connector.defaultArguments();
        listeningPort.ifPresent(p -> map.get("port").setValue(String.valueOf(p)));
        suspendMode.ifPresent(s -> map.get("suspend").setValue(String.valueOf(s)));
        timeout.ifPresent(t -> map.get("timeout").setValue(String.valueOf(t)));
        localAdress.ifPresent(l -> map.get("localAddress").setValue(l));

        try{
            return new VirtualMachineWrapper(connector.accept(map));
        }catch(Exception e){
            throw new PjdiException("Accept failed.", e);
        }
    }
}
