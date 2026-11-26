package com.superawesomestuff.craft.enchanting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.superawesomestuff.craft.playtest.PlaytestLog;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Blocks;

/**
 * The Season 1 enchanting table, laid out like Ascendant Arcana's. Slots: the item, and two material slots the player
 * fills from their inventory to pay for the selected enchantment. An enchanted book in a material slot takes one of its
 * enchantments straight up to the book's level instead, and that enchantment comes off the book. Actions arrive as menu button ids:
 * {@code action * 1000 + argument}.
 */
public class SlotEnchantingMenu extends AbstractContainerMenu {
	public static final int ITEM_SLOT = 0;
	public static final int FIRST_MATERIAL_SLOT = 1;
	public static final int LAST_MATERIAL_SLOT = 2;
	private static final int TABLE_SLOTS = 3;

	public static final int ACTION_ADD = 0;
	public static final int ACTION_REMOVE = 1;
	public static final int ACTION_UPGRADE = 3;

	public static final int ITEM_X = 18;
	public static final int ITEM_Y = 52;
	public static final int MATERIAL_X = 169;
	public static final int MATERIAL_Y = 67;
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
		for (int i = 0; i <= LAST_MATERIAL_SLOT - FIRST_MATERIAL_SLOT; i++) {
			this.addSlot(new Slot(this.table, FIRST_MATERIAL_SLOT + i, MATERIAL_X, MATERIAL_Y + i * MATERIAL_GAP));
		}
		this.addStandardInventorySlots(inventory, INVENTORY_X, INVENTORY_Y);
	}

	/** Tools, weapons and armour go in the item slot. Books don't: they're used from a material slot. */
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

	/** How many of {@code cost} sit in the material slots. */
	public int materialCount(Cost cost) {
		return Payment.count(this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, cost);
	}

	// Books

	/**
	 * The material slot holding the best book for {@code enchantment}, or -1. A book takes the enchantment straight up to
	 * the book's level, so it only counts if that's higher than the item's current level.
	 */
	public int bookSlot(Holder<Enchantment> enchantment) {
		int current = EnchantRules.enchantments(this.item()).getLevel(enchantment);
		int best = -1;
		int bestLevel = current;
		for (int i = FIRST_MATERIAL_SLOT; i <= LAST_MATERIAL_SLOT; i++) {
			ItemStack stack = this.table.getItem(i);
			int level = stack.is(Items.ENCHANTED_BOOK)
				? stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(enchantment) : 0;
			if (level > bestLevel) {
				best = i;
				bestLevel = level;
			}
		}
		return best;
	}

	/** The level a book in the material slots would take {@code enchantment} to, or 0 if there's no usable book. */
	public int bookTarget(Holder<Enchantment> enchantment) {
		int slot = this.bookSlot(enchantment);
		if (slot < 0) {
			return 0;
		}
		int level = this.table.getItem(slot).getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(enchantment);
		return Math.min(level, enchantment.value().getMaxLevel());
	}

	/**
	 * What adding {@code enchantment} costs right now. With a book, the book replaces the materials for every level it
	 * adds, but the high-level cost (Blaze Rods) is still due for each level from {@link EnchantingRecipes#highLevelFrom()}
	 * it adds.
	 */
	public List<Cost> addCosts(Holder<Enchantment> enchantment, EnchantRules.Check check) {
		int target = this.bookTarget(enchantment);
		if (!check.ok() || target == 0) {
			return check.costs();
		}
		int current = EnchantRules.enchantments(this.item()).getLevel(enchantment);
		Identifier id = EnchantRules.id(enchantment);
		if (id != null && EnchantingRecipes.highLevelExempt(id)) {
			return List.of();
		}
		int highLevels = Math.max(0, target - Math.max(current, EnchantingRecipes.highLevelFrom() - 1));
		if (highLevels == 0) {
			return List.of();
		}
		Cost high = EnchantingRecipes.highLevelCost();
		return List.of(new Cost(high.display(), high.tag(), high.count() * highLevels, 0));
	}

	/** Takes {@code enchantment} off the book in {@code slot}. A book with nothing left becomes a plain Book. */
	private void spendBook(int slot, Holder<Enchantment> enchantment) {
		ItemStack book = this.table.getItem(slot);
		ItemEnchantments.Mutable left = new ItemEnchantments.Mutable(
			book.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY));
		left.removeIf(enchantment::equals);
		ItemEnchantments rest = left.toImmutable();
		if (rest.isEmpty()) {
			this.table.setItem(slot, book.transmuteCopy(Items.BOOK));
		} else {
			book.set(DataComponents.STORED_ENCHANTMENTS, rest);
		}
	}

	public boolean canPay(Player player, List<Cost> costs) {
		return Payment.canAfford(player, this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, costs);
	}

	@Override
	public boolean clickMenuButton(Player player, int buttonId) {
		int action = buttonId / 1000;
		int argument = buttonId % 1000;
		ItemStack item = this.item();
		Registry<Enchantment> registry = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

		EnchantRules.Check check;
		List<Cost> costs;
		Runnable apply;
		String event;
		JsonObject details = new JsonObject();
		switch (action) {
			case ACTION_ADD, ACTION_REMOVE -> {
				Optional<? extends Holder<Enchantment>> holder = registry.get(argument);
				if (holder.isEmpty()) {
					return false;
				}
				Holder<Enchantment> enchantment = holder.get();
				if (action == ACTION_ADD) {
					check = EnchantRules.checkAddLevel(item, enchantment);
					costs = this.addCosts(enchantment, check);
					int bookSlot = this.bookSlot(enchantment);
					int target = this.bookTarget(enchantment);
					int from = EnchantRules.enchantments(item).getLevel(enchantment);
					event = "enchant";
					details.addProperty("enchantment", String.valueOf(EnchantRules.id(enchantment)));
					details.addProperty("from", from);
					details.addProperty("to", bookSlot >= 0 ? target : from + 1);
					details.addProperty("via", bookSlot >= 0 ? "book" : "materials");
					apply = () -> {
						if (bookSlot >= 0) {
							this.spendBook(bookSlot, enchantment);
							EnchantRules.setLevel(item, enchantment, target);
						} else {
							EnchantRules.addLevel(item, enchantment);
						}
					};
				} else {
					check = EnchantRules.checkRemove(item, enchantment);
					costs = check.costs();
					event = "unenchant";
					details.addProperty("enchantment", String.valueOf(EnchantRules.id(enchantment)));
					details.addProperty("from", EnchantRules.enchantments(item).getLevel(enchantment));
					apply = () -> EnchantRules.remove(item, enchantment);
				}
			}
			case ACTION_UPGRADE -> {
				if (argument < 0 || argument >= SlotUpgrade.values().length) {
					return false;
				}
				SlotUpgrade upgrade = SlotUpgrade.values()[argument];
				check = EnchantRules.checkUpgrade(item, upgrade);
				costs = check.costs();
				event = "slot_upgrade";
				details.addProperty("upgrade", upgrade.key());
				apply = () -> EnchantRules.applyUpgrade(item, upgrade);
			}
			default -> {
				return false;
			}
		}

		if (!check.ok() || !this.canPay(player, costs)) {
			return false;
		}
		if (player.level().isClientSide()) {
			return true;
		}
		Payment.take(player, this.table, FIRST_MATERIAL_SLOT, LAST_MATERIAL_SLOT, costs);
		apply.run();
		this.record(player, event, details, costs);
		this.table.setChanged();
		this.broadcastChanges();
		this.access.execute((level, pos) -> level.playSound(
			null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F
		));
		return true;
	}

	/** Writes what was just done at the table, what it cost and the item afterwards to the playtest log. */
	private void record(Player player, String event, JsonObject details, List<Cost> costs) {
		JsonArray paid = new JsonArray();
		for (Cost cost : costs) {
			JsonObject json = new JsonObject();
			json.addProperty("item", cost.tag() != null ? "#" + cost.tag().location() : BuiltInRegistries.ITEM.getKey(cost.display()).toString());
			json.addProperty("count", cost.count());
			paid.add(json);
		}
		details.add("cost", paid);
		if (player.hasInfiniteMaterials()) {
			details.addProperty("creative", true);
		}
		details.add("item", PlaytestLog.item(this.item()));
		PlaytestLog.event(player, event, details);
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
