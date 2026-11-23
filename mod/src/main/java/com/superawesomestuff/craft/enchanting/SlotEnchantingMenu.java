package com.superawesomestuff.craft.enchanting;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;

/**
 * The Season 1 enchanting table. One slot for the item, one for a found book; costs are paid from the player's
 * inventory. Actions arrive as menu button ids: {@code action * 1000 + argument}.
 */
public class SlotEnchantingMenu extends AbstractContainerMenu {
	public static final int ITEM_SLOT = 0;
	public static final int BOOK_SLOT = 1;
	public static final int ACTION_ADD = 0;
	public static final int ACTION_REMOVE = 1;
	public static final int ACTION_BOOK = 2;
	public static final int ACTION_UPGRADE = 3;

	public static final int ITEM_X = 12;
	public static final int ITEM_Y = 26;
	public static final int BOOK_X = 12;
	public static final int BOOK_Y = 62;
	public static final int INVENTORY_X = 57;
	public static final int INVENTORY_Y = 152;

	private final Container tableSlots = new SimpleContainer(2) {
		@Override
		public void setChanged() {
			super.setChanged();
			SlotEnchantingMenu.this.slotsChanged(this);
		}
	};
	private final ContainerLevelAccess access;

	public SlotEnchantingMenu(int containerId, Inventory inventory) {
		this(containerId, inventory, ContainerLevelAccess.NULL);
	}

	public SlotEnchantingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
		super(ModMenus.SLOT_ENCHANTING, containerId);
		this.access = access;
		this.addSlot(new Slot(this.tableSlots, ITEM_SLOT, ITEM_X, ITEM_Y) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return !stack.is(Items.ENCHANTED_BOOK);
			}
		});
		this.addSlot(new Slot(this.tableSlots, BOOK_SLOT, BOOK_X, BOOK_Y) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(Items.ENCHANTED_BOOK);
			}
		});
		this.addStandardInventorySlots(inventory, INVENTORY_X, INVENTORY_Y);
	}

	public static int button(int action, int argument) {
		return action * 1000 + argument;
	}

	public ItemStack item() {
		return this.tableSlots.getItem(ITEM_SLOT);
	}

	public ItemStack book() {
		return this.tableSlots.getItem(BOOK_SLOT);
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		int action = buttonId / 1000;
		int argument = buttonId % 1000;
		ItemStack item = this.item();
		Registry<Enchantment> registry = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

		EnchantRules.Check check;
		Runnable apply;
		switch (action) {
			case ACTION_ADD, ACTION_REMOVE -> {
				Optional<? extends Holder<Enchantment>> holder = registry.get(argument);
				if (holder.isEmpty()) {
					return false;
				}
				Holder<Enchantment> enchantment = holder.get();
				if (action == ACTION_ADD) {
					check = EnchantRules.checkAddLevel(item, enchantment);
					apply = () -> EnchantRules.addLevel(item, enchantment);
				} else {
					check = EnchantRules.checkRemove(item, enchantment);
					apply = () -> EnchantRules.remove(item, enchantment);
				}
			}
			case ACTION_BOOK -> {
				ItemStack book = this.book();
				check = EnchantRules.checkBook(item, book);
				apply = () -> {
					EnchantRules.applyBook(item, book);
					this.tableSlots.setItem(BOOK_SLOT, ItemStack.EMPTY);
				};
			}
			case ACTION_UPGRADE -> {
				if (argument < 0 || argument >= SlotUpgrade.values().length) {
					return false;
				}
				SlotUpgrade upgrade = SlotUpgrade.values()[argument];
				check = EnchantRules.checkUpgrade(item, upgrade);
				apply = () -> EnchantRules.applyUpgrade(item, upgrade);
			}
			default -> {
				return false;
			}
		}

		if (!check.ok() || !Payment.canAfford(player, check.costs())) {
			return false;
		}
		if (player.level().isClientSide()) {
			return true;
		}
		Payment.take(player, check.costs());
		apply.run();
		this.tableSlots.setChanged();
		this.broadcastChanges();
		this.access.execute((level, pos) -> level.playSound(
			null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F
		));
		return true;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((level, pos) -> this.clearContainer(player, this.tableSlots));
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(this.access, player, Blocks.ENCHANTING_TABLE);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);
		if (slot == null || !slot.hasItem()) {
			return ItemStack.EMPTY;
		}
		ItemStack stack = slot.getItem();
		ItemStack original = stack.copy();
		int inventoryStart = 2;
		int inventoryEnd = this.slots.size();
		if (slotIndex < inventoryStart) {
			if (!this.moveItemStackTo(stack, inventoryStart, inventoryEnd, true)) {
				return ItemStack.EMPTY;
			}
		} else {
			int target = stack.is(Items.ENCHANTED_BOOK) ? BOOK_SLOT : ITEM_SLOT;
			Slot targetSlot = this.slots.get(target);
			if (targetSlot.hasItem() || !targetSlot.mayPlace(stack)) {
				return ItemStack.EMPTY;
			}
			targetSlot.setByPlayer(stack.split(1));
		}
		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}
		if (stack.getCount() == original.getCount()) {
			return ItemStack.EMPTY;
		}
		slot.onTake(player, stack);
		return original;
	}
}
