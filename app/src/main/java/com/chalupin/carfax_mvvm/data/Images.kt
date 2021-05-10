package com.chalupin.carfax_mvvm.data

import androidx.room.Embedded
import java.io.Serializable

data class Images(@Embedded(prefix = "frst")val firstPhoto: FirstPhoto) : Serializable