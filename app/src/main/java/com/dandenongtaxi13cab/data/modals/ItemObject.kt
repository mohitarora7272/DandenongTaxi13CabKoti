package com.dandenongtaxi13cab.data.modals

/**
 * @author by Mohit Arora on 3/8/18.
 */
class ItemObject(_title: String, _imageUrl: String) {
    var title: String = _title
        get() = field
        set(value) {
            field = value
        }

    var imageUrl: String = _imageUrl
        get() = field
        set(value) {
            field = value
        }
}