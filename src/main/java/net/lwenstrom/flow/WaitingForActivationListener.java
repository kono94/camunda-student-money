package net.lwenstrom.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

import java.util.logging.Logger;

public class WaitingForActivationListener implements ExecutionListener {

    private final Logger LOGGER = Logger.getLogger(WaitingForActivationListener.class.getName());

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        execution.getProcessBusinessKey();
        LOGGER.info("Waiting for activation link being clicked (executionId="+ execution.getId() + ")");
    }
}
