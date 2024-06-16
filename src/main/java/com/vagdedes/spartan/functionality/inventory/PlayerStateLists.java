package com.vagdedes.spartan.functionality.inventory;

import com.vagdedes.spartan.abstraction.profiling.PlayerProfile;
import com.vagdedes.spartan.functionality.performance.ResearchEngine;
import com.vagdedes.spartan.functionality.server.SpartanBukkit;
import com.vagdedes.spartan.utils.minecraft.inventory.InventoryUtils;
import me.vagdedes.spartan.system.Enums;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PlayerStateLists {

    public static final String
            inactiveColour = "§8",
            noDataAvailable = "No data available at this time";
    private static final int[] ignoredSlots = new int[]{
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 16, 17,
            18, 19, 25, 26,
            27, 28, 34, 35,
            36, 37, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    private static final Map<UUID, Integer> cache = new LinkedHashMap<>();

    private static void fill(Inventory inventory, PlayerProfile profile,
                             List<String> description, int slot) {
        ItemStack item = InventoryUtils.getHead();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c" + profile.getName());

        List<String> lore = new ArrayList<>(description.size() + 10);
        InventoryUtils.prepareDescription(
                lore,
                profile.evidence.getType().toString()
        );

        if (!description.isEmpty()) {
            lore.addAll(description);
            lore.add("");
        }
        lore.add("§7Click this item to §eview the player's information§7.");

        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);

        SpartanBukkit.dataThread.executeWithPriority(() -> {
            ItemStack itemNew = profile.getSkull(true);
            ItemMeta metaNew = itemNew.getItemMeta();
            metaNew.setDisplayName(meta.getDisplayName());
            metaNew.setLore(lore);
            itemNew.setItemMeta(metaNew);
            inventory.setItem(slot, itemNew);
        });
    }

    public static int getPage(UUID uuid) {
        return cache.getOrDefault(uuid, 1);
    }

    public static boolean nextPage(UUID uuid) {
        int page = getPage(uuid);

        if (page < 999) {
            cache.put(uuid, page + 1);
            return true;
        }
        return false;
    }

    public static boolean previousPage(UUID uuid) {
        int page = getPage(uuid);

        if (page > 1) {
            cache.put(uuid, page - 1);
            return true;
        }
        return false;
    }

    private static List<PlayerProfile> getProfiles() {
        List<PlayerProfile> playerProfiles = ResearchEngine.getPlayerProfiles();

        if (!playerProfiles.isEmpty()) {
            List<PlayerProfile> list = new ArrayList<>(playerProfiles.size());

            for (PlayerProfile playerProfile : playerProfiles) {
                if (!playerProfile.isLegitimate()) {
                    list.add(playerProfile);
                }
            }
            return list;
        } else {
            return new ArrayList<>(0);
        }
    }

    public static void fill(UUID uuid, Inventory inventory) {
        List<String> lore = new ArrayList<>(20);
        int slotPosition = 0, limit = 15;
        Integer[] freeSlots = getFreeSlots(inventory);

        int listSize, page = getPage(uuid), skip = ((page - 1) * limit);
        List<PlayerProfile> playerProfiles = subList(getProfiles(), skip, skip + limit);

        if ((listSize = playerProfiles.size()) > 0) {
            for (PlayerProfile playerProfile : playerProfiles) {
                Collection<Enums.HackType> evidenceDetails = playerProfile.evidence.getKnowledgeList(false);

                if (!evidenceDetails.isEmpty()) {
                    lore.clear();
                    lore.add("§7Detected for§8:");

                    for (Enums.HackType hackType : evidenceDetails) {
                        lore.add("§4" + hackType.getCheck().getName());
                    }
                    fill(inventory, playerProfile, lore, freeSlots[slotPosition]);
                    slotPosition++;
                } else {
                    listSize--;
                }
            }

            if (listSize != limit) {
                InventoryUtils.prepareDescription(lore, noDataAvailable);
                lore.add("§cEmpty items like this will be filled with");
                lore.add("§cuseful information about your players");
                lore.add("§cas Spartan learns more about your server.");

                for (int i = listSize; i < limit; i++) {
                    InventoryUtils.add(inventory, inactiveColour + "Empty", lore, InventoryUtils.getHead(), freeSlots[slotPosition]);
                    slotPosition++;
                }
            }
        }
    }

    private static Integer[] getFreeSlots(Inventory inventory) {
        int slot = 0, maxSize = 45;
        List<Integer> array = new ArrayList<>(Math.min(inventory.getSize(), maxSize));

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null) {
                slot++;
            } else {
                boolean add = true;

                for (int ignoredSlot : ignoredSlots) {
                    if (ignoredSlot == slot) {
                        add = false;
                        break;
                    }
                }

                if (add) {
                    array.add(slot);
                }
                if (slot == maxSize) {
                    break;
                }
                slot++;
            }
        }
        return array.toArray(new Integer[0]);
    }

    private static <E> List<E> subList(List<E> list, int startIndex, int toIndex) {
        int size = list.size();
        return startIndex > size ? new ArrayList<>(0) :
                list.subList(startIndex, Math.min(toIndex, size));
    }
}
