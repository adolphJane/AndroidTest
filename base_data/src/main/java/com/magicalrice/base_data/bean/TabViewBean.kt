package com.magicalrice.base_data.bean

import androidx.annotation.DrawableRes

data class TabViewBean (
        var title: String,
        @DrawableRes var drawableId: Int,
        var tag: String,
        var fragmentName: String
)