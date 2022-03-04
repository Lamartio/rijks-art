package io.lamart.aholdart.optics

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class Optics {

    private val initial = Employee("Danny",
        Company("Lamarti",
            Address("Uddel",
                Street(13, "Vossen",
                    Door.Open("welcome")
                )
            )
        )
    )

    @Test
    fun composeAndModifyAHugeState() {
        val values = mutableListOf<Employee>(initial)
        val source = sourceOf({ values.last() }, values::add)

        source
            .compose(lensOf({ this.company },
                { company -> this.copy(company = company) })) // composition allows to work with sub-states
            .compose(lensOf({ address },
                { copy(address = it) })) // same as the line above, but than in shorthand
            .compose(maskOf({ street },
                { copy(street = it) })) // mask is different than lens, since street is an optional. Meaning that setting the editing the street can fail.
            .compose(maskOf({ door }, { copy(door = it) }))
            .compose(prismOf { this as? Door.Open }) // a shorthand to work with diverging states (sealed classes, enums)
            .compose(maskOf({ message }, { copy(message = it) }))
            .modify { message -> "$message!!!" } // get, set or modify the composed state

        assertThat(values.first().company.address.street?.door?.message)
            .isEqualTo("welcome")

        assertThat(values.last().company.address.street?.door?.message)
            .isEqualTo("welcome!!!")
    }

}