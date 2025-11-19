package mpl1.thelastguest.model.Character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.*;

import static java.lang.Math.random;

public class Murderer extends Character{

    private int killNbr = 1;
    private int roundBeoreDrop;
    private Item currentWeapon;

    public Murderer(Npc npc) {
        super(npc.getName(), npc.getStats(), npc.getX(),  npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    public int getKillNbr(){
        return this.killNbr;
    }

    public void addKillNbr(){
        this.killNbr++;
    }

    @Override
    public void getItem(List<Item> items){
        if (roundBeoreDrop <= 0 && !getItems().isEmpty()){
            dropItem(currentWeapon);
        }
        for (Item item : items) {
            if (item.getClass() == ActionItem.class){
                if (Objects.equals(((ActionItem) item).getAction(), "kill")){
                    pickItem(item);
                }
            }
        }
        roundBeoreDrop -- ;
    }

    public void setRoundBeoreDrop(int roundBeoreDrop) {
        this.roundBeoreDrop = roundBeoreDrop;
    }

    @Override
    public boolean kill(List <Character> npcs) {
        if (getItems().isEmpty())
            return false;
        for  (Character npc : npcs) {
            if (isPerceptible(npc.getX(), npc.getY())) {
                Item weapon = getItems().get((int)(random() * getItems().size()));
                npc.addClues(this, weapon);
                npc.setAlive(false);
                if (this.getClass() == Murderer.class) {
                    ((Murderer) this).addKillNbr();
                }
                this.currentWeapon = weapon;
                this.roundBeoreDrop = (int) (random() * 3);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPerceptible(int posX, int posY) {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        Queue<int[]> queue = new ArrayDeque<>();
        Map<String, String> prev = new HashMap<>();
        List<int[]> path = new ArrayList<>();
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int[][] grid = new int[murInt.getHeight()][murInt.getWidth()];
        int[] start = { getX(), getY() };
        int[] goal  = { posX , posY };
        int[] current;

        for (int y = 0; y < murInt.getHeight(); y++) {
            for (int x = 0; x < murInt.getWidth(); x++) {
                if (murInt.getCell(x, y) == null)
                    grid[y][x] = 0;
                else
                    grid[y][x] = 1;
            }
        }
        queue.add(start);
        prev.put(Arrays.toString(start), null);
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (Arrays.equals(current, goal))
                break;
            for (int[] dir : directions) {
                int nextX = current[0] + dir[0];
                int nextY = current[1] + dir[1];
                if (nextX >= 0 && nextY >= 0 && nextX < murInt.getWidth() && nextY < murInt.getHeight()) {
                    if (grid[nextY][nextX] == 0) {
                        int[] around = {nextX, nextY};
                        String key = Arrays.toString(around);
                        if (!prev.containsKey(key)) {
                            prev.put(key, Arrays.toString(current));
                            queue.add(around);
                        }
                    }
                }
            }
        }
        String key = Arrays.toString(goal);
        while (key != null) {
            String[] parts = key.replaceAll("[\\[\\] ]", "").split(",");
            path.add(new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
            });
            key = prev.get(key);
        }
        Collections.reverse(path);

        if (path.size() == 1 || path.size() - 1 > getPer()){
            return false;
        }
        return true;
    }
}
