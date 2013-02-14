/*
 *  Copyright:
 *  2013 Darius Mewes
 */

package de.dariusmewes.TimoliaCustom.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import de.dariusmewes.TimoliaCustom.Message;
import de.dariusmewes.TimoliaCustom.TimoliaCustom;

public class protnpc extends TCommand {

	public static List<String> active = new ArrayList<String>();
	public static List<UUID> prot = new ArrayList<UUID>();

	public protnpc(String name) {
		super(name);
		setIngame();
		setMaxArgs(0);
		setUsage("/protnpc");
		setDesc("Protecte Villager");
	}

	public void perform(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		active.add(p.getName());
		sender.sendMessage(prefix + "Bitte klicke auf einen Villager um ihn zu protecten!");
	}

	public static void addName(String name, Villager npc) {
		try {
			Location loc = npc.getLocation();
			String loca = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
			File file = new File(TimoliaCustom.dataFolder + File.separator + "schlaeger.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
			String date = new SimpleDateFormat("dd. MMM. yyyy, HH:mm").format(System.currentTimeMillis());
			output.append(date + "\t" + name + "\t" + loca + "\t" + npc.getProfession().name());
			output.newLine();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			File file = new File(TimoliaCustom.dataFolder + File.separator + "uuids.custom");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(prot);
			oos.close();
		} catch (Exception e) {
			Message.console("Fehler beim Speichern:");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void load() {
		try {
			File file = new File(TimoliaCustom.dataFolder + File.separator + "uuids.custom");
			if (file.exists()) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				prot = (List<UUID>) ois.readObject();
				ois.close();
			}
		} catch (Exception e) {
			Message.console("Fehler beim Laden:");
			e.printStackTrace();
		}
	}

}