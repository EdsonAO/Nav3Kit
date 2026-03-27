package com.earratea.nav3kit.navigation

import androidx.lifecycle.ViewModel

abstract class RouterViewModel : ViewModel(), RouterEffectEmitter by RouterEffectEmitterDelegate()