package mod.alexndr.machines.content.tile;

import javax.annotation.Nonnull;

import mod.alexndr.machines.api.content.AbstractModFurnaceTileEntity;
import mod.alexndr.machines.config.MachinesConfig;
import mod.alexndr.machines.content.container.OnyxFurnaceContainer;
import mod.alexndr.machines.init.ModBlocks;
import mod.alexndr.machines.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * 
 */
public class OnyxFurnaceTileEntity extends AbstractModFurnaceTileEntity implements ITickableTileEntity, INamedContainerProvider 
{

	public OnyxFurnaceTileEntity() 
	{
		super(ModTileEntityTypes.onyx_furnace.get(), IRecipeType.SMELTING);
	    YieldChance = MachinesConfig.onyxFurnaceYieldChance;
	    YieldAmount = MachinesConfig.onyxFurnaceYieldAmount;
	}

    @Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.onyx_furnace.get().getTranslationKey());
	}

	/**
	 * Called from {@link NetworkHooks#openGui}
	 * (which is called from {@link ElectricFurnaceBlock#onBlockActivated} on the logical server)
	 *
	 * @return The logical-server-side Container for this TileEntity
	 */
	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new OnyxFurnaceContainer(windowId, inventory, this);
	}

} // end class
