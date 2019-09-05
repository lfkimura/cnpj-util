package com.kimura.cnpjUtil

class CnpjUtil {

    companion object {
        const val CNPJ_LENGTH = 14

        val unformatCNPJ: String.() -> String = {
            this.replace(".", "")
                .replace("/", "")
                .replace("-", "").let {
                    if (it.length <= CNPJ_LENGTH)
                        it.padStart(CNPJ_LENGTH, '0')
                    else throw Exception("invalid.document")
                }
        }

        fun isValidCNPJ(cnpj: String): Boolean {
            cnpj.unformatCNPJ().let {
                return checkFirstDigit(it) && checkSecondDigit(it)
            }
        }

        val weightMapFirstDigit: Map<Int, Int> = mapOf(
            //position weigth
            12 to 2,
            11 to 3,
            10 to 4,
            9 to 5,
            8 to 6,
            7 to 7,
            6 to 8,
            5 to 9,
            4 to 2,
            3 to 3,
            2 to 4,
            1 to 5
        )
        val weightMapSecondDigit: Map<Int, Int> = mapOf(
            //position weigth
            13 to 2,
            12 to 3,
            11 to 4,
            10 to 5,
            9 to 6,
            8 to 7,
            7 to 8,
            6 to 9,
            5 to 2,
            4 to 3,
            3 to 4,
            2 to 5,
            1 to 6
        )

        private fun checkFirstDigit(cnpj: String): Boolean {
            weightMapFirstDigit.keys.sumBy {
                cnpj.substring(it - 1, it).toInt() * weightMapFirstDigit.get(it)!!

            }.rem(11).let { rest ->
                when (rest) {
                    0 -> 0
                    1 -> 0
                    else -> 11 - rest
                }.let { firstDigitCalculated ->
                    return firstDigitCalculated == cnpj.substring(12, 13).toInt()
                }
                return false
            }
        }

        private fun checkSecondDigit(cnpj: String): Boolean {
            weightMapSecondDigit.keys.sumBy {
                cnpj.substring(it - 1, it).toInt() * weightMapSecondDigit.get(it)!!
            }.rem(11).let { rest ->
                when (rest) {
                    0 -> 0
                    1 -> 0
                    else -> 11 - rest
                }.let { secondDigitCalculated ->
                    return secondDigitCalculated == cnpj.substring(13, 14).toInt()
                }
                return false
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val digit = isValidCNPJ("29.309.127/0001-79")
            print(digit)


        }
    }


}