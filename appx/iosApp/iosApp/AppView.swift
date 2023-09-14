import SwiftUI
import shared
import Combine

struct AppView: View {
    
    @Environment(\.machine) var machine: RijksMachine
    @State private var isShowingDetails = false
    
    var body: some View {
        NavigationView {
            ZStack {
                NavigationLink(destination: ArtDetailsView(machine: machine.details.forView), isActive: $isShowingDetails, label: EmptyView.init).hidden()
                ArtCollectionView(machine: machine.gallery)
            }
        }
        .environment(\.machine, machine)
        .onReceive(publisher(of: machine.gallery).map(\.isShowingDetails), perform: { isShowingDetails = $0 })
    }
}
