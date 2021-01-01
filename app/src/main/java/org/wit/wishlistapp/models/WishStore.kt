package org.wit.wishlistapp.models

interface WishStore {
    fun findAll() : List<WishModel>
    fun create(wish: WishModel)
    fun update(wish: WishModel)
}