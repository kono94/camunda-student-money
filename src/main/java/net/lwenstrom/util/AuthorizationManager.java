package net.lwenstrom.util;

import net.lwenstrom.CamundaBpmProcessApplication;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ACCESS;
import static org.camunda.bpm.engine.authorization.Resources.*;

public class AuthorizationManager {

    private final Logger LOGGER = Logger.getLogger(AuthorizationManager.class.getName());

    final IdentityServiceImpl identityService;
    final AuthorizationService authorizationService;
    final FilterService filterService;
    final TaskService taskService;

    public AuthorizationManager(ProcessEngine engine) {
        identityService = (IdentityServiceImpl) engine.getIdentityService();
        authorizationService = engine.getAuthorizationService();
        filterService = engine.getFilterService();
        taskService = engine.getTaskService();
    }

    public void deleteUser(String userId){
        identityService.deleteUser("john");
    }

    public void deleteGroup(String groupId){
        identityService.deleteGroup(groupId);
    }

    public void deleteFilter(String filterName){
        Filter filter = filterService.createFilterQuery().filterName(filterName).singleResult();
        filterService.deleteFilter(filter.getId());
    }

    public void createMembership(String groupId, String userId){
        identityService.createMembership(groupId, userId);
    }
    public void createNewUser(String userId,
                              String firstName,
                              String lastName,
                              String password,
                              String email) {
        User user = identityService.newUser(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        identityService.saveUser(user);
    }

    public void createNewGroup(String groupId, String name) {
        Group astaGroup = identityService.newGroup(groupId);
        astaGroup.setName(name);
        astaGroup.setType("WORKFLOW");
        identityService.saveGroup(astaGroup);
    }

    public void setTasklistAuthorizationForGroup(String groupId, String processDefinition, Permissions... permissions) {
        Authorization tasklistAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        tasklistAuth.setGroupId(groupId);
        tasklistAuth.addPermission(ACCESS);
        tasklistAuth.setResourceId("tasklist");
        tasklistAuth.setResource(APPLICATION);
        authorizationService.saveAuthorization(tasklistAuth);

        Authorization readProcessDefinition = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        readProcessDefinition.setGroupId(groupId);
        for (Permissions p : permissions) {
            readProcessDefinition.addPermission(p);
        }
        readProcessDefinition.setResource(Resources.PROCESS_DEFINITION);
        // restrict to invoice process definition only
        readProcessDefinition.setResourceId(processDefinition);
        authorizationService.saveAuthorization(readProcessDefinition);
    }

    public void setUserPermissionInGroup(String groupId, String resourceId, Permissions... permissions) {
        Authorization authorization = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        authorization.setGroupId(groupId);
        authorization.setResource(USER);
        authorization.setResourceId(resourceId);
        for (Permissions p : permissions) {
            authorization.addPermission(p);
        }
       authorizationService.saveAuthorization(authorization);
    }


    public void createGroupFilter(String description,
                                  int priority,
                                  String groupId,
                                  String filterName,
                                  String owner,
                                  Permissions... permissions) {
        Map<String, Object> filterProperties = new HashMap<>();
        filterProperties.put("description", description);
        filterProperties.put("priority", priority);
        // addVariables(filterProperties);
        TaskQuery query = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(groupId)).taskUnassigned();
        Filter candidateGroupTasksFilter = filterService.newTaskFilter().setName(filterName).setProperties(filterProperties).setOwner(owner).setQuery(query);
        filterService.saveFilter(candidateGroupTasksFilter);

        Authorization managementGroupFilterRead = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        managementGroupFilterRead.setResource(FILTER);
        managementGroupFilterRead.setResourceId(candidateGroupTasksFilter.getId());
        for (Permissions p : permissions) {
            managementGroupFilterRead.addPermission(p);
        }
        managementGroupFilterRead.setGroupId(groupId);
        authorizationService.saveAuthorization(managementGroupFilterRead);

    }
}
