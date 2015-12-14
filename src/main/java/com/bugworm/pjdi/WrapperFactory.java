package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.ListeningConnector;

import java.util.Map;

/**
 * Factory class for VirtualMachineWrapper.
 */
public class WrapperFactory {

    private final VirtualMachineManager manager;

    public static WrapperFactory newInstance(){
        return new WrapperFactory();
    }

    public WrapperFactory(){
        this(Bootstrap.virtualMachineManager());
    }

    public WrapperFactory(VirtualMachineManager vmm){
        manager = vmm;
    }

    /**
     * Waits for target VM to attach to ListeningConnector named "com.sun.jdi.SocketListen"
     * @param port port number
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper accept(int port){
        return accept(port, "com.sun.jdi.SocketListen");
    }

    /**
     * Waits for target VM to attach to ListeningConnector select by param.
     * @param port port number
     * @param name connector name
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper accept(int port, String name){

        ListeningConnector connector = manager.listeningConnectors().stream()
                .filter(lc -> lc.name().equals(name))
                .findAny()
                .orElseThrow(() -> new PjdiException("Can't find ListeningConnector. name=" + name));

        Map<String, Connector.Argument> map = connector.defaultArguments();
        map.get("port").setValue(String.valueOf(port));
        try{
            VirtualMachine vm = connector.accept(map);
            return new VirtualMachineWrapper(vm);
        }catch(Exception e){
            throw new PjdiException("Accept failed.", e);
        }
    }

    /**
     * Attaches to a running application with AttachingConnector named "com.sun.jdi.SocketAttach" and returns a VirtualMachineWrapper object.
     * @param hostname host name to attach
     * @param port port number
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper attach(String hostname, int port){
        return attach(hostname, port, "com.sun.jdi.SocketAttach");
    }

    /**
     * Attaches to a running application with AttachingConnector select by param and returns a VirtualMachineWrapper object.
     * @param hostname host name to attach
     * @param port port number
     * @param name connector name
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper attach(String hostname, int port, String name){

        AttachingConnector connector = manager.attachingConnectors().stream()
                .filter(ac -> ac.name().equals(name))
                .findAny()
                .orElseThrow(() -> new PjdiException("Can't find AttachingConnector. name=" + name));

        Map<String, Connector.Argument> map = connector.defaultArguments();
        map.get("hostname").setValue(hostname);
        map.get("port").setValue(String.valueOf(port));
        try{
            VirtualMachine vm = connector.attach(map);
            return new VirtualMachineWrapper(vm);
        }catch(Exception e){
            throw new PjdiException("Attach failed.", e);
        }
    }
    //TODO
    //launch
}
