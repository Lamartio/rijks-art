package io.lamart.rijksart.logic.overview

import io.lamart.lux.Machine

class OverviewMachine internal constructor(machine: Machine<OverviewState, OverviewActions>): Machine<OverviewState, OverviewActions>(machine)