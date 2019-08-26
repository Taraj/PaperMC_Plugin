import events.PlayerJoin
import org.bukkit.plugin.java.JavaPlugin

class Plugin : JavaPlugin(){

    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
    }
}