package com.superawesomestuff.craft.enchanting;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
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
 * The Season 1 enchanting table, laid out like Ascendant Arcana's. Slots: the item, a found book, and two material slots
 * the player fills from their inventory to pay for the selected enchantment. Actions arrive as menu button ids:
 * {@code action * 1000 + argument}.
 */
public class SlotEnchantingMenu extends AbstractContainerMenu {
	public static final int ITEM_SLOT = 0;
	public static final int BOOK_SLOT = 1;
	public static final int FIRST_MATERIAL_SLOT = 2;
	public static final int LAST_MATERIAL_SLOT = 3;
	private static final int TABLE_SLOTS = 4;

	public static final int ACTION_ADD = 0;
	public static final int ACTION_REMOVE = 1;
	public static final int ACTION_BOOK = 2;
	public static final int ACTION_UPGRADE = 3;

	public static final int ITEM_X = 18;
	public static final int ITEM_Y = 30;
	public static final int BOOK_X = 18;
	public static final int BOOK_Y = 84;
	public static final int MATERIAL_X = 169;
	public static final int MATERIAL_Y = 68;
	public static final int MATERIAL_GAP = 20;
	public static final int INVENTORY_X = 47;
	public static final int INVENTORY_Y = 140;

	private final Container table = new SimpleContainer(TABLE_SLOTS) {
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
		this.addSlot(new Slot(this.table, ITEM_SLOT, ITEM_X, ITEM_Y) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return canEnchant(stack);
			}
		});
		this.addSlot(new Slot(this.table, BOOK_SLOT, BOOK_X, BOOK_Y) {
			@Override
			public int getMaxStackSize() {
				return 1;
			}

			@Override
			public boolean mayPlace(ItemStack stack) {
				return stack.is(Items.ENCHANTED_BOOK);
			}
		});
		for (int i = 0; i <= LAST_MATERIAL_SLOT - FIRST_MATERIAL_SLOT; i++) {
			this.addSlot(new Slot(this.table, FIRST_MATERIAL_SLOT + i, MATERIAL_X, MATERIAL_Y + i * MATERIAL_GAP));
		}
		this.addStandardInventorySlots(inventory, INVENTORY_X, INVENTORY_Y);
	}

	/** Tools, weapons and armour go in the item slot. Books don't: they're applied, not enchanted. */
	public static boolean canEnchant(ItemStack stack) {
		return !stack.is(Items.BOOK) && !stack.is(Items.ENCHANTED_BOOK)
			&& (stack.isDamageableItem() || stack.has(DataComponents.ENCHANTABLE));
	}

	public static int button(int action, int argument) {
		return action * 1000 + argument;
	}

	public ItemStack item() {
		return this.table.getItem(ITEM_SLOT);
	}

	public ItemStack book() {
		return this.table.getItem(BOOK_SLOT);
	}

	/** How many of {@code cost} sit in the material slots. */
	public int materialCount(Cost cost) {
		return Payment.count(this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, cost);
	}

	public boolean canPay(Player player, EnchantRules.Check check) {
		return check.ok() && Payment.canAfford(player, this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, check.costs());
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
					this.table.setItem(BOOK_SLOT, ItemStack.EMPTY);
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

		if (!this.canPay(player, check)) {
			return false;
		}
		if (player.level().isClientSide()) {
			return true;
		}
		Payment.take(player, this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, check.costs());
		apply.run();
		this.table.setChanged();
		this.broadcastChanges();
		this.access.execute((level, pos) -> level.playSound(
			null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F
		));
		return true;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.access.execute((level, pos) -> this.clearContainer(player, this.table));
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
		int inventoryEnd = this.slots.size();
		if (slotIndex < TABLE_SLOTS) {
			if (!this.moveItemStackTo(stack, TABLE_SLOTS, inventoryEnd, true)) {
				return ItemStack.EMPTY;
			}
		} else if (stack.is(Items.ENCHANTED_BOOK)) {
			if (!this.moveItemStackTo(stack, BOOK_SLOT, BOOK_SLOT + 1, false)) {
				return ItemStack.EMPTY;
			}
		} else if (canEnchant(stack) && !this.slots.get(ITEM_SLOT).hasItem()) {
			this.slots.get(ITEM_SLOT).setByPlayer(stack.split(1));
		} else if (!this.moveItemStackTo(stack, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT + 1, false)) {
			return ItemStack.EMPTY;
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
