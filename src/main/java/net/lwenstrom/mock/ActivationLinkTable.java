package net.lwenstrom.mock;

import java.util.Hashtable;
import java.util.Map;


public class ActivationLinkTable implements TableMock<String, ActivationLinkTableEntry> {

    private static ActivationLinkTable instance;
    private Map<String, ActivationLinkTableEntry> activationLinks;

    private ActivationLinkTable() {
        activationLinks = new Hashtable<>();
    }


    public static ActivationLinkTable getInstance() {
        if (ActivationLinkTable.instance == null) {
            ActivationLinkTable.instance = new ActivationLinkTable();
        }
        return ActivationLinkTable.instance;
    }


    @Override
    public void save(ActivationLinkTableEntry entry) {
        activationLinks.put(entry.getActivationLink(), entry);
    }

    @Override
    public boolean contains(String link) {
        return activationLinks.containsKey(link);
    }

    @Override
    public ActivationLinkTableEntry search(String link) {
        return activationLinks.get(link);
    }

    @Override
    public void delete(String link) {
        activationLinks.remove(link);
    }

    @Override
    public int count() {
        return activationLinks.size();
    }
}
