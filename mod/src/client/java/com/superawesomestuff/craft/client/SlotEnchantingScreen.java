package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.enchanting.Cost;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.Payment;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import com.superawesomestuff.craft.enchanting.SlotUpgrade;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;

/** The Season 1 enchanting table: every enchantment the item can take, its cost, and buttons to add or remove it. */
public class SlotEnchantingScreen extends AbstractContainerScreen<SlotEnchantingMenu> {
	private static final int WIDTH = 252;
	private static final int HEIGHT = 236;
	private static final int LIST_X = 36;
	private static final int LIST_Y = 30;
	private static final int LIST_W = 208;
	private static final int ROW_H = 18;
	private static final int VISIBLE_ROWS = 6;
	private static final int BUTTON = 13;

	private static final int PANEL = 0xFFC6C6C6;
	private static final int DARK = 0xFF373737;
	private static final int LIGHT = 0xFFFFFFFF;
	private static final int SLOT = 0xFF8B8B8B;
	private static final int TEXT = 0xFF404040;
	private static final int ROW = 0xFFA8A8A8;
	private static final int ROW_HOVER = 0xFFB8B8D8;
	private static final int RED = 0xFFC03030;

	private int scroll;

	private enum Kind { ENCHANTMENT, UPGRADE, BOOK }

	private record Row(Kind kind, @Nullable Holder<Enchantment> enchantment, int registryId, @Nullable SlotUpgrade upgrade,
		int level, EnchantRules.Check add, EnchantRules.@Nullable Check remove) {
	}

	public SlotEnchantingScreen(SlotEnchantingMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, WIDTH, HEIGHT);
		this.inventoryLabelX = SlotEnchantingMenu.INVENTORY_X;
		this.inventoryLabelY = SlotEnchantingMenu.INVENTORY_Y - 11;
	}

	private List<Row> rows() {
		List<Row> rows = new ArrayList<>();
		ItemStack item = this.menu.item();
		if (item.isEmpty()) {
			return rows;
		}
		ItemStack book = this.menu.book();
		if (!book.isEmpty()) {
			rows.add(new Row(Kind.BOOK, null, -1, null, 0, EnchantRules.checkBook(item, book), null));
		}
		Registry<Enchantment> registry = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		for (Holder<Enchantment> enchantment : EnchantRules.options(item, registry)) {
			int level = EnchantRules.enchantments(item).getLevel(enchantment);
			EnchantRules.Check remove = level > 0 ? EnchantRules.checkRemove(item, enchantment) : null;
			rows.add(new Row(Kind.ENCHANTMENT, enchantment, registry.getId(enchantment.value()), null, level,
				EnchantRules.checkAddLevel(item, enchantment), remove));
		}
		for (SlotUpgrade upgrade : SlotUpgrade.values()) {
			rows.add(new Row(Kind.UPGRADE, null, -1, upgrade, 0, EnchantRules.checkUpgrade(item, upgrade), null));
		}
		return rows;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		int x = this.leftPos;
		int y = this.topPos;
		graphics.fill(x, y, x + WIDTH, y + HEIGHT, PANEL);
		graphics.outline(x, y, WIDTH, HEIGHT, DARK);
		for (Slot slot : this.menu.slots) {
			this.slotFrame(graphics, x + slot.x - 1, y + slot.y - 1);
		}
		graphics.fill(x + LIST_X - 1, y + LIST_Y - 1, x + LIST_X + LIST_W + 1, y + LIST_Y + ROW_H * VISIBLE_ROWS + 1, DARK);
		graphics.fill(x + LIST_X, y + LIST_Y, x + LIST_X + LIST_W, y + LIST_Y + ROW_H * VISIBLE_ROWS, SLOT);

		List<Row> rows = this.rows();
		this.scroll = Math.max(0, Math.min(this.scroll, rows.size() - VISIBLE_ROWS));
		graphics.enableScissor(x + LIST_X, y + LIST_Y, x + LIST_X + LIST_W, y + LIST_Y + ROW_H * VISIBLE_ROWS);
		for (int i = 0; i < VISIBLE_ROWS && i + this.scroll < rows.size(); i++) {
			this.extractRow(graphics, rows.get(i + this.scroll), x + LIST_X, y + LIST_Y + i * ROW_H, mouseX, mouseY);
		}
		graphics.disableScissor();
		if (rows.size() > VISIBLE_ROWS) {
			int trackH = ROW_H * VISIBLE_ROWS;
			int barH = Math.max(10, trackH * VISIBLE_ROWS / rows.size());
			int barY = (trackH - barH) * this.scroll / (rows.size() - VISIBLE_ROWS);
			graphics.fill(x + LIST_X + LIST_W - 3, y + LIST_Y + barY, x + LIST_X + LIST_W, y + LIST_Y + barY + barH, DARK);
		}
	}

	private void slotFrame(GuiGraphicsExtractor graphics, int x, int y) {
		graphics.fill(x, y, x + 18, y + 18, SLOT);
		graphics.horizontalLine(x, x + 17, y, DARK);
		graphics.verticalLine(x, y, y + 17, DARK);
		graphics.horizontalLine(x, x + 17, y + 17, LIGHT);
		graphics.verticalLine(x + 17, y, y + 17, LIGHT);
	}

	private void extractRow(GuiGraphicsExtractor graphics, Row row, int x, int y, int mouseX, int mouseY) {
		boolean hover = mouseX >= x && mouseX < x + LIST_W && mouseY >= y && mouseY < y + ROW_H;
		graphics.fill(x + 1, y + 1, x + LIST_W - 4, y + ROW_H - 1, hover ? ROW_HOVER : ROW);
		graphics.text(this.font, this.rowTitle(row), x + 4, y + 5, row.level > 0 || row.kind != Kind.ENCHANTMENT ? TEXT : 0xFF606060, false);

		int buttonX = x + LIST_W - 5 - BUTTON;
		if (row.remove != null) {
			this.button(graphics, buttonX, y + 2, "-", row.remove.ok() && Payment.canAfford(this.minecraft.player, row.remove.costs()), mouseX, mouseY);
		}
		int addX = buttonX - BUTTON - 2;
		boolean canAdd = row.add.ok() && Payment.canAfford(this.minecraft.player, row.add.costs());
		if (row.add.status() != EnchantRules.Status.MAX_LEVEL && row.add.status() != EnchantRules.Status.ALREADY_APPLIED) {
			this.button(graphics, addX, y + 2, "+", canAdd, mouseX, mouseY);
		}
		int costX = addX - 4;
		List<Cost> costs = row.add.ok() ? row.add.costs() : List.of();
		for (int i = costs.size() - 1; i >= 0; i--) {
			Cost cost = costs.get(i);
			String count = String.valueOf(cost.count());
			costX -= this.font.width(count) + 18;
			graphics.fakeItem(new ItemStack(cost.display()), costX, y + 1);
			boolean enough = this.minecraft.player.hasInfiniteMaterials() || Payment.count(this.minecraft.player, cost) >= cost.count();
			graphics.text(this.font, count, costX + 17, y + 5, enough ? TEXT : RED, false);
		}
		if (!row.add.ok() && row.add.status() != EnchantRules.Status.MAX_LEVEL) {
			Component reason = Component.translatable(row.add.status().translationKey());
			graphics.text(this.font, reason, addX - 4 - this.font.width(reason), y + 5, 0xFF707070, false);
		}
	}

	private Component rowTitle(Row row) {
		return switch (row.kind) {
			case BOOK -> Component.translatable("superawesomecraft.enchanting.apply_book");
			case UPGRADE -> Component.translatable("superawesomecraft.enchanting.add_slot", row.upgrade.title());
			case ENCHANTMENT -> {
				MutableComponent name = row.enchantment.value().description().copy();
				if (row.level > 0) {
					name.append(" ").append(Component.translatable("enchantment.level." + row.level));
				}
				yield name;
			}
		};
	}

	private void button(GuiGraphicsExtractor graphics, int x, int y, String label, boolean active, int mouseX, int mouseY) {
		boolean hover = active && mouseX >= x && mouseX < x + BUTTON && mouseY >= y && mouseY < y + BUTTON;
		graphics.fill(x, y, x + BUTTON, y + BUTTON, active ? (hover ? 0xFFE0E0E0 : 0xFFD0D0D0) : 0xFF909090);
		graphics.outline(x, y, BUTTON, BUTTON, DARK);
		graphics.centeredText(this.font, label, x + BUTTON / 2 + 1, y + 3, active ? TEXT : 0xFF606060);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
		super.extractLabels(graphics, xm, ym);
		graphics.centeredText(this.font, Component.translatable("superawesomecraft.enchanting.book_slot"),
			SlotEnchantingMenu.BOOK_X + 8, SlotEnchantingMenu.BOOK_Y + 20, TEXT);
		ItemStack item = this.menu.item();
		if (item.isEmpty()) {
			graphics.text(this.font, Component.translatable("superawesomecraft.enchanting.hint"), LIST_X, 18, TEXT, false);
			return;
		}
		MutableComponent header = Component.translatable("superawesomecraft.enchanting.slots",
			EnchantRules.usedSlots(item), EnchantRules.totalSlots(item));
		for (EnchantRules.Group group : EnchantRules.Group.values()) {
			if (this.itemUsesGroup(item, group)) {
				header.append("   ").append(Component.translatable(group.translationKey(), EnchantRules.groupTotal(item, group), group.cap));
			}
		}
		graphics.text(this.font, header, LIST_X, 18, TEXT, false);
	}

	private boolean itemUsesGroup(ItemStack item, EnchantRules.Group group) {
		Registry<Enchantment> registry = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		return EnchantRules.options(item, registry).stream().anyMatch(h -> h.is(group.tag));
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		Row row = this.rowAt(mouseX, mouseY);
		if (row != null) {
			graphics.setComponentTooltipForNextFrame(this.font, this.tooltip(row), mouseX, mouseY);
		}
	}

	private List<Component> tooltip(Row row) {
		List<Component> lines = new ArrayList<>();
		switch (row.kind) {
			case ENCHANTMENT -> {
				int max = row.enchantment.value().getMaxLevel();
				lines.add(Enchantment.getFullname(row.enchantment, Math.min(row.level + 1, max)).copy().withStyle(ChatFormatting.WHITE));
				if (row.enchantment.is(EnchantRules.Group.DAMAGE.tag) || row.enchantment.is(EnchantRules.Group.PROTECTION.tag)) {
					lines.add(Component.translatable("superawesomecraft.enchanting.shared_cap").withStyle(ChatFormatting.DARK_PURPLE));
				}
			}
			case UPGRADE -> lines.add(Component.translatable("superawesomecraft.enchanting.add_slot", row.upgrade.title()).withStyle(ChatFormatting.WHITE));
			case BOOK -> lines.add(Component.translatable("superawesomecraft.enchanting.apply_book_tooltip").withStyle(ChatFormatting.WHITE));
		}
		if (row.add.ok()) {
			for (Cost cost : row.add.costs()) {
				int have = this.minecraft.player.hasInfiniteMaterials() ? cost.count() : Payment.count(this.minecraft.player, cost);
				lines.add(Component.literal(Math.min(have, cost.count()) + " / " + cost.count() + " ")
					.append(new ItemStack(cost.display()).getHoverName())
					.withStyle(have >= cost.count() ? ChatFormatting.GRAY : ChatFormatting.RED));
			}
		} else {
			lines.add(Component.translatable(row.add.status().translationKey()).withStyle(ChatFormatting.GRAY));
		}
		if (row.remove != null) {
			lines.add(Component.translatable("superawesomecraft.enchanting.remove_hint").withStyle(ChatFormatting.DARK_GRAY));
		}
		return lines;
	}

	private @Nullable Row rowAt(double mouseX, double mouseY) {
		int x = this.leftPos + LIST_X;
		int y = this.topPos + LIST_Y;
		if (mouseX < x || mouseX >= x + LIST_W || mouseY < y || mouseY >= y + ROW_H * VISIBLE_ROWS) {
			return null;
		}
		int index = (int) ((mouseY - y) / ROW_H) + this.scroll;
		List<Row> rows = this.rows();
		return index < rows.size() ? rows.get(index) : null;
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		Row row = this.rowAt(event.x(), event.y());
		if (row != null) {
			int rowTop = this.topPos + LIST_Y + (int) ((event.y() - this.topPos - LIST_Y) / ROW_H) * ROW_H;
			int buttonX = this.leftPos + LIST_X + LIST_W - 5 - BUTTON;
			int addX = buttonX - BUTTON - 2;
			boolean inY = event.y() >= rowTop + 2 && event.y() < rowTop + 2 + BUTTON;
			if (inY && row.remove != null && event.x() >= buttonX && event.x() < buttonX + BUTTON) {
				return this.press(SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_REMOVE, row.registryId));
			}
			if (inY && event.x() >= addX && event.x() < addX + BUTTON) {
				int id = switch (row.kind) {
					case ENCHANTMENT -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, row.registryId);
					case UPGRADE -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_UPGRADE, row.upgrade.ordinal());
					case BOOK -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_BOOK, 0);
				};
				return this.press(id);
			}
			return true;
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
		if (this.rowAt(x, y) != null || scrollY != 0 && x >= this.leftPos + LIST_X && x < this.leftPos + LIST_X + LIST_W
			&& y >= this.topPos + LIST_Y && y < this.topPos + LIST_Y + ROW_H * VISIBLE_ROWS) {
			this.scroll -= (int) Math.signum(scrollY);
			return true;
		}
		return super.mouseScrolled(x, y, scrollX, scrollY);
	}
}
