package com.example.tpsynthese.domain.models

import kotlinx.serialization.Serializable

@Serializable
 class Gateway(
   val href:String = "",
   val serialNumber:String  = "",
   val revision:String  = "",
   val pin:String  = "",
   val hash:String  = "",
   val Customer:Customer  =  Customer(),
   val config: Config  = Config(),
   val connection: Connection  = Connection()
)
