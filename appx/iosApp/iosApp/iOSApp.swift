import SwiftUI
import shared
import Combine

@main
struct iOSApp: App {
    
    private let machine: RijksMachine = Platform().toMachine()
    @State private var isShowingDetailView = false
    
    init() {
        machine.actions.initialize()
    }
    
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ZStack {
                    NavigationLink(destination: ArtDetailsView(), isActive: $isShowingDetailView) { EmptyView() }
                    ArtCollectionView()
                    
                }
            }
            .environment(\.machine, machine)
            .onReceive(publisher(of: machine).map(\.selection).map { $0 != nil }, perform: { isShowingDetailView = $0 })
        }
    }
}
