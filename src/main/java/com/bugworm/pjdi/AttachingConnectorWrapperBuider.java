package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Builder class for VirtualMachineWrapper by AttachingConnector,
 */
public class AttachingConnectorWrapperBuider extends WrapperBuilder<AttachingConnectorWrapperBuider>{

    /** Port number */
    protected OptionalInt attachingPort = OptionalInt.empty();

    /** Timeout */
    protected OptionalInt timeout = OptionalInt.empty();

    /** Hostname */
    protected Optional<String> hostname = Optional.empty();

    /**
     * Constructs a AttachingConnectorWrapperBuider with default VirtualMachineManager object.
     */
    public AttachingConnectorWrapperBuider(){
        this(Bootstrap.virtualMachineManager());
    }

    /**
     * Constructs a AttachingConnectorWrapperBuider with the specified VirtualMachineManager object.
     * @param vmm VirtualMachineManager object
     */
    public AttachingConnectorWrapperBuider(VirtualMachineManager vmm){
        super(vmm);
    }

    /**
     * Sets the port number property.
     * @param port Port number
     * @return This instance
     */
    public AttachingConnectorWrapperBuider port(int port){
        attachingPort = OptionalInt.of(port);
        return this;
    }

    /**
     * Sets the timeout property,
     * @param time Timeout(ms)
     * @return This instance
     */
    public AttachingConnectorWrapperBuider timeout(int time){
        timeout = OptionalInt.of(time);
        return this;
    }

    /**
     * Sets the hostname property.
     * @param host Hostname
     * @return This instance
     */
    public AttachingConnectorWrapperBuider localAddress(String host){
        hostname = Optional.of(host);
        return this;
    }

    /**
     * Attaches to a running application and returns VirtualMachineWrapper object.
     * @return VirtualMachineWrapper object
     */
    public VirtualMachineWrapper attach(){

        AttachingConnector connector = manager.attachingConnectors().stream()
                .filter(ac -> ac.name().equals(name.orElse("com.sun.jdi.SocketAttach")))
                .findAny()
                .orElseThrow(() -> new PjdiException("Can't find AttachingConnector."));

        Map<String, Connector.Argument> map = connector.defaultArguments();
        attachingPort.ifPresent(p -> map.get("port").setValue(String.valueOf(p)));
        suspendMode.ifPresent(s -> map.get("suspend").setValue(String.valueOf(s)));
        timeout.ifPresent(t -> map.get("timeout").setValue(String.valueOf(t)));
        hostname.ifPresent(l -> map.get("hostname").setValue(l));

        try{
            return new VirtualMachineWrapper(connector.attach(map));
        }catch(Exception e){
            throw new PjdiException("Attach failed.", e);
        }
    }
}
