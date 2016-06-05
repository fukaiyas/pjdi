package com.bugworm.pjdi;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Builder class for VirtualMachineWrapper by LaunchingConnector.
 */
public class LaunchingConnectorWrapperBuilder extends WrapperBuilder<LaunchingConnectorWrapperBuilder>{

    /** JAVA_HOME for target VM */
    protected Optional<String> home = Optional.empty();

    /** Options for java command to start target VM */
    protected Optional<String> options = Optional.empty();

    /** The main class  */
    protected Optional<String> main = Optional.empty();

    /** Whether to suspend when target VM started */
    protected Optional<Boolean> suspendMode = Optional.empty();

    /** The character used to combine space-delimited text on the command line */
    protected Optional<String> quote = Optional.empty();

    /** The VM launcher executable */
    protected Optional<String> vmexec = Optional.empty();

    /**
     * Constructs a LaunchingConnectorWrapperBuilder with default VirtualMachineManager object.
     */
    public LaunchingConnectorWrapperBuilder(){
        this(Bootstrap.virtualMachineManager());
    }

    /**
     * Constructs a LaunchingConnectorWrapperBuilder with the specified VirtualMachineManager object.
     *
     * @param vmm VirtualMachineManager object
     */
    public LaunchingConnectorWrapperBuilder(VirtualMachineManager vmm){
        super(vmm);
    }

    /**
     * Sets the home property.
     * @param javaHome JAVA_HOME
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder home(String javaHome){
        home = Optional.of(javaHome);
        return this;
    }

    /**
     * Sets the options property.
     * @param opts Options
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder options(String opts){
        options = Optional.of(opts);
        return this;
    }

    /**
     * Seets the main property.
     * @param mainClass The main class name
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder main(String mainClass){
        main = Optional.of(mainClass);
        return this;
    }

    /**
     * Sets the suspend property.
     * @param suspend Whether to suspend when target VM started
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder suspend(boolean suspend){
        suspendMode = Optional.of(suspend);
        return this;
    }

    /**
     * Sets the quote character.
     * @param qt The quote character
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder quote(String qt){
        quote = Optional.of(qt);
        return this;
    }

    /**
     * Sets the VM launcher executable.
     * @param exe The VM launcher
     * @return This instance
     */
    public LaunchingConnectorWrapperBuilder vmexec(String exe){
        vmexec = Optional.of(exe);
        return this;
    }

    /**
     * TODO
     * @return VirtualMachineWrapper object
     */
    public VirtualMachineWrapper launch(){

        LaunchingConnector connector = connectorName.isPresent() ?
                manager.launchingConnectors().stream()
                        .filter(lc -> lc.name().equals(connectorName.get()))
                        .findAny()
                        .orElseThrow(() -> new PjdiException("Can't find LaunchingConnector.")) :
                manager.defaultConnector();

        Map<String, Connector.Argument> map = connector.defaultArguments();
        home.ifPresent(h -> map.get("home").setValue(h));
        options.ifPresent(o -> map.get("options").setValue(o));
        main.ifPresent(m -> map.get("main").setValue(m));
        suspendMode.ifPresent(s -> map.get("suspend").setValue(String.valueOf(s)));
        quote.ifPresent(q -> map.get("quote").setValue(q));
        vmexec.ifPresent(v -> map.get("vmexec").setValue(v));

        ExecutorService es = Executors.newFixedThreadPool(2);
        try{
            VirtualMachineWrapper wrapper = new VirtualMachineWrapper(connector.launch(map));
            wrapper.setExecutorService(es);
            Process pr = wrapper.process();
            es.execute(new InputStream2PrintStream(pr.getInputStream(), System.out));
            es.execute(new InputStream2PrintStream(pr.getErrorStream(), System.err));
            return wrapper;
        }catch(Exception e){
            throw new PjdiException("Accept failed.", e);
        }
    }
}
