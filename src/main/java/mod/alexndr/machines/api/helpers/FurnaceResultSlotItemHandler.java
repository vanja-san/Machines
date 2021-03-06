package mod.alexndr.machines.api.helpers;

import mod.alexndr.machines.api.content.AbstractModFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Based on vanilla FurnaceResultSlot class, but for SlotItemHandlers.
 *
 */
public class FurnaceResultSlotItemHandler extends SlotItemHandler
{
    private final PlayerEntity player;
    private int removeCount;
    private final TileEntity tile;
    
    public FurnaceResultSlotItemHandler(PlayerEntity player, TileEntity tileEntity, IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.tile = tileEntity;
        this.player = player;
    }

    /**
     * No, you can't stick items in the output slot.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return false;
    }

    
    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
         }
        return super.decrStackSize(amount);
    }

    @Override
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        if (!this.player.world.isRemote && this.tile instanceof AbstractModFurnaceTileEntity) 
        {
           ((AbstractModFurnaceTileEntity)this.tile).grantExperience(this.player);
        }
        this.removeCount = 0;
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    } // end onCrafting

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack)
    {
        this.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    
     
} // end class
