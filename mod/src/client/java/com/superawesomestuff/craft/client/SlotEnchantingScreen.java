package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.enchanting.Cost;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import com.superawesomestuff.craft.enchanting.SlotUpgrade;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;

/**
 * The Season 1 enchanting table, copied from Ascendant Arcana's layout: the item on the left, a scrolling list of
 * enchantment tiles in the middle, the selected enchantment's description top right, and material slots plus the
 * Enchant button bottom right. Fill the material slots from your inventory and the button lights up. Remove needs
 * a second click to confirm. An enchanted book in a material slot pays one level of an enchantment it holds.
 */
public class SlotEnchantingScreen extends AbstractContainerScreen<SlotEnchantingMenu> {
	private static final int WIDTH = 256;
	private static final int HEIGHT = 222;
	private static final int LIST_X = 56;
	private static final int LIST_Y = 8;
	private static final int TILE_W = 98;
	private static final int TILE_H = 23;
	private static final int VISIBLE = 5;
	private static final int LIST_H = TILE_H * VISIBLE;
	private static final int PANEL_X = 164;
	private static final int PANEL_W = 86;
	private static final int INFO_Y = 8;
	private static final int INFO_H = 52;
	private static final int MATERIAL_BOX_Y = 64;
	private static final int MATERIAL_BOX_H = LIST_Y + LIST_H - MATERIAL_BOX_Y;
	private static final int BUTTON_Y = 108;
	private static final int BUTTON_H = 14;
	private static final int ACTION_X = 166;
	private static final int ACTION_W = 41;
	private static final int REMOVE_X = 210;
	private static final int REMOVE_W = 38;
	private static final int CONFIRM_TICKS = 60;
	private static final float SMALL = 0.75F;

	private static final int BACKGROUND = 0xFFC6C6C6;
	private static final int DARK = 0xFF373737;
	private static final int LIGHT = 0xFFFFFFFF;
	private static final int SLOT = 0xFF8B8B8B;
	private static final int BOX = 0xFF6F6655;
	private static final int TILE = 0xFF8C826D;
	private static final int TILE_LOCKED = 0xFF5E574A;
	private static final int TILE_SELECTED = 0xFFC9839B;
	private static final int TILE_HOVER = 0xFFA3987F;
	private static final int TEXT = 0xFF404040;
	private static final int TEXT_LIGHT = 0xFFF0EAD6;
	private static final int TEXT_DIM = 0xFFB0A890;
	private static final int TEXT_ON = 0xFFFFF4A0;
	private static final int RED = 0xFFFF7070;
	private static final int GREEN = 0xFF90FF90;

	private int scroll;
	private @Nullable Object selectedKey;
	private ItemStack lastItem = ItemStack.EMPTY;
	private @Nullable Object confirmRemoveKey;
	private int confirmRemoveUntil;
	private int ticks;

	private enum Kind { ENCHANTMENT, UPGRADE }

	/** One tile. {@code key} identifies it across frames so the selection sticks while the item changes. */
	private record Entry(Kind kind, Object key, @Nullable Holder<Enchantment> enchantment, int registryId,
		@Nullable SlotUpgrade upgrade, int level, int maxLevel, EnchantRules.Check add, EnchantRules.@Nullable Check remove) {
		boolean locked() {
			return !this.add.ok() && this.add.status() != EnchantRules.Status.MAX_LEVEL && this.level == 0;
		}
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
		Registry<Enchantment> registry = this.minecraft.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		List<Holder<Enchantment>> options = EnchantRules.options(item, registry);
		options.sort(Comparator.comparing(h -> h.value().description().getString()));
		for (Holder<Enchantment> enchantment : options) {
			int level = EnchantRules.enchantments(item).getLevel(enchantment);
			entries.add(new Entry(Kind.ENCHANTMENT, Objects.requireNonNull(EnchantRules.id(enchantment)), enchantment,
				registry.getId(enchantment.value()), null, level, enchantment.value().getMaxLevel(),
				EnchantRules.checkAddLevel(item, enchantment), level > 0 ? EnchantRules.checkRemove(item, enchantment) : null));
		}
		for (SlotUpgrade upgrade : SlotUpgrade.values()) {
			int done = EnchantRules.hasUpgrade(item, upgrade) ? 1 : 0;
			entries.add(new Entry(Kind.UPGRADE, upgrade, null, -1, upgrade, done, 1, EnchantRules.checkUpgrade(item, upgrade), null));
		}
		return entries;
	}

	private List<Cost> payCosts(Entry entry) {
		return entry.kind == Kind.ENCHANTMENT ? this.menu.addCosts(entry.enchantment, entry.add) : entry.add.costs();
	}

	private boolean usesVoucher(Entry entry) {
		return entry.kind == Kind.ENCHANTMENT && entry.add.ok() && this.menu.voucherSlot(entry.enchantment) >= 0;
	}

	private boolean canAdd(Entry entry) {
		return entry.add.ok() && this.menu.canPay(this.minecraft.player, this.payCosts(entry));
	}

	private @Nullable Entry selected(List<Entry> entries) {
		for (Entry entry : entries) {
			if (entry.key.equals(this.selectedKey)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.ticks++;
		ItemStack item = this.menu.item();
		if (!ItemStack.isSameItem(item, this.lastItem)) {
			this.lastItem = item.copy();
			this.scroll = 0;
			this.selectedKey = null;
		}
		if (this.ticks > this.confirmRemoveUntil) {
			this.confirmRemoveKey = null;
		}
	}

	// Drawing helpers

	private void bevel(GuiGraphicsExtractor graphics, int x, int y, int w, int h, int topLeft, int bottomRight) {
		graphics.horizontalLine(x, x + w - 1, y, topLeft);
		graphics.verticalLine(x, y, y + h - 1, topLeft);
		graphics.horizontalLine(x, x + w - 1, y + h - 1, bottomRight);
		graphics.verticalLine(x + w - 1, y, y + h - 1, bottomRight);
	}

	private void box(GuiGraphicsExtractor graphics, int x, int y, int w, int h, int fill) {
		graphics.fill(x, y, x + w, y + h, fill);
		this.bevel(graphics, x, y, w, h, DARK, LIGHT);
	}

	private void small(GuiGraphicsExtractor graphics, Component text, int x, int y, int color) {
		graphics.pose().pushMatrix();
		graphics.pose().translate(x, y);
		graphics.pose().scale(SMALL, SMALL);
		graphics.text(this.font, text, 0, 0, color, false);
		graphics.pose().popMatrix();
	}

	/** Word-wrapped small text. Returns the y below the last line. */
	private int smallWrapped(GuiGraphicsExtractor graphics, FormattedText text, int x, int y, int width, int color, int maxLines) {
		List<FormattedCharSequence> lines = this.font.split(text, (int) (width / SMALL));
		graphics.pose().pushMatrix();
		graphics.pose().translate(x, y);
		graphics.pose().scale(SMALL, SMALL);
		int drawn = 0;
		for (FormattedCharSequence line : lines) {
			if (drawn >= maxLines) {
				break;
			}
			graphics.text(this.font, line, 0, drawn * 9, color, false);
			drawn++;
		}
		graphics.pose().popMatrix();
		return y + (int) Math.ceil(drawn * 9 * SMALL);
	}

	private void icon(GuiGraphicsExtractor graphics, ItemStack stack, int x, int y) {
		graphics.pose().pushMatrix();
		graphics.pose().translate(x, y);
		graphics.pose().scale(SMALL, SMALL);
		graphics.fakeItem(stack, 0, 0);
		graphics.pose().popMatrix();
	}

	private Component title(Entry entry) {
		return switch (entry.kind) {
			case UPGRADE -> Component.translatable("superawesomecraft.enchanting.add_slot", entry.upgrade.title());
			case ENCHANTMENT -> entry.enchantment.value().description();
		};
	}

	private Component description(Entry entry) {
		return switch (entry.kind) {
			case UPGRADE -> Component.translatable(entry.level > 0
				? "superawesomecraft.enchanting.upgrade_done" : "superawesomecraft.enchanting.upgrade_detail");
			case ENCHANTMENT -> {
				Identifier id = EnchantRules.id(entry.enchantment);
				String key = "superawesomecraft.enchanting.desc." + id.getNamespace() + "." + id.getPath();
				yield Component.translatableWithFallback(key, "");
			}
		};
	}

	private static String roman(int level) {
		return Component.translatable("enchantment.level." + level).getString();
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
			graphics.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, SLOT);
			this.bevel(graphics, x + slot.x - 1, y + slot.y - 1, 18, 18, DARK, LIGHT);
		}

		List<Entry> entries = this.entries();
		this.scroll = Math.max(0, Math.min(this.scroll, entries.size() - VISIBLE));
		this.box(graphics, x + LIST_X - 1, y + LIST_Y - 1, TILE_W + 8, LIST_H + 2, BOX);
		for (int i = 0; i < VISIBLE && i + this.scroll < entries.size(); i++) {
			this.extractTile(graphics, entries.get(i + this.scroll), x + LIST_X, y + LIST_Y + i * TILE_H, mouseX, mouseY);
		}
		int trackX = x + LIST_X + TILE_W + 1;
		graphics.fill(trackX, y + LIST_Y, trackX + 5, y + LIST_Y + LIST_H, 0xFF2E2A22);
		if (entries.size() > VISIBLE) {
			int barH = Math.max(14, LIST_H * VISIBLE / entries.size());
			int barY = (LIST_H - barH) * this.scroll / (entries.size() - VISIBLE);
			graphics.fill(trackX, y + LIST_Y + barY, trackX + 5, y + LIST_Y + barY + barH, 0xFFC0B898);
		}

		this.box(graphics, x + PANEL_X, y + INFO_Y, PANEL_W, INFO_H, BOX);
		this.box(graphics, x + PANEL_X, y + MATERIAL_BOX_Y, PANEL_W, MATERIAL_BOX_H, BOX);
		for (int i = 0; i <= SlotEnchantingMenu.LAST_MATERIAL_SLOT - SlotEnchantingMenu.FIRST_MATERIAL_SLOT; i++) {
			int sx = x + SlotEnchantingMenu.MATERIAL_X - 1;
			int sy = y + SlotEnchantingMenu.MATERIAL_Y - 1 + i * SlotEnchantingMenu.MATERIAL_GAP;
			graphics.fill(sx, sy, sx + 18, sy + 18, SLOT);
			this.bevel(graphics, sx, sy, 18, 18, DARK, LIGHT);
		}
		this.extractPanel(graphics, this.selected(entries), x, y, mouseX, mouseY);
	}

	private void extractTile(GuiGraphicsExtractor graphics, Entry entry, int x, int y, int mouseX, int mouseY) {
		boolean selected = entry.key.equals(this.selectedKey);
		boolean hover = mouseX >= x && mouseX < x + TILE_W && mouseY >= y && mouseY < y + TILE_H;
		int fill = selected ? TILE_SELECTED : entry.locked() ? TILE_LOCKED : hover ? TILE_HOVER : TILE;
		graphics.fill(x, y, x + TILE_W, y + TILE_H - 1, fill);
		this.bevel(graphics, x, y, TILE_W, TILE_H - 1, 0xFFB8AE95, 0xFF4A4438);

		List<Cost> costs = entry.add.ok() ? this.payCosts(entry) : List.of();
		int iconsW = costs.size() * 13;
		String name = this.font.plainSubstrByWidth(this.title(entry).getString(), (int) ((TILE_W - 6 - iconsW) / SMALL));
		int nameColor = entry.locked() ? TEXT_DIM : entry.level > 0 && entry.kind == Kind.ENCHANTMENT ? TEXT_ON : TEXT_LIGHT;
		this.small(graphics, Component.literal(name), x + 3, y + 3, nameColor);

		Component second = switch (entry.kind) {
			case ENCHANTMENT -> entry.locked()
				? Component.translatable(entry.add.status().translationKey())
				: Component.translatable("superawesomecraft.enchanting.tile_level", entry.level, entry.maxLevel);
			case UPGRADE -> Component.translatable(entry.level > 0 ? "superawesomecraft.enchanting.tile_done" : "superawesomecraft.enchanting.tile_slot");
		};
		this.small(graphics, second, x + 3, y + 12, entry.locked() ? 0xFFE08080 : selected ? 0xFF5A2E3E : TEXT_DIM);

		for (int i = 0; i < costs.size(); i++) {
			int ix = x + TILE_W - 15 - (costs.size() - 1 - i) * 13;
			this.icon(graphics, new ItemStack(costs.get(i).display()), ix, y + 3);
			boolean enough = this.minecraft.player.hasInfiniteMaterials() || this.menu.materialCount(costs.get(i)) >= costs.get(i).count();
			this.small(graphics, Component.literal(String.valueOf(costs.get(i).count())), ix, y + 14, enough ? GREEN : RED);
		}
	}

	private void extractPanel(GuiGraphicsExtractor graphics, @Nullable Entry entry, int x, int y, int mouseX, int mouseY) {
		int infoX = x + PANEL_X + 3;
		int infoY = y + INFO_Y + 3;
		int width = PANEL_W - 6;
		if (entry == null) {
			Component title = this.menu.item().isEmpty()
				? Component.translatable("superawesomecraft.enchanting.no_item_title") : this.menu.item().getHoverName();
			this.small(graphics, Component.literal(this.font.plainSubstrByWidth(title.getString(), (int) (width / SMALL))).withStyle(s -> s.withUnderlined(true)),
				infoX, infoY, TEXT_LIGHT);
			this.smallWrapped(graphics, Component.translatable(this.menu.item().isEmpty()
				? "superawesomecraft.enchanting.hint" : "superawesomecraft.enchanting.select"), infoX, infoY + 10, width, TEXT_DIM, 5);
			this.button(graphics, x + ACTION_X, y + BUTTON_Y, ACTION_W, Component.translatable("superawesomecraft.enchanting.button.enchant"),
				false, false, mouseX, mouseY);
			return;
		}

		Component heading = this.title(entry);
		if (entry.kind == Kind.ENCHANTMENT && entry.level < entry.maxLevel) {
			heading = Component.empty().append(heading).append(" " + roman(entry.level + 1));
		}
		this.small(graphics, Component.literal(this.font.plainSubstrByWidth(heading.getString(), (int) (width / SMALL)))
			.withStyle(s -> s.withUnderlined(true)), infoX, infoY, TEXT_ON);
		this.smallWrapped(graphics, this.description(entry), infoX, infoY + 10, width, TEXT_LIGHT, 5);

		// Materials
		int labelX = x + SlotEnchantingMenu.MATERIAL_X + 19;
		int labelW = x + PANEL_X + PANEL_W - 3 - labelX;
		if (entry.add.ok()) {
			List<Cost> costs = this.payCosts(entry);
			int row = 0;
			if (this.usesVoucher(entry)) {
				this.smallWrapped(graphics, Component.translatable("superawesomecraft.enchanting.voucher"), labelX,
					y + SlotEnchantingMenu.MATERIAL_Y + 1, labelW, GREEN, 2);
				row = 1;
			}
			for (int i = 0; i < costs.size() && row + i < 2; i++) {
				Cost cost = costs.get(i);
				boolean enough = this.minecraft.player.hasInfiniteMaterials() || this.menu.materialCount(cost) >= cost.count();
				Component label = Component.translatable("superawesomecraft.enchanting.cost", cost.count(), new ItemStack(cost.display()).getHoverName());
				this.smallWrapped(graphics, label, labelX, y + SlotEnchantingMenu.MATERIAL_Y + 1 + (row + i) * SlotEnchantingMenu.MATERIAL_GAP,
					labelW, enough ? TEXT_LIGHT : RED, 2);
			}
			if (costs.isEmpty() && !this.usesVoucher(entry)) {
				this.small(graphics, Component.translatable("superawesomecraft.enchanting.free"), labelX, y + SlotEnchantingMenu.MATERIAL_Y + 4, GREEN);
			}
		} else {
			Component reason = Component.translatable(entry.add.status().translationKey());
			this.smallWrapped(graphics, reason, labelX, y + SlotEnchantingMenu.MATERIAL_Y + 1, labelW, entry.locked() ? RED : TEXT_DIM, 4);
		}

		// Buttons
		Component action = Component.translatable(switch (entry.kind) {
			case ENCHANTMENT -> "superawesomecraft.enchanting.button.enchant";
			case UPGRADE -> "superawesomecraft.enchanting.button.add_slot";
		});
		this.button(graphics, x + ACTION_X, y + BUTTON_Y, ACTION_W, action, this.canAdd(entry), false, mouseX, mouseY);
		if (entry.remove != null) {
			boolean confirming = entry.key.equals(this.confirmRemoveKey);
			Component remove = Component.translatable(confirming ? "superawesomecraft.enchanting.button.confirm" : "superawesomecraft.enchanting.button.remove");
			this.button(graphics, x + REMOVE_X, y + BUTTON_Y, REMOVE_W, remove,
				entry.remove.ok() && this.menu.canPay(this.minecraft.player, entry.remove.costs()), confirming, mouseX, mouseY);
		}
	}

	private void button(GuiGraphicsExtractor graphics, int x, int y, int w, Component label, boolean active, boolean warning, int mouseX, int mouseY) {
		boolean hover = active && mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + BUTTON_H;
		int fill = !active ? 0xFF5A5446 : warning ? (hover ? 0xFFD05050 : 0xFFB04040) : hover ? 0xFFB9AF96 : 0xFF9C9279;
		graphics.fill(x, y, x + w, y + BUTTON_H, fill);
		this.bevel(graphics, x, y, w, BUTTON_H, active ? 0xFFD8CFB4 : 0xFF6E6858, DARK);
		int textW = (int) (this.font.width(label) * SMALL);
		this.small(graphics, label, x + (w - textW) / 2, y + 4, active ? 0xFF2A261E : 0xFF8A8270);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
		super.extractLabels(graphics, xm, ym);
		ItemStack item = this.menu.item();
		if (!item.isEmpty()) {
			Component slots = Component.translatable("superawesomecraft.enchanting.slots", EnchantRules.usedSlots(item), EnchantRules.totalSlots(item));
			int w = (int) (this.font.width(slots) * SMALL);
			this.small(graphics, slots, SlotEnchantingMenu.ITEM_X + 8 - w / 2, SlotEnchantingMenu.ITEM_Y + 20, TEXT);
		}
	}

	// Input

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		List<Entry> entries = this.entries();
		double mx = event.x() - this.leftPos;
		double my = event.y() - this.topPos;
		if (mx >= LIST_X && mx < LIST_X + TILE_W && my >= LIST_Y && my < LIST_Y + LIST_H) {
			int index = (int) ((my - LIST_Y) / TILE_H) + this.scroll;
			if (index < entries.size()) {
				this.selectedKey = entries.get(index).key;
				this.confirmRemoveKey = null;
			}
			return true;
		}
		Entry entry = this.selected(entries);
		if (entry != null && my >= BUTTON_Y && my < BUTTON_Y + BUTTON_H) {
			if (mx >= ACTION_X && mx < ACTION_X + ACTION_W) {
				return this.press(switch (entry.kind) {
					case ENCHANTMENT -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, entry.registryId);
					case UPGRADE -> SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_UPGRADE, entry.upgrade.ordinal());
				});
			}
			if (entry.remove != null && mx >= REMOVE_X && mx < REMOVE_X + REMOVE_W) {
				if (entry.key.equals(this.confirmRemoveKey)) {
					this.confirmRemoveKey = null;
					return this.press(SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_REMOVE, entry.registryId));
				}
				this.confirmRemoveKey = entry.key;
				this.confirmRemoveUntil = this.ticks + CONFIRM_TICKS;
				return true;
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
		if (scrollY != 0 && mx >= LIST_X && mx < LIST_X + TILE_W + 6 && my >= LIST_Y && my < LIST_Y + LIST_H) {
			this.scroll -= (int) Math.signum(scrollY);
			return true;
		}
		return super.mouseScrolled(x, y, scrollX, scrollY);
	}
}
