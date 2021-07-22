package artifality.block;

import artifality.ArtifalityMod;
import artifality.block.base.BaseGlassBlock;
import artifality.block.base.ClusterBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.registry.BlocksRegistry;

@SuppressWarnings("unused")
public class ArtifalityBlocks extends BlocksRegistry {

    public static final Block INCREMENTAL_CLUSTER = addCluster("incremental_cluster");
    public static final Block INCREMENTAL_BLOCK = addLitBlock("incremental_block");
    public static final Block INCREMENTAL_GLASS = addGlass("incremental_glass");
    public static final Block INCREMENTAL_LENS = addLens("incremental_lens");

    public static final Block LUNAR_CRYSTAL_CLUSTER = addCluster("lunar_crystal_cluster");
    public static final Block LUNAR_CRYSTAL_BLOCK = addLitBlock("lunar_crystal_block");
    public static final Block LUNAR_CRYSTAL_GLASS = addGlass("lunar_crystal_glass");
    public static final Block LUNAR_CRYSTAL_LENS = addLens("lunar_crystal_lens");

    public static final Block CRYSTAL_HEART_CLUSTER = addCluster("crystal_heart_cluster");
    public static final Block CRYSTAL_HEART_BLOCK = addLitBlock("crystal_heart_block");
    public static final Block CRYSTAL_HEART_GLASS = addGlass("crystal_heart_glass");
    public static final Block CRYSTAL_HEART_LENS = addLens("crystal_heart_lens");

    public static final Block ARTIFACT_UPGRADER = add("artifact_upgrader", new ArtifactUpgraderBlock(copyOf(Blocks.COBBLESTONE)));

    private static BlocksRegistry BLOCKS_REGISTRY;

    private ArtifalityBlocks() {
        super(ArtifalityMod.ITEMS_ITEM_GROUP);
    }

    private static Block addCluster(String name){
        return add(name, new ClusterBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(value -> 10)));
    }

    private static Block addGlass(String name){
        return add(name, new BaseGlassBlock(copyOf(Blocks.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque()));
    }

    private static Block addLitBlock(String name){
        return add(name, new BaseBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.AMETHYST_BLOCK).luminance(value -> 15)));
    }

    private static Block addLens(String name){
        return add(name, new IncrementalLensBlock(copyOf(Blocks.COBBLESTONE).sounds(BlockSoundGroup.NETHERITE).nonOpaque()));
    }

    private static Block add(String id, Block block){
        return getBlocksRegistry().register(ArtifalityMod.newId(id), block);
    }

    private static FabricBlockSettings copyOf(Block block){
        return FabricBlockSettings.copyOf(block);
    }

    @Override
    public Identifier createModId(String name) {
        return ArtifalityMod.newId(name);
    }

    private static BlocksRegistry getBlocksRegistry() {
        if(BLOCKS_REGISTRY == null) BLOCKS_REGISTRY = new ArtifalityBlocks();
        return BLOCKS_REGISTRY;
    }
}
