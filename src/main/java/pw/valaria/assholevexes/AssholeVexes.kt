package pw.valaria.assholevexes

import org.bukkit.entity.EntityType
import org.bukkit.entity.Vex
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import org.bukkit.plugin.java.JavaPlugin

class AssholeVexes : JavaPlugin(), Listener {
    val trackedVex: ArrayList<Vex> = ArrayList()
    override fun onEnable() {
        // Plugin startup logic
        this.server.scheduler.runTaskTimer(this,
            Runnable {
                for (trackedVex in trackedVex) {
                    trackedVex.isInvisible = trackedVex.isCharging
                }

            }, 2L, 2L
        )
        this.server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun targetPlayer(e: EntityTargetLivingEntityEvent) {
        val tracker = e.entity;
        if (tracker is Vex) {
            val trackee = e.target;
            if (trackee == null) {
                trackedVex.remove(tracker);
                tracker.isInvisible = false;
            } else if (trackee.type == EntityType.PLAYER) {
                trackedVex.add(tracker);
            }

        }
    }
}