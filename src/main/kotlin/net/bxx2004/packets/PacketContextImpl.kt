package net.bxx2004.packets

import net.bxx2004.packets.identifier.ContextIdentifier
import java.util.*

class PacketContextImpl(packetType:PacketType? = null) : PacketContext{
    companion object{
        const val serialVersionUID = 42L
    }

    override fun forEach(func: (identifier: ContextIdentifier, value: Any) -> Unit) {
        data.forEach { (t, u) ->
            func(t,u)
        }
    }
    val data = HashMap<ContextIdentifier,Any>()
    init {
        data[ContextIdentifier.of(ContextType.STRING,"packet_id")] = UUID.randomUUID()
        data[ContextIdentifier.of(ContextType.ENUM,"packet_type")] = packetType?:PacketType.ALL
        data[ContextIdentifier.of(ContextType.STRING,"time_stamp")] = System.currentTimeMillis()
    }
    override fun write(identifier: ContextIdentifier, value: Any, override: Boolean) :PacketContext{
        if (override){
            data[identifier] = value
        }else{
            data.putIfAbsent(identifier,value)
        }
        return this
    }
    fun takeFuture(): PacketContext{
        if (breakpointCache.containsKey(read<String>(ContextIdentifier(ContextType.STRING,"breakpoint_id"))!!)){
            var a = breakpointCache[read<String>(ContextIdentifier(ContextType.STRING,"breakpoint_id"))!!]!!
            breakpointCache.remove(read<String>(ContextIdentifier(ContextType.STRING,"breakpoint_id"))!!)
            return a
        }else{
            return this
        }
    }
    override fun <T> read(identifier: ContextIdentifier): T? {
        var r:T? = null
        data.forEach { (t, u) ->
            if (t == identifier){
                r = u as T
            }
        }
        return r
    }

    override fun equals(other: Any?): Boolean {
        if (other is PacketContext) {
            return data[ContextIdentifier.of(ContextType.STRING,"packet_id")] == other.read(ContextIdentifier.of(ContextType.STRING,"packet_id"))
        }
        return false
    }
}