package artifality.registry;

import artifality.ArtifalityMod;
import artifality.item.*;
import artifality.item.base.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ArtifalityItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    public static final Item FLORAL_STAFF = add("floral_staff", new FloralStaffItem(ArtifactConfigs.NATURE_STAFF));
    public static final Item FOREST_STAFF = add("forest_staff", new ForestStaffItem(ArtifactConfigs.NATURE_STAFF));
    public static final Item HARVEST_STAFF = add("harvest_staff", new HarvestStaffItem(ArtifactConfigs.NATURE_STAFF));

    public static final Item ZEUS_STAFF = add("zeus_staff", new ZeusStaffItem(ArtifactConfigs.ZEUS_STAFF));
    public static final Item INVISIBILITY_CAPE = add("invisibility_cape", new InvisibilityCapeItem(ArtifactConfigs.INVISIBILITY_CAPE));
    public static final Item BALLOON = add("balloon", new BalloonItem(ArtifactConfigs.BALLOON));
    public static final Item HAND_FAN = add("hand_fan", new HandFanItem(ArtifactConfigs.HAND_FAN));

    public static final Item LUNAR_CRYSTAL_WAND = add("lunar_crystal_wand", new LunarWandItem(ArtifactConfigs.LUNAR_CRYSTAL_WAND));

    public static final Item INCREMENTAL_CRYSTAL = add("incremental_crystal", new BaseItem(settings()));
    public static final Item LUNAR_CRYSTAL = add("lunar_crystal", new BaseItem(settings()));
    public static final Item LIFE_CRYSTAL = add("life_crystal", new BaseItem(settings()));
    public static final Item WRATH_CRYSTAL = add("wrath_crystal", new BaseItem(settings()));

    public static final Item INCREMENTAL_ORB = add("incremental_orb", new BaseItem(settings()));

    private static Item add(String name, Item item) {
        ITEMS.put(ArtifalityMod.locate(name), item);
        return item;
    }

    private static FabricItemSettings settings() {
        return new FabricItemSettings();
    }

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, ITEMS.get(id)));
    }
}
