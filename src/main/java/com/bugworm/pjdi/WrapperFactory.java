package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
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
     * Waits for target VM to attach to ListeningConnector named by param.
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
    //TODO
    //attach
    //launch
}
