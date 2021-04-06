package net.lwenstrom.flow;

import net.lwenstrom.ProcessConstants;
import net.lwenstrom.model.BankTransfer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class BankTransferSendingDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(BankTransferSendingDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("BankTransferSendingDelegate called");

        BankTransfer bankTransfer = (BankTransfer) execution.getVariable(ProcessConstants.VAR_BANK_TRANSFER);

        LOGGER.info("Sending " + bankTransfer.getXmlString());
    }
}
