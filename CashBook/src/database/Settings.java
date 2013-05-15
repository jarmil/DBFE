package database;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Settings {
    private static Settings settings;
    private Map<String, Object> properties;
    private final String settingPath = System.getProperty("user.home") + File.separator
            + "CashBook" + File.separator + "settings.xml";




    private Settings() {
        if (!load()) {
            int opt = JOptionPane
                    .showConfirmDialog(
                            null,
                            "Nepodařilo se načíst soubor s nastavením programu, chcete použít výchozí nastavení a uložit ho?");
            if (opt == JOptionPane.YES_OPTION) {
                properties = defaultDatabaseSetting();
                save();
            } else {
                XMLWorkSet.getInstance().saveErrorTrace(new RuntimeException("Uzivatel odpovedel NE na vytvoreni nastaveni"), "Uzivatel nepotvrdil vytvoreni defayult nastaveni v konstruktoru settings");
                System.exit(1);
            }
        }
    }

    public static Settings getSetting() {
        if(settings == null)
            settings = new Settings();
        return settings;
    }

    public Object get(String key){

        return properties.get(key);
    }

    private Map<String, Object> defaultDatabaseSetting() {

        String workPath = System.getProperty("user.home") + File.separator
                + "CashBook" + File.separator;
        String rootPath = System.getProperty("user.dir") + File.separator;

        properties = new HashMap<String, Object>();
        properties.put("statisticsNameAndPath",workPath + "statistics.xml");
        properties.put("databaseNameAndPath",workPath + "database.xml");
        properties.put(
                "elementNames",new String[]{"date", "type", "money",
                        "description"});
        properties.put(
                "tableHeaderTitles",new String[]{"Datum", "Typ", "Částka",
                        "Popis"});
        properties.put(
                "tableHeaderCount",4);
        properties.put(
                "typePosibilities",new String[]{"Vklad", "Výběr",
                        "Cestování", "Daně", "Dary", "Dům", "Elektřina",
                        "Hobby", "Hygiena", "Inkaso", "Interiér",
                        "Kultura", "Mzda", "Oděvy", "Ostatní", "Plyn",
                        "Pojistné", "Potraviny", "Převod", "Splátky",
                        "Spoření", "Škola", "Telefon", "Tisk", "Úroky",
                        "Úspory", "Voda", "Výpomoc", "Zdraví"});
        properties.put("rootDir",rootPath);
        properties.put("workDir",workPath);
        return properties;
    }


    @SuppressWarnings("unchecked")
    public boolean load() {
        try {
            properties = (Map<String, Object>) XMLWorkSet.getInstance().load(
                    settingPath);
            if (properties == null || properties.size() == 0) {
                int opt = JOptionPane
                        .showConfirmDialog(
                                null,
                                "Nepodařilo se načíst soubor s nastavením programu, chcete použít výchozí nastavení a uložit ho?");
                if (opt == JOptionPane.YES_OPTION) {
                    properties = defaultDatabaseSetting();
                    XMLWorkSet.getInstance().save(settingPath, properties);
                    return true;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            XMLWorkSet.getInstance().saveErrorTrace(e, "Problem pri nacitani settings v load funkci");
                return false;

        }
        return true;

    }

    private void save() {
        try {
            XMLWorkSet.getInstance().save(settingPath, properties);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                e.printStackTrace(new PrintWriter(settingPath));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

    }
}
