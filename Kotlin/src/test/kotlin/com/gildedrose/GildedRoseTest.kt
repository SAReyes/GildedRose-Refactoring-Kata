package com.gildedrose

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class GildedRoseTest : Spek({
    context("the day ends") {
        lateinit var sut: GildedRose
        lateinit var item: Item

        describe("a common item") {
            beforeEachTest {
                item = Item(name = "Standard Item", quality = 25, sellIn = 20)
                sut = GildedRose(arrayOf(item))
            }

            it("decreases its quality") {
                sut.updateQuality()

                item.quality `should equal` 24
            }

            it("decreases its sellIn value") {
                sut.updateQuality()

                item.sellIn `should equal` 19
            }

            it("decreases its quality twice as fast once the sell by date has passed") {
                item.sellIn = 0

                sut.updateQuality()

                item.quality `should equal` 23
            }

            it("s quality never goes below 0") {
                item.quality = 0

                sut.updateQuality()

                item.quality `should equal` 0
            }
        }

        describe("the Aged Brie") {
            beforeEachTest {
                item = Item(name = "Aged Brie", quality = 25, sellIn = 20)
                sut = GildedRose(arrayOf(item))
            }

            it("increases its quality the older it gets") {
                sut.updateQuality()

                item.quality `should equal` 26
            }

            it("increases its quality twice as fast after the sell in date") {
                item.sellIn = 0

                sut.updateQuality()

                item.quality `should equal` 27
            }
        }

        describe("A backstage pass") {
            beforeEachTest {
                item = Item(name = "Backstage passes to a TAFKAL80ETC concert", quality = 25, sellIn = 20)
                sut = GildedRose(arrayOf(item))
            }

            it("increases its quality the older it gets") {
                sut.updateQuality()

                item.quality `should equal` 26
            }

            it("increases its quality twice as fast 10 datys before the concert") {
                item.sellIn = 10

                sut.updateQuality()

                item.quality `should equal` 27
            }

            it("increases its quality by 3 @ 5 days before the concert") {
                item.sellIn = 5

                sut.updateQuality()

                item.quality `should equal` 28
            }

            it("drops its quality to 0 after the concert") {
                item.sellIn = 0

                sut.updateQuality()

                item.quality `should equal` 0
            }
        }

        describe("Sulfuras") {
            beforeEachTest {
                item = Item(name = "Sulfuras, Hand of Ragnaros", quality = 80, sellIn = 20)
                sut = GildedRose(arrayOf(item))
            }

            it("cannot be sold") {
                sut.updateQuality()

                item.sellIn `should equal` 20
            }

            it("never decreases its quality") {
                sut.updateQuality()

                item.quality `should equal` 80
            }
        }
    }
})
