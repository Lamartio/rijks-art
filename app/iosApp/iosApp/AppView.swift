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
                    destination: DetailsView(machine: machine.details.forView),
                    isActive: $isShowingDetails,
                    label: EmptyView.init
                ).hidden()
                
                GalleryView(machine: machine.gallery.forView)
            }
        }
        .environment(\.machine, machine)
        .onReceive(publisher(of: machine.gallery.forView).map(\.isShowingDetails), perform: { isShowingDetails = $0 })
    }
}
