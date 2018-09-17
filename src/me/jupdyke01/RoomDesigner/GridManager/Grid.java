package me.jupdyke01.RoomDesigner.GridManager;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.jupdyke01.RoomDesigner.Main;

public class Grid {

	private ArrayList<RGBStorage> undoCache = new ArrayList<>();

	private Main main;
	private ArrayList<TileSlot> slots = new ArrayList<>();
	public TileSlot selected = null;


	public Grid(Main main) {
		this.main = main;
		for (int x = 0; x < main.getTileX(); x++) {
			for (int y = 0; y < main.getTileY(); y++) {
				slots.add(new TileSlot(x * main.getTileSize(), y * main.getTileSize(), this));
			}
		}
	}

	public void export() {
		File folder = new File("Rooms");
		if (!folder.exists())
			folder.mkdir();
		File imageFile = new File(folder, folder.listFiles().length + ".png");
		if (!imageFile.exists()) {
			try {
				imageFile.createNewFile();
			} catch (IOException e) {

			}
		}
	}

	public void tick() {
		for (TileSlot slot : slots) {
			slot.tick();
		}
	}

	public void render(Graphics g) {
		for (int x = 0; x < main.getTileX(); x++) {
			for (int y = 0; y < main.getTileY(); y++) {
				g.setColor(Color.BLACK);
				g.drawRect(x * main.getTileSize() + main.getxOffset(), y * main.getTileSize() + main.getyOffset(), main.getTileSize(), main.getTileSize());
			}
		}
		for (TileSlot slot : slots) {
			slot.render(g);
		}
		for (TileSlot slot : slots) {
			slot.renderMenu(g);
		}
	}

	public void addUndo(RGBStorage rgbs) {
		if(undoCache.size() >= 50)
			undoCache.remove(0);
		//System.out.println(undoCache.size());
		undoCache.add(rgbs);
	}

	public RGBStorage getUndo() {
		if (undoCache.isEmpty())
			return null;
		RGBStorage rgbs = undoCache.get(undoCache.size() - 1);
		return rgbs;
	}

	public void removeUndo() {
		//	System.out.println(undoCache.isEmpty());
		if (undoCache.isEmpty())
			return;
		//	System.out.println(undoCache.size());
		undoCache.remove(undoCache.get(undoCache.size() - 1));
	}

	public Main getMain() {
		return main;
	}

}
