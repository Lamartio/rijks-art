import SwiftUI
import shared
import Combine

struct AppView: View {
    
    @Environment(\.machine) var machine: RijksMachine
    @State private var isShowingDetailView = false
    
    var body: some View {
        NavigationView {
            ZStack {
                NavigationLink(destination: ArtDetailsView(machine: machine.details), isActive: $isShowingDetailView, label: EmptyView.init).hidden()
                ArtCollectionView(machine: machine.overview)
            }
        }
        .environment(\.machine, machine)
        .onReceive(publisher(of: machine).map(\.isShowingDetails), perform: { isShowingDetailView = $0 })
    }
}
