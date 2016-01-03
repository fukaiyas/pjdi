package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.ListeningConnector;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Builder class for VirtualMachineWrapper by ListeningConnector.
 */
public class ListeningConnectorWrapperBuilder extends WrapperBuilder<ListeningConnectorWrapperBuilder>{

    /** Port number */
    protected OptionalInt listeningPort = OptionalInt.empty();

    /** Timeout */
    protected OptionalInt timeout = OptionalInt.empty();

    /** Local address */
    protected Optional<String> localAdress = Optional.empty();

    /**
     * Constructs a ListeningConnectorWrapperBuilder with default VirtualMachineManager object.
     */
    public ListeningConnectorWrapperBuilder(){
        this(Bootstrap.virtualMachineManager());
    }

    /**
     * Constructs a ListeningConnectorWrapeerBuilder with the specified VirtualMachineManager object.
     * @param vmm VirtualMachineManager object
     */
    public ListeningConnectorWrapperBuilder(VirtualMachineManager vmm){
        super(vmm);
    }

    /**
     * Sets the port number property.
     * @param port Port number
     * @return This instance
     */
    public ListeningConnectorWrapperBuilder port(int port){
        listeningPort = OptionalInt.of(port);
        return this;
    }

    /**
     * Sets the timeout property,
     * @param time Timeout(ms)
     * @return This instance
     */
    public ListeningConnectorWrapperBuilder timeout(int time){
        timeout = OptionalInt.of(time);
        return this;
    }

    /**
     * Sets the local address property.
     * @param address Local address
     * @return This instance
     */
    public ListeningConnectorWrapperBuilder localAddress(String address){
        localAdress = Optional.of(address);
        return this;
    }

    /**
     * Waits for a target VM to attach and return VirtualMachineWrapper object.
     * @return VirtualMachineWrapper object
     */
    public VirtualMachineWrapper accept(){

        ListeningConnector connector = manager.listeningConnectors().stream()
                .filter(lc -> lc.name().equals(name.orElse("com.sun.jdi.SocketListen")))
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
