package artifality.block;

import artifality.block.base.BasedBlock;
import artifality.item.base.TieredItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import ru.bclib.client.models.BasePatterns;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;

import java.util.Optional;

public class UpgradingPedestalBlock extends BasedBlock {

    public static final IntProperty CHARGES = Properties.CHARGES;

    public UpgradingPedestalBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CHARGES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return ActionResult.PASS;
        ItemStack itemStack = player.getStackInHand(hand);

        if(hand == Hand.MAIN_HAND && itemStack.isOf(ArtifalityBlocks.INCREMENTAL_CRYSTAL_BLOCK.asItem())){
            if(getCharges(state) < 3){
                chargeWithIncremental(world, pos, state);
                itemStack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }else if(hand == Hand.MAIN_HAND && itemStack.isOf(Items.NETHER_STAR)){
            if(getCharges(state) != 4){
                chargeWithNetherStar(world, pos, state);
                itemStack.decrement(1);
                return ActionResult.SUCCESS;
            }
        }else if(hand == Hand.MAIN_HAND && itemStack.getItem() instanceof TieredItem){
            if(getCharges(state) == 3 && TieredItem.getCurrentTier(itemStack) == 1){
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TieredItem.withTier(itemStack.getItem(), 2));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;

            }else if(getCharges(state) == 4 && TieredItem.getCurrentTier(itemStack) == 2){
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                player.setStackInHand(Hand.MAIN_HAND, TieredItem.withTier(itemStack.getItem(), 3));
                world.setBlockState(pos, this.getDefaultState());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    private static int getCharges(BlockState state) {
        return state.get(CHARGES);
    }

    public static void chargeWithIncremental(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGES, state.get(CHARGES) + 1), 3);
        world.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public static void chargeWithNetherStar(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGES, 4), 3);
        world.playSound(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        super.onBroken(world, pos, state);
        switch (getCharges(state)) {
            case 1 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_CRYSTAL_BLOCK));
            case 2 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_CRYSTAL_BLOCK, 2));
            case 3 -> dropStack((World) world, pos, new ItemStack(ArtifalityBlocks.INCREMENTAL_CRYSTAL_BLOCK, 3));
            case 4 -> dropStack((World) world, pos, new ItemStack(Items.NETHER_STAR));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    @Override
    public JsonUnbakedModel getItemModel(Identifier id) {
        Optional<String> pattern = PatternsHelper.createJson(BasePatterns.BLOCK_BOTTOM_TOP, id);
        return ModelsHelper.fromPattern(pattern);
    }
}
