import SwiftUI
import shared
import Combine

@main
struct iOSApp: App {
    
    private let machine: RijksMachine = Platform().toMachine()
    
    init() {
        machine.actions.initialize()
    }
    
    var body: some Scene {
        WindowGroup {
            AppView().environment(\.machine, machine)
        }
    }
}
