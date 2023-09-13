import SwiftUI
import shared
import Combine

struct AppView: View {
    
    @Environment(\.machine) var machine: RijksMachine
    @State private var isShowingDetails = false
    
    var body: some View {
        NavigationView {
            ZStack {
                NavigationLink(destination: ArtDetailsView(machine: machine.details), isActive: $isShowingDetails, label: EmptyView.init).hidden()
                ArtCollectionView(machine: machine.overview)
            }
        }
        .environment(\.machine, machine)
        .onReceive(publisher(of: machine.overview).map(\.isShowingDetails), perform: { isShowingDetails = $0 })
    }
}
