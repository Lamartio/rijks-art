import SwiftUI
import shared
import Combine

struct AppView: View {
    
    @Environment(\.machine) var machine: RijksMachine
    @State private var isShowingDetails = false
    
    var body: some View {
        NavigationView {
            ZStack {
                NavigationLink(
                    destination: DetailsView(machine: machine.details.viewModel),
                    isActive: $isShowingDetails,
                    label: EmptyView.init
                ).hidden()
                
                GalleryView(machine: machine.gallery.viewModel)
            }
        }
        .environment(\.machine, machine)
        .onReceive(publisher(of: machine.gallery.viewModel).map(\.isShowingDetails), perform: { isShowingDetails = $0 })
    }
}
