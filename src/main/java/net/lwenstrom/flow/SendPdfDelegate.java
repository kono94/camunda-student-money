package net.lwenstrom.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class SendPdfDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SendPdfDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Sende PDF");
    }
}
