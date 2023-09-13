import SwiftUI
import shared
import Combine

struct ArtDetailsView:  View {
    
    @Environment(\.machine) var machine: RijksMachine
    
    var body: some View {
        Text(machine.value.selection ?? "?")
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading: Button(
                action : { machine.actions.select(id: nil) },
                label: { Image(systemName: "arrow.left") }
            ))
    }
}
