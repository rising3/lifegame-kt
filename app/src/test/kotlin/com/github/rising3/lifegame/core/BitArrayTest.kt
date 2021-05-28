package com.github.rising3.lifegame.core

import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.*

class BitArrayTest : ExpectSpec({
    context("basic cases") {
        val actual: BitArray = BitArray.new(10, 9)
        expect("initialize") {
            actual.rows shouldBe 10
            actual.cols shouldBe 9
            actual.countBits() shouldBe 0
            actual.isVirtualArray shouldBe false
        }

        expect("setBit(true) Should be true") {
            actual.setBit(1, 1).getBit(1, 1) shouldBe true
        }

        expect("countBits() Should be 1") {
            actual.countBits() shouldBe 1
        }

        expect("setBit(false) Should be false") {
            actual.setBit(1, 1, false).getBit(1, 1) shouldBe false
        }

        expect("countBits() Should be 0") {
            actual.countBits() shouldBe 0
        }

        expect("clear() Should be countBit == 0") {
            // given
            actual.setBit(0, 0).setBit(0, 1).countBits() shouldBe 2

            // then
            actual.clear().countBits() shouldBe 0
        }

        expect("copy() Should be deep copy") {
            // given
            actual.setBit(1, 1).countBits() shouldBe 1

            // when
            val copy = actual.copy()

            // then
            actual.clear().countBits() shouldBe 0
            copy.countBits() shouldBe 1
        }
    }

    context("non virtual array cases") {
        context("positive position with out of range") {
            val actual: BitArray = BitArray.new(2, 9, false)
            expect("getBit() Should be false forever") {
                actual.setBit(0, 9).getBit(0, 0) shouldBe false
                actual.setBit(2, 0).getBit(0, 0) shouldBe false
            }
        }
        context("negative position with out of range") {
            val actual: BitArray = BitArray.new(2, 9, false)
            expect("getBit() Should be false forever") {
                actual.setBit(0, -1).getBit(0, 8) shouldBe false
                actual.setBit(10, 0).getBit(1, 0) shouldBe false
            }
        }
    }
    context("virtual array cases") {
        context("positive position with out of range") {
            val actual: BitArray = BitArray.new(2, 9, true)
            expect("getBit() Should be true") {
                actual.setBit(0, 9).getBit(0, 0) shouldBe true
                actual.setBit(2, 0).getBit(0, 0) shouldBe true
            }
        }
        context("negative position with out of range") {
            val actual: BitArray = BitArray.new(2, 9, true)
            expect("getBit() Should be true") {
                actual.setBit(0, -1).getBit(0, 8) shouldBe true
                actual.setBit(-1, 0).getBit(1, 0) shouldBe true
            }
        }
    }

    context("string builder cases") {
        expect("build() should be 10101010") {
            BitArray.new(1, 8)
                .setBit(0, 0).setBit(0, 2).setBit(0, 4).setBit(0, 6)
                .stringBuilder().build() shouldBe "10101010"
        }

        expect("build() should be !10101010,00000000#") {
            BitArray.new(2, 8)
                .setBit(0, 0).setBit(0, 2).setBit(0, 4).setBit(0, 6)
                .stringBuilder()
                .withLineSeparator(",")
                .withLinePrefix("!")
                .withLinePostfix("#")
                .build() shouldBe "!10101010,00000000#"
        }

        expect("build() should be !10101010,10000000#!10000001,10000000#") {
            BitArray.new(2, 9)
                .setBit(0, 0).setBit(0, 2).setBit(0, 4).setBit(0, 6).setBit(0, 8)
                .setBit(1, 0).setBit(1, 7).setBit(1, 8)
                .stringBuilder()
                .withByteSeparator(",")
                .withBytePrefix("!")
                .withBytePostfix("#")
                .build() shouldBe "!10101010,10000000#!10000001,10000000#"
        }

        expect("build() should be !1,0,1,0,1,0,1,0#!1,0,0,0,0,0,0,0#!1,0,0,0,0,0,0,1#!1,0,0,0,0,0,0,0#") {
            BitArray.new(2, 9)
                .setBit(0, 0).setBit(0, 2).setBit(0, 4).setBit(0, 6).setBit(0, 8)
                .setBit(1, 0).setBit(1, 7).setBit(1, 8)
                .stringBuilder()
                .withBitSeparator(",")
                .withBitPrefix("!")
                .withBitPostfix("#")
                .build() shouldBe "!1,0,1,0,1,0,1,0#!1,0,0,0,0,0,0,0#!1,0,0,0,0,0,0,1#!1,0,0,0,0,0,0,0#"
        }
    }
})