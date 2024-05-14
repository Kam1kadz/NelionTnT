package buttonroledev.neliontnt;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

public final class NelionTnT extends JavaPlugin implements Listener {

    private int explosionRadius = 10;
    private double fuseTime = 7.0;
    private String customTNTName = "SuperTnT";
    private String customTNTDescription = "Dynomite 10x10";

    private Set<Material> blocksToBreak = new HashSet<>();

    private Map<Integer, Boolean> customTNTEntities = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();

        loadConfigValues();

        blocksToBreak.add(Material.STONE);
    }

    @Override
    public void onDisable() {
    }
    private void loadConfigValues() {
        FileConfiguration config = getConfig();

        explosionRadius = config.getInt("explosionRadius", 10);
        fuseTime = config.getDouble("fuseTime", 7.0);
        customTNTName = ChatColor.translateAlternateColorCodes('&', config.getString("customTNTName", "SuperTnT"));

        List<String> loreLines = config.getStringList("customTNTDescription");
        customTNTDescription = String.join("\n", loreLines);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof TNTPrimed) {
            TNTPrimed tnt = (TNTPrimed) entity;

            if (customTNTEntities.containsKey(tnt.getEntityId())) {

                Location explosionLocation = event.getLocation();

                for (int x = -explosionRadius; x <= explosionRadius; x++) {
                    for (int y = -explosionRadius; y <= explosionRadius; y++) {
                        for (int z = -explosionRadius; z <= explosionRadius; z++) {
                            if (x * x + y * y + z * z <= explosionRadius * explosionRadius) {
                                Location blockLocation = explosionLocation.clone().add(x, y, z);
                                Block block = blockLocation.getBlock();
                                Material blockType = block.getType();

                                if (blockType != Material.BEDROCK &&
                                        blockType != Material.END_PORTAL_FRAME &&
                                        blockType != Material.SPAWNER) {
                                    block.breakNaturally();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private ItemStack createCustomTNT() {
        ItemStack customTNT = new ItemStack(Material.TNT);
        ItemMeta meta = customTNT.getItemMeta();
        meta.setDisplayName(customTNTName);

        List<String> loreLines = new ArrayList<>();
        loreLines.addAll(Arrays.asList(customTNTDescription.split("\\n")));
        meta.setLore(loreLines);

        customTNT.setItemMeta(meta);

        return customTNT;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givetnt")) {
            Player targetPlayer;
            int quantity;

            if (args.length >= 1) {
                targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer == null) {
                    sender.sendMessage("§x§F§F§A§6§0§0§lN§x§F§F§A§6§0§0§le§x§F§F§A§6§0§0§ll§x§F§F§A§6§0§0§li§x§F§F§A§6§0§0§lo§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT §x§F§F§A§6§0§0§l>§x§F§F§A§6§0§0§l> §x§F§F§A§6§0§0И§x§F§F§A§6§0§0г§x§F§F§A§6§0§0р§x§F§F§A§6§0§0о§x§F§F§A§6§0§0к §x§F§F§A§6§0§0н§x§F§F§A§6§0§0е §x§F§F§A§6§0§0н§x§F§F§A§6§0§0а§x§F§F§A§6§0§0й§x§F§F§A§6§0§0д§x§F§F§A§6§0§0е§x§F§F§A§6§0§0н§x§F§F§A§6§0§0!");
                    return true;
                }
            } else {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§x§F§F§A§6§0§0§lN§x§F§F§A§6§0§0§le§x§F§F§A§6§0§0§ll§x§F§F§A§6§0§0§li§x§F§F§A§6§0§0§lo§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT §x§F§F§A§6§0§0§l>§x§F§F§A§6§0§0§l> §x§F§F§A§6§0§0Т§x§F§F§A§6§0§0о§x§F§F§A§6§0§0л§x§F§F§A§6§0§0ь§x§F§F§A§6§0§0к§x§F§F§A§6§0§0о §x§F§F§A§6§0§0и§x§F§F§A§6§0§0г§x§F§F§A§6§0§0р§x§F§F§A§6§0§0о§x§F§F§A§6§0§0к§x§F§F§A§6§0§0и §x§F§F§A§6§0§0м§x§F§F§A§6§0§0о§x§F§F§A§6§0§0г§x§F§F§A§6§0§0у§x§F§F§A§6§0§0т §x§F§F§A§6§0§0и§x§F§F§A§6§0§0с§x§F§F§A§6§0§0п§x§F§F§A§6§0§0о§x§F§F§A§6§0§0л§x§F§F§A§6§0§0ь§x§F§F§A§6§0§0з§x§F§F§A§6§0§0о§x§F§F§A§6§0§0в§x§F§F§A§6§0§0а§x§F§F§A§6§0§0т§x§F§F§A§6§0§0ь §x§F§F§A§6§0§0к§x§F§F§A§6§0§0о§x§F§F§A§6§0§0м§x§F§F§A§6§0§0а§x§F§F§A§6§0§0н§x§F§F§A§6§0§0д§x§F§F§A§6§0§0у §x§F§F§A§6§0§0б§x§F§F§A§6§0§0е§x§F§F§A§6§0§0з §x§F§F§A§6§0§0у§x§F§F§A§6§0§0к§x§F§F§A§6§0§0а§x§F§F§A§6§0§0з§x§F§F§A§6§0§0а§x§F§F§A§6§0§0н§x§F§F§A§6§0§0и§x§F§F§A§6§0§0я §x§F§F§A§6§0§0н§x§F§F§A§6§0§0и§x§F§F§A§6§0§0к§x§F§F§A§6§0§0н§x§F§F§A§6§0§0е§x§F§F§A§6§0§0й§x§F§F§A§6§0§0м§x§F§F§A§6§0§0а §x§F§F§A§6§0§0и§x§F§F§A§6§0§0г§x§F§F§A§6§0§0р§x§F§F§A§6§0§0о§x§F§F§A§6§0§0к§x§F§F§A§6§0§0а§x§F§F§A§6§0§0!");
                    return true;
                }
                targetPlayer = (Player) sender;
            }

            if (args.length == 2) {
                try {
                    quantity = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§x§F§F§A§6§0§0Н§x§F§F§A§6§0§0е§x§F§F§A§6§0§0к§x§F§F§A§6§0§0о§x§F§F§A§6§0§0р§x§F§F§A§6§0§0р§x§F§F§A§6§0§0е§x§F§F§A§6§0§0к§x§F§F§A§6§0§0т§x§F§F§A§6§0§0н§x§F§F§A§6§0§0о§x§F§F§A§6§0§0е §x§F§F§A§6§0§0к§x§F§F§A§6§0§0о§x§F§F§A§6§0§0л§x§F§F§A§6§0§0и§x§F§F§A§6§0§0ч§x§F§F§A§6§0§0е§x§F§F§A§6§0§0с§x§F§F§A§6§0§0т§x§F§F§A§6§0§0в§x§F§F§A§6§0§0о§x§F§F§A§6§0§0!");
                    return true;
                }
            } else {
                quantity = 1;
            }

            if (sender instanceof Player && !((Player) sender).isOp()) {
                sender.sendMessage("§x§F§F§A§6§0§0§lN§x§F§F§A§6§0§0§le§x§F§F§A§6§0§0§ll§x§F§F§A§6§0§0§li§x§F§F§A§6§0§0§lo§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT §x§F§F§A§6§0§0§l>§x§F§F§A§6§0§0§l> §x§F§F§C§4§5§6У §x§F§F§C§4§5§6в§x§F§F§C§4§5§6а§x§F§F§C§4§5§6с §x§F§F§C§4§5§6н§x§F§F§C§4§5§6е§x§F§F§C§4§5§6д§x§F§F§C§4§5§6о§x§F§F§C§4§5§6с§x§F§F§C§4§5§6т§x§F§F§C§4§5§6а§x§F§F§C§4§5§6т§x§F§F§C§4§5§6о§x§F§F§C§4§5§6ч§x§F§F§C§4§5§6н§x§F§F§C§4§5§6о §x§F§F§C§4§5§6п§x§F§F§C§4§5§6р§x§F§F§C§4§5§6а§x§F§F§C§4§5§6в§x§F§F§C§4§5§6!");
                return true;
            }

            for (int i = 0; i < quantity; i++) {
                ItemStack customTNT = createCustomTNT();
                targetPlayer.getInventory().addItem(customTNT);
            }

            targetPlayer.sendMessage("§x§F§F§A§6§0§0§lN§x§F§F§A§6§0§0§le§x§F§F§A§6§0§0§ll§x§F§F§A§6§0§0§li§x§F§F§A§6§0§0§lo§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT§x§F§F§A§6§0§0§ln§x§F§F§A§6§0§0§lT §x§F§F§A§6§0§0§l>§x§F§F§A§6§0§0§l> §x§F§F§C§4§5§6В§x§F§F§C§4§5§6а§x§F§F§C§4§5§6м §x§F§F§C§4§5§6б§x§F§F§C§4§5§6ы§x§F§F§C§4§5§6л §x§F§F§C§4§5§6в§x§F§F§C§4§5§6ы§x§F§F§C§4§5§6д§x§F§F§C§4§5§6а§x§F§F§C§4§5§6н §x§F§F§C§4§5§6м§x§F§F§C§4§5§6о§x§F§F§C§4§5§6д§x§F§F§C§4§5§6и§x§F§F§C§4§5§6ф§x§F§F§C§4§5§6и§x§F§F§C§4§5§6ц§x§F§F§C§4§5§6и§x§F§F§C§4§5§6р§x§F§F§C§4§5§6о§x§F§F§C§4§5§6в§x§F§F§C§4§5§6а§x§F§F§C§4§5§6н§x§F§F§C§4§5§6н§x§F§F§C§4§5§6ы§x§F§F§C§4§5§6й §x§F§F§C§4§5§6д§x§F§F§C§4§5§6и§x§F§F§C§4§5§6н§x§F§F§C§4§5§6а§x§F§F§C§4§5§6м§x§F§F§C§4§5§6и§x§F§F§C§4§5§6т§x§F§F§C§4§5§6!");
        }

        return true;
    }


    private final Map<Player, Long> cooldownMap = new HashMap<>();
    private final long cooldownDuration = 400;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand != null && itemInHand.getType() == Material.TNT &&
                    itemInHand.hasItemMeta() &&
                    itemInHand.getItemMeta().hasDisplayName() &&
                    itemInHand.getItemMeta().getDisplayName().equals(customTNTName)) {

                event.setCancelled(true);

                if (hasCooldown(player)) {
                    return;
                }

                Location blockLocation = event.getClickedBlock().getLocation();
                BlockFace blockFace = event.getBlockFace();

                double offsetX = 0.0;
                double offsetY = 0.0;
                double offsetZ = 0.0;

                switch (blockFace) {
                    case UP:
                        offsetY = 1.0;
                        break;
                    case DOWN:
                        offsetY = -2.0;
                        break;
                    case NORTH:
                        offsetZ = -1.0;
                        break;
                    case SOUTH:
                        offsetZ = 1.0;
                        break;
                    case EAST:
                        offsetX = 1.0;
                        break;
                    case WEST:
                        offsetX = -1.0;
                        break;
                    default:
                        break;
                }

                Location spawnLocation = blockLocation.clone().add(0.5 + offsetX, 1.0 + offsetY, 0.5 + offsetZ);
                Location timerLocation = spawnLocation.clone().add(0.0, 1.0, 0.0); // Changed the Y offset to 2.0

                if (!blockLocation.getWorld().getNearbyEntities(timerLocation, 0.5, 0.5, 0.5).isEmpty()) {
                    return;
                }

                setCooldown(player);

                int amount = itemInHand.getAmount();
                if (amount > 1) {
                    itemInHand.setAmount(amount - 1);
                } else {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }

                TNTPrimed tntEntity = blockLocation.getWorld().spawn(spawnLocation, TNTPrimed.class);
                tntEntity.setFuseTicks((int) (fuseTime * 20)); // 3 seconds (20 ticks per second)
                tntEntity.setMetadata("CustomTNT", new FixedMetadataValue(this, true));

                customTNTEntities.put(tntEntity.getEntityId(), true);

                ArmorStand timerStand = blockLocation.getWorld().spawn(timerLocation, ArmorStand.class);
                timerStand.setGravity(false);
                timerStand.setVisible(false);
                timerStand.setSmall(true);
                timerStand.setMarker(true);
                timerStand.setInvulnerable(true);
                timerStand.setCustomNameVisible(true);
                timerStand.setCustomName("§x§F§F§A§5§0§0❤ §x§F§F§B§E§4§7§l" + fuseTime);
                timerStand.setMetadata("CustomTNTTimer", new FixedMetadataValue(this, tntEntity.getEntityId()));

                tntEntity.setPassenger(timerStand);

                new BukkitRunnable() {
                    double timeLeft = fuseTime;

                    @Override
                    public void run() {
                        timeLeft -= 0.05;

                        if (timeLeft <= 0) {
                            tntEntity.setFuseTicks(0);
                            timerStand.remove();
                            cancel();
                        } else {
                            DecimalFormat df = new DecimalFormat("0.00");
                            timerStand.setCustomName("§x§F§F§A§5§0§0❤ §x§F§F§B§E§4§7§l" + df.format(timeLeft));

                            Location timerLocation = tntEntity.getLocation().add(0, 2.2, 0);
                            timerStand.teleport(timerLocation);
                        }
                    }
                }.runTaskTimer(this, 1, 1);




            }
        }
    }

    private boolean hasCooldown(Player player) {
        long currentTime = System.currentTimeMillis();
        if (cooldownMap.containsKey(player)) {
            long lastTime = cooldownMap.get(player);
            if (currentTime - lastTime < cooldownDuration) {
                return true;
            }
        }
        return false;
    }

    private void setCooldown(Player player) {
        cooldownMap.put(player, System.currentTimeMillis());
    }

    @EventHandler
    public void onTNTDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getEntity();
            if (armorStand.hasMetadata("CustomTNTTimer")) {
                event.setCancelled(true);
                armorStand.remove();
            }
        }
    }
}
