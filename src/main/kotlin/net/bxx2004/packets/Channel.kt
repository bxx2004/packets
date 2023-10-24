package net.bxx2004.packets

import net.bxx2004.packets.identifier.XIdentifier

class Channel(var identifier: XIdentifier, var platform: Platform) {
    init {
        channels.add(this)
    }
    override fun equals(other: Any?): Boolean {
        if (other is Channel){
            return this.identifier == other.identifier && this.platform == other.platform
        }
        return false
    }

    override fun toString(): String {
        return identifier.toString()
    }
}
val channels = ArrayList<Channel>()
fun unregisterChannel(identifier: XIdentifier){
    channels.removeIf { it.identifier == identifier }
}