package ai.idealistic.vacan.compatibility;

import ai.idealistic.vacan.Register;
import ai.idealistic.vacan.abstraction.Enums;
import ai.idealistic.vacan.abstraction.configuration.ConfigurationBuilder;
import ai.idealistic.vacan.abstraction.protocol.PlayerProtocol;
import ai.idealistic.vacan.compatibility.manual.abilities.*;
import ai.idealistic.vacan.compatibility.manual.abilities.crackshot.CrackShot;
import ai.idealistic.vacan.compatibility.manual.abilities.crackshot.CrackShotPlus;
import ai.idealistic.vacan.compatibility.manual.building.*;
import ai.idealistic.vacan.compatibility.manual.damage.RealDualWield;
import ai.idealistic.vacan.compatibility.manual.entity.CraftBook;
import ai.idealistic.vacan.compatibility.manual.entity.Vehicles;
import ai.idealistic.vacan.compatibility.necessary.Floodgate;
import ai.idealistic.vacan.compatibility.necessary.protocollib.ProtocolLib;
import ai.idealistic.vacan.functionality.moderation.AwarenessNotifications;
import ai.idealistic.vacan.functionality.server.MultiVersion;
import ai.idealistic.vacan.utils.java.ReflectionUtils;
import ai.idealistic.vacan.utils.minecraft.server.ConfigUtils;
import ai.idealistic.vacan.utils.minecraft.server.PluginUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Compatibility {

    private static final String
            fileName = "compatibility",
            staticDirectory = ConfigurationBuilder.getDirectory(fileName);
    private static File file = new File(staticDirectory);
    private static final Map<String, Boolean> bool = new LinkedHashMap<>();

    public enum CompatibilityType {
        ADVANCED_ABILITIES("AdvancedAbilities"), CRACK_SHOT("CrackShot"),
        CRACK_SHOT_PLUS("CrackShotPlus"), CRAFT_BOOK("CraftBook"),
        MAGIC_SPELLS("MagicSpells"), PROTOCOL_LIB("ProtocolLib"),
        MC_MMO("mcMMO"), PROTOCOL_SUPPORT("ProtocolSupport"),
        TREE_FELLER("TreeFeller"), VEIN_MINER("VeinMiner"),
        GRAPPLING_HOOK("GrapplingHook"), RECENT_PVP_MECHANICS("RecentPvPMechanics"),
        MINE_BOMB("MineBomb"), SUPER_PICKAXE("SuperPickaxe"),
        REAL_DUAL_WIELD("RealDualWield"), MYTHIC_MOBS("MythicMobs"),
        ITEM_ATTRIBUTES("ItemAttributes"), PRINTER_MODE("PrinterMode"),
        VEHICLES("Vehicles"), MINE_TINKER("MineTinker"),
        WILD_TOOLS("WildTools"), AURELIUM_SKILLS("AureliumSkills"),
        CUSTOM_ENCHANTS_PLUS("CustomEncahntsPlus"), ECO_ENCHANTS("EcoEnchants"),
        ITEMS_ADDER("ItemsAdder"), RAMPEN_DRILLS("RampenDrills"),
        OLD_COMBAT_MECHANICS("OldCombatMechanics"), CUSTOM_KNOCKBACK("CustomKnockback"),
        PROJECT_KORRA("ProjectKorra"), FILE_GUI("FileGUI"), FLOODGATE("Floodgate");

        @Setter
        @Getter
        private boolean enabled, forced, functional, elseRunnable;
        private final String name;

        CompatibilityType(String name) {
            this.name = name;
            this.enabled = false;
            this.forced = false;
            this.functional = false;
            this.elseRunnable = false;
        }

        @Override
        public String toString() {
            return name;
        }

        public void refresh(boolean create) {
            boolean hardcoded, contains;
            String name;

            switch (this) {
                case PROTOCOL_SUPPORT: // Necessary
                case FLOODGATE: // Necessary
                case FILE_GUI: // Local
                    hardcoded = true;
                    contains = false;
                    name = this.toString().toLowerCase();
                    break;
                default:
                    hardcoded = false;
                    contains = false;
                    name = null;
                    break;
            }

            if (hardcoded) {
                this.enabled = true;
                this.forced = false;
                this.functional = contains ? PluginUtils.contains(name) : PluginUtils.exists(name);
            } else {
                file = new File(staticDirectory);
                String compatibility = this.toString();

                if (create) {
                    ConfigUtils.add(file, compatibility + ".enabled", !this.equals(CompatibilityType.WILD_TOOLS));
                    ConfigUtils.add(file, compatibility + ".force", false);
                }
                this.enabled = getBoolean(compatibility + ".enabled", create);
                this.forced = getBoolean(compatibility + ".force", create);
            }
        }

        public boolean isFunctional() {
            return enabled && functional;
        }

        // Separator

        public void setFunctional() {
            setFunctional(new String[]{this.toString()}, null, null, null);
        }

        public void setFunctional(Runnable runnable, Runnable elseRunnable) {
            setFunctional(new String[]{this.toString()}, null, runnable, elseRunnable);
        }

        // Separator

        public void setFunctional(String[] pluginsOrClasses, CompatibilityType[] compatibilities,
                                  Runnable runnable, Runnable elseRunnable) {
            if (this.isEnabled()) {
                if (this.functional) {
                    return;
                }
                boolean function = this.isForced();

                if (!function && pluginsOrClasses != null) {
                    int count = 0, desired = 0;

                    for (String pluginOrClass : pluginsOrClasses) {
                        if (pluginOrClass.isEmpty()) {
                            function = true;
                            break;
                        } else {
                            boolean partRequirement = pluginOrClass.endsWith("+");

                            if (partRequirement) {
                                desired++;
                                pluginOrClass = pluginOrClass.substring(0, pluginOrClass.length() - 1);
                            }
                            if (pluginOrClass.contains(".") ? ReflectionUtils.classExists(pluginOrClass) :
                                    pluginOrClass.startsWith("%") ? PluginUtils.contains(pluginOrClass.substring(1)) :
                                            PluginUtils.exists(pluginOrClass)) {
                                if (partRequirement) {
                                    count++;
                                } else {
                                    function = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!function) {
                        function = desired > 0 && count == desired;
                    }
                }

                if (!function && compatibilities != null) {
                    for (CompatibilityType compatibilityType : compatibilities) {
                        if (compatibilityType.isFunctional()) {
                            function = true;
                            break;
                        }
                    }
                }

                if (function) {
                    this.elseRunnable = false;

                    if (runnable != null) {
                        try {
                            runnable.run();
                            this.functional = true;
                        } catch (Exception ex) {
                            this.functional = false;
                            AwarenessNotifications.forcefullySend("Compatibility '" + this.toString() + "' failed to load.");
                        }
                    } else {
                        this.functional = true;
                    }
                } else {
                    this.functional = false;

                    if (!this.elseRunnable && elseRunnable != null) {
                        this.elseRunnable = true;
                        elseRunnable.run();
                    }
                }
            } else if (!this.elseRunnable && elseRunnable != null) {
                this.elseRunnable = true;
                elseRunnable.run();
            }
        }
    }

    public File getFile() {
        return file;
    }

    private static void refresh(boolean create) {
        for (CompatibilityType compatibilityType : CompatibilityType.values()) {
            compatibilityType.refresh(create);
        }
        CompatibilityType.MC_MMO.setFunctional();
        CompatibilityType.TREE_FELLER.setFunctional(
                new String[]{CompatibilityType.TREE_FELLER.toString()},
                new CompatibilityType[]{CompatibilityType.MC_MMO},
                null,
                null
        );
        CompatibilityType.CRAFT_BOOK.setFunctional(CraftBook::resetBoatLimit, null);
        CompatibilityType.CRACK_SHOT.setFunctional(
                () -> Register.enable(new CrackShot()),
                null
        );
        CompatibilityType.CRACK_SHOT_PLUS.setFunctional(
                () -> Register.enable(new CrackShotPlus()),
                null
        );
        CompatibilityType.CUSTOM_KNOCKBACK.setFunctional(
                new String[]{
                        "%knockback"
                },
                null,
                null,
                null);
        CompatibilityType.REAL_DUAL_WIELD.setFunctional(
                () -> Register.enable(new RealDualWield()),
                null
        );
        CompatibilityType.MAGIC_SPELLS.setFunctional(
                () -> Register.enable(new MagicSpells()),
                null
        );
        CompatibilityType.ADVANCED_ABILITIES.setFunctional(
                () -> Register.enable(new AdvancedAbilities()),
                null
        );
        CompatibilityType.OLD_COMBAT_MECHANICS.setFunctional();
        CompatibilityType.VEIN_MINER.setFunctional(
                VeinMiner::reload,
                null
        );
        CompatibilityType.PROJECT_KORRA.setFunctional(
                () -> Register.enable(new ProjectKorra()),
                null
        );
        CompatibilityType.GRAPPLING_HOOK.setFunctional(
                () -> Register.enable(new GrapplingHook()),
                null
        );
        CompatibilityType.MYTHIC_MOBS.setFunctional(
                MythicMobs::reload,
                null
        );
        CompatibilityType.CUSTOM_ENCHANTS_PLUS.setFunctional();
        CompatibilityType.ECO_ENCHANTS.setFunctional(
                ReflectionUtils.classExists("com.willfp.ecoenchants.enchants.EcoEnchant")
        );
        CompatibilityType.VEHICLES.setFunctional(
                () -> Register.enable(new Vehicles()),
                null
        );
        CompatibilityType.MINE_TINKER.setFunctional(
                () -> Register.enable(new MineTinker()),
                null
        );
        if (MultiVersion.isOrGreater(MultiVersion.MCVersion.V1_9)) {
            CompatibilityType.ITEM_ATTRIBUTES.setFunctional(
                    new String[]{""},
                    new CompatibilityType[]{
                            CompatibilityType.MINE_TINKER,
                            CompatibilityType.MYTHIC_MOBS,
                            CompatibilityType.PROJECT_KORRA
                    },
                    null,
                    null
            );
            CompatibilityType.RECENT_PVP_MECHANICS.setFunctional(
                    new String[]{""},
                    null,
                    null,
                    null
            );
        }
        CompatibilityType.WILD_TOOLS.setFunctional(
                () -> Register.enable(new WildTools()),
                null
        );
        CompatibilityType.FLOODGATE.setFunctional(
                new String[]{
                        "%" + CompatibilityType.FLOODGATE,
                        "%geyser"
                },
                null,
                Floodgate::reload,
                null
        );
        CompatibilityType.PROTOCOL_SUPPORT.setFunctional(
                new String[]{
                        "protocolsupport.api.Connection+",
                        "protocolsupport.api.ProtocolSupportAPI+",
                        CompatibilityType.PROTOCOL_SUPPORT + "+"
                },
                null,
                null,
                null
        );
        CompatibilityType.PROTOCOL_LIB.setFunctional(
                ProtocolLib::run,
                ProtocolLib::otherwise
        );
        CompatibilityType.RAMPEN_DRILLS.setFunctional(
                () -> Register.enable(new RampenDrills()),
                null
        );
        CompatibilityType.MINE_BOMB.setFunctional(
                MineBomb::reload,
                null
        );
        CompatibilityType.PRINTER_MODE.setFunctional();
        CompatibilityType.SUPER_PICKAXE.setFunctional(
                new String[]{
                        CompatibilityType.SUPER_PICKAXE.toString(),
                        CompatibilityType.SUPER_PICKAXE + "Reloaded"
                },
                null,
                null,
                null
        );
        CompatibilityType.AURELIUM_SKILLS.setFunctional(
                () -> Register.enable(new AureliumSkills()),
                null
        );
        CompatibilityType.ITEMS_ADDER.setFunctional();
    }

    private static boolean getBoolean(String path, boolean create) {
        Boolean data = bool.get(path);

        if (data != null) {
            return data;
        }
        if (!file.exists()) {
            if (!create) {
                return false;
            }
            create();
        }
        boolean value = YamlConfiguration.loadConfiguration(file).getBoolean(path);
        bool.put(path, value);
        return value;
    }

    public void clearCache() {
        bool.clear();
    }

    public void fastRefresh() {
        refresh(false);
    }

    public static void create() {
        file = new File(staticDirectory);
        bool.clear();
        refresh(true);
    }

    // Separator

    public List<CompatibilityType> getActiveCompatibilities() {
        CompatibilityType[] compatibilities = CompatibilityType.values();
        List<CompatibilityType> active = new ArrayList<>(compatibilities.length);

        for (CompatibilityType compatibility : compatibilities) {
            if (compatibility.isFunctional()) {
                active.add(compatibility);
            }
        }
        return active;
    }

    public List<CompatibilityType> getTotalCompatibilities() {
        CompatibilityType[] compatibilities = CompatibilityType.values();
        List<CompatibilityType> active = new ArrayList<>(compatibilities.length);

        for (CompatibilityType compatibility : CompatibilityType.values()) {
            if (compatibility.isEnabled()) {
                active.add(compatibility);
            }
        }
        return active;
    }

    public void evadeFalsePositives(PlayerProtocol protocol,
                                    Compatibility.CompatibilityType compatibilityType,
                                    Enums.HackType[] hackTypes,
                                    int ticks) {
        for (Enums.HackType hackType : hackTypes) {
            protocol.profile().getRunner(hackType).addDisableCause(compatibilityType.toString(), null, ticks);
        }
    }

    public void evadeFalsePositives(PlayerProtocol protocol,
                                    Compatibility.CompatibilityType compatibilityType,
                                    Enums.HackType hackType,
                                    int ticks) {
        protocol.profile().getRunner(hackType).addDisableCause(compatibilityType.toString(), null, ticks);
    }

    public void evadeFalsePositives(PlayerProtocol protocol,
                                    Compatibility.CompatibilityType compatibilityType,
                                    Enums.HackCategoryType[] types,
                                    int ticks) {
        for (Enums.HackType hackType : Enums.HackType.values()) {
            for (Enums.HackCategoryType type : types) {
                if (hackType.category == type) {
                    protocol.profile().getRunner(hackType).addDisableCause(compatibilityType.toString(), null, ticks);
                    break;
                }
            }
        }
    }

    public void evadeFalsePositives(PlayerProtocol protocol,
                                    Compatibility.CompatibilityType compatibilityType,
                                    Enums.HackCategoryType type,
                                    int ticks) {
        for (Enums.HackType hackType : Enums.HackType.values()) {
            if (hackType.category == type) {
                protocol.profile().getRunner(hackType).addDisableCause(compatibilityType.toString(), null, ticks);
            }
        }
    }

}
