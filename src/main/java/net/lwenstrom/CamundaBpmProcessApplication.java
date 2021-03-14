package net.lwenstrom;

import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;

/**
 * Process Application exposing this application's resources the process engine.
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {


  /**
   * In a @PostDeploy Hook you can interact with the process engine and access
   * the processes the application has deployed.
   *
   * docker run -d --name camunda -it -p 8080:8080 -e PREPEND_JAVA_OPTS='-Dfile.encoding=UTF-8' camunda/camunda-bpm-platform:latest
   */
  @PostDeploy
  public void onDeploymentFinished(ProcessEngine processEngine) {

    // start an initial process instance
//    Map<String, Object> variables = new HashMap<String, Object>();
//    variables.put("name", "John");
//
//    processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
  }

}
