package io.lamart.rijksart.logic.gallery

import io.lamart.lux.Machine

class GalleryMachine internal constructor(machine: Machine<GalleryViewState, GalleryActions>): Machine<GalleryViewState, GalleryActions>(machine)