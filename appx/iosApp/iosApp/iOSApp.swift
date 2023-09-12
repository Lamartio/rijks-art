import SwiftUI
import shared

@main
struct iOSApp: App {
    
    lazy var machine: RijksMachine = { RijksMachine() }()
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
