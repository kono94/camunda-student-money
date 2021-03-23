package net.lwenstrom.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class PdfGenerationDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(PdfGenerationDelegate.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {
         LOGGER.info("PDF GENERATION");
    }
}
