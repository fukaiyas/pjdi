package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.ListeningConnector;

import java.util.Map;

/**
 * Factory class for VirtualMachineWrapper.
 */
public class WrapperFactory {

    private final VirtualMachineManager manager;
    private final boolean suspendMode;

    public static WrapperFactory newInstance(){
        return new WrapperFactory();
    }

    public static WrapperFactory newInstance(boolean suspend){
        return new WrapperFactory();
    }

    /**
     * Use Bootstrap.virtualMachineManager() and set suspend property true.
     */
    public WrapperFactory(){
        this(Bootstrap.virtualMachineManager(), true);
    }

    /**
     * Use specified VirtualMachineManager and set suspend property true.
     * @param vmm VirtualMachineManager
     */
    public WrapperFactory(VirtualMachineManager vmm){
        this(vmm, true);
    }

    /**
     * Use specified VirtualMachineManager and suspend property.
     * @param vmm VirtualMachineManager
     * @param suspend Suspend property
     */
    public WrapperFactory(VirtualMachineManager vmm, boolean suspend){
        manager = vmm;
        suspendMode = suspend;
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
        map.get("suspend").setValue(String.valueOf(suspendMode));
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
        map.get("suspend").setValue(String.valueOf(suspendMode));
        try{
            VirtualMachine vm = connector.attach(map);
            return new VirtualMachineWrapper(vm);
        }catch(Exception e){
            throw new PjdiException("Attach failed.", e);
        }
    }

    /**
     * Launches an application with system classpath, connects to its VM and returns a VirtualMachineWrapper object.
     * @param className main class name
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper launch(String className){
        return launch(className, "");//TODO システムのクラスパスを指定して実行する

    }

    /**
     * Launches an application, connects to its VM and returns a VirtualMachineWrapper object.
     * @param className main class name
     * @param options options (require to include classpath)
     * @return VirtualMachineWrapper
     */
    public VirtualMachineWrapper launch(String className, String options){
        //TODO 引数も指定できるはず
        //TODO LaunchingConnectorの場合に名前指定のメソッド必要か？

        LaunchingConnector connector = manager.defaultConnector();
        Map<String, Connector.Argument> map = connector.defaultArguments();
        map.get("main").setValue(className);
        map.get("options").setValue(options);
        map.get("suspend").setValue(String.valueOf(suspendMode));
        try{
            VirtualMachine vm = connector.launch(map);
            //TODO System.outとSystem.errはリダイレクトできるようにしたい
            //TODO VirtualMachineWrapperに持たせるか？
            return new VirtualMachineWrapper(vm);
        }catch(Exception e){
            throw new PjdiException("Launch failed.", e);
        }
    }
}
