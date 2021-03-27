package net.lwenstrom;

import java.util.logging.Logger;

import net.lwenstrom.mock.StudentTable;
import net.lwenstrom.util.AuthorizationManager;
import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;

import org.camunda.bpm.engine.ProcessEngine;

import static org.camunda.bpm.engine.authorization.Permissions.*;


/**
 * Process Application exposing this application's resources the process engine.
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {

    private final Logger LOGGER = Logger.getLogger(CamundaBpmProcessApplication.class.getName());

    /**
     * In a @PostDeploy Hook you can interact with the process engine and access
     * the processes the application has deployed.
     * <p>
     * docker run -d --name camunda -it -p 8080:8080 -e PREPEND_JAVA_OPTS='-Dfile.encoding=UTF-8' camunda/camunda-bpm-platform:latest
     */
    @PostDeploy
    public void onDeploymentFinished(ProcessEngine engine) {
        LOGGER.info("Application has been deployed");

        //TODO: delete for "production"
        StudentTable.getInstance().reset();

        AuthorizationManager am = new AuthorizationManager(engine);
        try {
            // Delete default users and groups
            LOGGER.info("Lösche existierende User...");
            am.deleteUser("john");
            am.deleteUser("peter");
            am.deleteUser("mary");
            LOGGER.info("Lösche existierende Gruppen...");
            am.deleteGroup("accounting");
            am.deleteGroup("management");
            am.deleteGroup("sales");
            LOGGER.info("Lösche existierende TaskFilter...");
            am.deleteFilter("Mary's Tasks");
            am.deleteFilter("Peter's Tasks");
            am.deleteFilter("John's Tasks");
            am.deleteFilter("Accounting");
            am.deleteFilter("All Tasks");


            // Create users
            LOGGER.info("Erzeuge neue Nutzer (Judith, Alexander, Sabine und Christian)...");
            am.createNewUser("judith", "Judith", "Moller", "judith", "jmoller@asta.hs-bremerhaven.de");
            am.createNewUser("alexander", "Alexander", "Schulz", "alexander", "aschulz@iup.hs-bremerhaven.de");
            am.createNewUser("sabine", "Sabine", "Kreesmann", "sabine", "skreesmann@stadt-bremerhaven.de");
            am.createNewUser("christian", "Christian", "Erdnuss", "christian", "cerdnuss@stadt-bremerhaven.de");


            // Create groups (AStA, IuP and city)
            LOGGER.info("Erzeuge neue Gruppen AStA, IuP und Stadt Bremerhaven...");
            am.createNewGroup("asta", "AStA");
            am.createNewGroup("iup", "IuP");
            am.createNewGroup("city", "Stadt Bremerhaven");


            //  Add users to respective groups
            LOGGER.info("Füge Nutzer den einzelnen Gruppen zu...");
            am.createMembership("judith", "asta");
            am.createMembership("alexander", "iup");
            am.createMembership("sabine", "city");
            am.createMembership("christian", "city");


            // authorize groups for tasklist only:
            am.setTasklistAuthorizationForGroup("asta", ProcessConstants.PROCESS_DEFINITION_KEY, READ, READ_HISTORY, READ_INSTANCE);
            am.setTasklistAuthorizationForGroup("iup", ProcessConstants.PROCESS_DEFINITION_KEY, READ, READ_HISTORY, READ_INSTANCE);
            am.setTasklistAuthorizationForGroup("city", ProcessConstants.PROCESS_DEFINITION_KEY, READ, READ_HISTORY, READ_INSTANCE);


            // set user permissions in group
            am.setUserPermissionInGroup("asta", "judith", READ, READ_INSTANCE);
            am.setUserPermissionInGroup("iup", "alexander", READ, READ_INSTANCE);
            am.setUserPermissionInGroup("city", "sabine", READ, READ_INSTANCE);
            am.setUserPermissionInGroup("city", "christian", READ, READ_INSTANCE);


            // create default filters
            LOGGER.info("Erzeuge neue Filter für Gruppen...");
            am.createGroupFilter("Aufgaben für Gruppe AStA", -3, "asta", "AStA", "demo", READ, READ_INSTANCE);
            am.createGroupFilter("Aufgaben für Gruppe IuP", -3, "iup", "IuP", "demo", READ, READ_INSTANCE);
            am.createGroupFilter("Aufgaben für Gruppe Stadt", -3, "city", "Stadt Bremerhaven", "demo", READ, READ_INSTANCE);
            am.createAllTasksFilter();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }


        // start an initial process instance
//    Map<String, Object> variables = new HashMap<String, Object>();
//    variables.put("name", "John");
//
//    processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
    }
}
