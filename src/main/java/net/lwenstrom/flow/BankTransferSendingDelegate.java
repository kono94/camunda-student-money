package net.lwenstrom.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class BankTransferSendingDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(BankTransferSendingDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("BankTransferSendingDelegate called");
    }
}
