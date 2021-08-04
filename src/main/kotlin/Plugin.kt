import events.PlayerJoin
import org.bukkit.plugin.java.JavaPlugin
@Suppress("UNUSED")
class Plugin : JavaPlugin(){
    override fun onEnable() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
    }
}