package org.wit.wishlistapp.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


class WishMemStore: WishStore, AnkoLogger {

    val wishies = ArrayList<WishModel>()

    override fun findAll(): List<WishModel> {
        return wishies
    }

    override fun create(wish: WishModel) {
        wish.id = getId()
        wishies.add(wish)
        logAll()
    }

    override fun update(wish: WishModel) {
        var foundWish : WishModel? = wishies.find { w -> w.id ==wish.id }
        if(foundWish != null){
            foundWish.name = wish.name
            foundWish.time = wish.time
            foundWish.description = wish.description
            foundWish.image = wish.image
            logAll()
        }
    }

    override fun delete(wish: WishModel) {
        wishies.remove(wish)
    }

    fun logAll(){
        wishies.forEach {info("${it}")}
    }

}