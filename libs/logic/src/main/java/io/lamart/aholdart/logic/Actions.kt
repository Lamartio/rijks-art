package io.lamart.aholdart.logic

interface Actions {
    /**
     * Will get the latest results from persistence and loads it into the state
     */
    fun initialize()

    fun getAndFetchCollection(payload: CollectionPayload)
    fun getAndFetchDetails(objectNumber: String)
}