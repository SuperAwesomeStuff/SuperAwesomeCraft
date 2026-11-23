package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.enchanting.Cost;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.Payment;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import com.superawesomestuff.craft.enchanting.SlotUpgrade;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;

/**
 * The Season 1 enchanting table, laid out like Ascendant Arcana's: a fixed list of everything the item can take on the
 * left, and a detail panel for the selected entry on the right with its cost and the buttons. The list never reorders,
 * so nothing moves when you enchant.
 */
public class SlotEnchantingScreen extends AbstractContainerScreen<SlotEnchantingMenu> {
	private static final int WIDTH = 276;
	private static final int HEIGHT = 236;
	private static final int LIST_X = 36;
	private static final int LIST_Y = 18;
	private static final int LIST_W = 112;
	private static final int ROW_H = 14;
	private static final int VISIBLE_ROWS = 8;
	private static final int PANEL_X = 152;
	private static final int PANEL_Y = 18;
	private static final int PANEL_W = 116;
	private static final int PANEL_H = ROW_H * VISIBLE_ROWS;
	private static final int BUTTON_W = 52;
	private static final int BUTTON_H = 16;

	private static final int BACKGROUND = 0xFFC6C6C6;
	private static final int DARK = 0xFF373737;
	private static final int LIGHT = 0xFFFFFFFF;
	private static final int SLOT = 0xFF8B8B8B;
	private static final int INSET = 0xFF555555;
	private static final int TEXT = 0xFF404040;
	private static final int TEXT_LIGHT = 0xFFE0E0E0;
	private static final int TEXT_DIM = 0xFF9A9A9A;
	private static final int TEXT_ON = 0xFFFFFFA0;
	private static final int SELECTED = 0xFF6B6BA8;
	private static final int HOVER = 0xFF6A6A6A;
	private static final int RED = 0xFFFF6060;
	private static final int GREEN = 0xFF80FF80;

	private int scroll;
	private @Nullable Object selectedKey;
	private ItemStack lastItem = ItemStack.EMPTY;
	private boolean hadBook;

	private enum Kind { BOOK, ENCHANTMENT, UPGRADE }

	/** One list entry. {@code key} identifies it across frames so the selection sticks. */
	private record Entry(Kind kind, Object key, @Nullable Holder<Enchantment> enchantment, int registryId,
		@Nullable SlotUpgrade upgrade, int level, EnchantRules.Check add, EnchantRules.@Nullable Check remove) {
	}

	public SlotEnchantingScreen(SlotEnchantingMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, WIDTH, HEIGHT);
		this.inventoryLabelX = SlotEnchantingMenu.INVENTORY_X;
		this.inventoryLabelY = SlotEnchantingMenu.INVENTORY_Y - 11;
	}

	// Model

	private List<Entry> entries() {
		List<Entry> entries = new ArrayList<>();
		ItemStack item = this.menu.item();
		if (item.isEmpty()) {
			return entries;
		}
		ItemStack book = this.menu.book();
		if (!book.isEmpty()) {
			entries.add(new Entry(Kind.BOOK, "book", null, -1, null, 0, EnchantRules.checkBook(item, book), null));
		}
		Registry<Enchantment> registry = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		List<Holder<Enchantment>> options = EnchantRules.options(item, registry);
		options.sort(java.util.Comparator.comparing(h -> h.value().description().getString()));
		for (Holder<Enchantment> enchantment : options) {
			int level = EnchantRules.enchantments(item).getLevel(enchantment);
			EnchantRules.Check remove = level > 0 ? EnchantRules.checkRemove(item, enchantment) : null;
			entries.add(new Entry(Kind.ENCHANTMENT, Objects.requireNonNull(EnchantRules.id(enchantment)), enchantment,
				registry.getId(enchantment.value()), null, level, EnchantRules.checkAddLevel(item, enchantment), remove));
		}
		for (SlotUpgrade upgrade : SlotUpgrade.values()) {
			entries.add(new Entry(Kind.UPGRADE, upgrade, null, -1, upgrade, EnchantRules.hasUpgrade(item, upgrade) ? 1 : 0,
				EnchantRules.checkUpgrade(item, upgrade), null));
		}
		return entries;
	}

	private @Nullable Entry selected(List<Entry> entries) {
		for (Entry entry : entries) {
			if (entry.key.equals(this.selectedKey)) {
				return entry;
			}
		}
		return null;
	}

	private boolean affordable(EnchantRules.Check check) {
		return check.ok() && Payment.canAfford(this.minecraft.player, check.costs());
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		ItemStack item = this.menu.item();
		if (!ItemStack.isSameItem(item, this.lastItem)) {
			this.lastItem = item.copy();
			this.scroll = 0;
			List<Entry> entries = this.entries();
			this.selectedKey = entries.isEmpty() ? null : entries.getFirst().key;
		}
		boolean hasBook = !this.menu.book().isEmpty();
		if (hasBook && !this.hadBook) {
			this.selectedKey = "book";
		}
		this.hadBook = hasBook;
	}

	// Drawing

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		int x = this.leftPos;
		int y = this.topPos;
		graphics.fill(x, y, x + WIDTH, y + HEIGHT, BACKGROUND);
		this.bevel(graphics, x, y, WIDTH, HEIGHT, LIGHT, DARK);
		for (Slot slot : this.menu.slots) {
			this.slotFrame(graphics, x + slot.x - 1, y + slot.y - 1);
		}
		this.inset(graphics, x + LIST_X, y + LIST_Y, LIST_W, PANEL_H);
		this.inset(graphics, x + PANEL_X, y + PANEL_Y, PANEL_W, PANEL_H);

		List<Entry> entries = this.entries();
		this.scroll = Math.max(0, Math.min(this.scroll, entries.size() - VISIBLE_ROWS));
		for (int i = 0; i < VISIBLE_ROWS && i + this.scroll < entries.size(); i++) {
			this.extractRow(graphics, entries.get(i + this.scroll), x + LIST_X, y + LIST_Y + i * ROW_H, mouseX, mouseY);
		}
		if (entries.size() > VISIBLE_ROWS) {
			int barH = Math.max(12, PANEL_H * VISIBLE_ROWS / entries.size());
			int barY = (PANEL_H - barH) * this.scroll / (entries.size() - VISIBLE_ROWS);
			graphics.fill(x + LIST_X + LIST_W - 4, y + LIST_Y + barY, x + LIST_X + LIST_W - 1, y + LIST_Y + barY + barH, 0xFFB0B0B0);
		}
		this.extractPanel(graphics, this.selected(entries), x + PANEL_X, y + PANEL_Y, mouseX, mouseY);
	}

	private void bevel(GuiGraphicsExtractor graphics, int x, int y, int w, int h, int topLeft, int bottomRight) {
		graphics.horizontalLine(x, x + w - 1, y, topLeft);
		graphics.verticalLine(x, y, y + h - 1, topLeft);
		graphics.horizontalLine(x, x + w - 1, y + h - 1, bottomRight);
		graphics.verticalLine(x + w - 1, y, y + h - 1, bottomRight);
	}

	private void slotFrame(GuiGraphicsExtractor graphics, int x, int y) {
		graphics.fill(x, y, x + 18, y + 18, SLOT);
		this.bevel(graphics, x, y, 18, 18, DARK, LIGHT);
	}

	private void inset(GuiGraphicsExtractor graphics, int x, int y, int w, int h) {
		graphics.fill(x - 1, y - 1, x + w + 1, y + h + 1, DARK);
		graphics.fill(x, y, x + w, y + h, INSET);
		graphics.horizontalLine(x - 1, x + w, y + h, LIGHT);
		graphics.verticalLine(x + w, y - 1, y + h, LIGHT);
	}

	private void extractRow(GuiGraphicsExtractor graphics, Entry entry, int x, int y, int mouseX, int mouseY) {
		boolean selected = entry.key.equals(this.selectedKey);
		boolean hover = mouseX >= x && mouseX < x + LIST_W - 5 && mouseY >= y && mouseY < y + ROW_H;
		if (selected || hover) {
			graphics.fill(x, y, x + LIST_W - 5, y + ROW_H, selected ? SELECTED : HOVER);
		}
		boolean usable = entry.level > 0 || entry.add.ok() || entry.add.status() == EnchantRules.Status.MAX_LEVEL;
		int color = entry.kind == Kind.ENCHANTMENT && entry.level > 0 ? TEXT_ON : usable ? TEXT_LIGHT : TEXT_DIM;
		String right = switch (entry.kind) {
			case ENCHANTMENT -> entry.level > 0 ? roman(entry.level) : "";
			case UPGRADE -> entry.level > 0 ? "✔" : "";
			case BOOK -> "";
		};
		int rightW = this.font.width(right);
		String name = this.font.plainSubstrByWidth(this.rowName(entry).getString(), LIST_W - 12 - rightW);
		graphics.text(this.font, name, x + 3, y + 3, color, false);
		graphics.text(this.font, right, x + LIST_W - 7 - rightW, y + 3, color, false);
	}

	private Component rowName(Entry entry) {
		return switch (entry.kind) {
			case BOOK -> Component.translatable("superawesomecraft.enchanting.apply_book");
			case UPGRADE -> Component.translatable("superawesomecraft.enchanting.add_slot", entry.upgrade.title());
			case ENCHANTMENT -> entry.enchantment.value().description();
		};
	}

	private static String roman(int level) {
		return Component.translatable("enchantment.level." + level).getString();
	}

	private void extractPanel(GuiGraphicsExtractor graphics, @Nullable Entry entry, int x, int y, int mouseX, int mouseY) {
		int textX = x + 4;
		int wrap = PANEL_W - 8;
		if (entry == null) {
			Component hint = Component.translatable(this.menu.item().isEmpty()
				? "superawesomecraft.enchanting.hint" : "superawesomecraft.enchanting.select");
			graphics.textWithWordWrap(this.font, hint, textX, y + 4, wrap, TEXT_LIGHT, false);
			return;
		}
		int line = y + 4;
		graphics.text(this.font, this.font.plainSubstrByWidth(this.rowName(entry).getString(), wrap), textX, line, TEXT_ON, false);
		line += 12;
		Component detail = switch (entry.kind) {
			case ENCHANTMENT -> entry.add.status() == EnchantRules.Status.MAX_LEVEL
				? Component.translatable("superawesomecraft.enchanting.level_max", roman(entry.level))
				: entry.level > 0
				? Component.translatable("superawesomecraft.enchanting.level_next", roman(entry.level), roman(entry.level + 1))
				: Component.translatable("superawesomecraft.enchanting.level_new", roman(1));
			case UPGRADE -> Component.translatable(entry.level > 0
				? "superawesomecraft.enchanting.upgrade_done" : "superawesomecraft.enchanting.upgrade_detail");
			case BOOK -> Component.translatable("superawesomecraft.enchanting.apply_book_tooltip");
		};
		line = graphics.textWithWordWrap(this.font, detail, textX, line, wrap, TEXT_LIGHT, false) + 4;

		if (entry.add.ok()) {
			for (Cost cost : entry.add.costs()) {
				int have = this.minecraft.player.hasInfiniteMaterials() ? cost.count() : Payment.count(this.minecraft.player, cost);
				graphics.fakeItem(new ItemStack(cost.display()), textX, line);
				graphics.text(this.font, Math.min(have, cost.count()) + " / " + cost.count(), textX + 20, line + 4,
					have >= cost.count() ? GREEN : RED, false);
				line += 18;
			}
			if (entry.add.costs().isEmpty()) {
				graphics.text(this.font, Component.translatable("superawesomecraft.enchanting.free"), textX, line, GREEN, false);
			}
		} else if (entry.add.status() != EnchantRules.Status.MAX_LEVEL && entry.add.status() != EnchantRules.Status.ALREADY_APPLIED) {
			graphics.textWithWordWrap(this.font, Component.translatable(entry.add.status().translationKey()), textX, line, wrap, RED, false);
		}

		int buttonY = y + PANEL_H - BUTTON_H - 4;
		Component action = Component.translatable(switch (entry.kind) {
			case ENCHANTMENT -> "superawesomecraft.enchanting.button.enchant";
			case UPGRADE -> "superawesomecraft.enchanting.button.add_slot";
			case BOOK -> "superawesomecraft.enchanting.button.apply";
		});
		this.button(graphics, x + 4, buttonY, action, this.affordable(entry.add), mouseX, mouseY);
		if (entry.remove != null) {
			this.button(graphics, x + PANEL_W - 4 - BUTTON_W, buttonY,
				Component.translatable("superawesomecraft.enchanting.button.remove"), this.affordable(entry.remove), mouseX, mouseY);
		}
	}

	private void button(GuiGraphicsExtractor graphics, int x, int y, Component label, boolean active, int mouseX, int mouseY) {
		boolean hover = active && mouseX >= x && mouseX < x + BUTTON_W && mouseY >= y && mouseY < y + BUTTON_H;
		graphics.fill(x, y, x + BUTTON_W, y + BUTTON_H, active ? (hover ? 0xFFB8B8E0 : 0xFFA0A0A0) : 0xFF6E6E6E);
		this.bevel(graphics, x, y, BUTTON_W, BUTTON_H, active ? LIGHT : 0xFF8A8A8A, DARK);
		int textW = this.font.width(label);
		graphics.text(this.font, label, x + (BUTTON_W - textW) / 2, y + 4, active ? TEXT : 0xFF404040, false);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
		super.extractLabels(graphics, xm, ym);
		Component book = Component.translatable("superawesomecraft.enchanting.book_slot");
		graphics.text(this.font, book, SlotEnchantingMenu.BOOK_X + 8 - this.font.width(book) / 2, SlotEnchantingMenu.BOOK_Y + 19, TEXT, false);
		ItemStack item = this.menu.item();
		if (!item.isEmpty()) {
			Component slots = Component.translatable("superawesomecraft.enchanting.slots", EnchantRules.usedSlots(item), EnchantRules.totalSlots(item));
			graphics.text(this.font, slots, PANEL_X + PANEL_W - this.font.width(slots), 6, TEXT, false);
		}
	}

	// Input

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		List<Entry> entries = this.entries();
		double mx = event.x() - this.leftPos;
		double my = event.y() - this.topPos;
		if (mx >= LIST_X && mx < LIST_X + LIST_W && my >= LIST_Y && my < LIST_Y + PANEL_H) {
			int index = (int) ((my - LIST_Y) / ROW_H) + this.scroll;
			if (index < entries.size()) {
				this.selectedKey = entries.get(index).key;
			}
			return true;
		}
		Entry entry = this.selected(entries);
		int buttonY = PANEL_Y + PANEL_H - BUTTON_H - 4;
		if (entry != null && my >= buttonY && my < buttonY + BUTTON_H) {
			if (mx >= PANEL_X + 4 && mx < PANEL_X + 4 + BUTTON_W) {
				return this.press(switch (entry.kind) {
					case ENCHANTMENT -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, entry.registryId);
					case UPGRADE -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_UPGRADE, entry.upgrade.ordinal());
					case BOOK -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_BOOK, 0);
				});
			}
			if (entry.remove != null && mx >= PANEL_X + PANEL_W - 4 - BUTTON_W && mx < PANEL_X + PANEL_W - 4) {
				return this.press(SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_REMOVE, entry.registryId));
			}
		}
		return super.mouseClicked(event, doubleClick);
	}

	private boolean press(int buttonId) {
		if (this.menu.clickMenuButton(this.minecraft.player, buttonId)) {
			this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, buttonId);
		}
		return true;
	}

	@Override
	public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
		double mx = x - this.leftPos;
		double my = y - this.topPos;
		if (scrollY != 0 && mx >= LIST_X && mx < LIST_X + LIST_W && my >= LIST_Y && my < LIST_Y + PANEL_H) {
			this.scroll -= (int) Math.signum(scrollY);
			return true;
		}
		return super.mouseScrolled(x, y, scrollX, scrollY);
	}
}
