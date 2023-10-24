package net.bxx2004.packets.identifier

import net.bxx2004.packets.ContextType

class ContextIdentifier(type: ContextType,key:String) : XIdentifier(type.name,key){
    companion object{
        @JvmStatic
        fun of(o:String): ContextIdentifier {
            return ContextIdentifier(ContextType.valueOf(o.split(":")[0]),o.split(":")[1])
        }
        @JvmStatic
        fun of(namespace:ContextType,path:String): ContextIdentifier {
            return ContextIdentifier(namespace, path)
        }
    }
}